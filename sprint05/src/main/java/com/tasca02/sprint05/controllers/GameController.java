package com.tasca02.sprint05.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.tasca02.sprint05.models.Game;
import com.tasca02.sprint05.models.Player;
import com.tasca02.sprint05.models.Toss;
import com.tasca02.sprint05.services.PlayerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    /*
     * (test 1 = component, test 2 = integracio)
     */
    @Autowired
    private PlayerService playRepo;

    @Autowired
    private Game game;

    private static long anon_id = 0;
    private final static String ANON_NAME = "Anonimous_";

    @PostConstruct
    private void init() {
        //game = new Game();

        List<String> names = playRepo.findAll().stream().map(p -> p.getName()).filter(n -> n.contains(ANON_NAME))
                .toList();
        long tempId = 0;
        for (String name : names) {
            try {
                tempId = Long.parseLong(name.split(ANON_NAME)[0]);

                if (tempId > anon_id) {
                    anon_id = tempId;
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /** POST: /players : crea un jugador (test 1) */
    @PostMapping("/players")
    public ResponseEntity<Player> createPlayer(@RequestParam(name = "name", required = false) String name) {
        if (name == null) {
            name = ANON_NAME.concat(String.valueOf(anon_id));
            anon_id++;
        }

        Optional<Player> testPlayer = playRepo.findByName(name);

        if (testPlayer.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).header("Error", "The name already Exists").body(null);
        }

        Player newPlayer = new Player(name);
        playRepo.save(newPlayer);
        return ResponseEntity.ok(newPlayer);
    }

    /* PUT /players : modifica el nom del jugador (test 1) */
    @PutMapping("players/{name}")
    public ResponseEntity<Player> modifyPlayerName(@PathVariable(value = "name") final String name,
            @RequestParam(name = "newName", required = false) final String newName) {

        if (name == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Error", "No player name supplied").body(null);
        }

        if (newName == null) {
            return ResponseEntity.badRequest().header("Error", "No new name supplied").body(null);
        }
        Optional<Player> player = playRepo.findByName(name);

        if (player.isPresent() == false) {
            this.noPlayerFound(name);
        }

        player.get().setName(newName);
        playRepo.save(player.get());
        return ResponseEntity.ok(player.get());
    }

    /*
     * POST /players/{id}/games/ : un jugador específic realitza una tirada dels
     * daus. (test 1)
     */
    @PostMapping("/players{id}/games")
    public ResponseEntity<Toss> throwDice(@PathVariable(name = "id") final Long id) {
        if (id == null) {
            this.noIdSupplied();
        }

        Optional<Player> player = playRepo.findById(id);

        if (player.isPresent() == false) {
            this.noPlayerFound(id);
        }

        Toss toss = game.generateToss();
        player.get().addToss(toss);
        playRepo.save(player.get());
        return ResponseEntity.ok(toss);
    }

    /* DELETE /players/{id}/games: elimina les tirades del jugador (test 1) */
    @DeleteMapping("/players/{id}/games")
    public ResponseEntity<Player> deleteTosses(@PathVariable(name = "id") final Long id) {
        if (id == null) {
            this.noIdSupplied();
        }

        Optional<Player> player = playRepo.findById(id);

        if (player.isPresent() == false) {
            this.noPlayerFound(id);
        }

        player.get().clearTosses();
        playRepo.save(player.get());

        return ResponseEntity.ok(player.get());
    }

    /*
     * GET /players/: retorna el llistat de tots els jugadors del sistema amb el seu
     * percentatge mig d’èxits (Test 1)
     */
    @GetMapping("/players/")
    public ResponseEntity<Map<String, Double>> getAllPlayersPercentatge() {
        List<Player> players = playRepo.findAll();

        if (players == null || players.size() == 0) {
            this.noPlayersFound();
        }
        Map<String, Double> results = players.stream()
                .collect(Collectors.toMap(Player::getName, p -> p.getPercentage(game.getWinningSum())));

        return ResponseEntity.ok(results);
    }

    /*
     * GET /players/{id}/games: retorna el llistat de jugades per un jugador. (Test
     * 1)
     */
    @GetMapping(value = "/players/{id}/games")
    public ResponseEntity<List<Toss>> getTossList(@PathVariable(name = "id") final Long id) {

        if (id == null) {
            return ResponseEntity.badRequest().header("Error", "No id supplied").body(null);
        }

        Optional<Player> player = playRepo.findById(id);
        if (player.isPresent() == false) {
            this.noPlayerFound(id);
        }

        return ResponseEntity.ok(player.get().getTosses());
    }

    /* GET /players/ranking: retorna el ranking mig de tots els jugadors del sistema
     * . És a dir, el percentatge mig d’èxits.*/
    @GetMapping("/players/ranking")
    public ResponseEntity<Double> getRanking() {
        List<Player> players = playRepo.findAll();
        if (players == null || players.size() == 0) {
            this.noPlayersFound();
        }
        
        Double percSum = players.stream().map(p -> p.getPercentage(this.game.getWinningSum()))
                .collect(Collectors.summingDouble(Double::doubleValue));

        return ResponseEntity.ok(percSum / players.size());
    }


    /* GET /players/ranking/loser: retorna el jugador amb pitjor percentatge d’èxit */
    @GetMapping("/players/ranking/loser")
    public ResponseEntity<Player> getWorstPlayer() {
        Optional<Player> player = playRepo.findWorsePlayer();

        if (player.isPresent() == false) {
            this.noPlayersFound();
        }

        return ResponseEntity.ok(player.get());
    }    

    /* GET /players/ranking/winner: retorna el jugador amb pitjor percentatge d’èxit */
    @GetMapping("/players/ranking/winner")
    public ResponseEntity<Player> getBestPlayer() {
        Optional<Player> player = playRepo.findBestPlayer();

        if (player.isPresent() == false) {
            this.noPlayersFound();
        }

        return ResponseEntity.ok(player.get());
    }


    private <T> ResponseEntity<T> noIdSupplied() {
        return ResponseEntity.badRequest().header("Error", "No id supplied").body(null);
    }

    private <T> ResponseEntity<T> noPlayerFound(final Long id) {
        return ResponseEntity.badRequest().header("error", "No player found by id: " + String.valueOf(id))
                .body(null);
    }

    private <T> ResponseEntity<T> noPlayerFound(final String name) {
        return ResponseEntity.badRequest().header("error", "No player found by name: " + name).body(null);
    }

    private <T> ResponseEntity<T> noPlayersFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Error", "No players found").body(null);
    }
}
