package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}
	
	public Page<SaleReportDTO> getReport(String dateMin, String dateMax, String sellerName, Pageable pageable) {
		LocalDate min = convertMinDate(dateMin);
		LocalDate max = convertMaxDate(dateMax);
		
		Page<Sale> result = repository.searchReport(min, max, sellerName, pageable);
		return result.map(x -> new SaleReportDTO(x));
	}
	
	public List<SaleSummaryDTO> getSummary(String dateMin, String dateMax) {
		LocalDate min = convertMinDate(dateMin);
		LocalDate max = convertMaxDate(dateMax);

		List<Sale> result = repository.searchSummary(min, max);
		
		return summarySeller(result);
	}
	
	
	private LocalDate convertMaxDate(String dateMax) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate max = ("".equals(dateMax)) ? today : LocalDate.parse(dateMax);
		return max;
	}
	
	private LocalDate convertMinDate(String dateMin) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate overYear = today.minusYears(1L);
		LocalDate min = ("".equals(dateMin)) ? overYear : LocalDate.parse(dateMin); 
		return min;
	}
	
	public List<SaleSummaryDTO> summarySeller(List<Sale> seller) {
		
		List<SaleSummaryDTO> list = new ArrayList<>();
		
		Map<String, Double> map = new TreeMap<>();
		for(Sale sl  : seller) {
			map.put(sl.getSeller().getName(), 0.0);
		}
			
		for(String sl : map.keySet()) {
			double total = seller.stream()
					.filter(s -> s.getSeller().getName().equals(sl))
					.map(s -> s.getAmount()).reduce(0.0, (x, y) -> x + y);
			
			list.add(new SaleSummaryDTO(sl, total));
		}
		
		return list;
	}
	
}
