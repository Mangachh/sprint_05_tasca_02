package com.tasca02.sprint05.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.tasca02.sprint05.models.Player;
import com.tasca02.sprint05.models.Toss;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class PlayerRepoImplTest {

    @Autowired
    private PlayerRepoImpl repo;

    // crea un jugador 
    @Test
    public void createPlayer() {
        Player player = new Player("Player_1");
        Player savedPlayer = repo.save(player);

        assertEquals(player, savedPlayer);
    }
    
    // modifica el nom del jugador
    @Test
    public void modifyPlayersName() {
        String oldName = "Old_Name";
        String newName = "New_Name";

        Player player = new Player(oldName);
        repo.save(player);
        Optional<Player> savedPlayer = repo.findById(player.getId());

        assertTrue(savedPlayer.isPresent());

        savedPlayer.get().setName(newName);
        player = repo.save(savedPlayer.get());
        assertEquals(newName, player.getName());
    }

    @Test
    public void addTosses() {
        Player player = new Player("Player_Test");
        Toss tossA = new Toss((byte) 2, (byte) 5);
        player.addToss(tossA);
        Toss tossB = new Toss((byte) 2, (byte) 5);
        player.addToss(tossB);

        assertEquals(player.getTosses().size(), 2);
        assertTrue(player.getTosses().contains(tossA));
        assertTrue(player.getTosses().contains(tossB));
    }
    
    @Test
    public void deleteTosses() {
        Player player = new Player("Player_Test");
        Toss tossA = new Toss((byte) 2, (byte) 5);
        player.addToss(tossA);
        Toss tossB = new Toss((byte) 2, (byte) 5);
        player.addToss(tossB);

        // mira si han entrado bien
        assertEquals(player.getTosses().size(), 2);
        assertTrue(player.getTosses().contains(tossA));
        assertTrue(player.getTosses().contains(tossB));

        player.clearTosses();
        player = repo.save(player);
        assertEquals(player.getTosses().size(), 0);

    }
    
    @Test
    public void listOfAll() {
        int oldCount = repo.findAll().size();

        Player player1 = new Player("Player_1");
        Toss tossA = new Toss((byte) 2, (byte) 7);
        Toss tossB = new Toss((byte) 5, (byte) 2);
        player1.addToss(tossA);
        player1.addToss(tossB);

        repo.save(player1);

        Player player2 = new Player("Player_2");
        tossA = new Toss((byte) 6, (byte) 1);
        tossB = new Toss((byte) 5, (byte) 2);
        player2.addToss(tossA);
        player2.addToss(tossB);

        repo.save(player2);

        List<Player> players = repo.findAll();

        assertEquals(players.size(), oldCount + 2);
        assertTrue(players.contains(player1));
        assertTrue(players.contains(player2));
        assertEquals(50, player1.getPercentage(7));
        assertEquals(100, player2.getPercentage(7));
    }

    @Test
    public void getAllGame() {
        Player player = new Player("Player_Test");
        Toss tossA = new Toss((byte) 2, (byte) 5);
        player.addToss(tossA);
        Toss tossB = new Toss((byte) 2, (byte) 5);
        player.addToss(tossB);

        assertEquals(player.getTosses().size(), 2);
        assertTrue(player.getTosses().contains(tossA));
        assertTrue(player.getTosses().contains(tossB));
    }

    @Test
    public void getWorstPlayer() {
        Player player1 = new Player("Player_1");
        Toss tossA = new Toss((byte) 2, (byte) 7);
        Toss tossB = new Toss((byte) 5, (byte) 2);
        player1.addToss(tossA);
        player1.addToss(tossB);

        repo.save(player1);

        Player player2 = new Player("Player_2");
        tossA = new Toss((byte) 6, (byte) 1);
        tossB = new Toss((byte) 5, (byte) 2);
        player2.addToss(tossA);
        player2.addToss(tossB);

        repo.save(player2);

        Optional<Player> worst = repo.findWorsePlayer();
        assertTrue(worst.isPresent());
        assertEquals(player1, worst.get());
    }

    @Test
    public void getBestPlayer() {
        Player player1 = new Player("Player_1");
        Toss tossA = new Toss((byte) 2, (byte) 7);
        Toss tossB = new Toss((byte) 5, (byte) 2);
        player1.addToss(tossA);
        player1.addToss(tossB);

        repo.save(player1);

        Player player2 = new Player("Player_2");
        tossA = new Toss((byte) 6, (byte) 1);
        tossB = new Toss((byte) 5, (byte) 2);
        player2.addToss(tossA);
        player2.addToss(tossB);

        repo.save(player2);

        Optional<Player> best = repo.findBestPlayer();
        assertTrue(best.isPresent());
        assertEquals(player2, best.get());
    }
    
   
}
