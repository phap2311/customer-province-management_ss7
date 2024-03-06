package com.example.customerprovincemanagement.controller;

import com.example.customerprovincemanagement.model.Customer;
import com.example.customerprovincemanagement.model.Province;
import com.example.customerprovincemanagement.service.ICustomerService;
import com.example.customerprovincemanagement.service.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
    @RequestMapping("/provinces")
    public class ProvinceController {
        @Autowired
        private IProvinceService iProvinceService;

    @Autowired
    private ICustomerService iCustomerService;
        @GetMapping
        public ModelAndView listProvince() {
            ModelAndView modelAndView = new ModelAndView("/province/list");
            Iterable<Province> provinces = iProvinceService.findAll();
            modelAndView.addObject("provinces", provinces);
            return modelAndView;
        }
        @GetMapping("/create")
        public ModelAndView createForm(){
            ModelAndView modelAndView = new ModelAndView("/province/create");
            modelAndView.addObject("province", new Province());
            return modelAndView;
        }
        @PostMapping("/create")
        public String create(@ModelAttribute("province") Province province, RedirectAttributes attributes){
            iProvinceService.save(province);
            attributes.addFlashAttribute("success","create new province success");
            return "redirect: /provinces";
        }
    @GetMapping("/update/{id}")
    public ModelAndView updateForm(@PathVariable Long id) {
        Optional<Province> province = iProvinceService.findById(id);
        if (province.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/province/update");
            modelAndView.addObject("province", province.get());
            return modelAndView;
        }
        return null;
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("province") Province province,
                         RedirectAttributes redirect) {
        iProvinceService.save(province);
        redirect.addFlashAttribute("message", "Update province successfully");
        return "redirect:/provinces";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirect) {
        iCustomerService.remove(id);
        redirect.addFlashAttribute("message", "Delete customer successfully");
        return "redirect:/customers";
    }
}
