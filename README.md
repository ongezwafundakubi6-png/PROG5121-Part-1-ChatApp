Student Name:Ongezwa Hlobisa Fundakubi
Student number :ST10479137
Module Name: Programming 1A
Module Code: PROG5121
Assignment: ChatApp Part 1 project

 ChatApp Part 3

 PROJECT OVERVIEW

Assessment: Part 3 part 2
Project Overview
Code: PROG5121 Assignment – ChatApp Part 1 Project
ChatApp Part 3
PROJECT OVERVIEW
Assessment: Part 1, Part 2 and Part 3
This Java console application was created for the PROG5121 assignment. It allows users to register and log in, then create, send, store, disregard, and manage messages. Part 3 adds a stored-messages menu, message searches and reports, and persistent JSON storage.

Features

Validates usernames for an underscore and a maximum length of five characters
Checks password complexity requirements
Validates South African cellphone numbers in international format (+27 followed by nine digits)
Registers users and authenticates login credentials
Displays a welcome message using the user’s first and last name
Creates unique ten-digit message IDs and message hashes
Lets users send, disregard, store, and view messages
Loads stored messages from storedMessages.json after login
Displays the sender and recipient of stored messages
Finds the longest stored message
Searches by message ID or recipient
Deletes stored messages by message hash and updates the JSON file
Displays a report of stored and sent messages
Includes JUnit tests for login, messaging, and message management

Technologies Used

Java 17 or later
Apache Maven 3.8 or later
NetBeans
Gson 2.10.1
JUnit 5
GitHub Actions

Project Structure
The Maven project contains the following main files:

src/main/java/com/mycompany/chatapppart1/Main.java – Runs the console application and handles user input.
src/main/java/com/mycompany/chatapppart1/Login.java – Handles registration, validation, and login logic.
src/main/java/com/mycompany/chatapppart1/Message.java – Handles message validation, IDs, hashes, sending, and storage.
src/main/java/com/mycompany/chatapppart1/MessageManager.java – Manages message arrays, searches, reports, and JSON storage.
src/test/java/com/mycompany/chatapppart1/LoginTest.java – Tests registration and login features.
src/test/java/com/mycompany/chatapppart1/MessageTest.java – Tests message features.
src/test/java/com/mycompany/chatapppart1/MessageManagerTest.java – Tests arrays, JSON reload, searches, deletion, reports, and edge cases.
pom.xml – Maven project configuration and dependencies.
storedMessages.json – Stores messages marked as stored.

How to Run

Open the project folder as a Maven project in NetBeans.
Make sure Java 17 or later is configured.
Run Main.java from the com.mycompany.chatapppart1 package.
Follow the registration and login prompts in the console.

Alternatively, open a terminal in the project folder and run:mvn compile exec:java -Dexec.mainClass=com.mycompany.chatapppart1.Main
