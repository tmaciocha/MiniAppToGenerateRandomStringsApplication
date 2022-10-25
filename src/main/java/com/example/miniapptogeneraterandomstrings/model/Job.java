package com.example.miniapptogeneraterandomstrings.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Job {

    private int id;
    private int min;
    private int max;
    private String text;
    private int numberOfStrings;

}
