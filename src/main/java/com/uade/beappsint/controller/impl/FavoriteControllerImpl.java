package com.uade.beappsint.controller.impl;

import com.uade.beappsint.controller.FavoriteController;
import com.uade.beappsint.dto.favs.AddFavoriteRequestDTO;
import com.uade.beappsint.dto.favs.FavoriteDTO;
import com.uade.beappsint.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteControllerImpl implements FavoriteController {
    private final FavoriteService favoriteService;

    @PostMapping("/add")
    public ResponseEntity<FavoriteDTO> addProductToFavorites(@RequestBody AddFavoriteRequestDTO addFavoriteRequestDTO) {
        return ResponseEntity.ok(favoriteService.addProductToFavorites(addFavoriteRequestDTO));
    }
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Void> removeProductFromFavorites(@PathVariable Long productId) {
        favoriteService.removeProductFromFavorites(productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearFavorites() {
        favoriteService.clearFavorites();
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<FavoriteDTO>> getAllFavorites() {
        return ResponseEntity.ok(favoriteService.getAllFavorites());
    }
}