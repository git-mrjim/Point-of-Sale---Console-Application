/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package point.of.sale.console.application;

/**
 *
 * @author Admin
 */
public class Auth {
    private String authFullname;
    private String authUsername;
    private String authPassword;
    public String validationMessage = "";
    private String existChecker(String stringToCheck, String[] stringOfArrayToCheck) {
        
        String found = "";
         for (String item : stringOfArrayToCheck) {
            if (stringToCheck.contains(item)) {
                found = found + item + " ";
            }
        }
         
         return found;
    }
    
    
    Auth(String fullname, String username, String password) {
        this.authFullname = fullname;
        this.authUsername = username;
        this.authPassword = password;
    }
    
    public boolean Validate() {
        
        if (this.authPassword.equals("") || this.authUsername.equals("") || this.authFullname.equals("")) {
            this.validationMessage = "Invalid Username, Password or Fullname is empty";
            return false;
        } else if (this.authPassword.contains(" ") || this.authUsername.contains(" ")) {
            this.validationMessage = "Invalid Username or Password there should be no space";
            return false;
        } else if (this.authPassword.length() < 8) {
            this.validationMessage = "Invalid Password should be atleas 8 characters";
            return false;
        }
        
        String[] capitalLetters = "abcdefghijklmnopqrstuvwxyz".toUpperCase().split("");
        String[] numbers = "0123456789".split("");
        String[] symbols = "`~!@#$%^&*()-_=+[{]}\\|;:'\",<.>/?".split("");
        
        String capitalLettersFound = this.existChecker(authPassword, capitalLetters);
        String numbersFound = this.existChecker(authPassword, numbers);
        String symbolsFound = this.existChecker(authPassword, symbols);
        
        if (capitalLettersFound.equals("")) {
            this.validationMessage = "Invalid Password should be contain capital letter";
            return false;
        } else if (numbersFound.equals("")) {
            this.validationMessage = "Invalid Password should be contain number";
            return false;
        } else if (!symbolsFound.equals("")) {
            this.validationMessage = "Invalid Password should be no symbols";
            return false;
        }
        
        this.validationMessage = String.format("Capital Letters: %s%nNumbers: %s%nSymbols: %s", capitalLettersFound, numbersFound, "none");
        return true;
    }
    
    public boolean setFullname(String newFullname) {
        this.authFullname = newFullname;
        return true;
    }
    
    public boolean setUsername(String newUsername) {
        this.authUsername = newUsername;
        return true;
    }
    
    public boolean setPassword(String newPassword) {
        this.authPassword = newPassword;
        return true;
    }
    
    public String getFullname() {
        return this.authFullname;
    }
    
    public String getUsername() {
        return this.authUsername;
    }
    
    public String getPassword() {
        return this.authPassword;
    }
    
}
