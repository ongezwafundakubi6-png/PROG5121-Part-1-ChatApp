/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapppart01;

/**
 *
 * @author Student
 */
public class Login {
    
  //Declare variables that are going to be used by the user
  String username;
  String password;
  String phonenumber;
  
  //User nqame validtion
  public boolean  checkUserName(String username) {
    return username.contains("_")&& username.length()<=5;
  }
  
  //Password validations
  public boolean checkPasswordComplexity(String password) {
      boolean hasCapital = false;
      boolean hasNumber = false;
      boolean hasSpecial = false;
      
      for(int i = 0; i < password.length(); i++){
          char c = password.charAt(i);
      
       if (Character.isUpperCase(c)) {
           hasCapital =true;
       } else if (Character.isDigit(c)) {
           hasNumber = true;
       } else if (!Character.isLetterOrDigit(c)) {
           hasSpecial = true;
       }
    }
  
  return password.length()>=8 && hasCapital && hasNumber && hasSpecial;
  }
       }
  //Phone  nu m ber validation
 public boolean checkCellPhoneNumber(String phone) {
    return phone.startsWith("+27") && phone.length() <= 12;
    
    
 }

