package com.tasca02.sprint05.repositories.sql;


import com.tasca02.sprint05.models.Player;
import com.tasca02.sprint05.repositories.ITestGeneral;

import org.springframework.data.jpa.repository.JpaRepository;


public interface IPlayerRepoSQL extends JpaRepository<Player, Long>, ITestGeneral<Player>{
}
