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


// This class is responsible for Authentication it's validate the user input and serve as a blueprint of the user
public class Auth {
    private String authFullname;
    private String authUsername;
    private String authPassword;
    public String validationMessage = "";
    private String exist(String stringToCheck, String[] stringOfArrayToCheck) {
        
        String found = "";
         for (String item : stringOfArrayToCheck) {
            if (stringToCheck.contains(item)) {
                found = found + item + " ";
            }
        }
         
         return found;
    }
    
    public boolean empty(String item) {
        if (item.equals("")) {
            return true;
        }
        return false;
    }
    
    Auth() {
        
    }
    
    Auth(String fullname, String username, String password) {
        this.setFullname(fullname);
        this.setUsername(username);
        this.setPassword(password);
    }
    
    
    public boolean validateFullname (String fullname) {
        
        String[] symbols = "`~!#$%^*()=+[{]}\\|;:'\",<>/?@&_-.".split("");
        String symbolsFound = this.exist(fullname, symbols);
        
        if (this.empty(fullname)) {
            this.validationMessage = "Invalid: Fullname is empty";
            return false;
        } else if (!symbolsFound.equals("")) {
            this.validationMessage = "Invalid: Fullname is don't have special character input you're real fullname";
            return false;
        }
        
        this.validationMessage = "";
        return true;
    }
    
    public boolean validateUsername(String username) {
        
        String[] symbols = "_-.".split("");
        String[] otherSymbols = "`~!#$%^*()=+[{]}\\|;:'\",<>/?@&".split("");
        
        String otherSymbolsFound = this.exist(username, otherSymbols);
        
        boolean isDuplicateUsername = false;
        for (Auth item : Storage.Users) {
            if (item.getUsername().equals(username)) {
                isDuplicateUsername = true;
                break;
            }
        }
        
        if (this.empty(username)) {
            this.validationMessage = "Invalid: Username is empty";
            return false;
        } else if (isDuplicateUsername) {
            this.validationMessage = "Invalid: Username is already exist.";
            return false;
        } else if (!otherSymbolsFound.equals("")) {
            this.validationMessage = "Invalid: Username should be contain atleast one of this special characters only _-.\nyou have " + otherSymbolsFound;
            return false;
        }
        
        this.validationMessage = "";
        return true;
    }
    
    public boolean validatePassword (String password) {
        
        String[] capitalLetters = "abcdefghijklmnopqrstuvwxyz".toUpperCase().split("");
        String[] numbers = "0123456789".split("");
        String[] symbols = "@_-.&".split("");
        String[] otherSymbols = "`~!#$%^*()=+[{]}\\|;:'\",<>/?".split("");
        
        String capitalLettersFound = this.exist(password, capitalLetters);
        String numbersFound = this.exist(password, numbers);
        String otherSymbolsFound = this.exist(password, otherSymbols);
        String symbolsFound = this.exist(password, symbols);
        
        if (this.empty(password)) {
            this.validationMessage = "Invalid: Password is empty";
            return false;
        } else if (password.length() < 8) {
            this.validationMessage = "Invalid: Password should be atleast 8 characters";
            return false;
        } else if (capitalLettersFound.equals("")) {
            this.validationMessage = "Invalid: Password should be contain capital letter";
            return false;
        } else if (numbersFound.equals("")) {
            this.validationMessage = "Invalid: Password should be contain number";
            return false;
        } else if (!otherSymbolsFound.equals("")) {
            this.validationMessage = "Invalid: Password should be contain atleast one of this special characters only @_-.&\nyou have " + otherSymbolsFound;
            return false;
        } else if (symbolsFound.equals("")) {
            this.validationMessage = "Invalid: Password should be contain atleast one of this special characters @_-.&";
            return false;
        }
        
        this.validationMessage = "";
        return true;
    }
    
    public boolean setFullname(String newFullname) {
        if (this.validateFullname(newFullname)) {
            this.authFullname = newFullname;
            return true;
        } else {
            return false;
        }
    }
    
    public boolean setUsername(String newUsername) {
        if (this.validateUsername(newUsername)) {
            this.authUsername = newUsername;
            return true;
        } else {
            return false;
        }
    }
    
    public boolean setPassword(String newPassword) {
        if (this.validatePassword(newPassword)) {
            this.authPassword = newPassword;
            return true;
        } else {
            return false;
        }
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
    
    public String getPasswordWithStar() {
        String star = "";
        for (int i = 0; i < this.authPassword.length(); i++) {
            star = star + "*";
        }
        return star;
    }
    
}
