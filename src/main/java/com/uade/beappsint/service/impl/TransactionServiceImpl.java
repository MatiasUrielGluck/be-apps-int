package com.uade.beappsint.service.impl;

import com.uade.beappsint.dto.cart.CartDTO;
import com.uade.beappsint.dto.cart.CartItemDTO;
import com.uade.beappsint.dto.transaction.TransactionDTO;
import com.uade.beappsint.dto.transaction.TransactionItemDTO;
import com.uade.beappsint.entity.*;
import com.uade.beappsint.exception.BadRequestException;
import com.uade.beappsint.repository.TransactionItemRepository;
import com.uade.beappsint.repository.TransactionRepository;
import com.uade.beappsint.service.AuthService;
import com.uade.beappsint.service.CartService;
import com.uade.beappsint.service.EmailService;
import com.uade.beappsint.service.TransactionService;
import com.uade.beappsint.utils.CommonUtilities;
import com.uade.beappsint.utils.TransactionUtilities;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final CartService cartService;
    private final AuthService authService;
    private final TransactionRepository transactionRepository;
    private final TransactionItemRepository transactionItemRepository;
    private final EmailService emailService;

    @Value("${FRONTEND_CLIENT_URL}")
    private String frontendClientUrl;

    public void createAndSendEmailReceipt(TransactionDTO transactionDTO) {
        Customer customer = authService.getAuthenticatedCustomer();
        // Send email
        Map<String, String> replacements = new HashMap<>();
        replacements.put("transactionId", transactionDTO.getId().toString());

        StringBuilder itemsReplacement = new StringBuilder();

        DecimalFormat df = new DecimalFormat("#.##");

        for (TransactionItemDTO item : transactionDTO.getItems()) {
            itemsReplacement.append("<div class=\"element\"><p>Nombre: ").append(item.getProductName()).append(" (x").append(item.getQuantity()).append(")").append("</p>");
            itemsReplacement.append("<p>Precio unitario: ").append(item.getAmountUnitARS()).append("</p>");
            itemsReplacement.append("<p>Precio total: ").append(df.format(item.getAmountUnitARS() * item.getQuantity())).append("</p></div>");
        }

        replacements.put("items", itemsReplacement.toString());
        replacements.put("total", "<p>Total: " + df.format(transactionDTO.getAmountARS()) + "</p>");
        replacements.put("link", frontendClientUrl + "compras");

        try {
            emailService.sendEmailFromTemplate(customer.getEmail(), "Factura de la compra #" + transactionDTO.getId(), "TransactionReceipt", replacements);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public TransactionDTO createTransaction() {
        cartService.checkoutCart();
        CartDTO cartDTO = cartService.getUserCart();

        double conversionRate = TransactionUtilities.getConversionRate();

        Transaction savedTransaction = initializeTransaction(conversionRate);
        createTransactionItems(savedTransaction, cartDTO.getCartItems(), conversionRate);

        savedTransaction.setAmountARS(CommonUtilities.formatAmount(cartDTO.getTotalPrice()));
        savedTransaction.setAmountUSD(TransactionUtilities.calculateAmountUsdFromArs(cartDTO.getTotalPrice(), conversionRate));
        savedTransaction = transactionRepository.save(savedTransaction);

        cartService.clearCart();

        TransactionDTO response = getTransactionById(savedTransaction.getId());

        createAndSendEmailReceipt(response);

        return response;
    }

    @Override
    public List<TransactionDTO> getTransactions() {
        Customer customer = authService.getAuthenticatedCustomer();
        List<Transaction> transactions = transactionRepository.findAllByCustomerId(customer.getId());
        List<TransactionDTO> transactionDTOS = new ArrayList<>();

        for (Transaction transaction : transactions) {
            transactionDTOS.add(getTransactionById(transaction.getId()));
        }

        return transactionDTOS;
    }

    @Override
    public TransactionDTO getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new BadRequestException("Transaction not found"));

        List<TransactionItem> items = transactionItemRepository.findAllByTransactionId(transaction.getId());
        List<TransactionItemDTO> mappedItems = new ArrayList<>();

        for (TransactionItem item : items) {
            mappedItems.add(item.toDTO());
        }

        return TransactionDTO.builder()
                .id(transaction.getId())
                .customerInfo(transaction.getCustomer().toDto())
                .date(LocalDate.now())
                .amountUSD(transaction.getAmountUSD())
                .amountARS(transaction.getAmountARS())
                .conversionRate(transaction.getConversionRate())
                .items(mappedItems)
                .build();
    }

    private Transaction initializeTransaction(double conversionRate) {
        Transaction transaction = Transaction.builder()
                .conversionRate(conversionRate)
                .date(LocalDate.now())
                .customer(authService.getAuthenticatedCustomer())
                .build();
        return transactionRepository.save(transaction);
    }

    private void createTransactionItems(Transaction transaction, List<CartItemDTO> items, double conversionRate) {
        double amountARS;
        double amountUSD;

        for (CartItemDTO cartItemDTO : items) {
            amountARS = CommonUtilities.formatAmount(cartItemDTO.getProduct().getPrice());
            amountUSD = CommonUtilities.formatAmount(TransactionUtilities.calculateAmountUsdFromArs(amountARS, conversionRate));

            TransactionItem item = TransactionItem.builder()
                    .productId(cartItemDTO.getProduct().getId())
                    .productName(cartItemDTO.getProduct().getName())
                    .transaction(transaction)
                    .amountUnitARS(amountARS)
                    .amountUnitUSD(amountUSD)
                    .quantity(cartItemDTO.getQuantity())
                    .build();
            transactionItemRepository.save(item);
        }
    }
}
