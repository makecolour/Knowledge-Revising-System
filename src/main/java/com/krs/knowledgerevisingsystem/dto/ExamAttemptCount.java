package com.krs.knowledgerevisingsystem.dto;

public class ExamAttemptCount {
    String examName;
    Long count;

    // Constructor, getters, and setters

    public ExamAttemptCount() {
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public ExamAttemptCount(Long count, String examName) {
        this.count = count;
        this.examName = examName;
    }
}
