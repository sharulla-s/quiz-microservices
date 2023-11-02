package com.sharulla.quizservice.dao;

import com.sharulla.quizservice.dto.QuizDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class QuizDao {
    private final JdbcTemplate jdbcTemplate;

    public QuizDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer insertQuiz(String title) {
        String sql = "INSERT INTO quiz (title) VALUES (?)";

        try {
            KeyHolder holder = new GeneratedKeyHolder();

            this.jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                            ps.setString(1, title);
                            return ps;
                        }
                    }, holder);
            return holder.getKey().intValue();

        } catch (Exception e) {
            throw new IllegalArgumentException("Error in inserting quiz", e);
        }
    }

    public QuizDto getQuizById(int id) {
        String sql = "SELECT * FROM quiz WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(QuizDto.class));
        } catch (Exception e) {
            throw new IllegalArgumentException("Error in retrieving quiz", e);
        }
    }
}
