package com.uade.beappsint.service;

import com.uade.beappsint.dto.favs.AddFavoriteRequestDTO;
import com.uade.beappsint.dto.favs.FavoriteDTO;

import java.util.List;

public interface FavoriteService {
    FavoriteDTO addProductToFavorites(AddFavoriteRequestDTO addFavoriteRequestDTO);
    void removeProductFromFavorites(Long productId);
    void clearFavorites();
    List<FavoriteDTO> getAllFavorites();
}
