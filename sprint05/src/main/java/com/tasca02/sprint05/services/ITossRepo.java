package com.tasca02.sprint05.services;

import com.tasca02.sprint05.models.Toss;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITossRepo extends JpaRepository<Toss, Long> {
    
}
