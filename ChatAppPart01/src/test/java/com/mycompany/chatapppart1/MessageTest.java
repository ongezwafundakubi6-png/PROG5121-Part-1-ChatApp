
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt
 * to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java
 * to edit this template
 */

package com.mycompany.chatapppart1;

// ==========================================
// 1. IMPORTS
// ==========================================

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

// ==========================================
// 2. MESSAGE TEST CLASS
// ==========================================

public class MessageTest {

    // ==========================================
    // 3. SETUP METHOD
    // ==========================================

    // Reset message arrays and JSON filename before each test.
    @BeforeEach
    public void setUp() {

        Message.resetMessages();
        Message.resetFileName();
    }

    // ==========================================
    // 4. MESSAGE ID TESTS
    // ==========================================

    @Test
    public void testMessageIDGenerated() {

        // Create a message using automatic ID generation.
        Message message = new Message(
                "+271234567",
                "Hi Mike, join us tonight?"
        );

        // Check that the message ID contains exactly ten digits.
        assertTrue(message.checkMessageID());

        assertTrue(message.getMessageID().matches("\\d{10}"));
    }

    @Test
    public void testInvalidMessageID() {

        // Create a message with an ID longer than ten characters.
        Message message = new Message(
                "12345678901",
                "+27123456789",
                "Hello",
                0
        );

        // The ID should be rejected.
        assertFalse(message.checkMessageID());
    }

    // ==========================================
    // 5. MESSAGE LENGTH TESTS
    // ==========================================

    @Test
    public void testMessageLengthWithinLimit() {

        // Create a message with fewer than 250 characters.
        Message message = new Message(
                "0012345678",
                "+271234567",
                "Hi Mike, can you join us for dinner tonight?",
                0
        );

        // Check the expected success response.
        assertEquals(
                "Message sent",
                message.checkMessageLength()
        );
    }

    @Test
    public void testMessageLengthExceedsLimit() {

        // Create a message containing 260 characters.
        String longMessage = "A".repeat(260);

        Message message = new Message(
                "0012345678",
                "+271234567",
                longMessage,
                0
        );

        // Check the exact error message.
        assertEquals(
                "Message exceeds 250 characters by 10; please reduce the size.",
                message.checkMessageLength()
        );
    }

    // ==========================================
    // 6. RECIPIENT CELLPHONE VALIDATION TESTS
    // ==========================================

    @Test
    public void testValidRecipientCell() {

        // Use a valid South African number in international format.
        Message message = new Message(
                "0012345678",
                "+27123456789",
                "Hello",
                0
        );

        assertEquals(
                "Cell phone number successfully captured.",
                message.checkRecipientCell()
        );
    }

    @Test
    public void testInvalidRecipientCell() {

        // Use a number in local format without the international code.
        Message message = new Message(
                "0012345678",
                "085575975889",
                "Hello",
                0
        );

        assertEquals(
                "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                message.checkRecipientCell()
        );
    }

    // ==========================================
    // 7. MESSAGE HASH TESTS
    // ==========================================

    @Test
    public void testMessageHash() {

        // Use a fixed ID and message number to test the exact hash.
        Message message = new Message(
                "0012345678",
                "+271234567",
                "Hi Mike, can you join us for dinner tonight?",
                0
        );

        assertEquals(
                "00:0:HITONIGHT",
                message.createMessageHash()
        );
    }

    @Test
    public void testRemainingMessageHashesInLoop() {

        // Supply multiple IDs and message texts with known expected hashes.
        String[] ids = {
            "0012345678",
            "1212345678",
            "9912345678"
        };

        String[] messages = {
            "Hi Mike, can you join us tonight?",
            "Hello Sam, see you tomorrow!",
            "Good morning, have a great day."
        };

        String[] expectedHashes = {
            "00:0:HITONIGHT",
            "12:1:HELLOTOMORROW",
            "99:2:GOODDAY"
        };

        // Check every expected hash.
        for (int i = 0; i < ids.length; i++) {

            Message message = new Message(
                    ids[i],
                    "+271234567",
                    messages[i],
                    i
            );

            assertEquals(
                    expectedHashes[i],
                    message.createMessageHash()
            );
        }
    }

    // ==========================================
    // 8. SEND MESSAGE TEST
    // ==========================================

    @Test
    public void testSendMessage() {

        // Create a message and choose the send action.
        Message message = new Message(
                "0012345678",
                "+271234567",
                "Hello",
                0
        );

        assertEquals(
                "Message successfully sent.",
                message.sentMessage("1")
        );
    }

    // ==========================================
    // 9. DISREGARD MESSAGE TEST
    // ==========================================

    @Test
    public void testDisregardMessage() {

        // Create a message and choose the disregard action.
        Message message = new Message(
                "0012345678",
                "+271234567",
                "Hello",
                0
        );

        assertEquals(
                "Press 0 to delete the message.",
                message.sentMessage("2")
        );
    }

    // ==========================================
    // 10. STORE MESSAGE TEST
    // ==========================================

    @Test
    public void testStoreMessage() {

        // Create a message using a separate test JSON file.
        Message message = new Message(
                "0012345678",
                "+271234567",
                "Hello",
                0,
                "test-messages.json"
        );

        try {

            // Check that the message is successfully stored.
            assertEquals(
                    "Message successfully stored.",
                    message.sentMessage("3")
            );

        } finally {

            // Restore the application's default JSON filename.
            Message.resetFileName();
        }
    }

    // ==========================================
    // 11. PART 3 ARRAY TESTS
    // ==========================================

    @Test
    public void testPart3ArraysWithRequiredTestData() {

        // Message 1: Sent.
        Message messageOne = new Message(
                "1111111111",
                "+27834557896",
                "Did you get the cake?",
                0
        );

        // Message 2: Stored.
        Message messageTwo = new Message(
                "2222222222",
                "+27838884567",
                "Where are you? You are late! I have asked you to be on time.",
                1,
                "part3-test-messages.json"
        );

        // Message 3: Disregarded.
        Message messageThree = new Message(
                "3333333333",
                "+27834484567",
                "Yohoooo, I am at your gate.",
                2
        );

        // Message 4: Sent.
        Message messageFour = new Message(
                "4444444444",
                "+27838884567",
                "It is dinner time !",
                3
        );

        // Message 5: Stored.
        Message messageFive = new Message(
                "5555555555",
                "+27838884567",
                "Ok, I am leaving without you.",
                4,
                "part3-test-messages.json"
        );

        // Add each message using its required status.
        assertEquals(
                "Message successfully sent.",
                messageOne.sentMessage("1")
        );

        assertEquals(
                "Message successfully stored.",
                messageTwo.sentMessage("3")
        );

        assertEquals(
                "Press 0 to delete the message.",
                messageThree.sentMessage("2")
        );

        assertEquals(
                "Message successfully sent.",
                messageFour.sentMessage("1")
        );

        assertEquals(
                "Message successfully stored.",
                messageFive.sentMessage("3")
        );

        // Verify the expected number of messages in each array.
        assertEquals(2, messageOne.getSentMessagesCount());
        assertEquals(1, messageOne.getDisregardedMessagesCount());
        assertEquals(2, messageOne.getStoredMessagesCount());
        assertEquals(5, messageOne.getMessageHashesCount());
        assertEquals(5, messageOne.getMessageIDsCount());
    }

    // ==========================================
    // 12. SEARCH MESSAGES BY RECIPIENT TEST
    // ==========================================

    @Test
    public void testSearchMessagesByRecipient() {

        // Create two stored messages for the same recipient.
        Message messageOne = new Message(
                "2222222222",
                "+27838884567",
                "Where are you? You are late! I have asked you to be on time.",
                1,
                "search-test-messages.json"
        );

        Message messageTwo = new Message(
                "5555555555",
                "+27838884567",
                "Ok, I am leaving without you.",
                4,
                "search-test-messages.json"
        );

        // Store both messages.
        messageOne.sentMessage("3");
        messageTwo.sentMessage("3");

        // Search for the recipient.
        String result = messageOne.searchMessagesByRecipient(
                "+27838884567"
        );

        // Confirm that both messages are found.
        assertTrue(result.contains(
                "Where are you? You are late! I have asked you to be on time."
        ));

        assertTrue(result.contains(
                "Ok, I am leaving without you."
        ));

        Message.resetFileName();
    }

    // ==========================================
    // 13. DELETE MESSAGE BY HASH TEST
    // ==========================================

    @Test
    public void testDeleteMessageByHash() {

        // Create a message using a separate test JSON file.
        Message message = new Message(
                "6666666666",
                "+27838884567",
                "Where are you? You are late! I have asked you to be on time.",
                1,
                "delete-test-messages.json"
        );

        // Store the message.
        message.sentMessage("3");

        // Get the message hash.
        String hash = message.createMessageHash();

        // Delete the message using its hash.
        assertEquals(
                "Message successfully deleted.",
                message.deleteMessageByHash(hash)
        );

        // Confirm that the stored message was removed.
        assertEquals(0, message.getStoredMessagesCount());

        Message.resetFileName();
    }

    // ==========================================
    // 14. DISPLAY MESSAGE REPORT TEST
    // ==========================================

    @Test
    public void testDisplayMessageReport() {

        // Create the first sent message.
        Message messageOne = new Message(
                "1111111111",
                "+27834557896",
                "Did you get the cake?",
                0
        );

        // Create the second sent message.
        Message messageTwo = new Message(
                "4444444444",
                "+27838884567",
                "It is dinner time !",
                3
        );

        // Send both messages.
        messageOne.sentMessage("1");
        messageTwo.sentMessage("1");

        // Generate the full message report.
        String report = messageOne.displayMessageReport();

        // Check that both sent messages appear.
        assertTrue(report.contains("Did you get the cake?"));
        assertTrue(report.contains("It is dinner time !"));

        // Check the total number of sent messages.
        assertTrue(report.contains("Total Sent Messages: 2"));
    }

    // ==========================================
    // 15. SEARCH MESSAGE BY ID TEST
    // ==========================================

    @Test
    public void testSearchMessageByID() {

        // Create a stored message with a specific message ID.
        Message message = new Message(
                "7777777777",
                "+27838884567",
                "Hello, this is a stored message.",
                0,
                "search-id-test.json"
        );

        // Store the message.
        message.sentMessage("3");

        // Search for the stored message using its ID.
        String result = message.searchMessageByID("7777777777");

        // Confirm that the correct message and recipient are displayed.
        assertTrue(result.contains("Hello, this is a stored message."));
        assertTrue(result.contains("+27838884567"));

        // Reset the JSON filename to the application's default file.
        Message.resetFileName();
    }

    // ==========================================
    // 16. DISPLAY LONGEST MESSAGE TEST
    // ==========================================

    @Test
    public void testDisplayLongestMessage() {

        // Create a short stored message.
        Message shortMessage = new Message(
                "8888888888",
                "+27838884567",
                "Hello.",
                0,
                "longest-test.json"
        );

        // Create a longer stored message.
        Message longMessage = new Message(
                "9999999999",
                "+27838884567",
                "This is the longest stored message in this test.",
                1,
                "longest-test.json"
        );

        // Store both messages.
        shortMessage.sentMessage("3");
        longMessage.sentMessage("3");

        // Display the longest stored message.
        String result = longMessage.displayLongestMessage();

        // Confirm that the longest message is displayed.
        assertTrue(result.contains(
                "This is the longest stored message in this test."
        ));

        // Reset the JSON filename to the application's default file.
        Message.resetFileName();
    }

    // ==========================================
    // 17. DISPLAY SENDER AND RECIPIENT TEST
    // ==========================================

    @Test
    public void testDisplaySenderAndRecipient() {

        // Create a message with a specific recipient.
        Message message = new Message(
                "1010101010",
                "+27838884567",
                "Hello there.",
                0,
                "sender-recipient-test.json"
        );

        // Set the sender's cellphone number.
        message.setSender("0838884567");

        // Store the message.
        message.sentMessage("3");

        // Display the sender and recipient details.
        String result = message.displaySenderAndRecipient();

        // Confirm that both cellphone numbers are displayed.
        assertTrue(result.contains("0838884567"));
        assertTrue(result.contains("+27838884567"));

        // Reset the JSON filename to the application's default file.
        Message.resetFileName();
    }

} // End of MessageTest class
