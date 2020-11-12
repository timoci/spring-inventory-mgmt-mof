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

import com.forestry.inv.model.Orders;
import com.forestry.inv.model.repository.OrderRepository;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class OrderController {

	
	//Autowired inventoryRepository Object
	@Autowired
	OrderRepository orderRepository;


	//Need Model Object here to pass data to frontend
	@GetMapping("/order") 
	public String getOrder(Model model) {
		//fetch list of Orders
		List<Orders> listOrders =orderRepository.findAll();
		
		//Set the Model Object
		model.addAttribute("orders",listOrders);
		
		//Here Return the name of HTML file or view file
		return "order";
	}
	
	@PostMapping("/order/ordersave")
	public String saveOrder(HttpServletRequest request,Model model) {
		System.out.println("IN Orders");
		//@ModelAttribute("order") Orders order
		
		  try {
		long id=   Long.valueOf(request.getParameter("id"));
		String orderno=request.getParameter("orderno");
		String orderdate=request.getParameter("orderdate");
		String orderdescription=request.getParameter("orderdescription");
		String orderamount=request.getParameter("orderamount");
		String officer=request.getParameter("officer");	
		
			
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
		
		//fetch list of orders
		List<Orders> listOrders =orderRepository.findAll();
		
		//Set the Model Object
		model.addAttribute("orders",listOrders);
		
		//Here Return the name of HTML file or view file
		  } catch(Exception ex) {
	        	System.out.println("ex"+ex);
	        	model.addAttribute("error",ex);
	        }
		return "Orders";
	}
	
	
	//Need Model Object here to pass data to frontend
	 @RequestMapping(path = "/order/delete/{id}")
	public String deleteOrder( @PathVariable("id") Long id,Model model) {
		//fetch list of orders
        try {
				
		System.out.println("ID to delete:"+id);
		Optional<Orders> o= orderRepository.findById(id);
		
		Orders odr=o.get();
		
		orderRepository.delete(odr);
		
		List<Orders> listOrders =orderRepository.findAll();
		
		//Set the Model Object
		model.addAttribute("orders",listOrders);
		
		//Here Return the name of HTML file or view file
        }
        catch(Exception ex) {
        	System.out.println("ex"+ex);
        	model.addAttribute("error",ex);
        }
		return "Orders";
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
