package com.tasca02.sprint05.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Tosses")
public class Toss {

    private static long ID_MAIN;
    @Id
    private Long id;
    @Column(name="dice_a")
    private byte diceA;
    @Column(name="dice_b")
    private byte diceB;


    public Toss() {

    }
    
    public Toss(byte resultA, byte resultB) {
        this.diceA = resultA;
        this.diceB = resultB;
        this.id = ID_MAIN;
        ID_MAIN++;
    }


    public byte getResultDiceA() {
        return diceA;
    }


    public void setResultDiceA(byte resultDiceA) {
        this.diceA = resultDiceA;
    }


    public byte getResultDiceB() {
        return diceB;
    }


    public void setResultDiceB(byte resultDiceB) {
        this.diceB = resultDiceB;
    }
    
    public long getId() {
        return id;
    }



    public void setId(long id) {
        this.id = id;
        if (this.id > ID_MAIN) {
            ID_MAIN = this.id;
        }
    }

    public int getSum() {
        return this.diceA + this.diceB;
    }

    @Override
    public boolean equals(final Object other) {
        if (other == null || this.getClass().isInstance(other) == false) {
            return false;
        }

        Toss otherToss = (Toss) other;
        return otherToss.getId() == this.id &&
                otherToss.getResultDiceA() == this.diceA &&
                otherToss.getResultDiceB() == this.getResultDiceB();
    }
    
    
}
