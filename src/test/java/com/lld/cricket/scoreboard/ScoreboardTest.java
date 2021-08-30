package com.lld.cricket.scoreboard;

import com.lld.cricket.scoreboard.exception.CricketScoreBoardException;
import com.lld.cricket.scoreboard.model.Batsman;
import com.lld.cricket.scoreboard.model.Scoreboard;
import com.lld.cricket.scoreboard.model.TeamBatting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScoreboardTest {

    Scoreboard scoreboard;

    @BeforeEach
    void setup(){
        int numberOfPlayers = 5;
        List<String> playerOrder = new ArrayList<>(Arrays.asList("P1", "P2", "P3", "P4", "P5"));
        List<Batsman> batsmen = new ArrayList<>();
        for(int i = 0; i < numberOfPlayers; i++){
            batsmen.add(new Batsman(playerOrder.get(i)));
        }
        scoreboard = Scoreboard.builder()
                .numberOfPlayers(numberOfPlayers)
                .playerList(batsmen)
                .teamBatting(TeamBatting.TEAMA)
                .build();
    }

    @Test
    public void testValidateAndUpdateBallResult(){
        scoreboard.validateAndUpdateBallResult("4");
        scoreboard.validateAndUpdateBallResult("6");
        scoreboard.validateAndUpdateBallResult("W");
        scoreboard.validateAndUpdateBallResult("W");
        scoreboard.validateAndUpdateBallResult("1");
        scoreboard.validateAndUpdateBallResult("1");

        assertEquals(10, scoreboard.getPlayerList().get(0).getScore());
        assertEquals(1, scoreboard.getPlayerList().get(1).getScore());
        assertEquals(0, scoreboard.getPlayerList().get(2).getScore());
        assertEquals(1, scoreboard.getPlayerList().get(3).getScore());

        assertEquals(12, scoreboard.getCurrScore());
        assertEquals(2, scoreboard.getWicketsDown());

        scoreboard.validateAndUpdateBallResult("6");
        scoreboard.validateAndUpdateBallResult("1");
        scoreboard.validateAndUpdateBallResult("W");
        scoreboard.validateAndUpdateBallResult("W");

        assertEquals(10, scoreboard.getPlayerList().get(0).getScore());
        assertEquals(8, scoreboard.getPlayerList().get(1).getScore());
        assertEquals(0, scoreboard.getPlayerList().get(2).getScore());
        assertEquals(1, scoreboard.getPlayerList().get(3).getScore());
        assertEquals(0, scoreboard.getPlayerList().get(4).getScore());

        assertEquals(19, scoreboard.getCurrScore());
        assertEquals(4, scoreboard.getWicketsDown());
    }

    @Test
    public void testNonValidBallOutcome(){
      assertThrows(CricketScoreBoardException.class, () ->{
          scoreboard.validateAndUpdateBallResult("West");
      });
    }
}
