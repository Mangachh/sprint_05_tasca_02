package com.tasca02.sprint05.repositories;

import com.tasca02.sprint05.services.ITossRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TossRepoImpl {
    @Autowired
    private ITossRepo repo;
}
