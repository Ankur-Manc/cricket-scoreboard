package com.lld.cricket.scoreboard.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Batsman extends Player {
    private Integer score = 0;
    private Integer numberOf4s = 0;
    private Integer numberOf6s = 0;
    private Integer numberOfBallsFaced = 0;

    public Batsman(String name){
        super(name);
    }

    public void updateScore(Integer score){
        this.setScore(this.getScore() + score);
    }

    public void incrementNumberOf4s(){
        this.setNumberOf4s(this.getNumberOf4s() + 1);
    }

    public void incrementNumberOf6s(){
        this.setNumberOf6s(this.getNumberOf6s() + 1);
    }

    public void incrementNumberOfBallsFaced(){
        this.setNumberOfBallsFaced(this.getNumberOfBallsFaced() + 1);
    }

    private void setScore(Integer score){
        this.score = score;
    }

    private void setNumberOf4s(Integer numberOf4s){
        this.numberOf4s = numberOf4s;
    }

    private void setNumberOf6s(Integer numberOf6s){
        this.numberOf6s = numberOf6s;
    }

    private void setNumberOfBallsFaced(Integer numberOfBallsFaced){
        this.numberOfBallsFaced = numberOfBallsFaced;
    }

}
