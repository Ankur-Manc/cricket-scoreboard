package com.lld.cricket.scoreboard;

import com.lld.cricket.scoreboard.model.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class ScoreboardApplication {

	private static Scoreboard getScoreBoardForTeam(Integer numberOfPlayers, List<String> playerOrder,
												   TeamBatting teamBatting){
		List<Batsman> batsmen = new ArrayList<>();
		for(int i = 0; i < numberOfPlayers; i++){
			batsmen.add(new Batsman(playerOrder.get(i)));
		}
		return Scoreboard.builder()
				.numberOfPlayers(numberOfPlayers)
				.playerList(batsmen)
				.teamBatting(teamBatting)
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(ScoreboardApplication.class, args);
		System.out.println("Enter the number of players for each team:");
		Scanner sc = new Scanner(System.in);
		int numberOfPlayers = sc.nextInt();
		System.out.println("Enter the number of overs");
		int overs = sc.nextInt();
		System.out.println("Batting order for team 1");
		List<String> playerOrder = new ArrayList<>();
		for(int i = 0; i < numberOfPlayers; i++){
			playerOrder.add(sc.next());
		}
		Scoreboard scoreboard = getScoreBoardForTeam(numberOfPlayers, playerOrder, TeamBatting.TEAMA);
		for(int i = 0; i < overs; i++){
			System.out.println("Over " + (i+1) + ":");
			int validDeliveries = 0;
			while(validDeliveries != 6) {
				String ballResult = sc.next();
				if(scoreboard.validateAndUpdateBallResult(ballResult)) {
					validDeliveries++;
				}
			}
			scoreboard.showBatsmanScoreBoard();
		}

		Integer target = scoreboard.getCurrScore();
		System.out.println("Batting order for team 2");
		List<String> playerOrderB = new ArrayList<>();
		for(int i = 0; i < numberOfPlayers; i++){
			playerOrderB.add(sc.next());
		}
		Scoreboard scoreboardB = getScoreBoardForTeam(numberOfPlayers, playerOrderB, TeamBatting.TEAMB);

		for(int i = 0; i < overs; i++){
			System.out.println("Over " + (i+1) + ":");
			int validDeliveries = 0;
			while(validDeliveries != 6) {
				String ballResult = sc.next();
				if(scoreboardB.validateAndUpdateBallResult(ballResult)) {
					validDeliveries++;
				}
				if(scoreboardB.getCurrScore() > target) {
					scoreboardB.showBatsmanScoreBoard();
					System.out.println(MatchResult.TEAM_2_WINS.getValue() + " by " +
							(numberOfPlayers - scoreboardB.getWicketsDown()) + " wickets.");
					return;
				}
				if(scoreboardB.getInningsOver()){
					scoreboardB.showBatsmanScoreBoard();
					System.out.println(MatchResult.TEAM_1_WINS.getValue() + " by " +
							(scoreboard.getCurrScore() - scoreboardB.getCurrScore()) + " runs.");
					return;
				}
			}
			scoreboardB.showBatsmanScoreBoard();
		}
		if(scoreboardB.getCurrScore().equals(target)){
			System.out.println(MatchResult.MATCH_DRAWN.getValue() + " due to same score");
		} else {
			System.out.println(MatchResult.TEAM_1_WINS.getValue() + " by " +
					(scoreboard.getCurrScore() - scoreboardB.getCurrScore()) + " runs.");
		}
	}

}
