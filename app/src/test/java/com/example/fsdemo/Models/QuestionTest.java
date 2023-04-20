package com.example.fsdemo.Models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    @Test
    void getQuestion() {
        Question question = new Question("Question 1", "A", "B", "C", "D", 1);
        assertEquals("Question 1", question.getQuestion());
    }

    @Test
    void setQuestion() {
        Question question = new Question("Question 1", "A", "B", "C", "D", 1);
        question.setQuestion("Question 1");
        assertEquals("Question 1", question.getQuestion());
    }

    @Test
    void getOptionA() {
        Question question = new Question("Question 1", "A", "B", "C", "D", 1);
        assertEquals("A", question.getOptionA());
    }

    @Test
    void setOptionA() {
        Question question = new Question("Question 1", "", "B", "C", "D", 1);
        question.setOptionA("A");
        assertEquals("A", question.getOptionA());
    }

    @Test
    void getOptionB() {
        Question question = new Question("Question 1", "A", "B", "C", "D", 1);
        assertEquals("B", question.getOptionB());
    }

    @Test
    void setOptionB() {
        Question question = new Question("Question 1", "A", "", "C", "D", 1);
        question.setOptionB("B");
        assertEquals("B", question.getOptionB());
    }

    @Test
    void getOptionC() {
        Question question = new Question("Question 1", "A", "B", "C", "D", 1);
        assertEquals("C", question.getOptionC());
    }

    @Test
    void setOptionC() {
        Question question = new Question("Question 1", "A", "B", "", "D", 1);
        question.setOptionC("C");
        assertEquals("C", question.getOptionC());
    }

    @Test
    void getOptionD() {
        Question question = new Question("Question 1", "A", "B", "C", "D", 1);
        assertEquals("D", question.getOptionD());
    }

    @Test
    void setOptionD() {
        Question question = new Question("Question 1", "A", "B", "C", "D", 1);
        question.setOptionA("D");
        assertEquals("D", question.getOptionD());
    }

    @Test
    void getCorrectAns() {
        Question question = new Question("Question 1", "A", "B", "C", "D", 1);
        assertEquals(1 , question.getCorrectAns());
    }

    @Test
    void setCorrectAns() {
        Question question = new Question("Question 1", "", "B", "C", "D", 0);
        question.setCorrectAns(1);
        assertEquals(1, question.getCorrectAns());
    }
}