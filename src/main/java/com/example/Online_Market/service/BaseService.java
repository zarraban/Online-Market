package com.example.Online_Market.service;

import javassist.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface BaseService<T1, T2>{
    T2 save(T1 entity) throws NotFoundException;
    boolean deleteByEmail(String email);
    Optional<List<T2>> getAll();
    Optional<T2> getByEmail(String email);
}
