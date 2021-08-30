package com.lld.cricket.scoreboard.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CricketScoreBoardException extends  RuntimeException{
    private ExceptionType exceptionType;
    private String message;
}
