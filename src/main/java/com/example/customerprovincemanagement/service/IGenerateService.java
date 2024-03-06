package com.example.customerprovincemanagement.service;

import com.example.customerprovincemanagement.model.Province;

import java.util.Optional;

public interface IGenerateService<P> {
    Iterable<P> findAll();

    void save(P p);

    Optional<P> findById(Long id);

    void remove(Long id);
}
