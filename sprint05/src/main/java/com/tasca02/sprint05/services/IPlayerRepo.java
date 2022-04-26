package com.tasca02.sprint05.services;

import com.tasca02.sprint05.models.Player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPlayerRepo extends JpaRepository<Player, Long>{
    
}
