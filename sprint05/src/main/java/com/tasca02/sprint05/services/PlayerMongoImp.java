package com.tasca02.sprint05.services;

import java.util.List;
import java.util.Optional;

import com.tasca02.sprint05.models.Player;
import com.tasca02.sprint05.repositories.mongo.IPlayerRepoMongo;

import org.springframework.beans.factory.annotation.Autowired;


public class PlayerMongoImp implements IGeneralService<Player> {
    
    @Autowired
    IPlayerRepoMongo repo;

    @Override
    public Player save(Player obj) {
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
    public Player findByName(final String name) {
        return repo.findByName(name);
    }

    @Override
    public Player findFirstByOrderByIdDesc() {
        return repo.findFirstByOrderByIdDesc();
    }
    
}
