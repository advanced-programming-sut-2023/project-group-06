package org.example.Model;

import javafx.scene.image.Image;
import org.apache.commons.codec.digest.DigestUtils;
import org.example.View.ProfileMenu;

public class User implements Comparable<User> {
    private String username = "";
    private String hashedPassword = "";
    private String nickname = "";
    private String email = "";
    private String hashedAnswerToQuestion = "";
    private int questionIndex = -1;
    private String slogan = "";
    private int highScore;
    private int numberOfLoginAttempts = 0;
    private long lastLoginAttemptTime;
    private Image avatar;

    public User(String username, String password, String nickname, String email, String slogan) {
        this.username = username;
        this.hashedPassword = DigestUtils.sha256Hex(password);
        this.nickname = nickname;
        this.email = email;
        this.slogan = slogan;
        Data.addUser(this);
        /*
        93088
        11
        6357437
        6357467
        6357513*
        6357518
        278529
        3497394
        6357549
        6357356
        6357555
         */
        this.avatar = new Image(ProfileMenu.class.getResource("/Images/avatar8.jpg").toString());
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isPasswordCorrect(String password) {
        return (hashedPassword.equals(DigestUtils.sha256Hex(password)));
    }

    public boolean changePassword(String newPassword, String oldPassword) {
        if (isPasswordCorrect(oldPassword)) {
            hashedPassword = DigestUtils.sha256Hex(newPassword);
            return true;
        }
        return false;
    }

    public boolean changePasswordBySecurityQuestion(String answer, String newPassword) {
        if (hashedAnswerToQuestion.equals(DigestUtils.sha256Hex(answer))) {
            hashedPassword = DigestUtils.sha256Hex(newPassword);
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
        return hashedAnswerToQuestion.equals(DigestUtils.sha256Hex(answerToQuestion));
    }

    public String getHashedAnswerToQuestion() {
        return hashedAnswerToQuestion;
    }

    public int getQuestionIndex() {
        return questionIndex;
    }

    public void setAnswerToQuestion(String answerToQuestion) {
        if (!this.hashedAnswerToQuestion.equals("")) return;
        this.hashedAnswerToQuestion = answerToQuestion;
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

    public void addHighScore(int highScore){
        this.highScore += highScore;
    }

    public int getRank() {
        return Data.getUserRank(this);
    }

    public int compareTo(User user) {
        return this.highScore - user.highScore;
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }
}
