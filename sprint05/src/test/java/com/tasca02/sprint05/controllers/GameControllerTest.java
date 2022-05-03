package com.tasca02.sprint05.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.tasca02.sprint05.models.Game;
import com.tasca02.sprint05.models.Player;
import com.tasca02.sprint05.models.Toss;
import com.tasca02.sprint05.services.PlayerService;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService service;

    @MockBean
    private Game game;

    @Test
    void testCreatePlayer() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.post("/players?name=mock_01"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("@.name").value("mock_01"));

    }

    @Test
    void testModifyPlayerName() throws Exception {
        Mockito.when(this.service.findByName("fake_name")).thenReturn(new Player("fake_name"));

        this.mockMvc.perform(MockMvcRequestBuilders.put("/players/fake_name?newName=true_name"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("@.name").value("true_name"));

    }

    @Test
    void testThrowDice() throws Exception {
        Mockito.when(this.service.findById(0)).thenReturn(Optional.of(new Player("test_mocki")));
        Mockito.when(this.game.generateToss()).thenReturn(new Toss((byte) 1, (byte) 1));

        this.mockMvc.perform(MockMvcRequestBuilders.post("/players/0/games/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("@.sum").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("@.resultDiceA").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("@.resultDiceB").value("1"));
    }

    @Test
    void testDeleteTosses() throws Exception {
        Player mockPlayer = new Player("Mock_Player");
        mockPlayer.addToss(new Toss());
        mockPlayer.addToss(new Toss());

        Mockito.when(this.service.findById(0)).thenReturn(Optional.of(mockPlayer));

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/players/0/games"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("@.tosses").isEmpty());
    }

    @Test
    void testGetAllPlayersPercentatge() throws Exception {
        this.playersAndTosses();

        this.mockMvc.perform(MockMvcRequestBuilders.get("/players/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("@.['Player 1']").value(0.0))
                .andExpect(MockMvcResultMatchers.jsonPath("@.['Player 2']").value(100.0));

    }

    @Test
    void testGetTossList() throws Exception {
        Player a = new Player("Player 1");
        a.addToss(new Toss((byte) 1, (byte) 1));
        a.addToss(new Toss((byte) 2, (byte) 2));

        Mockito.when(this.service.findById(0)).thenReturn(Optional.of(a));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/players/0/games"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("@").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("@.[0].resultDiceA").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("@.[0].resultDiceB").value(1));

    }

    @Test
    void testGetBestPlayer() throws Exception {
        Mockito.when(this.service.findBestPlayer()).thenReturn(Optional.of(new Player("Player end")));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/players/ranking/winner"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("@.name").value("Player end"));

    }

    @Test
    void testGetRanking() throws Exception {
        this.playersAndTosses();
        this.mockMvc.perform(MockMvcRequestBuilders.get("/players/ranking"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("@.[0].name").value("Player 2"));
        
    }

    @Test
    void testGetWorstPlayer() throws Exception {
        Mockito.when(this.service.findBestPlayer()).thenReturn(Optional.of(new Player("Player end")));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/players/ranking/winner"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("@.name").value("Player end"));

    }

    private void playersAndTosses() {
        Player a = new Player("Player 1");
        a.addToss(new Toss((byte) 1, (byte) 1));
        a.addToss(new Toss((byte) 2, (byte) 2));
        Player b = new Player("Player 2");
        b.addToss(new Toss((byte) 6, (byte) 1));
        b.addToss(new Toss((byte) 6, (byte) 1));

        List<Player> players = new ArrayList<>(List.of(a, b));

        Mockito.when(this.service.findAll()).thenReturn(players);
        Mockito.when(this.game.getWinningSum()).thenReturn(7);
    }

}
