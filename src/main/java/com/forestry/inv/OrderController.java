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

import com.forestry.inv.model.Orders;
import com.forestry.inv.model.repository.OrderRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class OrderController {

	@Autowired
	OrderRepository orderRepository;


	//Need Model Object here to pass data to frontend
	@GetMapping("/order") 
	public String getOrder(Model model) {
		
		//Here Return the name of HTML file or view file
		return getOrderPaginated(model,1,"id","ASC");
	}
	
	@GetMapping("/order/page/{pageNumber}") 
	public String getOrderPaginated(Model model,@PathVariable("pageNumber") int currentPage,
			@RequestParam(value = "sortField", defaultValue = "id") String sortField,
			@RequestParam(value = "sortDirection", defaultValue = "ASC") String  sortDirection) {
		
		 Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortField).ascending() 
				 : Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(currentPage-1, 5,sort);
		Page<Orders> orderPage= orderRepository.findAll(pageable);
		
		long totalItems = orderPage.getTotalElements();
		int totalPages = orderPage.getTotalPages();
		
		List<Orders> listOrders =orderPage.getContent();
		
		//Set the Model Object
		model.addAttribute("orders",listOrders);
		model.addAttribute("currentPage",currentPage);
		model.addAttribute("totalItems",totalItems);
		model.addAttribute("totalPages",totalPages);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDirection);
		model.addAttribute("reverseSortDir", sortDirection.equals("asc") ? "desc" : "asc");
		
		
		//Here Return the name of HTML file or view file
		return "order";
	}
	
	
	@GetMapping("/add_order") 
	public String addOrder(Model model) {
	
		return "add_order";
	}
	
	
	@PostMapping("/order/ordersave")
	public String saveOrder(HttpServletRequest request,Model model) {
		System.out.println("IN Orders");
		
		  try {
			  long id=   Long.valueOf(request.getParameter("id"));
		String orderno=request.getParameter("orderno");
	
		String orderdescription=request.getParameter("orderdescription");
		String orderamount=request.getParameter("orderamount");
		String officer=request.getParameter("officer");	
		String orderdate=request.getParameter("orderdate");
			
		Orders orders =new Orders();
		orders.setId(id);
		orders.setOrderno(orderno);
		orders.setOrderdate(orderdate);
		orders.setOrderdescription(orderdescription);
		orders.setOrderamount(Double.valueOf(orderamount));
		orders.setOfficer(officer);
		
		Orders odr=orderRepository.save(orders);
		
		if(odr!=null)
		System.out.println("Orders Saved"+odr);
		
		getOrderPaginated(model,1,"id","ASC");
		
		//Here Return the name of HTML file or view file
		  } catch(Exception ex) {
	        	System.out.println("ex"+ex);
	        	model.addAttribute("error",ex);
	        }
		return "order";	}
	

	
	//Need Model Object here to pass data to front end
	 @RequestMapping(path = "/order/delete/{id}")
	public String deleteOrder( @PathVariable("id") Long id,Model model) {

		 try {
				
		System.out.println("ID to delete:"+id);
		Optional<Orders> o= orderRepository.findById(id);
		
		Orders odr=o.get();
		
		orderRepository.delete(odr);
		
		getOrderPaginated(model,1,"id","ASC");
		
		//Here Return the name of HTML file or view file
       }
       catch(Exception ex) {
       	System.out.println("ex"+ex);
       	model.addAttribute("error",ex);
       }
		return "order";
	}
	
	 
	 
	 
		//Need Model Object here to pass data to frontend
	 @RequestMapping(path = "/order/edit/{id}")
	public String editOrder(@PathVariable("id") Long id,Model model) {
		//fetch list of inventories
        try {
				
		System.out.println("ID to Edit:"+id);
		Optional<Orders> o= orderRepository.findById(id);
		
		Orders odr=o.get();
		
		
		//Set the Model Object
		model.addAttribute("order",odr);
		
		//Here Return the name of HTML file or view file
        }
        catch(Exception ex) {
        	System.out.println("ex"+ex);
        	model.addAttribute("error",ex);
        }
		return "edit_order";
		//return "inventory";
	}

	
}
