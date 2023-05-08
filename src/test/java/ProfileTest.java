import org.example.Controller.ProfileController;
import org.example.Model.Data;
import org.example.View.Commands;
import org.example.View.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class ProfileTest {

    /*
        username : Mammad
        password : M235711m@
    */

    @BeforeAll
    public static void init() throws IOException {
        moveFile("src/main/java/org/example/Model/Data.json", "src/test/DataCopy.json");
        moveFile("src/test/FakeData.json", "src/main/java/org/example/Model/Data.json");
        Data.loadData("src/main/java/org/example/Model/Data.json");
    }

    @Test
    public void changeUsernameTest() throws IOException {
        String command;
        Commands regex = Commands.CHANGE_USERNAME;
        command = "profile change -u ";
        Assertions.assertEquals(Response.EMPTY_USERNAME, ProfileController.changeUsername(Commands.getMatcher(command, regex)));
    }

    @AfterAll
    public static void cleanUp() throws IOException {
        moveFile("src/main/java/org/example/Model/Data.json", "src/test/FakeData.json");
        moveFile("src/test/DataCopy.json", "src/main/java/org/example/Model/Data.json");
    }

    public static boolean moveFile(String sourcePath, String targetPath) {

        File fileToMove = new File(sourcePath);

        return fileToMove.renameTo(new File(targetPath));
    }
}