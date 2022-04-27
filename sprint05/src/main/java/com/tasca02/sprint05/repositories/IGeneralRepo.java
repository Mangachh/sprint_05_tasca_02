package com.tasca02.sprint05.repositories;

import java.util.List;
import java.util.Optional;

public interface IGeneralRepo<T> {
    
    T save(final T obj);

    Optional<T> findById(long id);

    List<T> findAll();

    
    
}
