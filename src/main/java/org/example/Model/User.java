package org.example.Model;

public class User {
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String answerToQuestion;
    private int questionIndex = -1;
    private String slogan;
    private int highScore;
    private int numberOfLoginAttempts = 0;
    private long lastLoginAttemptTime;

    public User(String username, String password, String nickname, String email, String slogan) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.slogan = slogan;
        Data.addUser(this);
    }

    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isPasswordCorrect(String password) {
        return (this.password.equals(password));
    }

    public boolean changePassword(String newPassword, String oldPassword) {
        if (oldPassword.equals(this.password)) {
            this.password = newPassword;
            return true;
        }
        return false;
    }

    public boolean changePasswordBySecurityQuestion(String answer, String newPassword) {
        if (answerToQuestion.equals(answer)) {
            this.password = newPassword;
            return true;
        }
        return false;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAnswerToQuestionCorrect(String answerToQuestion) {
        return answerToQuestion.equals(this.answerToQuestion);
    }

    public String getAnswerToQuestion() {
        return answerToQuestion;
    }

    public int getQuestionIndex() {
        return questionIndex;
    }

    public void setAnswerToQuestion(String answerToQuestion) {
        if (this.answerToQuestion != null) return;
        this.answerToQuestion = answerToQuestion;
    }

    public void setQuestionIndex(int questionIndex) {
        if (this.questionIndex != -1) return;
        this.questionIndex = questionIndex;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public int getNumberOfLoginAttempts() {
        return numberOfLoginAttempts;
    }

    public void setNumberOfLoginAttempts(int numberOfLoginAttempts) {
        this.numberOfLoginAttempts = numberOfLoginAttempts;
    }

    public long getLastLoginAttemptTime() {
        return lastLoginAttemptTime;
    }

    public void setLastLoginAttemptTime(long lastLoginAttemptTime) {
        this.lastLoginAttemptTime = lastLoginAttemptTime;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getRank() {
        return Data.getUserRank(this);
    }
}
