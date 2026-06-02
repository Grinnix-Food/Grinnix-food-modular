package com.grinnix.food.payment.controller;

import com.grinnix.food.payment.dto.PaymentRequest;
import com.grinnix.food.payment.entitys.PaymentOrderEntity;
import com.grinnix.food.payment.service.PaymentService;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("payment/")
@RequiredArgsConstructor
public class PaymentController {

  private final PaymentService paymentOrderService;

  @GetMapping
  public ResponseEntity<List<PaymentOrderEntity>> listPaymentOrdes() {
    return ResponseEntity.ok(paymentOrderService.listPaymentOrders());
  }

  @PostMapping
  public ResponseEntity<Void> processPayment(
    @Validated @RequestBody PaymentRequest requestBody
  ) {
    paymentOrderService.processPayment(
      requestBody.card(),
      requestBody.paymentOrderId()
    );
    return ResponseEntity.ok().build();
  }
}
