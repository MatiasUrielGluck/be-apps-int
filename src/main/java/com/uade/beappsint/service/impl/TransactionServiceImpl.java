package com.uade.beappsint.service.impl;

import com.uade.beappsint.dto.transaction.TransactionDTO;
import com.uade.beappsint.entity.Product;
import com.uade.beappsint.entity.Transaction;
import com.uade.beappsint.exception.BadRequestException;
import com.uade.beappsint.repository.TransactionRepository;
import com.uade.beappsint.service.AuthService;
import com.uade.beappsint.service.CartService;
import com.uade.beappsint.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

/**
 * Implementation of the TransactionService interface.
 * Provides methods for handling transaction-related operations.
 */
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final CartService cartService;
    private final AuthService authService;
    private final TransactionRepository transactionRepository;

    /**
     * Calculates the total amount in ARS for a list of products.
     *
     * @param products the list of products
     * @return the total amount in ARS
     */
    private double calculateTotalAmountArs(List<Product> products) {
        double result = 0.0;

        for (Product product : products) {
            result += product.getPrice();
        }

        return result;
    }

    /**
     * Retrieves the conversion rate from ARS to USD.
     * This method emulates the value of the dollar and should call an external service to get the real value.
     *
     * @return the conversion rate from ARS to USD
     */
    private double getConversionRate() {
        // TODO: Emulamos el valor del dolar. Debería realizarse un llamado a un servicio externo para obtener el valor real.
        Random random = new Random();
        int min = 1200;
        int max = 1500;
        return random.nextInt((max - min) + 1) + min;
    }

    /**
     * Converts an amount in ARS to USD using the conversion rate.
     *
     * @param amountArs the amount in ARS
     * @return the equivalent amount in USD
     */
    private double getAmountUsdFromAmountArs(double amountArs) {
        return amountArs / getConversionRate();
    }

    /**
     * Initializes a new transaction based on the products in the user's cart.
     *
     * @return the initialized transaction data transfer object
     * @throws BadRequestException if the cart is empty
     */
    @Override
    public TransactionDTO initializeTransaction() {
        List<Product> products = cartService.getUserCart().getProducts();
        if (products.isEmpty()) throw new BadRequestException("Empty cart cannot create transaction");

        double amountARS = calculateTotalAmountArs(products);
        double amountUSD = getAmountUsdFromAmountArs(amountARS);

        Transaction transaction = Transaction.builder()
                .amountARS(amountARS)
                .amountUSD(amountUSD)
                .conversionRate(getConversionRate())
                .date(LocalDate.now())
                .customer(authService.getAuthenticatedCustomer())
                .products(products)
                .paid(false)
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);
        return savedTransaction.toDTO();
    }

    /**
     * Confirms a transaction by its ID.
     *
     * @param transactionId the ID of the transaction to confirm
     * @return the confirmed transaction data transfer object
     */
    @Override
    public TransactionDTO confirmTransaction(Long transactionId) {
        return null;
    }
}
