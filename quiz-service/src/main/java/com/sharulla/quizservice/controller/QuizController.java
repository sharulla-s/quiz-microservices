package com.sharulla.quizservice.controller;

import com.sharulla.quizservice.dto.QuestionWrapperDto;
import com.sharulla.quizservice.dto.QuizCreationDto;
import com.sharulla.quizservice.dto.QuizDto;
import com.sharulla.quizservice.dto.QuizResponseDto;
import com.sharulla.quizservice.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {
    @Autowired
    QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity createQuiz(@RequestBody QuizCreationDto quizCreationDto) {

        try {
            QuizDto quizDto = quizService.createQuiz(quizCreationDto.getCategory(), quizCreationDto.getNumberOfQuestions(), quizCreationDto.getTitle());
            return ResponseEntity.status(HttpStatus.OK).body(quizDto);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        }

    }

    @GetMapping("/getQuiz")
    public ResponseEntity getQuiz(@RequestParam Integer quizId) {

        try {
            List<QuestionWrapperDto> questionWrapperDto = quizService.getQuizQuestion(quizId);
            return ResponseEntity.status(HttpStatus.OK).body(questionWrapperDto);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        }

    }

    @PostMapping("/submit")
    public ResponseEntity submit(@RequestBody List<QuizResponseDto> responseDtoList) {

        try {
            Integer rightAnswerCount = quizService.submitQuiz(responseDtoList);
            return ResponseEntity.status(HttpStatus.OK).body(rightAnswerCount);

        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }

    }
}
