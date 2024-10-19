package org.example.Models;

import lombok.Getter;

@Getter
public enum Score {
    ZERO_POINT(0),
    FIRST_POINT(15),
    SECOND_POINT(30),
    THIRD_POINT(40),
    EVEN("EVEN"),
    MORE("MORE"),
    LESS("LESS");

    private final String value;
    Score(int score){
        value = String.valueOf(score);
    }
    Score(String score){
        value = score;
    }
}
