package com.sharulla.questionservice.controller;

import com.sharulla.questionservice.dto.QuestionDto;
import com.sharulla.questionservice.dto.QuestionWrapperDto;
import com.sharulla.questionservice.dto.QuizResponseDto;
import com.sharulla.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sharulla.questionservice.entity.Question;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/get")
    public ResponseEntity getAllQuestions(){

        try {

            List<QuestionDto> questions = questionService.getAllQuestions();

            return ResponseEntity.status(HttpStatus.OK).body(questions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/getByCategory")
    public ResponseEntity getAllQuestions(
            @RequestParam String category){

        try {

            List<QuestionDto> questions = questionService.getQuestionsByCategory(category);

            return ResponseEntity.status(HttpStatus.OK).body(questions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity addQuestion(
            @RequestBody Question question){

        try {

           questionService.addQuestion(question);

            return ResponseEntity.status(HttpStatus.OK).body("Success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category, @RequestParam Integer numberOfQuestions){
        try {
            List<Integer> questionIds =  questionService.generateQuestionsForQuiz(category, numberOfQuestions);
            return ResponseEntity.status(HttpStatus.OK).body(questionIds);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/getQuestions")
    public ResponseEntity<List<QuestionWrapperDto>> getQuestionsFromId(@RequestBody List<Integer> questionIds){
        try {
            List<QuestionWrapperDto> questionWrapperDtoList = questionService.getQuestionsForIds(questionIds);
            return ResponseEntity.status(HttpStatus.OK).body(questionWrapperDtoList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<QuizResponseDto> responseDtoList){
        try {
            Integer score = questionService.getQuizScore(responseDtoList);
            return ResponseEntity.status(HttpStatus.OK).body(score);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
