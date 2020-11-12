package com.forestry.inv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.forestry.inv.model.Inventory;
import com.forestry.inv.model.repository.InventoryRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class InventoryRestController {
	
	@Autowired
	InventoryRepository inventoryRepository;

	@GetMapping("/inventory/fetch")
	public List<Inventory> getInventory() {
		
		List<Inventory> listInventorys =inventoryRepository.findAll();
		
		return listInventorys;
	}

	@PostMapping("/inventory/save")
	public Inventory saveInventory(@RequestBody Inventory inventory) {
		
		Inventory inv=inventoryRepository.save(inventory);
		return inv;
	}

}
