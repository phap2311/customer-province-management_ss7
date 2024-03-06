package com.example.customerprovincemanagement.controller;

import com.example.customerprovincemanagement.model.Customer;
import com.example.customerprovincemanagement.model.Province;
import com.example.customerprovincemanagement.service.ICustomerService;
import com.example.customerprovincemanagement.service.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private ICustomerService iCustomerService;

    @Autowired
    private IProvinceService iProvinceService;

    @ModelAttribute("provinces")
    public Iterable<Province> listProvinces() {
        return iProvinceService.findAll();
    }

    @GetMapping("")
    public ModelAndView listCustomer(@PageableDefault(size = 2) Pageable pageable) {
        Page<Customer>customers = iCustomerService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/customer/list");

        modelAndView.addObject("customers", customers);
        return modelAndView;
    }
    @GetMapping("/search")
    public ModelAndView listCustomersSearch(@RequestParam("search") Optional<String> search, Pageable pageable){
        Page<Customer> customers;
        if(search.isPresent()){
            customers = iCustomerService.findAllByFirstNameContaining(pageable, search.get());
        } else {
            customers = iCustomerService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/customer/list");
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }


    @GetMapping("/create")
    public ModelAndView createForm() {
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("customer") Customer customer, RedirectAttributes attributes) {
        iCustomerService.save(customer);
        attributes.addFlashAttribute("success", "create customer success");
        return "redirect:/customers";
    }
    @GetMapping("update/{id}")
    public ModelAndView updateForm(@PathVariable Long id){
        Optional<Customer>customer = iCustomerService.findById(id);
        if(customer.isPresent()){
            ModelAndView modelAndView = new ModelAndView("/customer/update");
            modelAndView.addObject("customer",customer.get());
            return modelAndView;
        }
        return null;
    }
    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("customer") Customer customer, RedirectAttributes attributes){
        iCustomerService.save(customer);
        attributes.addFlashAttribute("success", "update success");
        return "redirect:/customers";
    }

@GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        iCustomerService.remove(id);
        attributes.addFlashAttribute("message","Delete customer successfully");
        return "redirect:/customers";
}

}
