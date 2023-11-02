package com.sharulla.quizservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class QuestionWrapperDto {
    private Integer id;
    private String Category;
    private String question;
    private String option1;
    private String option2;
    private String option3;
}
