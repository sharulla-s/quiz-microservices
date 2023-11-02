package com.sharulla.quizservice.service;

import com.sharulla.quizservice.dao.*;
import com.sharulla.quizservice.dto.QuestionWrapperDto;
import com.sharulla.quizservice.dto.QuizDto;
import com.sharulla.quizservice.dto.QuizResponseDto;
import com.sharulla.quizservice.feign.QuizInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;
    @Autowired
    QuizQuestionDao quizQuestionDao;
    @Autowired
    QuizInterface quizInterface;
    public QuizDto createQuiz(String category, Integer numberOfQuestions, String title) {

        List<Integer> questionIds = quizInterface.getQuestionsForQuiz(category, numberOfQuestions).getBody();

        QuizDto quizDto = new QuizDto();
        quizDto.setTitle(title);
        quizDto.setQuestionIds(questionIds);
        Integer quizId = quizDao.insertQuiz(title);
        quizDto.setId(quizId);
        quizDto.setQuestionIds(questionIds);

        for (Integer questionId : questionIds) {
            quizQuestionDao.insertQuizQuestion(quizId, questionId);
        }
        return quizDto;
    }

    public List<QuestionWrapperDto> getQuizQuestion(Integer quizId) {
        List<Integer> quizQuestions = quizQuestionDao.getQuestionIdsOfQuiz(quizId);
        return quizInterface.
                getQuestionsFromId(quizQuestions).getBody();
    }

    public Integer submitQuiz(List<QuizResponseDto> quizResponseDtoList) {

        return quizInterface.getScore(quizResponseDtoList).getBody();
    }
}
