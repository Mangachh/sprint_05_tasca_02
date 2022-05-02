package com.tasca02.sprint05.services;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.tasca02.sprint05.Exceptions.NameExistsException;
import com.tasca02.sprint05.models.Player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    @Autowired
    private IGeneralService<Player> repo;

    private static final String NAME_UNKNOWN = "Anonimous";

    // como metemos los id a mano, deberíamos ver cual es el último, 
    // así que vamos
    @PostConstruct
    public void init() {
        Player p = repo.findFirstByOrderByIdDesc();

        if (p != null) {
            Player.setIdMain(p.getId() + 1);
        }
    }
    

    public PlayerService() {
    }

    public PlayerService(final IGeneralService<Player> repo) {
        this.repo = repo;
    }

    public Player save(String name) {
        if (name == null || name.isEmpty()) {
            name = NAME_UNKNOWN;
        }

        Player p = new Player();
        
        p.setName(NAME_UNKNOWN.concat("_").concat(String.valueOf(p.getId())));
        repo.save(p);
        return p;
    }
   
    public Player save(final Player player) {

        Player temp = this.repo.findByName(player.getName());

        if (temp != null && player.getId() != temp.getId()) {
            throw new NameExistsException();
        }

        return repo.save(player);
    }
    

    public Optional<Player> findById(long id) {
        return repo.findById(id);
    }

    public List<Player> findAll() {
        return repo.findAll();
    }
    
    public Optional<Player> findWorsePlayer() {
        List<Player> players = repo.findAll();
        if (players == null || players.size() == 0) {
            return Optional.empty();
        }
        Comparator<Player> comparator = Comparator.comparing(p -> p.getPercentage(7));
        return Optional.of(players.stream().sorted(comparator).toList().get(0));
    }

    public Optional<Player> findBestPlayer() {
        List<Player> players = repo.findAll();
        if (players == null || players.size() == 0) {
            return Optional.empty();
        }
        Comparator<Player> comparator = Comparator.comparing(p -> p.getPercentage(7));
        return Optional.of(players.stream().sorted(comparator).toList().get(players.size() - 1));
    }

    
    public Player findByName(final String name) {
        if (name == null) {
            return null;
        }

        List<Player> players = repo.findAll().stream().filter(p -> p.getName().equalsIgnoreCase(name)).toList();
        if (players == null || players.size() == 0) {
            return null;
        }

        return players.get(0);
    }
    
    
    
}
