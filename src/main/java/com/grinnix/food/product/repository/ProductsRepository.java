package com.grinnix.food.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grinnix.food.product.entitys.ProductsEntity;

@Repository
public interface ProductsRepository
  extends JpaRepository<ProductsEntity, Long> {}
