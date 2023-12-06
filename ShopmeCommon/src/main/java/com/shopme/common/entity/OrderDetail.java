package com.shopme.common.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_details")
public class OrderDetail  {
	private int quantity;
	private float productCost;
	private float shippingCost;
	private float unitPrice;
	private float subtotal;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Products product;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
	
	public OrderDetail() {
	}

	public OrderDetail(String categoryName, int quantity, float productCost, float shippingCost, float subtotal) {
		this.product = new Products();
		this.product.setCategory(new Category(categoryName));
		this.quantity = quantity;
		this.productCost = productCost * quantity;
		this.shippingCost = shippingCost;
		this.subtotal = subtotal;
	}
	
	public OrderDetail(int quantity, String productName, float productCost, float shippingCost, float subtotal) {
		this.product = new Products(productName);
		this.quantity = quantity;
		this.productCost = productCost * quantity;
		this.shippingCost = shippingCost;
		this.subtotal = subtotal;
	}	

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getProductCost() {
		return productCost;
	}

	public void setProductCost(float productCost) {
		this.productCost = productCost;
	}

	public float getShippingCost() {
		return shippingCost;
	}

	public void setShippingCost(float shippingCost) {
		this.shippingCost = shippingCost;
	}

	public float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public float getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}

	public Products getProduct() {
		return product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
