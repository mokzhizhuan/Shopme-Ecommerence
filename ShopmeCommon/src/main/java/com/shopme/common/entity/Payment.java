package com.shopme.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "payment")
public class Payment extends IdIdentify {
	@Enumerated(EnumType.STRING)
	@Column(name = "payment_type", length = 20)
	private PaymentType paymenttype;
	
	@Column(name="credit_car_no", length = 20)
	private String creditcarono;
	
	@Column(name="cvc", length = 3)
	private int cvc;
	

	public PaymentType getPaymenttype() {
		return paymenttype;
	}

	public void setPaymenttype(PaymentType paymenttype) {
		this.paymenttype = paymenttype;
	}

	public String getCreditcarono() {
		return creditcarono;
	}

	public void setCreditcarono(String creditcarono) {
		this.creditcarono = creditcarono;
	}

	public int getCvc() {
		return cvc;
	}

	public void setCvc(int cvc) {
		this.cvc = cvc;
	}
	
	
}
