
package com.mycompany.chatapppart1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    @Test
    public void testValidUserName() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.checkUserName());
    }

    @Test
    public void testValidPasswordComplexity() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.checkPasswordComplexity());
    }

    @Test
    public void testValidCellPhoneNumber() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.checkCellPhoneNumber());
    }

    @Test
    public void testRegisterUser() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976");

        String result = login.registerUser();

        assertEquals(
                "Username successfully captured.\n"
                + "Password successfully captured.\n"
                + "Cell phone number successfully added.\n",
                result
        );
    }

    @Test
    public void testLoginUser() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976");

        login.setEnteredUsername("kyl_1");
        login.setEnteredPassword("Ch&&sec@ke99!");

        assertTrue(login.loginUser());
    }

    @Test
    public void testReturnLoginStatus() {
        Login login = new Login(
                "Kyle", "user", "kyl_1",
                "Ch&&sec@ke99!", "+27838968976"
        );

        login.setEnteredUsername("kyl_1");
        login.setEnteredPassword("Ch&&sec@ke99!");

        assertEquals(
                "Welcome Kyle user, it is great to see you again.",
                login.returnLoginStatus()
        );
    }

    @Test
    public void testInvalidUserName() {
        Login login = new Login("kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976");
        assertFalse(login.checkUserName());
    }

    @Test
    public void testInvalidPasswordComplexity() {
        Login login = new Login("kyl_1", "password", "+27838968976");
        assertFalse(login.checkPasswordComplexity());
    }

    @Test
    public void testInvalidPhoneNumber() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "08966553");
        assertFalse(login.checkCellPhoneNumber());
    }

    @Test
    public void testInvalidRegisterUser() {
        Login login = new Login("kyle!!!!!!!", "password", "08966553");

        String result = login.registerUser();

        assertEquals(
                "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.\n"
                + "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.\n"
                + "Cell phone number incorrectly formatted or does not contain international code.\n",
                result
        );
    }

    @Test
    public void testInvalidLoginUser() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976");

        login.setEnteredUsername("kyl_1");
        login.setEnteredPassword("WrongPassword1!");

        assertFalse(login.loginUser());
    }

    @Test
    public void testInvalidReturnLoginStatus() {
        Login login = new Login("kyl_1", "Ch&&sec@ke99!", "+27838968976");

        login.setEnteredUsername("wrong_username");
        login.setEnteredPassword("WrongPassword1!");

        assertEquals(
                "Username or password incorrect, please try again.",
                login.returnLoginStatus()
        );
    }
}
