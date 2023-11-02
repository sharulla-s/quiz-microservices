package com.sharulla.questionservice.service;

import com.sharulla.questionservice.dao.QuestionDao;
import com.sharulla.questionservice.dto.QuestionDto;
import com.sharulla.questionservice.dto.QuestionWrapperDto;
import com.sharulla.questionservice.dto.QuizResponseDto;
import com.sharulla.questionservice.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;
    public List<QuestionDto> getAllQuestions() {
        return questionDao.getAllQuestions();
    }

    public List<QuestionDto> getQuestionsByCategory(String category) {
        return questionDao.getQuestionsByCategory(category);
    }

    public void addQuestion(Question question) {
        questionDao.insertQuestion(question);
    }

    public List<Integer> generateQuestionsForQuiz(String category, Integer numberOfQuestions) {
        return questionDao.getRandomQuestions(category, numberOfQuestions);
    }

    public List<QuestionWrapperDto> getQuestionsForIds(List<Integer> questionIds) {

        List<QuestionDto>  questionDtoList = questionDao.getByIds(questionIds);
        List<QuestionWrapperDto> questionWrapperDtoList = new ArrayList<>();

        for (QuestionDto questionDto : questionDtoList){
            QuestionWrapperDto questionWrapperDto = QuestionWrapperDto
                    .builder()
                    .id(questionDto.getId())
                    .Category(questionDto.getCategory())
                    .question(questionDto.getOption1())
                    .option1(questionDto.getOption1())
                    .option2(questionDto.getOption2())
                    .option3(questionDto.getOption3())
                    .build();
            questionWrapperDtoList.add(questionWrapperDto);
        }
        return questionWrapperDtoList;
    }

    public Integer getQuizScore(List<QuizResponseDto> responseDtoList) {

        Integer rightAnswerCount = 0;

        for (QuizResponseDto responseDto : responseDtoList) {

            QuestionDto question = questionDao.getById(responseDto.getQuestionId());
            if (responseDto.getAnswer().equalsIgnoreCase(question.getRightAnswer())) {
                rightAnswerCount++;
            }
        }
        return rightAnswerCount;
    }
}
