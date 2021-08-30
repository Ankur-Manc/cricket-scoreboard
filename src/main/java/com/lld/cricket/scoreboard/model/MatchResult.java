package com.lld.cricket.scoreboard.model;

public enum MatchResult {
 TEAM_1_WINS ("TEAM 1 wins"), TEAM_2_WINS("TEAM 2 wins"), MATCH_DRAWN("Match drawn");

 private String result;

 public String getValue(){
     return this.result;
 }

 private MatchResult(String result){
     this.result = result;
 }

}
