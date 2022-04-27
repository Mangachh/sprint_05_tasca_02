package com.tasca02.sprint05.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import com.tasca02.sprint05.models.Game;
import com.tasca02.sprint05.models.Player;
import com.tasca02.sprint05.models.Toss;

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
    private GameController controller;

    @Autowired
    private Game game;

    @Test
    @Transactional
    void testCreatePlayer() {
        String name = "Player_Create_Test";
        ResponseEntity<Player> playerResponse = controller.createPlayer(name);
        assertEquals(HttpStatus.OK, playerResponse.getStatusCode());
        assertEquals(name, playerResponse.getBody().getName());
    }

    @Test
    void testThrowDice() {
        ResponseEntity<Player> playerResponse = controller.createPlayer("Player_Throw_Test");
        ResponseEntity<Toss> tossA = controller.throwDice(playerResponse.getBody().getId());
        ResponseEntity<Toss> tossB = controller.throwDice(playerResponse.getBody().getId());

        assertNotNull(tossA.getBody());
        assertNotNull(tossB.getBody());
    }

    @Test
    @Transactional
    void testDeleteTosses() {
        ResponseEntity<Player> playerResponse = controller.createPlayer("Player_Test");
        Toss tossA = controller.throwDice(playerResponse.getBody().getId()).getBody();
        Toss tossB = controller.throwDice(playerResponse.getBody().getId()).getBody();

        playerResponse = controller.deleteTosses(playerResponse.getBody().getId());

        assertTrue(playerResponse.hasBody());
        assertEquals(0, playerResponse.getBody().getTosses().size());

    }

    @Test
    void testGetAllPlayersPercentatge() {
        ResponseEntity<Map<String, Double>> playerPercList = controller.getAllPlayersPercentatge();
        int oldSize;
        if (playerPercList.hasBody() == false) {
            oldSize = 0;
        } else {
            oldSize = playerPercList.getBody().size();
        }

        ResponseEntity<Player> playerResponse = controller.createPlayer("Player_Test_1");
        Toss tossA = controller.throwDice(playerResponse.getBody().getId()).getBody();
        Toss tossB = controller.throwDice(playerResponse.getBody().getId()).getBody();

        Double percentageA = playerResponse.getBody().getPercentage(game.getWinningSum());

        ResponseEntity<Player> playerResponse2 = controller.createPlayer("Player_Test_2");
        Toss tossC = controller.throwDice(playerResponse.getBody().getId()).getBody();
        Toss tossD = controller.throwDice(playerResponse.getBody().getId()).getBody();
        Double percentageB = playerResponse2.getBody().getPercentage(game.getWinningSum());

        playerPercList = controller.getAllPlayersPercentatge();

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
        ResponseEntity<Player> playerResponse = controller.createPlayer("Player_Test_1");
        controller.throwDice(playerResponse.getBody().getId()).getBody();
        controller.throwDice(playerResponse.getBody().getId()).getBody();
        controller.throwDice(playerResponse.getBody().getId()).getBody();
        controller.throwDice(playerResponse.getBody().getId()).getBody();
        controller.throwDice(playerResponse.getBody().getId()).getBody();
        controller.throwDice(playerResponse.getBody().getId()).getBody();


        Double percentageA = playerResponse.getBody().getPercentage(game.getWinningSum());

        ResponseEntity<Player> playerResponse2 = controller.createPlayer("Player_Test_2");
        Double percentageB = playerResponse2.getBody().getPercentage(game.getWinningSum());

        ResponseEntity<Player> bestPlayerAsk = controller.getBestPlayer();
        Player bestPlayer = percentageA > percentageB ? playerResponse.getBody() : playerResponse2.getBody();
        
        assertTrue(bestPlayerAsk.hasBody());
        assertEquals(bestPlayer, bestPlayerAsk.getBody());

    }

    @Test
    void testGetRanking() {
        ResponseEntity<Player> playerResponse = controller.createPlayer("Player_Test_1");
        controller.throwDice(playerResponse.getBody().getId()).getBody();
        controller.throwDice(playerResponse.getBody().getId()).getBody();

        Double percentageA = playerResponse.getBody().getPercentage(game.getWinningSum());

        ResponseEntity<Player> playerResponse2 = controller.createPlayer("Player_Test_2");
        controller.throwDice(playerResponse.getBody().getId()).getBody();
        controller.throwDice(playerResponse.getBody().getId()).getBody();
        Double percentageB = playerResponse2.getBody().getPercentage(game.getWinningSum());

        Double total = (percentageA + percentageB) / 2;
        ResponseEntity<Double> answer = controller.getRanking();

        assertTrue(answer.hasBody());
        assertEquals(total, answer.getBody());
    }

    @Test
    void testGetTossList() {
        ResponseEntity<Player> playerResponse = controller.createPlayer("Player_Test_2");
        Toss tossA = controller.throwDice(playerResponse.getBody().getId()).getBody();
        Toss tossB = controller.throwDice(playerResponse.getBody().getId()).getBody();
    
        ResponseEntity<List<Toss>> tosses = controller.getTossList(playerResponse.getBody().getId());
        assertTrue(tosses.hasBody());
        assertEquals(2, tosses.getBody().size());
        assertTrue(tosses.getBody().contains(tossA));
        assertTrue(tosses.getBody().contains(tossB));
    }

    @Test
    void testGetWorstPlayer() {
        ResponseEntity<Player> playerResponse = controller.createPlayer("Player_Test_1");
        controller.throwDice(playerResponse.getBody().getId()).getBody();
        controller.throwDice(playerResponse.getBody().getId()).getBody();
        controller.throwDice(playerResponse.getBody().getId()).getBody();
        controller.throwDice(playerResponse.getBody().getId()).getBody();
        controller.throwDice(playerResponse.getBody().getId()).getBody();
        controller.throwDice(playerResponse.getBody().getId()).getBody();


        Double percentageA = playerResponse.getBody().getPercentage(game.getWinningSum());

        ResponseEntity<Player> playerResponse2 = controller.createPlayer("Player_Test_2");
        Double percentageB = playerResponse2.getBody().getPercentage(game.getWinningSum());

        ResponseEntity<Player> bestPlayerAsk = controller.getBestPlayer();
        Player bestPlayer = percentageA < percentageB ? playerResponse.getBody() : playerResponse2.getBody();
        
        assertTrue(bestPlayerAsk.hasBody());
        assertEquals(bestPlayer, bestPlayerAsk.getBody());
    }

    @Test
    void testModifyPlayerName() {
        String oldName = "Player_test";
        String newName = "New_Name_Test";

        controller.createPlayer(oldName);
        ResponseEntity<Player> modifiedPlayer = controller.modifyPlayerName(oldName, newName);

        assertTrue(modifiedPlayer.hasBody());
        assertEquals(newName, modifiedPlayer.getBody().getName());
    }

}
