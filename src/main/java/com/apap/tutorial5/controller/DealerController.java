package com.apap.tutorial5.controller;

import java.util.List;
import java.util.Optional;
import java.util.Collections;
import java.util.Comparator;

import com.apap.tutorial5.model.*;
import com.apap.tutorial5.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * DealerController
 * */
@Controller
public class DealerController {
	@Autowired
	private DealerService dealerService;
	
	@Autowired
	private CarService carService;
	
	@RequestMapping("/")
	private String home(Model model) {
		model.addAttribute("title", "home");
		return "home";
	}
	
	@RequestMapping(value = "/dealer/add", method = RequestMethod.GET)
	private String add (Model model) {
		model.addAttribute("dealer",new DealerModel());
		return "addDealer";
	}
	
	@RequestMapping(value = "/dealer/add", method = RequestMethod.POST)
	private String addDealerSubmit (@ModelAttribute DealerModel dealer) {
		dealerService.addDealer(dealer);
		return "add";
	}
	
	@RequestMapping(value = "/dealer/view", method = RequestMethod.GET)
	private String viewDealer (String dealerId, Model model) {
		if(dealerService.getDealerDetailById(Long.parseLong(dealerId)).isPresent()){
			DealerModel dealer =  dealerService.getDealerDetailById(Long.parseLong(dealerId)).get();
			List<CarModel> listCar = dealer.getListCar();
			Collections.sort(listCar, comparePrice);
			model.addAttribute("viewDealer", dealer);
			model.addAttribute("car", listCar);
			model.addAttribute("title", "Dealer " + dealerId);
			
			return "view-dealer";
			
			}
		return "error";
		}	
	
	@RequestMapping (value = "/dealer/delete/{id}", method = RequestMethod.GET)
	private String deleteDealer (@PathVariable(value = "id") String id, Model model) {
		DealerModel dealer =  dealerService.getDealerDetailById(Long.parseLong(id)).get();
		dealerService.deleteDealer(dealer);
		model.addAttribute("title", " Delete Dealer");
		return "delete-dealer";
	}
	
	@RequestMapping(value = "/dealer/view-all", method = RequestMethod.GET)
	private String viewAll (Model model) {
		List<DealerModel> cars = dealerService.getAllDetailDealer();
		model.addAttribute("dealer", cars);
		model.addAttribute("title", "Informasi Dealer");
		return "view-all-dealer";
	}
	
	@RequestMapping(value = "/dealer/update/{id}", method = RequestMethod.GET)
	private String updateDealer(@PathVariable(value = "id") long id, Model model) {
		DealerModel dealer = dealerService.getDealerDetailById(id).get();
		model.addAttribute("dealer",dealer);
		model.addAttribute("title", "Update Dealer");
		return "update-dealer";
	}
	
	@RequestMapping(value = "/dealer/update/{id}", method = RequestMethod.POST)
	private String updateDealerSubmit(@PathVariable (value = "id") long id, @ModelAttribute Optional<DealerModel> dealer) {
		if(dealer.isPresent()) {
			dealerService.updateDealer(id, dealer);
			return "update";
		}
		return "error";
	}
	
	public static Comparator<CarModel> comparePrice = new Comparator<CarModel>() {
		public int compare(CarModel o1, CarModel o2) {
			Long price1 = o1.getPrice();
			Long price2 = o2.getPrice();
			return price1.compareTo(price2);
		}
	};
}
