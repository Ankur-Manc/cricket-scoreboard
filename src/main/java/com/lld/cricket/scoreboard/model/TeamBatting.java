package com.lld.cricket.scoreboard.model;

public enum TeamBatting {
    TEAMA(1), TEAMB(2);

    private Integer result;

    public Integer getValue(){
        return this.result;
    }

    private TeamBatting(Integer result){
        this.result = result;
    }
}
