package com.mg.quizapp.repository;

import com.mg.quizapp.model.Question;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository <Question, Integer>{

    List<Question> findByCategory(String category);

    @Modifying
    @Transactional
    @Query("update Question q set q.questionTitle = ?2, q.option1 = ?3, q.option2 = ?4, q.option3 = ?5, q.option4 = ?6, q.rightAnswer = ?7, q.difficultyLevel = ?8, q.category = ?9 where q.id = ?1")
    int updateQuestionById(Integer id, String questionTitle, String option1, String option2, String option3, String option4, String rightAnswer, String difficultyLevel, String category);

    @Query(value = "SELECT * FROM Question q WHERE q.category=?1 ORDER BY RANDOM() LIMIT ?2", nativeQuery = true)
    List<Question> findRandomQuestionByCategory(String category, int numberOfQuestions);
}
