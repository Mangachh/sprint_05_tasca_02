package com.tasca02.sprint05.services;

import java.util.List;
import java.util.Optional;

import com.tasca02.sprint05.models.Player;
import com.tasca02.sprint05.repositories.mongo.IPlayerRepoMongo;
import com.tasca02.sprint05.repositories.sql.IPlayerRepoSQL;

import org.springframework.beans.factory.annotation.Autowired;

public class PlayerFullImp implements IGeneralService<Player>{
    
    @Autowired
    private IPlayerRepoMongo mongoRepo;

    @Autowired 
    private IPlayerRepoSQL sqlRepo;


    @Override
    public Player save(Player obj) {
        mongoRepo.save(obj);
        return sqlRepo.save(obj);
    }

    @Override
    public Optional<Player> findById(long id) {
        return sqlRepo.findById(id);
    }

    @Override
    public List<Player> findAll() {
        return mongoRepo.findAll();
    }

    @Override
    public Player findByName(String name) {
        return mongoRepo.findByName(name);
    }

    @Override
    public Player findFirstByOrderByIdDesc() {
        return sqlRepo.findFirstByOrderByIdDesc();
    }


}
