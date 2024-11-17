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

import java.util.Scanner;
import java.io.IOException;


// This class is responsible for displaying all the components of the system
public class Components {
    
    private Scanner scan = new Scanner(System.in);
    private Auth authActive;
    private Auth admin = new Auth("admin", "admin", "admin");
    
    private String fullname;
    private String username;
    private String password;
    private String input;
    
    Components() {
        
        Storage.Users.add(admin);
        
    }
    
    public void Clear() {
        
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        
    }
    
    public void Start() {
        
        System.out.println("\n");
        System.out.println("Jimwell Ibay, John Benedict Calara, Charizza Flores");
        System.out.println("--------------------------------------------");
        System.out.println("Point of Sale - Console Application");
        System.out.println("--------------------------------------------");
        System.out.println("1. Sign-up\n2. Login\n3. Exit");
        System.out.println("--------------------------------------------");

        System.out.print("What do you want to do (?): ");
        this.input = this.scan.nextLine();

        switch(this.input) {
            case "1":
                this.Clear();
                this.SignUp();
                break;
            case "2":
               this.Clear();
               this.Login();
               break;
            case "3":
               this.Clear();
               break;
            default:
               this.Clear();
               System.out.println("Invalid input.");
               this.Start();
               break;
        }
        
    }
    
    public void FullnameForm(Auth user, String format) {
        
        while (true) {
            
            System.out.printf("%sFullname: ", format);
            this.fullname = this.scan.nextLine();
            
            if (user.validateFullname(this.fullname)) {
                user.setFullname(this.fullname);
                break;
            } else {
                this.Clear();
                System.out.println(user.validationMessage);
            }
            
        }
        
    }
    
    public void UsernameForm(Auth user, String format) {
        
        while (true) {
            
            System.out.printf("%sUsername: ", format);
            this.username = this.scan.nextLine();
            
            if (user.validateUsername(this.username)) {
                user.setUsername(this.username);
                break;
            } else {
                this.Clear();
                System.out.println(user.validationMessage);
            }
            
        }
        
    }
    
    public void PasswordForm(Auth user, String format) {
        
        String previousInputPassword = "";
        while (true) {
            
            if (!previousInputPassword.equals("")) {
                System.out.println("Previous: " + previousInputPassword);
            }
            System.out.printf("%sPassword: ", format);
            this.password = this.scan.nextLine();
            previousInputPassword = this.password;
            
            if (user.validatePassword(this.password)) {
                user.setPassword(this.password);
                break;
            } else {
                this.Clear();
                System.out.println(user.validationMessage);
            }
            
        }
        
    }
    
    public void SignUp() {
        
        Auth user = new Auth();
        
        System.out.println("\n");
        System.out.println("--------------------------------------------");
        System.out.println("Sign-up");
        System.out.println("--------------------------------------------");
        
        this.FullnameForm(user, "");
        this.UsernameForm(user, "");
        this.PasswordForm(user, "");
        
        Storage.Users.add(user);
        this.Clear();
        System.out.println("Sign-up successful.");

        boolean loop = true;
        while (loop) {

            System.out.print("Go back (1 - Yes | 2 - No): ");
            this.input = this.scan.nextLine();

            switch(this.input) {
            case "1":
                this.Clear();
                loop = false;
                this.Start();
                break;
            case "2":
                this.Clear();
                loop = false;
                this.SignUp();
                break;
            default:
                this.Clear();
                System.out.println("Invalid input.");
               break;
            }
        }      
    }
    
    public void Login() {
        
        System.out.println("\n");
        System.out.println("--------------------------------------------");
        System.out.println("Login");
        System.out.println("--------------------------------------------");
        System.out.print("Username: ");
        this.username = this.scan.nextLine();
        System.out.print("Password: ");
        this.password = this.scan.nextLine();

        boolean isSuccess = false;
        for (Auth item : Storage.Users) {

            String itemUsername = item.getUsername();
            String itemPassword = item.getPassword();
            boolean validateUsername = itemUsername.equals(this.username);
            boolean validatePassword = itemPassword.equals(this.password); 

            if (validateUsername ^ validatePassword) {
                if (!validateUsername) {
                    this.Clear();
                    System.out.println("Username is incorrect.");
                } else if (!validatePassword) {
                    this.Clear();
                    System.out.println("Password is incorrect.");
                }
                this.Login();
            } else if (validateUsername && validatePassword) {
                isSuccess = true;
                this.Clear();
                System.out.println("Login successful.");
                this.authActive = item;
                this.MainMenu();
                break;
            }
        }
        
        if (!isSuccess) {
            this.Clear();
            System.out.println("Account not found.");
            this.Login();
        } 
        
    }
    
    public void MainMenu() {
        
        System.out.println("\n");
        System.out.println("--------------------------------------------");
        System.out.printf(" Main Menu                User: %s%n", this.authActive.getFullname());
        System.out.println("--------------------------------------------");
        System.out.println("1. Product Maintenance\n2. Transaction Module\n3. View Transactions\n4. Account Settings\n5. Logout");
        System.out.println("--------------------------------------------");

        System.out.print("What do you want to do (?): ");
        this.input = this.scan.nextLine();

        switch(this.input) {
            case "1":
            case "2":
            case "3":
               this.Clear();
               System.out.println("1 - 3 is not available yet..");
               this.MainMenu();
               break;
            case "4":
               this.Clear();
               this.AccountSettings();
               break;
            default:
               this.Clear();
               System.out.println("Invalid input.");
               this.MainMenu();
               break;
        }
        
    }
    
    public void AccountSettings() {
        
        System.out.println("\n");
        System.out.println("--------------------------------------------");
        System.out.println(" Account Settings");
        System.out.println("--------------------------------------------");
        System.out.printf("Fullname: %s%nUsername: %s%nPassword: %s%n", this.authActive.getFullname(), this.authActive.getUsername(), this.authActive.getPasswordWithStar());
        System.out.println("--------------------------------------------");

        System.out.print("Do you want to edit (1 - Yes | 2 - No): ");
        this.input = this.scan.nextLine();

        switch(this.input) {
        case "1":
            this.Clear();
            this.EditAccount();
            break;
        case "2":
            this.Clear();
            this.MainMenu();
            break;
        default:
            this.Clear();
            System.out.println("Invalid input.");
            this.AccountSettings();
           break;
        }
    }
    
    
    public void EditAccount() {
        
        System.out.println("\n");
        System.out.println("--------------------------------------------");
        System.out.println("Edit Account");
        System.out.println("--------------------------------------------");
        System.out.println("1. Edit Fullname\n2. Edit Username\n3. Edit Password\n4. Back");
        System.out.println("--------------------------------------------");
        
        System.out.print("What do you want to edit (?): ");
        this.input = this.scan.nextLine();

        switch(this.input) {
            case "1":
               this.Clear();
               this.EditFullname();
               break;
            case "2":
               this.Clear();
               this.EditUsername();
               break;
            case "3":
               this.Clear();
               this.EditPassword();
               break;
            case "4":
               this.Clear();
               this.AccountSettings();
               break;
            default:
               this.Clear();
               System.out.println("Invalid input.");
               this.EditAccount();
               break;
        }
        
    }
    
    public void EditFullname() {
        
        System.out.println("\n");
        System.out.println("--------------------------------------------");
        System.out.println("Edit Fullname");
        System.out.println("--------------------------------------------");
        
        System.out.println("Fullname: " + this.authActive.getFullname());
        this.FullnameForm(authActive, "New ");
        
        this.Clear();
        System.out.println("Fullname updated successfully.");
        this.EditAccount();
        
    }
    
    public void EditUsername() {
        
        System.out.println("\n");
        System.out.println("--------------------------------------------");
        System.out.println("Edit Username");
        System.out.println("--------------------------------------------");
        
        System.out.println("Username: " + this.authActive.getUsername());
        this.UsernameForm(authActive, "New ");
        
        this.Clear();
        System.out.println("Username updated successfully.");
        this.EditAccount();
        
    }
    
    public void EditPassword() {
        
        System.out.println("\n");
        System.out.println("--------------------------------------------");
        System.out.println("Edit Password");
        System.out.println("--------------------------------------------");
        
        System.out.print("Current Password: ");
        this.input = this.scan.nextLine();
        
        if (authActive.getPassword().equals(this.input)) {
            this.PasswordForm(authActive, "New ");
        } else {
            this.Clear();
            System.out.println("Current Password is incorrect.");
            this.EditPassword();
        }
            
        this.Clear();
        System.out.println("Password updated successfully.");
        this.EditAccount();
        
    }
    
    public void ProductMaintenance() {
        
        System.out.println("\n");
        System.out.println("--------------------------------------------");
        System.out.println("Product Maintenance");
        System.out.println("--------------------------------------------");
        
        System.out.println("\n");
        System.out.println("List of Products");
        
        System.out.println("\n");
        System.out.println("--------------------------------------------");
        System.out.println("Operations:");
        System.out.println("--------------------------------------------");
        System.out.println("1. Add\n2. Edit\n3. Delete\n4. Search\n5. Back");
        System.out.println("--------------------------------------------");
        
        System.out.print("What do you want to do (?): ");
        this.input = this.scan.nextLine();

        switch(this.input) {
            case "1":
            case "2":
            case "3":
            case "4":
               this.Clear();
               System.out.println("1 - 4 is not available yet.");
               this.ProductMaintenance();
               break;
            case "5":
                this.Clear();
                this.MainMenu();
                break;
            default:
               this.Clear();
               System.out.println("Invalid input.");
               this.ProductMaintenance();
               break;
        }
        
    }
    
}
