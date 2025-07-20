package com.mg.quizapp.model;

import jakarta.persistence.criteria.CriteriaBuilder;

public class QuizResponse {

    private Integer id;
    private String responseOption;

    public Integer getId() {
        return id;
    }

    public String getResponseOption() {
        return responseOption;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setResponseOption(String responseOption) {
        this.responseOption = responseOption;
    }
}
