package com.tasca02.sprint05.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import com.tasca02.sprint05.models.Player;
import com.tasca02.sprint05.services.IGeneralService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class IGeneralServiceTest {
    
    @Autowired
    private IGeneralService<Player> repo;

    @Test
    void testFindAll() {
        Player expected_1 = new Player("Player_test_1");
        repo.save(expected_1);

        Player expected_2 = new Player("Player_test_2");
        repo.save(expected_2);

        List<Player> players = repo.findAll();

        assertNotNull(players);
        assertTrue(players.size() > 0);
        assertTrue(players.contains(expected_1));
        assertTrue(players.contains(expected_2));
    }

    @Test
    void testFindById() {
        Player expected = new Player("Player_test");
        repo.save(expected);

        Optional<Player> real = repo.findById(expected.getId());

        assertTrue(real.isPresent());
        Player realPlayer = real.get();
        assertTrue(expected.equals(realPlayer));
    }

    @Test
    void testSave() {
        Player expected = new Player("Player_test");
        repo.save(expected);

        Optional<Player> real = repo.findById(expected.getId());

        assertTrue(real.isPresent());
        assertTrue(expected.equals(real.get()));
    }

    @Test
    void testFindByName() {
        Player expected = new Player("Test_name_player");
        repo.save(expected);

        Player real = repo.findByName(expected.getName());

        assertNotNull(real);
        assertTrue(expected.equals(real));

    }
}
