/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.chatapppart1;

// ==========================================
// 1. IMPORTS
// ==========================================

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


// ==========================================
// 2. MESSAGE TEST CLASS
// ==========================================

public class MessageTest {

    // ==========================================
    // 3. TEST MESSAGE ID GENERATION
    // ==========================================

    @Test
    public void testMessageIDGenerated() {

        // Create a message using automatic ID generation
        Message message = new Message(
                "+27123456789",
                "Hi Mike, join us tonight?"
        );

        // Check that the message ID contains exactly ten digits
        assertTrue(message.checkMessageID());
        assertTrue(message.getMessageID().matches("\\d{10}"));
    }


    // ==========================================
    // 4. TEST MESSAGE LENGTH WITHIN LIMIT
    // ==========================================

    @Test
    public void testMessageLengthWithinLimit() {

        // Create a message with fewer than 250 characters
        Message message = new Message(
                "0012345678",
                "+27123456789",
                "Hi Mike, can you join us for dinner tonight?",
                0
        );

        // Check the expected success response
        assertEquals(
                "Message sent",
                message.checkMessageLength()
        );
    }


    // ==========================================
    // 5. TEST MESSAGE LENGTH EXCEEDS LIMIT
    // ==========================================

    @Test
    public void testMessageLengthExceedsLimit() {

        // Create a message containing 260 characters
        String longMessage = "A".repeat(260);

        Message message = new Message(
                "0012345678",
                "+27123456789",
                longMessage,
                0
        );

        // Check the exact error message
        assertEquals(
                "Message exceeds 250 characters by 10; "
                + "please reduce the size.",
                message.checkMessageLength()
        );
    }


    // ==========================================
    // 6. TEST VALID RECIPIENT CELL NUMBER
    // ==========================================

    @Test
    public void testValidRecipientCell() {

        // Use a valid South African international number
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


    // ==========================================
    // 7. TEST INVALID RECIPIENT CELL NUMBER
    // ==========================================

    @Test
    public void testInvalidRecipientCell() {

        // Use a local-format number without the international code
        Message message = new Message(
                "0012345678",
                "085575975889",
                "Hello",
                0
        );

        assertEquals(
                "Cell phone number is incorrectly formatted "
                + "or does not contain an international code. "
                + "Please correct the number and try again.",
                message.checkRecipientCell()
        );
    }


    // ==========================================
    // 8. TEST MESSAGE HASH
    // ==========================================

    @Test
    public void testMessageHash() {

        // Use a fixed ID and message number to test the hash
        Message message = new Message(
                "0012345678",
                "+27123456789",
                "Hi Mike, can you join us for dinner tonight?",
                0
        );

        assertEquals(
                "00:0:HITONIGHT",
                message.createMessageHash()
        );
    }


    // ==========================================
    // 9. TEST MULTIPLE MESSAGE HASHES
    // ==========================================

    @Test
    public void testRemainingMessageHashesInLoop() {

        // Supply multiple IDs
        String[] ids = {
            "0012345678",
            "1212345678",
            "9912345678"
        };

        // Supply different message texts
        String[] messages = {
            "Hi Mike, can you join us tonight?",
            "Hello Sam, see you tomorrow!",
            "Good morning, have a great day."
        };

        // Expected hash results
        String[] expectedHashes = {
            "00:0:HITONIGHT",
            "12:1:HELLOTOMORROW",
            "99:2:GOODDAY"
        };

        // Check every expected hash
        for (int i = 0; i < ids.length; i++) {

            Message message = new Message(
                    ids[i],
                    "+27123456789",
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
    // 10. TEST SENDING A MESSAGE
    // ==========================================

    @Test
    public void testSendMessage() {

        // Choose the send action
        Message message = new Message(
                "0012345678",
                "+27123456789",
                "Hello",
                0
        );

        assertEquals(
                "Message successfully sent.",
                message.sentMessage("1")
        );
    }


    // ==========================================
    // 11. TEST DISREGARDING A MESSAGE
    // ==========================================

    @Test
    public void testDisregardMessage() {

        // Choose the disregard action
        Message message = new Message(
                "0012345678",
                "+27123456789",
                "Hello",
                0
        );

        assertEquals(
                "Press 0 to delete the message.",
                message.sentMessage("2")
        );
    }


    // ==========================================
    // 12. TEST STORING A MESSAGE
    // ==========================================

    @Test
    public void testStoreMessage() {

        // Choose the store action
        Message message = new Message(
                "0012345678",
                "+27123456789",
                "Hello",
                0
        );

        assertEquals(
                "Message successfully stored.",
                message.sentMessage("3")
        );
    }


    // ==========================================
    // 13. TEST INVALID MESSAGE ID
    // ==========================================

    @Test
    public void testInvalidMessageID() {

        // Create a message with an ID longer than ten characters
        Message message = new Message(
                "12345678901",
                "+27123456789",
                "Hello",
                0
        );

        // The ID should be rejected
        assertFalse(message.checkMessageID());
    }

}
