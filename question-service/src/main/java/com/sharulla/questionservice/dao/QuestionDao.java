package com.sharulla.questionservice.dao;

import com.sharulla.questionservice.dto.QuestionDto;
import com.sharulla.questionservice.entity.Question;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class QuestionDao {
    private final JdbcTemplate jdbcTemplate;

    public QuestionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<QuestionDto> getAllQuestions() {
        String sql = "SELECT * FROM question";

        try{
            return this.jdbcTemplate.query(sql, new Object[]{},
                    new BeanPropertyRowMapper<>(QuestionDto.class));
        } catch (Exception e){
            return null;
        }
    }

    public List<QuestionDto> getQuestionsByCategory(String category) {
        String sql = "SELECT * FROM question where category = ?";

        try{
            return this.jdbcTemplate.query(sql, new Object[]{ category },
                    new BeanPropertyRowMapper<>(QuestionDto.class));
        } catch (Exception e){
            return null;
        }
    }

    public List<Integer> getRandomQuestions(String category, int numberOfQuestions) {
        String sql = "SELECT q.id FROM question q WHERE q.category = ? ORDER BY RAND() LIMIT ?";

        try {
            return jdbcTemplate.queryForList(sql, new Object[]{category, numberOfQuestions}, Integer.class);
        } catch (Exception e) {
            return null;
        }
    }


    public List<QuestionDto> getByIds(List<Integer> questionIds) {
        String sql = "SELECT * FROM question WHERE id IN " + integerParameterBuilder(questionIds);
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(QuestionDto.class));
    }

    public QuestionDto getById(Integer questionId) {
        String sql = "SELECT * FROM question WHERE id = ? ";
        return jdbcTemplate.queryForObject(sql, new Object[]{questionId},
        new BeanPropertyRowMapper<>(QuestionDto.class));
    }



    public static String integerParameterBuilder(List<Integer> params) {
        if (Objects.isNull(params) || params.isEmpty())
            return "('')";

        String param = "(";

        for (Integer i =0; i < params.size(); i++){
            param += "'" + params.get(i).toString() + "'";
            param += ',';
        }
        param = param.substring(0, param.length() -1);
        param += ')';

        return param;

    }

    public void insertQuestion(Question question) {
        String sql = "INSERT INTO question (category, question, option1, option2, option3, right_answer) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            jdbcTemplate.update(
                    sql,
                    question.getCategory(),
                    question.getQuestion(),
                    question.getOption1(),
                    question.getOption2(),
                    question.getOption3(),
                    question.getRightAnswer()
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("Error in inserting data", e);
        }
    }


}
