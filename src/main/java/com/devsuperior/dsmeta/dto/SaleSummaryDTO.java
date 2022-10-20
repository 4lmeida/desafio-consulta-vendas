package com.devsuperior.dsmeta.dto;

import java.util.HashMap;
import java.util.Map;

import com.devsuperior.dsmeta.entities.Sale;

public class SaleSummaryDTO {
	
	private String name;
	private Double total;
	
	
	public SaleSummaryDTO(String name, Double total) {
		this.name = name;
		this.total = total;
	}
	
	public SaleSummaryDTO(Sale entity) {
		name = entity.getSeller().getName();
		total = total(entity);
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getAmount() {
		return total;
	}

	public void setAmount(Double amount) {
		this.total = amount;
	}
	
	public Double total(Sale sale) {
		Map<String, Double> map = new HashMap<>();
		for(Sale sl  : sale.getSeller().getSales()) {
			map.put(sl.getSeller().getName(), 0.0);
		}
		
		
		for(String seller : map.keySet()) {
			double total = sale.getSeller().getSales().stream()
					.filter(s -> s.getSeller().getName().equals(seller))
					.map(s -> s.getAmount()).reduce(0.0, (x, y) -> x + y);
			map.put(seller, total);
		}
		
		
		return null ;
	}
}
