package com.lld.cricket.scoreboard.model;

import com.lld.cricket.scoreboard.exception.CricketScoreBoardException;
import com.lld.cricket.scoreboard.exception.ExceptionType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Builder
public class Scoreboard {
    private final TeamBatting teamBatting;
    private final List<Batsman> playerList;
    private final Integer numberOfPlayers;
    private final Integer numberOfOvers;
    @Builder.Default
    private Integer currScore = 0;
    @Builder.Default
    private Integer ballsBowled = 0;
    @Builder.Default
    private Integer wicketsDown = 0;
    @Builder.Default
    private Boolean inningsOver = false;
    @Builder.Default
    private Integer strikerPlayer = 0;
    @Builder.Default
    private Integer nonStrikerPlayer = 1;

    private void strikeChange(){
        int onStrikePlayer = strikerPlayer;
        this.strikerPlayer = nonStrikerPlayer;
        this.nonStrikerPlayer = onStrikePlayer;
    }

    private void updateBallsBowled() {
        this.ballsBowled += 1;
    }

    public boolean validateAndUpdateBallResult(String ballResult){
        if(ballResult.equals("Wd") || ballResult.equals("Nb")){
            this.currScore++;
            return false;
        }
        playerList.get(strikerPlayer).incrementNumberOfBallsFaced();
        System.out.println("Striker: " + this.strikerPlayer + ", non: " + this.nonStrikerPlayer);
        this.updateBallsBowled();
        switch(ballResult) {
            case "W":
                wicketsDown++;
                if(wicketsDown == numberOfPlayers - 1){
                    inningsOver = true;
                } else {
                    strikerPlayer = Math.max(strikerPlayer, nonStrikerPlayer) + 1;
                }
                break;
            case "1":
            case "3":
                int runsOnBall = Integer.valueOf(ballResult);
                currScore += runsOnBall;
                playerList.get(strikerPlayer).updateScore(runsOnBall);
                this.strikeChange();
                break;
            case "2":;
                currScore += 2;
                playerList.get(strikerPlayer).updateScore(2);
                break;
            case "4":;
                currScore += 4;
                playerList.get(strikerPlayer).updateScore(4);
                playerList.get(strikerPlayer).incrementNumberOf4s();
                break;
            case "6":;
                currScore += 6;
                playerList.get(strikerPlayer).updateScore(6);
                playerList.get(strikerPlayer).incrementNumberOf6s();
                break;
            default:
                throw new CricketScoreBoardException(ExceptionType.INVALID_BALL_OUTCOME, "invalid ball result:" +
                        ballResult);
        }

        if(this.getBallsBowled() %6 == 0){
            this.strikeChange();
        }

        return true;
    }

    public void showBatsmanScoreBoard() {
        System.out.println("Scorecard for Team "+ teamBatting.getValue() + ":");
        System.out.println("Player Name" + "   " + "Score" + "    " + "4s" +  "  " + "6s" + "  " + "Balls");
        playerList.forEach(player -> {
            System.out.println(player.getName() + "              " + player.getScore() + "   " + player.getNumberOf4s() + "   " +
                    player.getNumberOf6s() + "   " + player.getNumberOfBallsFaced());
        });
        System.out.println("Total: " + currScore + "/" + wicketsDown);
        if(this.getBallsBowled() % 6 == 0){
            System.out.println("Overs: " + this.getBallsBowled()/6);
        } else {
            int oversBowled = this.getBallsBowled() / 6;
            int ballsBowledForCurrOver = this.getBallsBowled() % 6;
            System.out.println("Overs: " + oversBowled + "." + ballsBowledForCurrOver);
         }
        System.out.println();
        System.out.println();
    }
}
