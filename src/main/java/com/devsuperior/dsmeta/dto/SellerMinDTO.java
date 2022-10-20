package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.entities.Seller;

public class SellerMinDTO {
	
	private Long id;
	private String SellerName;
	
	public SellerMinDTO() {
	}
	
	public SellerMinDTO(Long id, String sellerName) {
		this.id = id;
		this.SellerName = sellerName;
	}
	
	public SellerMinDTO(Seller entity) {
		this.id = entity.getId();
		this.SellerName = entity.getName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSellerName() {
		return SellerName;
	}

	public void setSellerName(String sellerName) {
		SellerName = sellerName;
	}
	
	
	
	
}
