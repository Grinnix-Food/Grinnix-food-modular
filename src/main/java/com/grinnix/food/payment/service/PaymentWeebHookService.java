package com.grinnix.food.payment.service;

import com.grinnix.food.payment.entitys.PaymentOrderEntity;
import org.springframework.stereotype.Service;

@Service
public class PaymentWeebHookService {

  public boolean pay(String card, PaymentOrderEntity order) {
    return true;
  }
}
