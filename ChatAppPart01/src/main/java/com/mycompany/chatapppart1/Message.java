
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt
 * to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java
 * to edit this template
 */

package com.mycompany.chatapppart1;

// ==========================================
// 1. IMPORTS
// ==========================================

import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Message class handles message details,
 * validation and message storage.
 *
 * @author Student
 */
public class Message {

    // ==========================================
    // 2. INSTANCE VARIABLES
    // ==========================================

    // Variables used to store message information.
    private String messageID;
    private String recipient;
    private String message;
    private String messageHash;
    private String sender;

    // Stores the total number of successfully sent messages.
    private static int totalMessages = 0;

    // Stores the default JSON filename for messages.
    private static String fileName =
            System.getProperty("user.dir") + "/messages.json";

    // Arrays used to store messages according to their status.
    private static Message[] sentMessages = new Message[0];
    private static Message[] disregardedMessages = new Message[0];
    private static Message[] storedMessages = new Message[0];

    // Arrays used to store the hashes and IDs of all messages.
    private static String[] messageHashes = new String[0];
    private static String[] messageIDs = new String[0];

    // Stores the number assigned to this message, starting at 0.
    private int messageNumber;

    // Automatically assigned to this message, starting at 0.
    private static int nextMessageNumber = 0;

    // ==========================================
    // 3. CONSTRUCTORS
    // ==========================================

    // Creates a message with a custom JSON storage filename for testing.
    public Message(String messageID, String recipient,
            String message, int messageNumber, String fileName) {

        this.messageID = messageID;
        this.recipient = recipient;
        this.message = message;
        this.messageNumber = messageNumber;

        // Use the supplied filename for storing this message.
        Message.fileName = fileName;
    }

    // Creates a message with an automatically generated ID.
    public Message(String recipient, String message) {

        this.messageID = generateMessageID();
        this.recipient = recipient;
        this.message = message;
        this.messageNumber = nextMessageNumber++;
    }

    // Creates a message with a supplied ID for unit testing.
    public Message(String messageID, String recipient, String message) {

        this.messageID = messageID;
        this.recipient = recipient;
        this.message = message;
        this.messageNumber = nextMessageNumber++;
    }

    // Creates a message with a specific number for unit testing.
    public Message(String messageID, String recipient,
            String message, int messageNumber) {

        this.messageID = messageID;
        this.recipient = recipient;
        this.message = message;
        this.messageNumber = messageNumber;
    }

    // ==========================================
    // 4. MESSAGE ARRAY MANAGEMENT
    // ==========================================

    // Adds a message to the sent messages array.
    private static void addSentMessage(Message newMessage) {

        Message[] updatedArray =
                new Message[sentMessages.length + 1];

        for (int i = 0; i < sentMessages.length; i++) {
            updatedArray[i] = sentMessages[i];
        }

        updatedArray[updatedArray.length - 1] = newMessage;
        sentMessages = updatedArray;
    }

    // Adds a message to the disregarded messages array.
    private static void addDisregardedMessage(Message newMessage) {

        Message[] updatedArray =
                new Message[disregardedMessages.length + 1];

        for (int i = 0; i < disregardedMessages.length; i++) {
            updatedArray[i] = disregardedMessages[i];
        }

        updatedArray[updatedArray.length - 1] = newMessage;
        disregardedMessages = updatedArray;
    }

    // Adds a message to the stored messages array.
    private static void addStoredMessage(Message newMessage) {

        Message[] updatedArray =
                new Message[storedMessages.length + 1];

        for (int i = 0; i < storedMessages.length; i++) {
            updatedArray[i] = storedMessages[i];
        }

        updatedArray[updatedArray.length - 1] = newMessage;
        storedMessages = updatedArray;
    }

    // Adds a message hash to the message hashes array.
    private static void addMessageHash(String newHash) {

        String[] updatedArray =
                new String[messageHashes.length + 1];

        for (int i = 0; i < messageHashes.length; i++) {
            updatedArray[i] = messageHashes[i];
        }

        updatedArray[updatedArray.length - 1] = newHash;
        messageHashes = updatedArray;
    }

    // Adds a message ID to the message IDs array.
    private static void addMessageID(String newID) {

        String[] updatedArray =
                new String[messageIDs.length + 1];

        for (int i = 0; i < messageIDs.length; i++) {
            updatedArray[i] = messageIDs[i];
        }

        updatedArray[updatedArray.length - 1] = newID;
        messageIDs = updatedArray;
    }

    // Adds the message ID and hash to their respective arrays.
    private void addMessageDetails() {

        String hash = createMessageHash();

        addMessageID(messageID);
        addMessageHash(hash);
    }

    // ==========================================
    // 5. FILE NAME MANAGEMENT
    // ==========================================

    // Resets the JSON filename to the application's default file.
    public static void resetFileName() {

        fileName = System.getProperty("user.dir") + "/messages.json";
    }

    // ==========================================
    // 6. MESSAGE ID MANAGEMENT
    // ==========================================

    // Returns the message ID.
    public String getMessageID() {

        return messageID;
    }

    // Generates a random ten-digit message ID.
    public String generateMessageID() {

        long randomID = java.util.concurrent.ThreadLocalRandom
                .current()
                .nextLong(1000000000L, 10000000000L);

        return String.valueOf(randomID);
    }

    // Checks that the message ID contains exactly ten digits.
    public boolean checkMessageID() {

        return messageID != null && messageID.matches("\\d{10}");
    }

    // ==========================================
    // 7. MESSAGE VALIDATION
    // ==========================================

    // Checks whether the message exceeds 250 characters.
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

    // Checks the recipient number's format.
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
    // 8. MESSAGE HASH GENERATION
    // ==========================================

    // Creates a hash using the ID, message number,
    // first word and last word.
    public String createMessageHash() {

        if (messageID == null || messageID.length() < 2
                || message == null || message.trim().isEmpty()) {

            return "";
        }

        // Get the first two digits of the message ID.
        String firstTwoDigits = messageID.substring(0, 2);

        // Remove punctuation and split the message into words.
        String cleanedMessage =
                message.replaceAll("[^a-zA-Z0-9\\s]", "");

        String[] words = cleanedMessage.trim().split("\\s+");

        // Get the first and last words.
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        // Build the hash and convert it to uppercase.
        messageHash = (firstTwoDigits + ":"
                + messageNumber + ":"
                + firstWord + lastWord).toUpperCase();

        return messageHash;
    }

    // ==========================================
    // 9. SEND, DISREGARD OR STORE MESSAGE
    // ==========================================

    // Allows the user to send, disregard or store a message.
    public String sentMessage(String choice) {

        switch (choice) {

            case "1":

                addSentMessage(this);
                addMessageDetails();
                totalMessages++;

                return "Message successfully sent.";

            case "2":

                addDisregardedMessage(this);
                addMessageDetails();

                return "Press 0 to delete the message.";

            case "3":

                String result = storeMessage();

                if (result.equals("Message successfully stored.")) {
                    addStoredMessage(this);
                    addMessageDetails();
                }

                return result;

            default:

                return "Invalid choice. Please select 1, 2, or 3.";
        }
    }

    // ==========================================
    // 10. DISPLAY SENT MESSAGES
    // ==========================================

    // Displays messages successfully sent during this session.
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

    // Returns the total number of messages sent.
    public int returnTotalMessages() {

        return totalMessages;
    }

    // ==========================================
    // 11. STORE MESSAGE IN JSON FILE
    // ==========================================

    // Stores this message in a JSON file.
    public String storeMessage() {

        List<String> jsonMessages = new ArrayList<>();

        // Read existing messages from the JSON file.
        java.io.File file = new java.io.File(fileName);

        if (file.exists()) {

            try (java.util.Scanner fileReader =
                    new java.util.Scanner(file)) {

                StringBuilder content = new StringBuilder();

                while (fileReader.hasNextLine()) {
                    content.append(fileReader.nextLine());
                }

                String existing = content.toString().trim();

                if (existing.startsWith("[")
                        && existing.endsWith("]")) {

                    existing = existing.substring(
                            1, existing.length() - 1
                    ).trim();

                    if (!existing.isEmpty()) {

                        // Keep existing JSON objects.
                        jsonMessages.add(existing);
                    }
                }

            } catch (IOException e) {

                return "Unable to read the message storage file.";
            }
        }

        // Escape special characters before adding text to JSON.
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

        // Escape the sender's cellphone number.
        String safeSender = sender == null ? ""
                : sender.replace("\\", "\\\\")
                        .replace("\"", "\\\"");

        // Create a JSON object for the current message.
        String newObject = "{"
                + "\"messageID\":\"" + messageID + "\","
                + "\"messageHash\":\"" + safeHash + "\","
                + "\"sender\":\"" + safeSender + "\","
                + "\"recipient\":\"" + safeRecipient + "\","
                + "\"message\":\"" + safeMessage + "\""
                + "}";

        // Add the current message and write the JSON array.
        jsonMessages.add(newObject);

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

    // ==========================================
    // 12. DISPLAY LONGEST STORED MESSAGE
    // ==========================================

    // Finds and displays the longest stored message.
    public String displayLongestMessage() {

        Message longestMessage = null;

        // Search only the stored messages array.
        for (Message currentMessage : storedMessages) {

            if (longestMessage == null
                    || currentMessage.message.length()
                    > longestMessage.message.length()) {

                longestMessage = currentMessage;
            }
        }

        if (longestMessage == null) {
            return "No stored messages available.";
        }

        return "Longest Stored Message: "
                + longestMessage.message;
    }

    // ==========================================
    // 13. SEARCH MESSAGES BY RECIPIENT
    // ==========================================

    // Searches sent and stored messages for a particular recipient.
    public String searchMessagesByRecipient(String searchRecipient) {

        StringBuilder output = new StringBuilder();

        // Search sent messages.
        for (Message currentMessage : sentMessages) {

            if (currentMessage.recipient.equals(searchRecipient)) {

                output.append("Message: ")
                        .append(currentMessage.message)
                        .append("\n");
            }
        }

        // Search stored messages.
        for (Message currentMessage : storedMessages) {

            if (currentMessage.recipient.equals(searchRecipient)) {

                output.append("Message: ")
                        .append(currentMessage.message)
                        .append("\n");
            }
        }

        if (output.length() == 0) {
            return "No messages found for this recipient.";
        }

        return output.toString();
    }

    // ==========================================
    // 14. REMOVE MESSAGES FROM ARRAYS
    // ==========================================

    // Removes a message from an array using its position.
    private static Message[] removeMessageFromArray(
            Message[] messages, int index) {

        Message[] updatedArray =
                new Message[messages.length - 1];

        int newIndex = 0;

        for (int i = 0; i < messages.length; i++) {

            if (i != index) {

                updatedArray[newIndex] = messages[i];
                newIndex++;
            }
        }

        return updatedArray;
    }

    // Removes an ID or hash from a String array.
    private static String[] removeStringFromArray(
            String[] values, int index) {

        String[] updatedArray =
                new String[values.length - 1];

        int newIndex = 0;

        for (int i = 0; i < values.length; i++) {

            if (i != index) {

                updatedArray[newIndex] = values[i];
                newIndex++;
            }
        }

        return updatedArray;
    }

    // ==========================================
    // 15. DELETE MESSAGE USING HASH
    // ==========================================

    // Deletes a message using its message hash.
    public String deleteMessageByHash(String searchHash) {

        // Search the sent messages array.
        for (int i = 0; i < sentMessages.length; i++) {

            if (sentMessages[i].messageHash.equals(searchHash)) {

                Message deletedMessage = sentMessages[i];

                sentMessages =
                        removeMessageFromArray(sentMessages, i);

                removeMessageDetails(deletedMessage);

                return "Message successfully deleted.";
            }
        }

        // Search the stored messages array.
        for (int i = 0; i < storedMessages.length; i++) {

            if (storedMessages[i].messageHash.equals(searchHash)) {

                Message deletedMessage = storedMessages[i];

                storedMessages =
                        removeMessageFromArray(storedMessages, i);

                removeMessageDetails(deletedMessage);

                return "Message successfully deleted.";
            }
        }

        // Search the disregarded messages array.
        for (int i = 0; i < disregardedMessages.length; i++) {

            if (disregardedMessages[i].messageHash.equals(searchHash)) {

                Message deletedMessage = disregardedMessages[i];

                disregardedMessages =
                        removeMessageFromArray(disregardedMessages, i);

                removeMessageDetails(deletedMessage);

                return "Message successfully deleted.";
            }
        }

        return "Message hash not found.";
    }

    // Removes the corresponding message ID and hash.
    private static void removeMessageDetails(Message deletedMessage) {

        for (int i = 0; i < messageIDs.length; i++) {

            if (messageIDs[i].equals(deletedMessage.messageID)) {

                messageIDs = removeStringFromArray(messageIDs, i);
                break;
            }
        }

        for (int i = 0; i < messageHashes.length; i++) {

            if (messageHashes[i].equals(deletedMessage.messageHash)) {

                messageHashes =
                        removeStringFromArray(messageHashes, i);

                break;
            }
        }
    }

    // ==========================================
    // 16. DISPLAY FULL MESSAGE REPORT
    // ==========================================

    // Displays a report containing all sent messages.
    public String displayMessageReport() {

        StringBuilder report = new StringBuilder();

        report.append("========== MESSAGE REPORT ==========\n");

        if (sentMessages.length == 0) {
            return "No sent messages available for the report.";
        }

        for (int i = 0; i < sentMessages.length; i++) {

            report.append("\nMessage Number: ")
                    .append(i + 1)
                    .append("\nMessage ID: ")
                    .append(sentMessages[i].messageID)
                    .append("\nMessage Hash: ")
                    .append(sentMessages[i].messageHash)
                    .append("\nRecipient: ")
                    .append(sentMessages[i].recipient)
                    .append("\nMessage: ")
                    .append(sentMessages[i].message)
                    .append("\n------------------------------------\n");
        }

        report.append("\nTotal Sent Messages: ")
                .append(sentMessages.length);

        return report.toString();
    }

    // ==========================================
    // 17. MESSAGE COUNT METHODS
    // ==========================================

    // Returns the number of sent messages.
    public int getSentMessagesCount() {

        return sentMessages.length;
    }

    // Returns the number of disregarded messages.
    public int getDisregardedMessagesCount() {

        return disregardedMessages.length;
    }

    // Returns the number of stored messages.
    public int getStoredMessagesCount() {

        return storedMessages.length;
    }

    // Returns the number of message hashes.
    public int getMessageHashesCount() {

        return messageHashes.length;
    }

    // Returns the number of message IDs.
    public int getMessageIDsCount() {

        return messageIDs.length;
    }

    // ==========================================
    // 18. JSON DATA EXTRACTION
    // ==========================================

    // Extracts a value from a JSON object.
    private static String getJsonValue(String jsonObject, String key) {

        String expression = "\"" + Pattern.quote(key)
                + "\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\"";

        Matcher matcher =
                Pattern.compile(expression).matcher(jsonObject);

        if (matcher.find()) {
            return unescapeJson(matcher.group(1));
        }

        return null;
    }

    // Converts escaped JSON characters back to normal characters.
    private static String unescapeJson(String value) {

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < value.length(); i++) {

            char current = value.charAt(i);

            if (current == '\\' && i + 1 < value.length()) {

                char next = value.charAt(++i);

                switch (next) {

                    case 'n':
                        result.append('\n');
                        break;

                    case 'r':
                        result.append('\r');
                        break;

                    case 't':
                        result.append('\t');
                        break;

                    case '"':
                        result.append('"');
                        break;

                    case '\\':
                        result.append('\\');
                        break;

                    case '/':
                        result.append('/');
                        break;

                    case 'u':

                        if (i + 4 < value.length()) {

                            String unicode =
                                    value.substring(i + 1, i + 5);

                            result.append(
                                    (char) Integer.parseInt(unicode, 16)
                            );

                            i += 4;
                        }

                        break;

                    default:
                        result.append(next);
                }

            } else {

                result.append(current);
            }
        }

        return result.toString();
    }

    // ==========================================
    // 19. READ STORED MESSAGES FROM JSON FILE
    // ==========================================

    // Reads stored messages from the JSON file.
    public String readStoredMessages() {

        java.io.File file = new java.io.File(fileName);

        if (!file.exists()) {
            return "No stored messages file found.";
        }

        StringBuilder content = new StringBuilder();

        try (java.util.Scanner fileReader =
                new java.util.Scanner(file)) {

            while (fileReader.hasNextLine()) {
                content.append(fileReader.nextLine()).append("\n");
            }

        } catch (IOException e) {

            return "Unable to read stored messages.";
        }

        // Reset the stored messages array before loading the file.
        storedMessages = new Message[0];

        // Track JSON objects without splitting message text incorrectly.
        int depth = 0;
        boolean insideString = false;
        boolean escaped = false;

        StringBuilder jsonObject = new StringBuilder();

        for (int i = 0; i < content.length(); i++) {

            char current = content.charAt(i);

            if (depth > 0) {
                jsonObject.append(current);
            }

            if (insideString) {

                if (escaped) {

                    escaped = false;

                } else if (current == '\\') {

                    escaped = true;

                } else if (current == '"') {

                    insideString = false;
                }

            } else {

                if (current == '"') {

                    insideString = true;

                } else if (current == '{') {

                    depth++;

                    if (depth == 1) {

                        jsonObject.setLength(0);
                        jsonObject.append(current);
                    }

                } else if (current == '}') {

                    depth--;

                    if (depth == 0) {
                        loadJsonMessage(jsonObject.toString());
                    }
                }
            }
        }

        return displayStoredMessages();
    }

    // ==========================================
    // 20. LOAD JSON MESSAGE
    // ==========================================

    // Converts a JSON object into a Message object.
    private static void loadJsonMessage(String jsonObject) {

        // Extract the message details from the JSON object.
        String id = getJsonValue(jsonObject, "messageID");
        String hash = getJsonValue(jsonObject, "messageHash");
        String recipient = getJsonValue(jsonObject, "recipient");
        String messageText = getJsonValue(jsonObject, "message");
        String sender = getJsonValue(jsonObject, "sender");

        // Check that all required message details are available.
        if (id == null || hash == null
                || recipient == null || messageText == null) {

            return;
        }

        // Check whether this message ID already exists.
        for (Message existingMessage : storedMessages) {

            if (existingMessage.messageID.equals(id)) {
                return;
            }
        }

        // Create a message using the information from the JSON file.
        Message loadedMessage = new Message(
                id, recipient, messageText, storedMessages.length
        );

        // Restore the saved message hash and sender.
        loadedMessage.messageHash = hash;
        loadedMessage.setSender(sender);

        // Add the message to the stored messages array.
        addStoredMessage(loadedMessage);

        // Check whether the message ID already exists in the ID array.
        boolean idExists = false;

        for (String existingID : messageIDs) {

            if (existingID.equals(id)) {

                idExists = true;
                break;
            }
        }

        // Check whether the message hash already exists in the hash array.
        boolean hashExists = false;

        for (String existingHash : messageHashes) {

            if (existingHash.equals(hash)) {

                hashExists = true;
                break;
            }
        }

        // Add the message ID if it does not already exist.
        if (!idExists) {
            addMessageID(id);
        }

        // Add the message hash if it does not already exist.
        if (!hashExists) {
            addMessageHash(hash);
        }
    }

    // ==========================================
    // 21. GETTER METHODS
    // ==========================================

    // Returns the recipient's cellphone number.
    public String getRecipient() {

        return recipient;
    }

    // Returns the message content.
    public String getMessage() {

        return message;
    }

    // ==========================================
    // 22. DISPLAY STORED MESSAGES
    // ==========================================

    // This method displays all messages that have been stored.
    public String displayStoredMessages() {

        // Check whether the stored messages array is empty.
        if (storedMessages.length == 0) {

            // Inform the user that no messages have been stored.
            return "No stored messages found.";
        }

        // Create a StringBuilder to collect the message details.
        StringBuilder output = new StringBuilder();

        // Loop through each message in the stored messages array.
        for (Message message : storedMessages) {

            // Display the message ID.
            output.append("Message ID: ")
                    .append(message.getMessageID())
                    .append("\n");

            // Display the recipient's cellphone number.
            output.append("Recipient: ")
                    .append(message.getRecipient())
                    .append("\n");

            // Display the message content.
            output.append("Message: ")
                    .append(message.getMessage())
                    .append("\n");
        }

        // Return all the stored message details.
        return output.toString();
    }

    // ==========================================
    // 23. SENDER MANAGEMENT
    // ==========================================

    // Sets the sender's cellphone number.
    public void setSender(String sender) {

        this.sender = sender;
    }

    // Returns the sender's cellphone number.
    public String getSender() {

        return sender;
    }

    // ==========================================
    // 24. DISPLAY SENDER AND RECIPIENT
    // ==========================================

    // Displays the sender and recipient of all stored messages.
    public String displaySenderAndRecipient() {

        StringBuilder output = new StringBuilder();

        if (storedMessages.length == 0) {
            return "No stored messages available.";
        }

        output.append("===== STORED MESSAGE CONTACTS =====\n");

        for (int i = 0; i < storedMessages.length; i++) {

            output.append("\nMessage ID: ")
                    .append(storedMessages[i].messageID)
                    .append("\nSender: ")
                    .append(storedMessages[i].sender == null
                            ? "Not available"
                            : storedMessages[i].sender)
                    .append("\nRecipient: ")
                    .append(storedMessages[i].recipient)
                    .append("\n");
        }

        return output.toString();
    }

    // ==========================================
    // 25. SEARCH MESSAGE BY ID
    // ==========================================

    // Searches for a message using its message ID.
    public String searchMessageByID(String searchID) {

        for (Message currentMessage : storedMessages) {

            if (currentMessage.messageID.equals(searchID)) {

                return "Recipient: " + currentMessage.recipient
                        + "\nMessage: " + currentMessage.message;
            }
        }

        return "Message ID not found.";
    }

    // ==========================================
    // 26. RESET MESSAGES FOR UNIT TESTING
    // ==========================================

    // Resets the message arrays and counters before each unit test.
    public static void resetMessages() {

        sentMessages = new Message[0];
        disregardedMessages = new Message[0];
        storedMessages = new Message[0];

        messageHashes = new String[0];
        messageIDs = new String[0];

        totalMessages = 0;
        nextMessageNumber = 0;
    }

} // End of Message class
