package com.forestry.inv;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String getInventory(Model model,@RequestParam(value = "keyword", defaultValue = "de") String keyword) {
		
		
		//Here Return the name of HTML file or view file
		
		keyword=keyword.toLowerCase();
		
	    if(!keyword.equals("de")) {
	    	return getInventoryPaginated2(model,1,"id","ASC",keyword);
	    }else	
	    	 getInventoryPaginated(model,1,"id","ASC","de");
	    
		return getInventoryPaginated(model,1,"id","ASC","de");
	
	}
	
	
	public String getInventoryPaginated2(Model model,int currentPage,String sortField,String sortDirection,String keyword) {
		
		
		System.out.println("*** "+sortField+" "+sortDirection);
		 Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortField).ascending() 
				 : Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(currentPage-1, 5,sort);
		
		if(keyword!=null) {
			keyword=keyword.toLowerCase();
		}
		
		//Page<Inventory> inventoryPage= inventoryRepository.findAll(pageable);
		Page<Inventory> inventoryPage=inventoryRepository.search(keyword, pageable);
		
		long totalItems = inventoryPage.getTotalElements();
		int totalPages = inventoryPage.getTotalPages();
		
		List<Inventory> listInventorys =inventoryPage.getContent();
		
		//Set the Model Object
		model.addAttribute("inventorys",listInventorys);
		model.addAttribute("currentPage",currentPage);
		model.addAttribute("totalItems",totalItems);
		model.addAttribute("totalPages",totalPages);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDirection);
		model.addAttribute("reverseSortDir", sortDirection.equals("asc") ? "desc" : "asc");
		
		
		//Here Return the name of HTML file or view file
		return "inventory";
	}
	
	@GetMapping("/inventory/page/{pageNumber}") 
	public String getInventoryPaginated(Model model,@PathVariable("pageNumber") int currentPage,
			@RequestParam(value = "sortField", defaultValue = "id") String sortField,
			@RequestParam(value = "sortDirection", defaultValue = "ASC") String  sortDirection,
			@RequestParam(value = "keyword", defaultValue = "de") String  keyword) {
		
		 Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortField).ascending() 
				 : Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(currentPage-1, 5,sort);
		Page<Inventory> inventoryPage=null;
		
		if(keyword.equalsIgnoreCase("de")) {
		inventoryPage= inventoryRepository.findAll(pageable);
		}else {
	   inventoryPage=inventoryRepository.search(keyword, pageable);
	   System.out.println("Inventory"+inventoryPage);
		}
		
		long totalItems = inventoryPage.getTotalElements();
		int totalPages = inventoryPage.getTotalPages();
		
		List<Inventory> listInventorys =inventoryPage.getContent();
		System.out.println("Inventory"+listInventorys);
		
		//Set the Model Object
		model.addAttribute("inventorys",listInventorys);
		model.addAttribute("currentPage",currentPage);
		model.addAttribute("totalItems",totalItems);
		model.addAttribute("totalPages",totalPages);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDirection);
		model.addAttribute("reverseSortDir", sortDirection.equals("asc") ? "desc" : "asc");
		
		
		//Here Return the name of HTML file or view file
		return "inventory";
	}
	
	
	@GetMapping("/add_inventory") 
	public String addInventory(Model model) {
		
		return "add_inventory";
	}
	
	
	@PostMapping("/inventorysave")
	public String saveInventory(HttpServletRequest request, Model model) {
		System.out.println("IN Inventory");
		
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
		
		//getInventoryPaginated(model,1,"id","ASC");
		
		getInventoryPaginated(model,1,"id","ASC","de");
		
		//Here Return the name of HTML file or view file
		  } catch(Exception ex) {
	        	System.out.println("ex"+ex);
	        	model.addAttribute("error",ex);
	        }
		return "inventory";
	}
	
	
	//Need Model Object here to pass data to front end
	 @RequestMapping(path = "/delete/{id}")
	public String deleteInventory( @PathVariable("id") Long id,Model model) {
		//fetch list of inventories
        try {
				
		System.out.println("ID to delete:"+id);
		Optional<Inventory> i= inventoryRepository.findById(id);
		
		Inventory inv=i.get();
		
		inventoryRepository.delete(inv);
		
		getInventoryPaginated(model,1,"id","ASC","de");
		
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
