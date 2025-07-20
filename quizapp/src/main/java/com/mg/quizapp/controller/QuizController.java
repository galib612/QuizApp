package com.mg.quizapp.controller;

import com.mg.quizapp.model.Question;
import com.mg.quizapp.model.QuestionWrapper;
import com.mg.quizapp.model.QuizResponse;
import com.mg.quizapp.service.QuizService;
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


    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category,
                                                     @RequestParam String title,
                                                     @RequestParam Integer numberOfQuestions){

        return quizService.createQuiz(category, title, numberOfQuestions);
    }

    @GetMapping("getQuiz/{quizId}")
    public ResponseEntity<List<QuestionWrapper>> getQuizById(@PathVariable Integer quizId){

        return quizService.getQuizById(quizId);
    }

    @PostMapping("submitQuiz/{id}")
    public ResponseEntity<String> submitQuizById(@PathVariable Integer quizId, @RequestBody List<QuizResponse> quizResponses){

        return quizService.submitQuizById(quizId, quizResponses);
    }


}
