package com.tasca02.sprint05.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.mongodb.core.mapping.Document;

@Entity
@Table(name = "Players")
@Document("player")
public class Player {
    // nada, lo dejamos así porque es más fácil 
    // usar el mismo perfil para todos los perficles
    private static long ID_MAIN;

    @Id
    private Long id;
    private String name;
    
    @OneToMany(
        cascade = CascadeType.MERGE,
        fetch = FetchType.EAGER)
    @JoinColumn(
        name = "player_id",
        referencedColumnName = "id"
    )
    List<Toss> tosses;

    public Player() {
    }
    
    public Player(String name) {
        this.tosses = new ArrayList<Toss>();
        this.name = name;
        this.id = ID_MAIN;
        ID_MAIN++;
    }

    public Player(String name, Long id) {
        this.tosses = new ArrayList<>();
        this.name = name;
        this.id = id;
        if (id > ID_MAIN) {
            ID_MAIN = id++;
        }
    }
    
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
        if (this.id > ID_MAIN) {
            ID_MAIN = this.id;
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTosses(List<Toss> tosses) {
        this.tosses = tosses;
    }

    public List<Toss> getTosses() {
        return this.tosses;
    }

    public void addToss(final Toss toss) {
        this.tosses.add(toss);
    }

    public void clearTosses() {
        this.tosses.clear();
    }

    public static void setIdMain(Long idMain) {
        ID_MAIN = idMain;
    }

    public double getPercentage(int winningResult) {
        if (this.tosses.size() == 0) {
            return 0f;
        }
        
        double success = this.tosses.stream().filter(t -> t.getSum() == winningResult).toArray().length;
        return (success / this.tosses.size()) * 100;
    }


    @Override
    public String toString() {
        return "Player [id=" + id + ", name=" + name + "]";
    }

    @Override
    public boolean equals(final Object other) {

        if (other == null) {
            return false;
        }

        if (this.getClass().isInstance(other) == false) {
            return false;
        }

        Player otherPlayer = (Player) other;
        return this.name.equals(otherPlayer.getName()) && this.id == otherPlayer.getId();        
    }

    @Override
    public int hashCode() {
        return this.id.intValue();
    }

    
}