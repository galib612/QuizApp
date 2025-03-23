package com.mg.quizapp.service;

import com.mg.quizapp.model.Question;
import com.mg.quizapp.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {

        try {
            // JPA Automatically generates the Hibernate query based on the method name
            return new ResponseEntity<>(questionRepository.findByCategory(category), HttpStatus.OK);

            // Below one used the stream api to filter the questions based on the category
            //return questionRepository.findAll().stream().filter(question -> question.getCategory().equals(category)).toList();
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionRepository.save(question);
            return new ResponseEntity<>("Question Added Successfully", HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteQuestionById(Integer id) {

        try {
            questionRepository.deleteById(id);
            return new ResponseEntity<>("Question Deleted Successfully", HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String updateQuestionById(Integer id, Question question) {
        int updatedRow = questionRepository.updateQuestionById(id,
                                        question.getQuestionTitle(),
                                        question.getOption1(),
                                        question.getOption2(),
                                        question.getOption3(),
                                        question.getOption4(),
                                        question.getRightAnswer(),
                                        question.getDifficultyLevel(),
                                        question.getCategory());

        return updatedRow > 0 ? "Question Updated Successfully": "Question Updated Successfully";
    }
}
