package com.tasca02.sprint05.repositories;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.tasca02.sprint05.models.Player;
import com.tasca02.sprint05.services.IPlayerRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

@Service
public class PlayerRepoImpl {
    @Autowired
    private IPlayerRepo repo;

    public Player save(final Player player) {
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

    public Optional<Player> findByName(final String name) {
        if (name == null) {
            return Optional.empty();
        }

        List<Player> players = repo.findAll().stream().filter(p -> p.getName().equalsIgnoreCase(name)).toList();
        if (players == null || players.size() == 0) {
            return Optional.empty();
        }

        return Optional.of(players.get(0));
    }
    
    
}
