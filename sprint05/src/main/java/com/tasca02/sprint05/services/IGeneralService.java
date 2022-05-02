package com.tasca02.sprint05.services;

import java.util.List;
import java.util.Optional;


public interface IGeneralService<T> {
    
    T save(final T obj);

    Optional<T> findById(long id);

    List<T> findAll();
    
    T findByName(final String name);

    T findFirstByOrderByIdDesc();
    
}
