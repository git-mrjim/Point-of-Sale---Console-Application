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
import java.util.ArrayList;

public class Components {
    
    private Scanner scan = new Scanner(System.in);
    private ArrayList<Auth> Storage = new ArrayList<>();
    private Auth authLogin;
    private Auth admin = new Auth("admin", "admin", "admin");
    
    private String fullname;
    private String username;
    private String password;
    private String input;
    
    private boolean loop;
    
    Components() {
        
        this.Storage.add(admin);
        
        loop = true;
        while(loop) {
            
            this.Intro();
            
            switch(this.input) {
                case "1":
                    this.SignUp();
                    break;
                case "2":
                   this.Login();
                   break;
                case "3":
                   loop = false;
                   break;
                default:
                   System.out.println("Invalid input.");
                   break;
            }
            
        }
        
    }
    
    public void Intro() {
        
        System.out.println("\n");
        System.out.println("Jimwell Ibay, John Benedict Calara, Charizza Flores");
        System.out.println("--------------------------------------------");
        System.out.println("Point of Sale - Console Application");
        System.out.println("--------------------------------------------");
        System.out.println("1. Sign-up\n2. Login\n3. Exit");
        System.out.println("--------------------------------------------");
        
        System.out.print("What do you want to do (?): ");
        this.input = this.scan.nextLine();
        
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
        
        System.out.println("\n");
        System.out.println("Warning: Password should be atleas 8 characters, should have capital letters and numbers and should not hsve symbols");
        System.out.println("Warning: Password and Username should not hsve space and should not empty");;
        System.out.println("--------------------------------------------");
        System.out.println("Sign-up");
        System.out.println("--------------------------------------------");
        this.AuthForm(true);
        System.out.println("--------------------------------------------");
        
        Auth auth = new Auth(this.fullname, this.username, this.password);
        
        boolean isDuplicateUsername = false;
        for (Auth item : this.Storage ) {
            if (item.getUsername().equals(auth.getUsername())) {
                isDuplicateUsername = true;
            }
        }
        
        if (isDuplicateUsername) {
            System.out.println("Username is already exist.");
        }
        
        if (auth.Validate()) {
            
            System.out.println("\n");
            System.out.println("--------------------------------------------");
            System.out.println("Sign-up Information");
            System.out.println("--------------------------------------------");
            System.out.println(auth.validationMessage);
            System.out.println("--------------------------------------------");
            
            System.out.println("Sign up successful.");
            this.Storage.add(auth);
            
            System.out.println("\n");
            System.out.print("You want to login? (Yes - 1 | No - 2):");
            this.input = this.scan.nextLine();
            
            switch(this.input) {
                case "1":
                    this.Login();
                    break;
                case "2":
                    break;
                default:
                    System.out.println("Invalid input.");
                   break;
                    
            }
            
            
        } else {
            System.out.println(auth.validationMessage);
            this.SignUp();
        }
        
    }
    
    public void Login() {
        
        System.out.println("\n");
        System.out.println("--------------------------------------------");
        System.out.println("Login");
        System.out.println("--------------------------------------------");
        this.AuthForm(false);
        System.out.println("--------------------------------------------");

        boolean isSuccess = false;
        for (Auth item : this.Storage) {

            String itemUsername = item.getUsername();
            String itemPassword = item.getPassword();
            boolean validateUsername = itemUsername.equals(this.username);
            boolean validatePassword = itemPassword.equals(this.password);

            if (validateUsername ^ validatePassword) {
                if (!validateUsername) {
                    System.out.println("Username is incorrect.");
                } else if (!validatePassword) {
                    System.out.println("Password is incorrect.");
                }
                this.Login();
            } else if (validateUsername && validatePassword) {
                isSuccess = true;
                System.out.println("Login successful.");
                this.authLogin = item;
                this.MainMenu();
                break;
            }
        }
        
        if (!isSuccess) {
            System.out.println("Account not found.");
        } 
        
    }
    
    public void MainMenu() {
        
        boolean loop = true;
        while(loop) {
            
            System.out.println("\n");
            System.out.println("--------------------------------------------");
            System.out.printf(" Main Menu                User: %s%n", this.authLogin.getFullname());
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
                System.out.println("The 1 - 4 is not available yet.");
                break;
            case "5":
                this.authLogin = null;
                loop = false;
                break;
            default:
                System.out.println("Invalid input.");
               break;
                    
            }
            
        }
        
        
    }
    
}
