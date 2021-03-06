package com.tasca02.sprint05.services;

import java.util.List;
import java.util.Optional;

import com.tasca02.sprint05.models.Player;
import com.tasca02.sprint05.repositories.sql.IPlayerRepoSQL;

import org.springframework.beans.factory.annotation.Autowired;


public class PlayerSQLImp implements IGeneralService<Player> {

    @Autowired
    IPlayerRepoSQL repo;

    @Override
    public Player save(final Player obj) {
        return repo.save(obj);
    }
    
    @Override
    public Optional<Player> findById(long id) {
        return repo.findById(id);
    }

    @Override
    public List<Player> findAll() {
        return repo.findAll();
    }

    @Override
    public Player findByName(String name) {
        return repo.findByName(name);
    }
    
    @Override
    public Player findFirstByOrderByIdDesc() {
        return repo.findFirstByOrderByIdDesc();
    }
    
}
