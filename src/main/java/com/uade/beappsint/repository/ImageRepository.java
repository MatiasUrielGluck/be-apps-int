package com.uade.beappsint.repository;

import com.uade.beappsint.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}