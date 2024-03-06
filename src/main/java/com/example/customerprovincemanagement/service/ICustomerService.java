package com.example.customerprovincemanagement.service;

import com.example.customerprovincemanagement.model.Customer;
import com.example.customerprovincemanagement.model.Province;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICustomerService extends IGenerateService<Customer> {
    Page<Customer> findAll(Pageable pageable);

    Iterable<Customer> findAllByProvince(Province province);
    Page<Customer> findAllByFirstNameContaining(Pageable pageable, String name);
}
