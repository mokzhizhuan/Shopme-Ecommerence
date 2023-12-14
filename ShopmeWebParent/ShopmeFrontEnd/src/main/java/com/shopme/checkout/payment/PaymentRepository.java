package com.shopme.checkout.payment;

import org.springframework.data.repository.CrudRepository;

import com.shopme.common.entity.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Integer> {

}
