package com.forestry.inv.model.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.forestry.inv.model.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long>{

	@Query("SELECT i FROM Inventory i WHERE lower(CONCAT(i.id, ' ', i.description, ' ', i.sn, ' ', i.division, ' ', i.category, ' ', i.status, ' ', i.station, ' ', i.cost)) LIKE %:keyword%")
	public Page<Inventory> search(@Param("keyword") String keyword,Pageable p);
}

