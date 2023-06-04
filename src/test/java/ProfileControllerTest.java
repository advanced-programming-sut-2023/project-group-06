import org.example.Controller.ProfileController;
import org.example.Model.Data;
import org.example.View.Commands;
import org.example.View.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ProfileControllerTest {

    /*
        username : Mammad
        password : M23571113m@
    */

    @BeforeAll
    public static void init() throws IOException {
        System.out.println("started");
        boolean happy = Data.loadData("test/FakeData.json");
        if (happy) System.out.println("Data loaded from src/test/FakeData.json");
        else Assertions.assertEquals(0,1);
    }
    @Test
    public void changeUsernameTest() throws IOException {
        changeUsernameDoer("profile change -u ", Response.EMPTY_USERNAME);
        changeUsernameDoer("profile change -u @mir", Response.INVALID_USERNAME_FORMAT);
        changeUsernameDoer("profile change -u Mammad", Response.SAME_USERNAME);
        changeUsernameDoer("profile change -u Allie", Response.USERNAME_EXISTS);
        changeUsernameDoer("profile change -u Mohammad", Response.USERNAME_CHANGE);
        Assertions.assertEquals("Mohammad", Data.getCurrentUser().getUsername());
        changeUsernameDoer("profile change -u Mammad", Response.USERNAME_CHANGE);
    }
    private static void changeUsernameDoer(String cmd, Response expectedResponse) {
        Commands regex = Commands.CHANGE_USERNAME;
        Assertions.assertEquals(expectedResponse, ProfileController.changeUsername(Commands.getMatcher(cmd, regex)));
    }

    @Test
    public void changeNicknameTest() throws IOException {
        changeNicknameDoer("profile change -n ", Response.EMPTY_NICKNAME);
        changeNicknameDoer("profile change -n Barqi", Response.SAME_NICKNAME);
        changeNicknameDoer("profile change -n Barghi", Response.NICKNAME_CHANGE);
        Assertions.assertEquals("Barghi", Data.getCurrentUser().getNickname());
        changeNicknameDoer("profile change -n Barqi", Response.NICKNAME_CHANGE);
    }
    private static void changeNicknameDoer(String cmd, Response expectedResponse) {
        Commands regex = Commands.CHANGE_NICKNAME;
        Assertions.assertEquals(expectedResponse, ProfileController.changeNickname(Commands.getMatcher(cmd, regex)));
    }

    @Test
    public void changePasswordTest() throws IOException {
        changePasswordDoer("profile change -o  -n nothing", Response.EMPTY_PASSWORD);
        changePasswordDoer("profile change -o nothing -n ", Response.EMPTY_PASSWORD);
        changePasswordDoer("profile change -o incorPass -n eseayPass", Response.INCORRECT_OLD_PASSWORD);
        changePasswordDoer("profile change -o M23571113m@ -n M23571113m@", Response.SAME_PASSWORD);
        changePasswordDoer("profile change -o M23571113m@ -n M2m@", Response.SHORT_PASSWORD);
        changePasswordDoer("profile change -o M23571113m@ -n m23571113m@", Response.PASSWORD_CAPITAL);
        changePasswordDoer("profile change -o M23571113m@ -n M23571113M@", Response.PASSWORD_LOWER);
        changePasswordDoer("profile change -o M23571113m@ -n m@#%&!!!#M@", Response.PASSWORD_NUMBER);
        changePasswordDoer("profile change -o M23571113m@ -n m23571113M2", Response.PASSWORD_SYMBOL);
        changePasswordDoer("profile change -o M23571113m@ -n m23571113M2", Response.PASSWORD_SYMBOL);
        changePasswordDoer("profile change -o M23571113m@ -n m2357M@", Response.RE_ENTER_PASSWORD);
    }
    private static void changePasswordDoer(String cmd, Response expectedResponse) {
        Commands regex = Commands.CHANGE_PASSWORD;
        Assertions.assertEquals(expectedResponse, ProfileController.changePassword(Commands.getMatcher(cmd, regex)));
    }

    @Test
    public void confirmReEnteredPasswordTest() throws IOException {
        Assertions.assertEquals(Response.PASSWORD_CONFIRMATION, ProfileController.confirmReEnteredPassword("M23571113m@", "m2357M@", "m2356M@"));
        Assertions.assertEquals(Response.INCORRECT_OLD_PASSWORD, ProfileController.confirmReEnteredPassword("M235711m@", "m2357M@", "m2357M@"));
        Assertions.assertEquals(Response.PASSWORD_CHANGE, ProfileController.confirmReEnteredPassword("M23571113m@", "m2357M@", "m2357M@"));
        Assertions.assertTrue(Data.getCurrentUser().isPasswordCorrect("m2357M@"));
        Assertions.assertEquals(Response.PASSWORD_CHANGE, ProfileController.confirmReEnteredPassword("m2357M@", "M23571113m@", "M23571113m@"));
    }

    @Test
    public void changeEmailTest() throws IOException {
        changeEmailDoer("profile change -e ", Response.EMPTY_EMAIL);
        changeEmailDoer("profile change -e mmd@gmail.com", Response.SAME_EMAIL);
        changeEmailDoer("profile change -e \"mmd@gmail.com\"", Response.SAME_EMAIL);
        changeEmailDoer("profile change -e alli@gmail.com", Response.EMAIL_EXISTS);
        changeEmailDoer("profile change -e https://t.me/mmdMmdian", Response.INVALID_EMAIL_FORMAT);
        changeEmailDoer("profile change -e mohammad@yahoo.com", Response.EMAIL_CHANGE);
        Assertions.assertEquals("mohammad@yahoo.com", Data.getCurrentUser().getEmail());
        changeEmailDoer("profile change -e mmd@gmail.com", Response.EMAIL_CHANGE);
    }
    private static void changeEmailDoer(String cmd, Response expectedResponse) {
        Commands regex = Commands.CHANGE_EMAIL;
        Assertions.assertEquals(expectedResponse, ProfileController.changeEmail(Commands.getMatcher(cmd, regex)));
    }

    @Test
    public void changeSloganTest() throws IOException {
        changeSloganDoer("profile change -s ", Response.EMPTY_SLOGAN);
        changeSloganDoer("profile change -s \"Goomba Goomba\"", Response.SAME_SLOGAN);
        changeSloganDoer("profile change -s \"Jakarta Jakarta\"", Response.SLOGAN_CHANGE);
        Assertions.assertEquals("Jakarta Jakarta", Data.getCurrentUser().getSlogan());
        changeSloganDoer("profile change -s \"Goomba Goomba\"", Response.SLOGAN_CHANGE);
    }
    private static void changeSloganDoer(String cmd, Response expectedResponse) {
        Commands regex = Commands.CHANGE_SLOGAN;
        Assertions.assertEquals(expectedResponse, ProfileController.changeSlogan(Commands.getMatcher(cmd, regex)));
    }

    @Test
    public void removeSloganTest() throws IOException{
        Assertions.assertEquals(Response.SLOGAN_REMOVE, ProfileController.removeSlogan());
        Assertions.assertEquals("", Data.getCurrentUser().getSlogan());
        Assertions.assertEquals(Response.SLOGAN_ALREADY_NULL, ProfileController.removeSlogan());
        changeSloganDoer("profile change -s \"Goomba Goomba\"", Response.SLOGAN_CHANGE);
    }

    @Test
    public void showFunctionsTest(){
        Assertions.assertEquals(12, ProfileController.showHighScore());
        Assertions.assertEquals(2, ProfileController.showRank());
        Assertions.assertEquals("Goomba Goomba", ProfileController.showSlogan());
        String expected = "username: Mammad\nnickname: Barqi\nemail: mmd@gmail.com\nslogan: Goomba Goomba\nhighscore: 12\nrank: 2";
        Assertions.assertEquals(expected, ProfileController.showInfo());
    }

    @AfterAll
    public static void cleanUp() throws IOException {
        System.out.println("finished");
    }


}