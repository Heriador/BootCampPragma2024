package com.bootcamp2024.StockMicroservice.infrastructure.output.mysql.repository;

import com.bootcamp2024.StockMicroservice.infrastructure.output.mysql.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IBrandRepository extends JpaRepository<BrandEntity, Long> {
    Optional<BrandEntity> findByName(String brandName);
}
