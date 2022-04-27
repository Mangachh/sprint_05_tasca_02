package com.tasca02.sprint05;

import com.tasca02.sprint05.models.Player;
import com.tasca02.sprint05.repositories.IGeneralRepo;
import com.tasca02.sprint05.services.PlayerMongoImp;
import com.tasca02.sprint05.services.PlayerSQLImp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.tasca02.sprint05.repositories.sql")
@EnableMongoRepositories(basePackages = "com.tasca02.sprint05.repositories.mongo")
public class Config {

    @Bean
    @Profile("mysql")
    IGeneralRepo<Player> repoMysql() {
        return new PlayerSQLImp();
    }

    @Bean
    @Profile("mongo")
    IGeneralRepo<Player> repoMong() {
        return new PlayerMongoImp();
    }
}
