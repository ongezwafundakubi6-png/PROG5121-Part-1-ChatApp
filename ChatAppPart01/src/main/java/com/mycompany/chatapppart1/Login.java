/*
Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package com.mycompany.chatapppart1;
/**
The Login class handles the registration and login functionality
of the ChatApp application
@author Student
*/
public class Login {
//Variables used to store the user's registration information
private String username;
private String password;
private String phoneNumber;
//Variables used to store the information entered during login
private String enteredUsername;
private String enteredPassword;
//Variables used to store the user's first and last name
private String firstName;
private String lastName;
/*
Construtor used to initialise the user's registration details.
The values entered by the user are assigned to the class variables.
*/
public Login (String firstName, String lastName,String username, String password, String phoneNumber){
this.firstName = firstName;
this.lastName = lastName;
this.username = username;
this.password = password;
this.phoneNumber = phoneNumber;
}
// Constructor used by tests that do not need names.
public Login(String username, String password, String phoneNumber) {
this.firstName = "";
this.lastName = "";
this.username = username;
this.password = password;
this.phoneNumber = phoneNumber;
}
/*
This method sets the user's password.
It is used to store the password entered by the user
after the Login object has been created.
*/
public void setPassword(String password){
this.password = password;
}
/*
This method sets the user's cellphone number.
It is used to store the cellphone number entered by
the user after the Login object has been created.
*/
public void setPhoneNumber(String phoneNumber){
this.phoneNumber = phoneNumber;
}
/*
This method returns the user's registered username.
It allows other classes to access the username without
directly accessing the private variable.
*/
public String getUsername(){
return username;
}
/*
This method sets the username entered during login.
It is used to store the username entered by the user
when attempting to log in.
*/
public void setEnteredUsername(String enteredUsername){
this.enteredUsername = enteredUsername;
}
/*
This method sets the password entered during login.
It is used to store the password entered by the user
when attempting to log in.
*/
public void setEnteredPassword(String enteredPassword){
this.enteredPassword = enteredPassword;
}
/*
This method checks whether the useranme is correctly formatted.
The username must contain an underscore and must not be
more than five characters long.
*/
public boolean checkUserName(){
//Check whether the username contains an underscore
//and is no more than five characters long
return username.contains("_") && username.length()<=5;
}
/*
This method checks whether the password meets the required
complexity rules.

The password must:
-Have at least 8 characters
-Contain an uppercase letter
-Contain a number
-Contain a special charcater
*/
public boolean checkPasswordComplexity(){
//Boolean variables are used to keep track of whether
//each password requirement has been found.
boolean hasUppercase = false;
boolean hasNumber = false;
boolean hasSpecialCharacter = false;
//If the password has fewer than 8 characters,
//it is automatically invalid.
if (password.length()< 8){
return false;
}
//Loop through each character in the password.
for (int i = 0; i < password.length(); i++){
//Store the current character in the password.
char character = password.charAt(i);
//Check whether the character is an uppercase letter.
if (Character.isUpperCase(character)){
hasUppercase = true;
}
//Check whether the charcater is a number.
if (Character.isDigit(character)){
hasNumber = true;
}
//Check whether the character is not a letter or number.
//If so, it is considered a special character.
if (!Character.isLetterOrDigit(character)){
hasSpecialCharacter = true;
}
}
//The password is valid only if all three requirements
//have been met.
if (hasUppercase && hasNumber && hasSpecialCharacter){
return true;
} else{
return false;
}
}
/*
This method checks whether the South African cellphone number
is correctly formatted.
*
The number must:
-Start with +27
-Contain 12 characters in total
-Contain only numbers after +27
*/
public boolean checkCellPhoneNumber(){
//Check that the number starts with the South African
//international country code +27 and has 12 characters.
if (phoneNumber.startsWith("+27")
&&
phoneNumber.length() == 12){
//Start checking the characters after +27
for (int i = 3; i< phoneNumber.length(); i++){
//Check that every character after +27 is a digit.
if (!Character.isDigit(phoneNumber.charAt(i))){
return false;
}
}
//The cellphone number passed all the checks.
return true;
} else {
//The cellphone number did not meet the requirements.
return false;
}
}
/*
This method registers the user by checking the required
registration information and returning the appropriate
message for each registration requirement.

*/
public String registerUser() {
    String message = "";

    if (checkUserName()) {
        message += "Username successfully captured.\n";
    } else {
        message += "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.\n";
    }

    if (checkPasswordComplexity()) {
        message += "Password successfully captured.\n";
    } else {
        message += "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.\n";
    }

    if (checkCellPhoneNumber()) {
        message += "Cell phone number successfully added.\n";
    } else {
        message += "Cell phone number incorrectly formatted or does not contain international code.\n";
    }

    return message;
}
/*
This method checks whether the username and password entered
during login match the username and password stored during
registration
*/
public boolean loginUser(){
//Compare the entered username with the registered username
//AND compare the entered password with the registered password.
if (enteredUsername.equals(username)
&&
enteredPassword.equals(password)){
//Both credentials match.
return true;
} else {
//One or both credentials do not match.
return false;
}
}
/*
This method returns the appropriate message depending on
whether the login was successfull or unsuccessful.
*/
public String returnLoginStatus(){
//Call loginUser() to check whether the credentials match.
if (loginUser()) {
//Return a welcome message if the login is successful.
return "Welcome " + firstName + " " + lastName + ", it is great to see you again.";
} else {
//Return an error message if the login fails.
return "Username or password incorrect, please try again.";
}
}
}