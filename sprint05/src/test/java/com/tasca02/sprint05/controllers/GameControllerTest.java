package com.tasca02.sprint05.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import com.tasca02.sprint05.models.Game;
import com.tasca02.sprint05.models.Player;
import com.tasca02.sprint05.models.Toss;
import com.tasca02.sprint05.repositories.PlayerRepoImpl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class GameControllerTest {
    @Autowired
    private GameController playRepo;

    @Autowired
    private Game game;

    @Test
    void testCreatePlayer() {
        String name = "Player_Test";
        ResponseEntity<Player> playerResponse = playRepo.createPlayer(name);
        assertEquals(HttpStatus.OK, playerResponse.getStatusCode());
        assertEquals(name, playerResponse.getBody().getName());
    }

    @Test
    void testThrowDice() {
        ResponseEntity<Player> playerResponse = playRepo.createPlayer("Player_Test");
        ResponseEntity<Toss> tossA = playRepo.throwDice(playerResponse.getBody().getId());
        ResponseEntity<Toss> tossB = playRepo.throwDice(playerResponse.getBody().getId());

        assertNotNull(tossA.getBody());
        assertNotNull(tossB.getBody());
    }

    @Test
    void testDeleteTosses() {
        ResponseEntity<Player> playerResponse = playRepo.createPlayer("Player_Test");
        Toss tossA = playRepo.throwDice(playerResponse.getBody().getId()).getBody();
        Toss tossB = playRepo.throwDice(playerResponse.getBody().getId()).getBody();

        playerResponse = playRepo.deleteTosses(playerResponse.getBody().getId());

        assertTrue(playerResponse.hasBody());
        assertEquals(0, playerResponse.getBody().getTosses().size());

    }

    @Test
    void testGetAllPlayersPercentatge() {
        ResponseEntity<Map<String, Double>> playerPercList = playRepo.getAllPlayersPercentatge();
        int oldSize;
        if (playerPercList.hasBody() == false) {
            oldSize = 0;
        } else {
            oldSize = playerPercList.getBody().size();
        }

        ResponseEntity<Player> playerResponse = playRepo.createPlayer("Player_Test_1");
        Toss tossA = playRepo.throwDice(playerResponse.getBody().getId()).getBody();
        Toss tossB = playRepo.throwDice(playerResponse.getBody().getId()).getBody();

        Double percentageA = playerResponse.getBody().getPercentage(game.getWinningSum());

        ResponseEntity<Player> playerResponse2 = playRepo.createPlayer("Player_Test_2");
        Toss tossC = playRepo.throwDice(playerResponse.getBody().getId()).getBody();
        Toss tossD = playRepo.throwDice(playerResponse.getBody().getId()).getBody();
        Double percentageB = playerResponse2.getBody().getPercentage(game.getWinningSum());

        playerPercList = playRepo.getAllPlayersPercentatge();

        assertTrue(playerPercList.hasBody());
        assertEquals(2, playerPercList.getBody().size() - oldSize);
        assertTrue(playerPercList.getBody().containsKey(playerResponse.getBody().getName()));
        assertTrue(playerPercList.getBody().containsKey(playerResponse2.getBody().getName()));
        assertEquals(playerPercList.getBody().get(playerResponse2.getBody().getName()),
                playerResponse.getBody().getPercentage(game.getWinningSum()));
        assertEquals(playerPercList.getBody().get(playerResponse2.getBody().getName()),
                playerResponse.getBody().getPercentage(game.getWinningSum()));
    }

    @Test
    void testGetBestPlayer() {
        ResponseEntity<Player> playerResponse = playRepo.createPlayer("Player_Test_1");
        playRepo.throwDice(playerResponse.getBody().getId()).getBody();
        playRepo.throwDice(playerResponse.getBody().getId()).getBody();
        playRepo.throwDice(playerResponse.getBody().getId()).getBody();
        playRepo.throwDice(playerResponse.getBody().getId()).getBody();
        playRepo.throwDice(playerResponse.getBody().getId()).getBody();
        playRepo.throwDice(playerResponse.getBody().getId()).getBody();


        Double percentageA = playerResponse.getBody().getPercentage(game.getWinningSum());

        ResponseEntity<Player> playerResponse2 = playRepo.createPlayer("Player_Test_2");
        Double percentageB = playerResponse2.getBody().getPercentage(game.getWinningSum());

        ResponseEntity<Player> bestPlayerAsk = playRepo.getBestPlayer();
        Player bestPlayer = percentageA > percentageB ? playerResponse.getBody() : playerResponse2.getBody();
        
        assertTrue(bestPlayerAsk.hasBody());
        assertEquals(bestPlayer, bestPlayerAsk.getBody());

    }

    @Test
    void testGetRanking() {
        ResponseEntity<Player> playerResponse = playRepo.createPlayer("Player_Test_1");
        playRepo.throwDice(playerResponse.getBody().getId()).getBody();
        playRepo.throwDice(playerResponse.getBody().getId()).getBody();

        Double percentageA = playerResponse.getBody().getPercentage(game.getWinningSum());

        ResponseEntity<Player> playerResponse2 = playRepo.createPlayer("Player_Test_2");
        playRepo.throwDice(playerResponse.getBody().getId()).getBody();
        playRepo.throwDice(playerResponse.getBody().getId()).getBody();
        Double percentageB = playerResponse2.getBody().getPercentage(game.getWinningSum());

        Double total = (percentageA + percentageB) / 2;
        ResponseEntity<Double> answer = playRepo.getRanking();

        assertTrue(answer.hasBody());
        assertEquals(total, answer.getBody());
    }

    @Test
    void testGetTossList() {
        ResponseEntity<Player> playerResponse = playRepo.createPlayer("Player_Test_2");
        Toss tossA = playRepo.throwDice(playerResponse.getBody().getId()).getBody();
        Toss tossB = playRepo.throwDice(playerResponse.getBody().getId()).getBody();
    
        ResponseEntity<List<Toss>> tosses = playRepo.getTossList(playerResponse.getBody().getId());
        assertTrue(tosses.hasBody());
        assertEquals(2, tosses.getBody().size());
        assertTrue(tosses.getBody().contains(tossA));
        assertTrue(tosses.getBody().contains(tossB));
    }

    @Test
    void testGetWorstPlayer() {
        ResponseEntity<Player> playerResponse = playRepo.createPlayer("Player_Test_1");
        playRepo.throwDice(playerResponse.getBody().getId()).getBody();
        playRepo.throwDice(playerResponse.getBody().getId()).getBody();
        playRepo.throwDice(playerResponse.getBody().getId()).getBody();
        playRepo.throwDice(playerResponse.getBody().getId()).getBody();
        playRepo.throwDice(playerResponse.getBody().getId()).getBody();
        playRepo.throwDice(playerResponse.getBody().getId()).getBody();


        Double percentageA = playerResponse.getBody().getPercentage(game.getWinningSum());

        ResponseEntity<Player> playerResponse2 = playRepo.createPlayer("Player_Test_2");
        Double percentageB = playerResponse2.getBody().getPercentage(game.getWinningSum());

        ResponseEntity<Player> bestPlayerAsk = playRepo.getBestPlayer();
        Player bestPlayer = percentageA < percentageB ? playerResponse.getBody() : playerResponse2.getBody();
        
        assertTrue(bestPlayerAsk.hasBody());
        assertEquals(bestPlayer, bestPlayerAsk.getBody());
    }

    @Test
    void testModifyPlayerName() {
        String oldName = "Player_test";
        String newName = "New_Name_Test";

        playRepo.createPlayer(oldName);
        ResponseEntity<Player> modifiedPlayer = playRepo.modifyPlayerName(oldName, newName);

        assertTrue(modifiedPlayer.hasBody());
        assertEquals(newName, modifiedPlayer.getBody().getName());
    }

}
