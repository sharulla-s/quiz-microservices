package com.sharulla.quizservice.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuizQuestionDao {
    private final JdbcTemplate jdbcTemplate;

    public QuizQuestionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertQuizQuestion(Integer quizId, Integer questionId) {
        String sql = "INSERT INTO quiz_question (quiz_id, question_id) VALUES (?, ?)";
        try {
            jdbcTemplate.update(sql,quizId, questionId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error in inserting data", e);
        }
    }

    public List<Integer> getQuestionIdsOfQuiz(Integer quizId) {

        String sql = "Select question_id from quiz_question where quiz_id = ?";
        return jdbcTemplate.queryForList(sql, Integer.class, quizId);
    }
}
