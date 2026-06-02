package com.grinnix.food.order.repositories;

import com.grinnix.food.order.entitys.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository
  extends JpaRepository<OrderItemEntity, Long> {}
