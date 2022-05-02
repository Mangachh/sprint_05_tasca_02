package com.tasca02.sprint05.services;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.transaction.Transactional;

import com.tasca02.sprint05.Exceptions.NameExistsException;
import com.tasca02.sprint05.models.Player;
import com.tasca02.sprint05.models.Toss;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
// TODO: cambiar esto a ver
public class PlayerServiceTest {

    @Mock
    private IGeneralService<Player> repo;
    private AutoCloseable autoCloseable;
    private PlayerService service;

    @BeforeEach
    void init() {
        this.autoCloseable = MockitoAnnotations.openMocks(this);
        this.service = new PlayerService(repo);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    // crea un jugador
    @Test
    public void canSave() {
        // when
        Player player = new Player("Player_Create_1");

        try {
            service.save(player);
        } catch (NameExistsException e) {

        }
        // then
        ArgumentCaptor<Player> playerArgCaptor = ArgumentCaptor.forClass(Player.class);
        verify(repo).save(playerArgCaptor.capture());
        Player captured = playerArgCaptor.getValue();
        assertEquals(player, captured);
    }

    

    @Test
    public void canFindAll() {
        service.findAll();
        verify(repo).findAll();
    }

    @Test
    public void canFindById() {
        Player expected = new Player("Test-12");
        repo.save(expected);
        repo.findById(expected.getId());
        verify(repo).findById(expected.getId());

    }

    @Test
    public void canFindWorsePlayer() {

    }
}
