package com.sharulla.quizservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class QuizCreationDto {
    private String category;
    private Integer numberOfQuestions;
    private String title;
}
