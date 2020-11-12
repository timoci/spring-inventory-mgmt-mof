package com.forestry.inv;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forestry.inv.model.Inventory;
import com.forestry.inv.model.repository.InventoryRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class InventoryController {

	
	//Autowired inventoryRepository Object
	@Autowired
	InventoryRepository inventoryRepository;


	//Need Model Object here to pass data to frontend
	@GetMapping("/inventory") 
	public String getInventory(Model model) {
		//fetch list of inventorys
		List<Inventory> listInventorys =inventoryRepository.findAll();
		
		//Set the Model Object
		model.addAttribute("inventorys",listInventorys);
		
		//Here Return the name of HTML file or view file
		return "inventory";
	}
	
	
	
	@GetMapping("/add_inventory") 
	public String addInventory(Model model) {
		
		return "add_inventory";
	}
	
	
	@PostMapping("/inventorysave")
	public String saveInventory(HttpServletRequest request,Model model) {
		System.out.println("IN Inventory");
		//@ModelAttribute("employee") Employee employee
		
		  try {
		long id=   Long.valueOf(request.getParameter("id"));
		String description=request.getParameter("description");
		String sn=request.getParameter("sn");
		String division=request.getParameter("division");
		String category=request.getParameter("category");	
		String cost=request.getParameter("cost");
		String status=request.getParameter("status");
		String station=request.getParameter("station");
		
		
		Inventory inventory =new Inventory();
		inventory.setId(id);
		inventory.setDescription(description);
		inventory.setSn(sn);
		inventory.setDivision(division);
		inventory.setCategory(category);
		inventory.setCost(Double.valueOf(cost));
		inventory.setStatus(status);
		inventory.setStation(station);
		
		Inventory inv=inventoryRepository.save(inventory);
		
		if(inv!=null)
		System.out.println("Inventory Saved"+inv);
		
		//fetch list of inventories
		List<Inventory> listInventorys =inventoryRepository.findAll();
		
		//Set the Model Object
		model.addAttribute("inventorys",listInventorys);
		
		//Here Return the name of HTML file or view file
		  } catch(Exception ex) {
	        	System.out.println("ex"+ex);
	        	model.addAttribute("error",ex);
	        }
		return "inventory";
	}
	
	
	//Need Model Object here to pass data to frontend
	 @RequestMapping(path = "/delete/{id}")
	public String deleteInventory( @PathVariable("id") Long id,Model model) {
		//fetch list of inventories
        try {
				
		System.out.println("ID to delete:"+id);
		Optional<Inventory> i= inventoryRepository.findById(id);
		
		Inventory inv=i.get();
		
		inventoryRepository.delete(inv);
		
		List<Inventory> listInventorys =inventoryRepository.findAll();
		
		//Set the Model Object
		model.addAttribute("inventorys",listInventorys);
		
		//Here Return the name of HTML file or view file
        }
        catch(Exception ex) {
        	System.out.println("ex"+ex);
        	model.addAttribute("error",ex);
        }
		return "inventory";
	}

		//Need Model Object here to pass data to frontend
	 @RequestMapping(path = "/edit/{id}")
	public String editInventory(@PathVariable("id") Long id,Model model) {
		//fetch list of inventories
        try {
				
		System.out.println("ID to Edit:"+id);
		Optional<Inventory> i= inventoryRepository.findById(id);
		
		Inventory inv=i.get();
		
		
		//Set the Model Object
		model.addAttribute("inventory",inv);
		
		//Here Return the name of HTML file or view file
        }
        catch(Exception ex) {
        	System.out.println("ex"+ex);
        	model.addAttribute("error",ex);
        }
		return "edit_inventory";
		//return "inventory";
	}

	
}
