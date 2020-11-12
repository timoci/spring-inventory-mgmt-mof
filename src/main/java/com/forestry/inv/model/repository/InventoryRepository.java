package com.forestry.inv.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.forestry.inv.model.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long>{

}
