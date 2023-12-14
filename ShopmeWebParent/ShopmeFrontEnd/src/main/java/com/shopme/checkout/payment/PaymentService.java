package com.shopme.checkout.payment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Category;
import com.shopme.common.entity.Payment;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepository payment;
	
	public List<Payment> listAll()
	{
		return (List<Payment>)payment.findAll();
	}
	
	
	public Payment save(Payment pay) {
		// TODO Auto-generated method stub
		return payment.save(pay);
	}
}
