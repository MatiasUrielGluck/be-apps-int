package com.uade.beappsint.service.impl;

import com.uade.beappsint.dto.favs.AddFavoriteRequestDTO;
import com.uade.beappsint.dto.favs.FavoriteDTO;
import com.uade.beappsint.service.FavoriteService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Override
    public FavoriteDTO addProductToFavorites(AddFavoriteRequestDTO addFavoriteRequestDTO) {
        // Lógica para añadir el producto a favoritos
        return new FavoriteDTO();
    }

    @Override
    public void removeProductFromFavorites(Long productId) {
        // Lógica para eliminar el producto de favoritos
    }

    @Override
    public void clearFavorites() {
        // Lógica para limpiar todos los productos de favoritos
    }

    @Override
    public List<FavoriteDTO> getAllFavorites() {
        // Lógica para obtener todos los productos de favoritos
        return new ArrayList<>();
    }
}