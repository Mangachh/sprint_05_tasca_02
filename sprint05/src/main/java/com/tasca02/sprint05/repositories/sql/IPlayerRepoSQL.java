package com.tasca02.sprint05.repositories.sql;

import com.tasca02.sprint05.models.Player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;


public interface IPlayerRepoSQL extends JpaRepository<Player, Long>{
    
}
