
package com.mycompany.chatapppart1;

// ==========================================
// 1. IMPORTS
// ==========================================

import java.util.Scanner;

/**
 * The Main class runs the QuickChat application.
 * It accepts keyboard input and controls registration,
 * login, and message management.
 *
 * @author Student
 */
public class Main {

    // ==========================================
    // 2. MAIN METHOD
    // ==========================================

    public static void main(String[] args) {

        // Create a Scanner to accept keyboard input
        Scanner input = new Scanner(System.in);


        // ==========================================
        // 3. REGISTRATION
        // ==========================================

        System.out.println("===== REGISTRATION =====");

        // Capture first name
        System.out.print("Enter your first name: ");
        String firstName = input.nextLine();

        while (firstName.trim().isEmpty()) {
            System.out.println("First name cannot be empty.");
            System.out.print("Enter your first name: ");
            firstName = input.nextLine();
        }

        // Capture last name
        System.out.print("Enter your last name: ");
        String lastName = input.nextLine();

        while (lastName.trim().isEmpty()) {
            System.out.println("Last name cannot be empty.");
            System.out.print("Enter your last name: ");
            lastName = input.nextLine();
        }

        // Capture username
        System.out.print("Enter your username: ");
        String username = input.nextLine();

        while (username.trim().isEmpty()) {
            System.out.println("Username cannot be empty.");
            System.out.print("Enter your username: ");
            username = input.nextLine();
        }


        // ==========================================
        // 4. CREATE LOGIN OBJECT
        // ==========================================

        Login login = new Login(
                firstName,
                lastName,
                username,
                "",
                ""
        );

        // Validate username
        if (!login.checkUserName()) {
            System.out.println(
                    "Username is not correctly formatted; "
                    + "please ensure that your username "
                    + "contains an underscore and is no more "
                    + "than five characters in length."
            );

            input.close();
            return;
        }

        System.out.println("Username successfully captured.");


        // ==========================================
        // 5. PASSWORD REGISTRATION
        // ==========================================

        System.out.print("Enter your password: ");
        String password = input.nextLine();

        while (password.trim().isEmpty()) {
            System.out.println("Password cannot be empty.");
            System.out.print("Enter your password: ");
            password = input.nextLine();
        }

        login.setPassword(password);

        // Validate password complexity
        if (!login.checkPasswordComplexity()) {
            System.out.println(
                    "Password is not correctly formatted; "
                    + "please ensure that the password contains "
                    + "at least eight characters, a capital letter, "
                    + "a number, and a special character."
            );

            input.close();
            return;
        }

        System.out.println("Password successfully captured.");


        // ==========================================
        // 6. CELLPHONE REGISTRATION
        // ==========================================

        System.out.print("Enter your cellphone number: ");
        String phoneNumber = input.nextLine();

        while (phoneNumber.trim().isEmpty()) {
            System.out.println("Cellphone number cannot be empty.");
            System.out.print("Enter your cellphone number: ");
            phoneNumber = input.nextLine();
        }

        login.setPhoneNumber(phoneNumber);

        // Validate cellphone number
        if (!login.checkCellPhoneNumber()) {
            System.out.println(
                    "Cellphone number is not correctly formatted; "
                    + "please ensure that the cellphone number "
                    + "starts with +27 and is followed by 9 numbers."
            );

            input.close();
            return;
        }

        System.out.println("Cellphone number successfully added.");


        // ==========================================
        // 7. LOGIN
        // ==========================================

        System.out.println();
        System.out.println("===== LOGIN =====");

        // Capture login username
        System.out.print("Enter your username: ");
        String enteredUsername = input.nextLine();

        while (enteredUsername.trim().isEmpty()) {
            System.out.println("Username cannot be empty.");
            System.out.print("Enter your username: ");
            enteredUsername = input.nextLine();
        }

        // Check username
        if (!enteredUsername.equals(login.getUsername())) {
            System.out.println(
                    "Username or password incorrect, please try again."
            );

            input.close();
            return;
        }

        // Capture login password
        System.out.print("Enter your password: ");
        String enteredPassword = input.nextLine();

        while (enteredPassword.trim().isEmpty()) {
            System.out.println("Password cannot be empty.");
            System.out.print("Enter your password: ");
            enteredPassword = input.nextLine();
        }

        login.setEnteredUsername(enteredUsername);
        login.setEnteredPassword(enteredPassword);

        // Validate login details
        if (!login.loginUser()) {
            System.out.println(login.returnLoginStatus());

            input.close();
            return;
        }

        System.out.println(login.returnLoginStatus());
        System.out.println("Welcome to QuickChat.");


        // ==========================================
        // 8. SET MESSAGE LIMIT
        // ==========================================

        System.out.print(
                "How many messages would you like to send? "
        );

        int messageLimit;

        while (true) {
            String numberInput = input.nextLine();

            try {
                messageLimit = Integer.parseInt(numberInput);

                if (messageLimit >= 1) {
                    break;
                }

                System.out.print(
                        "Please enter at least 1 message: "
                );

            } catch (NumberFormatException e) {
                System.out.print(
                        "Invalid input. Enter a whole number: "
                );
            }
        }


        // ==========================================
        // 9. MESSAGE TRACKING VARIABLES
        // ==========================================

        int messagesEntered = 0;
        Message lastMessage = null;


        // ==========================================
        // 10. QUICKCHAT MENU
        // ==========================================

        while (true) {

            System.out.println();
            System.out.println("===== QUICKCHAT MENU =====");
            System.out.println("1) Send messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");

            String menuChoice = input.nextLine();

            switch (menuChoice) {

                // ==================================
                // OPTION 1: SEND MESSAGE
                // ==================================

                case "1":

                    // Check message limit
                    if (messagesEntered >= messageLimit) {
                        System.out.println(
                                "You have reached your message limit."
                        );
                        break;
                    }

                    // Capture recipient cellphone number
                    System.out.print(
                            "Enter recipient cellphone number: "
                    );

                    String recipient = input.nextLine();

                    // Validate recipient number
                    Message recipientCheck = new Message(
                            "0000000000",
                            recipient,
                            "Validation",
                            0
                    );

                    String recipientStatus =
                            recipientCheck.checkRecipientCell();

                    if (!recipientStatus.equals(
                            "Cell phone number successfully captured."
                    )) {
                        System.out.println(recipientStatus);
                        break;
                    }

                    // Capture message text
                    System.out.print("Enter your message: ");
                    String messageText = input.nextLine();

                    // Check for an empty message
                    if (messageText.trim().isEmpty()) {
                        System.out.println(
                                "Message cannot be empty."
                        );
                        break;
                    }

                    // Check message length
                    if (messageText.length() > 250) {
                        System.out.println(
                                "Message exceeds 250 characters by "
                                + (messageText.length() - 250)
                                + "; please reduce the size."
                        );
                        break;
                    }

                    // Create the message object
                    Message message = new Message(
                            recipient,
                            messageText
                    );

                    // Generate and display message hash
                    System.out.println(
                            "Message Hash: "
                            + message.createMessageHash()
                    );

                    // Display message actions
                    System.out.println("1) Send Message");
                    System.out.println("2) Disregard Message");
                    System.out.println("3) Store Message");
                    System.out.print("Choose an option: ");

                    String actionChoice = input.nextLine();

                    // Process the selected action
                    String actionResult =
                            message.sentMessage(actionChoice);

                    System.out.println(actionResult);

                    // Handle message deletion
                    if (actionChoice.equals("2")) {

                        System.out.print(
                                "Enter 0 to delete the message: "
                        );

                        String deleteChoice = input.nextLine();

                        if (deleteChoice.equals("0")) {
                            System.out.println("Message deleted.");
                        } else {
                            System.out.println(
                                    "Invalid input. "
                                    + "Message was not deleted."
                            );
                        }
                    }

                    // Display details of sent messages
                    if (actionChoice.equals("1")
                            && actionResult.equals(
                                    "Message successfully sent."
                            )) {

                        System.out.println(
                                "Message ID: "
                                + message.getMessageID()
                        );

                        System.out.println(
                                "Recipient: " + recipient
                        );

                        System.out.println(
                                "Message: " + messageText
                        );
                    }

                    // Track the latest processed message
                    lastMessage = message;

                    // Increase count for a valid action
                    if (actionChoice.equals("1")
                            || actionChoice.equals("2")
                            || actionChoice.equals("3")) {

                        messagesEntered++;
                    }

                    break;


                // ==================================
                // OPTION 2: SHOW SENT MESSAGES
                // ==================================

                case "2":

                    if (lastMessage == null) {
                        System.out.println(
                                "No messages have been sent yet."
                        );
                    } else {
                        System.out.println(
                                "===== RECENTLY SENT MESSAGES ====="
                        );

                        System.out.println(
                                lastMessage.printMessages()
                        );
                    }

                    break;


                // ==================================
                // OPTION 3: QUIT
                // ==================================

                case "3":

                    System.out.println("Exiting QuickChat.");

                    if (lastMessage != null) {
                        System.out.println(
                                "Total messages sent: "
                                + lastMessage.returnTotalMessages()
                        );
                    } else {
                        System.out.println(
                                "Total messages sent: 0"
                        );
                    }

                    input.close();
                    return;


                // ==================================
                // INVALID MENU OPTION
                // ==================================

                default:

                    System.out.println(
                            "Invalid option. "
                            + "Please choose 1, 2, or 3."
                    );
            }
        }
    }
}
