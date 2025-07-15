package com.example.csc325_capstoneproject.model;

/**
 * A class which stores all the data for the Tests.
 * @since 6/18/2025
 * @author Nathaniel Rivera
 */
public class Test {
    /**
     * An enum storing the subject of the test
     */
    private Subject subject;
    /**
     * An int storing the number of questions on the test
     */
    private int question_count;
    /**
     * An int storing the number of questions the user got right on the test
     */
    private int num_correct;
    /**
     * A string storing the date the test was taken by the user.
     */
    private String date_taken;
    /**
     * An int which stores the grade level of the user.
     */
    private int grade_level;
    /**
     * An int storing the score of the test.
     */
    private int score;
    /**
     * An int storing a count of how many test the child has taken for the given subject
     */
    private int count;

    /**
     * Default constructor for the Test class.
     * @since 6/18/2025
     * @author Nathaniel Rivera
     */
    Test() {
        this.subject = null;
        this.question_count = 0;
        this.num_correct = 0;
        this.date_taken = "N/A";
        this.score = 0;
    }

    /**
     * Parameterized constructor for the Test class.
     * @param subject The subject of the test.
     * @param question_count The amount of questions on the test.
     * @param num_correct The number of questions the user got correct on the test.
     * @param date_taken The date the user took the test.
     * @param grade_level THe grade level of the test.
     * @since 6/18/2025
     * @author Nathaniel Rivera
     */
    public Test(Subject subject, int question_count, int num_correct, String date_taken, int grade_level) {
        this.subject = subject;
        this.question_count = question_count;
        this.num_correct = num_correct;
        this.date_taken = date_taken;
        this.grade_level = grade_level;
        double setScore = ((double) num_correct) / ((double) question_count) * 100;
        this.score = (int) setScore;
    }

    /**
     * Parameterized constructor for the Test class with count.
     * @param subject The subject of the test.
     * @param question_count The amount of questions on the test.
     * @param num_correct The number of questions the user got correct on the test.
     * @param date_taken The date the user took the test.
     * @param grade_level THe grade level of the test.
     * @param count The amount of the tests the user has taken on the specific subject
     * @since 6/18/2025
     * @author Nathaniel Rivera
     */
    public Test(Subject subject, int question_count, int num_correct, String date_taken, int grade_level, int count) {
        this.subject = subject;
        this.question_count = question_count;
        this.num_correct = num_correct;
        this.date_taken = date_taken;
        this.grade_level = grade_level;
        this.score = getScore();
        this.count = count;
    }

    /**
     * Getter method for Subject.
     * @return the subject of the Test.
     * @since 6/18/2025
     * @author Nathaniel Rivera
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * Setter method for Subject.
     * @param subject The subject of the Test.
     * @since 6/18/2025
     * @author Nathaniel Rivera
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    /**
     * Getter method for question_count.
     * @return the question_count of the Test.
     * @since 6/18/2025
     * @author Nathaniel Rivera
     */
    public int getQuestionCount() {
        return question_count;
    }

    /**
     * Setter method for question_count.
     * @param question_count The question_count of the User.
     * @since 6/18/2025
     * @author Nathaniel Rivera
     */
    public void setQuestionCount(int question_count) {
        this.question_count = question_count;
    }

    /**
     * Getter method for num_correct.
     * @return the num_correct for the Test.
     * @since 6/18/2025
     * @author Nathaniel Rivera
     */
    public int getNumCorrect() {
        return num_correct;
    }

    /**
     * Setter method for num_correct.
     * @param num_correct The num_correct of the User.
     * @since 6/18/2025
     * @author Nathaniel Rivera
     */
    public void setNumCorrect(int num_correct) {
        this.num_correct = num_correct;
    }

    /**
     * Getter method for Date.
     * @return the date_taken for the Test.
     * @since 6/18/2025
     * @author Nathaniel Rivera
     */
    public String getDateTaken() {
        return date_taken;
    }

    /**
     * Setter method for date_taken.
     * @param date_taken The date_taken of the User.
     * @since 6/18/2025
     * @author Nathaniel Rivera
     */
    public void setDateTaken(String date_taken) {
        this.date_taken = date_taken;
    }

    /**
     * Calculates and returns the score of the test in the form of a String.
     * The String returned will have at most one decimal place.
     * @return The score of the test in the form of a string.
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score of the test'
     * @param initScore The new score of the test
     * @since 7/15/2025
     * @author Nathaniel Rivera
     */
    public void setScore(int initScore) {
        score = initScore;
    }

    /**
     * Getter method for Grade Level.
     * @return the grade_level for the Test.
     * @since 7/7/2025
     * @author Nathaniel Rivera
     */
    public int getGradeLevel() {
        return grade_level;
    }

    /**
     * Setter method for grade_level.
     * @param grade_level The grade_level of the User.
     * @since 7/7/2025
     * @author Nathaniel Rivera
     */
    public void setGradeLevel(int grade_level) {
        this.grade_level = grade_level;
    }

    /**
     * Getter method for count.
     * @return the count for the Test
     * @since 7/7/2025
     * @author Nathaniel Rivera
     */
    public int getCount() {
        return count;
    }
}
