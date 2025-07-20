package com.mg.quizapp.service;

import com.mg.quizapp.model.Question;
import com.mg.quizapp.model.QuestionWrapper;
import com.mg.quizapp.model.Quiz;
import com.mg.quizapp.model.QuizResponse;
import com.mg.quizapp.repository.QuestionRepository;
import com.mg.quizapp.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class QuizService {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuestionRepository questionRepository;

    public ResponseEntity<String> createQuiz(String category, String title, int numberOfQuestions) {

        List<Question> questions = questionRepository.findRandomQuestionByCategory(category, numberOfQuestions);
        Quiz quiz = new Quiz();

        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizRepository.save(quiz);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizById(Integer quizId){
        Quiz quiz = quizRepository.findById(quizId).orElse(null);
        if(quiz == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }else{
            List<QuestionWrapper> questionWrappers = quiz.getQuestions().stream()
                    .map(question -> new QuestionWrapper(question.getId(), question.getQuestionTitle(), question.getOption1(),
                            question.getOption2(), question.getOption3(), question.getOption4())).toList();

            return new ResponseEntity<>(questionWrappers, HttpStatus.OK);
        }
    }

    public ResponseEntity<String> submitQuizById(Integer quizId, List<QuizResponse> quizResponses) {
        Quiz quiz = quizRepository.findById(quizId).orElse(null);
        if(quiz == null){
            return new ResponseEntity<>("Quiz Not Found", HttpStatus.NOT_FOUND);
        }else{
            List<Question> questions = quiz.getQuestions();
            int score = 0;
            for (QuizResponse quizResponse : quizResponses) {
                String userOption = quizResponse.getResponseOption();
                Integer questionId = quizResponse.getId();
                Question quizQuestion = questions.stream().filter(question -> question.getId().equals(questionId)).findFirst().orElse(null);
                if (quizQuestion == null) {
                    return new ResponseEntity<>("Question Not Found", HttpStatus.NOT_FOUND);
                }else{
                    String rightAnswer = quizQuestion.getRightAnswer();
                    String userAnswer = null;
                    if(userOption.equals("option1")) {
                        userAnswer = quizQuestion.getOption1();
                    }
                    else if(userOption.equals("option2")) {
                        userAnswer = quizQuestion.getOption2();
                    }
                    else if(userOption.equals("option3")) {
                        userAnswer = quizQuestion.getOption2();
                    }
                    else if(userOption.equals("option4")) {
                        userAnswer = quizQuestion.getOption2();
                    }
                    else{
                        return new ResponseEntity<>("Invalid Option Selected", HttpStatus.BAD_REQUEST);
                    }
                    if(rightAnswer.equals(userAnswer))
                        score++;
                }
            }

        }

    }

}
