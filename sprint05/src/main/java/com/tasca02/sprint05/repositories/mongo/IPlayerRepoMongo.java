package com.tasca02.sprint05.repositories.mongo;

import com.tasca02.sprint05.models.Player;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface IPlayerRepoMongo extends MongoRepository<Player, Long> {
    
}
