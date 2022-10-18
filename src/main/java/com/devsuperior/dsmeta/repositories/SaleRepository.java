package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {
	
	@Query("SELECT obj.seller.name, obj.sale.date "
			+ "FROM Sale obj "
			+ "INNER JOIN tb_seller ON tb_sales.seller_id = tb_seller.id "
			+ "WHERE obj.sale.date  BETWEEN ':dateMin' AND  ':dateMax' "
			+ "AND  UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :sellerName, '%')) ")
	Page<Sale> searchReport(LocalDate dateMin, LocalDate dateMax, String sellerName, Pageable pageable);
	
	/*@Query("SELECT obj.name, obj.date "
			+ "FROM Sale obj "
			+ "INNER JOIN tb_seller ON tb_sales.seller_id = tb_seller.id "
			+ "WHERE obj.date  BETWEEN ':dateMin' AND  ':dateMax' ")
	List<SaleSummaryDTO> searchSummary(LocalDate dateMin, LocalDate dateMax);
	*/
	
}
