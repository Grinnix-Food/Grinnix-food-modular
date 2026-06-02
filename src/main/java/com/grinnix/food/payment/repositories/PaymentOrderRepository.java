package com.grinnix.food.payment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grinnix.food.payment.entitys.PaymentOrderEntity;

public interface PaymentOrderRepository
    extends JpaRepository<PaymentOrderEntity, Long> {
}