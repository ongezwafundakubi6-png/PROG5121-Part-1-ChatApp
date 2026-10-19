/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapppart1;
// Imports
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The Message class handles message details and validation.
 * @author Student
 */
public class Message {

    // ==========================================
    // 1. VARIABLES
    // ==========================================

    private String messageID;
    private String recipient;
    private String message;
    private String messageHash;

    private static int totalMessages = 0;
    private static List<Message> sentMessages = new ArrayList<>();
    private int messageNumber;
    private static int nextMessageNumber = 0;


    // ==========================================
    // 2. CONSTRUCTORS
    // ==========================================

    // Constructor with automatically generated message ID
    public Message(String recipient, String message) {
        this.messageID = generateMessageID();
        this.recipient = recipient;
        this.message = message;
        this.messageNumber = nextMessageNumber++;
    }

    // Constructor with supplied message ID
    public Message(String messageID, String recipient, String message) {
        this.messageID = messageID;
        this.recipient = recipient;
        this.message = message;
        this.messageNumber = nextMessageNumber++;
    }

    // Constructor with a specific message number
    public Message(String messageID, String recipient,
                   String message, int messageNumber) {
        this.messageID = messageID;
        this.recipient = recipient;
        this.message = message;
        this.messageNumber = messageNumber;
    }


    // ==========================================
    // 3. GETTERS
    // ==========================================

    public String getMessageID() {
        return messageID;
    }


    // ==========================================
    // 4. GENERATE MESSAGE ID
    // ==========================================

    public String generateMessageID() {
        long randomID = ThreadLocalRandom.current()
                .nextLong(1000000000L, 10000000000L);

        return String.valueOf(randomID);
    }


    // ==========================================
    // 5. VALIDATE MESSAGE ID
    // ==========================================

    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }


    // ==========================================
    // 6. VALIDATE MESSAGE LENGTH
    // ==========================================

    public String checkMessageLength() {
        if (message == null || message.trim().isEmpty()) {
            return "Message cannot be empty.";
        }

        if (message.length() > 250) {
            int excessCharacters = message.length() - 250;

            return "Message exceeds 250 characters by "
                    + excessCharacters
                    + "; please reduce the size.";
        }

        return "Message sent";
    }


    // ==========================================
    // 7. VALIDATE RECIPIENT CELL NUMBER
    // ==========================================

    public String checkRecipientCell() {
        if (recipient != null
                && recipient.matches("\\+27[0-9]{9}")) {

            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted "
                + "or does not contain an international code. "
                + "Please correct the number and try again.";
    }


    // ==========================================
    // 8. CREATE MESSAGE HASH
    // ==========================================

    public String createMessageHash() {
        if (messageID == null || messageID.length() < 2
                || message == null || message.trim().isEmpty()) {
            return "";
        }

        String firstTwoDigits = messageID.substring(0, 2);

        String cleanedMessage = message.replaceAll(
                "[^a-zA-Z0-9\\s]", "");

        String[] words = cleanedMessage.trim().split("\\s+");

        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        messageHash = (firstTwoDigits + ":"
                + messageNumber + ":"
                + firstWord + lastWord).toUpperCase();

        return messageHash;
    }


    // ==========================================
    // 9. SEND, DISREGARD OR STORE MESSAGE
    // ==========================================

    public String sentMessage(String choice) {
        switch (choice) {
            case "1":
                sentMessages.add(this);
                totalMessages++;
                return "Message successfully sent.";

            case "2":
                return "Press 0 to delete the message.";

            case "3":
                return storeMessage();

            default:
                return "Invalid choice. Please select 1, 2, or 3.";
        }
    }


    // ==========================================
    // 10. DISPLAY SENT MESSAGES
    // ==========================================

    public String printMessages() {
        StringBuilder output = new StringBuilder();

        for (Message sentMessage : sentMessages) {
            output.append("Message ID: ")
                    .append(sentMessage.messageID)
                    .append("\nMessage Hash: ")
                    .append(sentMessage.messageHash)
                    .append("\nRecipient: ")
                    .append(sentMessage.recipient)
                    .append("\nMessage: ")
                    .append(sentMessage.message)
                    .append("\n");
        }

        if (output.length() == 0) {
            return "No messages have been sent yet.";
        }

        return output.toString();
    }


    // ==========================================
    // 11. RETURN TOTAL MESSAGES
    // ==========================================

    public int returnTotalMessages() {
        return totalMessages;
    }


    // ==========================================
    // 12. STORE MESSAGE IN JSON FILE
    // ==========================================

    public String storeMessage() {
        String fileName = "messages.json";
        List<String> jsonMessages = new ArrayList<>();

        File file = new File(fileName);

        // Read existing JSON content
        if (file.exists()) {
            try (Scanner fileReader = new Scanner(file)) {
                StringBuilder content = new StringBuilder();

                while (fileReader.hasNextLine()) {
                    content.append(fileReader.nextLine());
                }

                String existing = content.toString().trim();

                if (existing.startsWith("[")
                        && existing.endsWith("]")) {

                    existing = existing.substring(
                            1, existing.length() - 1).trim();

                    if (!existing.isEmpty()) {
                        jsonMessages.add(existing);
                    }
                }

            } catch (IOException e) {
                return "Unable to read the message storage file.";
            }
        }

        // Escape special characters for JSON
        String safeMessage = message
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");

        String safeRecipient = recipient
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");

        String safeHash = createMessageHash()
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");

        // Create JSON object
        String newObject = "{"
                + "\"messageID\":\"" + messageID + "\","
                + "\"messageHash\":\"" + safeHash + "\","
                + "\"recipient\":\"" + safeRecipient + "\","
                + "\"message\":\"" + safeMessage + "\""
                + "}";

        jsonMessages.add(newObject);

        // Write messages to JSON file
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("[\n");

            for (int i = 0; i < jsonMessages.size(); i++) {
                writer.write(jsonMessages.get(i));

                if (i < jsonMessages.size() - 1) {
                    writer.write(",");
                }

                writer.write("\n");
            }

            writer.write("]");

            return "Message successfully stored.";

        } catch (IOException e) {
            return "Unable to store the message.";
        }
    }
}
