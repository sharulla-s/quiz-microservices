package com.sharulla.questionservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Question {
    private Integer id;
    private String Category;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String rightAnswer;
}
