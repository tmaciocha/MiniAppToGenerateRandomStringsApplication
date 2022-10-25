package com.example.miniapptogeneraterandomstrings.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
