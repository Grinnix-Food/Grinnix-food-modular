package com.grinnix.food.payment.service;

import java.util.Random;
import org.springframework.stereotype.Service;

import com.grinnix.food.payment.entitys.PaymentOrderEntity;

@Service
public class PaymentHookService {

  public boolean pay(String card, PaymentOrderEntity order) {
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    }

    Integer lastNumberOfCard = Integer.parseInt(
      card.substring(card.length() - 1)
    );
    Integer randomSeed = lastNumberOfCard;
    Random generate = new Random(randomSeed);

    return generate.nextBoolean();
  }
}
