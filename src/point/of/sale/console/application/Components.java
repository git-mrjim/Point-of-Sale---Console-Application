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
    
    public void AuthForm(boolean isForSignUp) {
        
        if (isForSignUp) {
            System.out.print("Fullname: ");
            this.fullname = this.scan.nextLine();
        }
        
        System.out.print("Username: ");
        this.username = this.scan.nextLine();
        System.out.print("Password: ");
        this.password = this.scan.nextLine();
        
    }
    
    public void SignUp() {
        
        Auth user = new Auth();
        
        System.out.println("\n");
        System.out.println("--------------------------------------------");
        System.out.println("Sign-up");
        System.out.println("--------------------------------------------");
        
        while (true) {
            
            System.out.print("Fullname: ");
            this.fullname = this.scan.nextLine();
            
            if (user.validateFullname(this.fullname)) {
                user.setFullname(this.fullname);
                break;
            } else {
                this.Clear();
                System.out.println(user.validationMessage);
            }
            
        }
        
        while (true) {
            
            System.out.print("Username: ");
            this.username = this.scan.nextLine();
            
            if (user.validateUsername(this.username)) {
                user.setUsername(this.username);
                break;
            } else {
                this.Clear();
                System.out.println(user.validationMessage);
            }
            
        }
        String previousInputPassword = "";
        while (true) {
            
            if (!previousInputPassword.equals("")) {
                System.out.println("Previous: " + previousInputPassword);
            }
            System.out.print("Password: ");
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
        
        Storage.Users.add(user);
        this.Clear();
        System.out.println("Sign-up successful.");

        boolean loop = true;
        while (loop) {

            System.out.print("You want to login? (Yes - 1 | No - 2): ");
            this.input = this.scan.nextLine();

            switch(this.input) {
            case "1":
                this.Clear();
                loop = false;
                this.Login();
                break;
            case "2":
                this.Clear();
                loop = false;
                this.Start();
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
            case "4":
                this.Clear();
                System.out.println("The 1 - 4 is not available yet.");
                this.MainMenu();
                break;
            case "5":
                this.Clear();
                this.authActive = null;
                this.Start();
                break;
            default:
                this.Clear();
                System.out.println("Invalid input.");
                this.MainMenu();
               break;

        }
    }
    
}
