package com.example.Online_Market.service;

import java.util.List;
import java.util.Optional;

public interface BaseService<T1, T2>{
    void save(T1 entity);
    boolean deleteByEmail(String email);
    Optional<List<T2>> getAll();
    Optional<T2> getByEmail(String email);
}
