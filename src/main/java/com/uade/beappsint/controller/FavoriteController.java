package com.uade.beappsint.controller;

import com.uade.beappsint.dto.favs.AddFavoriteRequestDTO;
import com.uade.beappsint.dto.favs.FavoriteDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FavoriteController {
    ResponseEntity<FavoriteDTO> addProductToFavorites(AddFavoriteRequestDTO addFavoriteRequestDTO);
    ResponseEntity<Void> removeProductFromFavorites(Long productId);
    ResponseEntity<Void> clearFavorites();
    ResponseEntity<List<FavoriteDTO>> getAllFavorites();
}
