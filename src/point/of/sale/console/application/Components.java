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
import java.util.InputMismatchException;


// This class is responsible for displaying all the components of the system
public class Components {
    
    private Scanner scan = new Scanner(System.in);
    private Auth authActive;
    private Auth admin = new Auth("admin", "admin", "Admin_123");
    
    private String fullname;
    private String username;
    private String password;
    private String input;
    
    private InOut inOut;
    
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
        System.out.println("1. Sign-up\n2. Login\n3. In and Out\n4. Exit");
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
               this.InAndOut();
               break;
            case "4":
               this.Clear();
               break;
            default:
               this.Clear();
               System.out.println("Invalid input.");
               this.Start();
               break;
        }
        
    }
    
    public void InAndOut() {
         
        try {
            
            this.inOut.displayInOut();
            System.out.println("Input BACK if you want to back.");
            System.out.println("Input SEARCH if you want to search.");
            System.out.print("Input: ");
            this.input = this.scan.nextLine();
        
            switch(this.input.toLowerCase()) {
                case "back":
                    this.Clear();
                    this.Start();
                    break;
                case "search":
                    this.Clear();

                    boolean loop = true; 
                    while (loop) {

                        System.out.println("Input BACK if you want to back.");
                        System.out.print("Username (Search): ");
                        this.input = this.scan.nextLine();

                        if (this.input.toLowerCase().equals("back")) {
                            this.Clear();
                            this.InAndOut();
                            loop = false;
                        }

                        this.inOut.displayInOut(input);

                    }

                    break;
                default: 
                    this.Clear();
                    System.out.println("Invalid input.");
                    this.InAndOut();
                    break;
            }
    
        } catch (NullPointerException e) {
            this.Clear();
            System.out.println("It's empty.");
            this.Start();
        }
                
    }
    
    public void FullnameForm(Auth user, String format) {
        
        while (true) {
            
            System.out.printf("%sFullname: ", format);
            this.fullname = this.scan.nextLine();
            
            if (user.setFullname(this.fullname)) {
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
            
            if (user.setUsername(this.username)) {
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
            
            if (user.setPassword(this.password)) {
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
                this.inOut = new InOut(authActive);
                this.inOut.in();
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
               this.Clear();
               this.ProductMaintenance();
               break;
            case "2":
                this.Clear();
                this.TransactionModule();
                break;
            case "3":
               this.Clear();
               this.ViewTransactions();
               break;
            case "4":
               this.Clear();
               this.AccountSettings();
               break;
            case "5":
                this.Clear();
                System.out.println("Bye " + authActive.getFullname() + " :(");
                this.inOut.out();
                this.inOut.calculateWorkDuration();
                Storage.InOut.add(this.inOut);
                authActive = null;
                this.Start();
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
        System.out.printf("Fullname: %s%nUsername: %s%nPassword: %s%n", 
                this.authActive.getFullname(), 
                this.authActive.getUsername(), 
                this.authActive.getPasswordWithStar()
        );
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
        
        Product product = new Product();
        product.productsList();
        
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
                this.Clear();
                this.AddProduct();
                break;
            case "2":
                this.Clear();
                this.EditProduct();
                break;
            case "3":
                this.Clear();
                this.DeleteProduct();
                break;
            case "4":
               this.Clear();
               this.SearchProduct();
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
    
    public void ProductNameForm(Product product, String format) {
        
        while (true) {
            
            System.out.printf("%sProduct Name: ", format);
            String input = this.scan.nextLine();
            
            if (input.equals("") || input.equals(" ")) {
                System.out.println("Invalid: It's empty");
                continue;
            } 
            
            if (product.setName(input)) {
                break;
            } else {
                System.out.println("Invalid: That's name is already exist.");
            }
            
        }
        
    }
    
    public void ProductStocksForm(Product product, String format) {
        
        while (true) {
            
             try {
                
                  System.out.printf("%sProduct Stocks: ", format);
                  int input = this.scan.nextInt();
                  
                  if (product.setStocks(input)) {
                      break;
                  }
                    
                } catch (InputMismatchException e) {

                    System.out.println("Invalid: Enter an integer only");
                    this.scan.nextLine();
                    
            } 
            
        }
        
    }
    
     public void ProductIncreaseStocksForm(Product product) {
         
         while (true) {
            
             try {
                
                  System.out.print("Increase Stocks By: ");
                  int input = this.scan.nextInt();
                  
                  if (product.stacksIn(input)) {
                      break;
                  }
                    
                } catch (InputMismatchException e) {

                    System.out.println("Invalid: Enter an integer only");
                    this.scan.nextLine();
                    
            } 
            
        }
         
     }
     
     public void ProductReduceStocksForm(Product product) {
         
         while (true) {
            
             try {
                
                  System.out.print("Reduce Stocks By: ");
                  int input = this.scan.nextInt();
                  
                  if (product.stacksOut(input)) {
                      break;
                  }
                    
                } catch (InputMismatchException e) {

                    System.out.println("Invalid: Enter an integer only");
                    this.scan.nextLine();
                    
            } 
            
        }
         
     }
    
    public void ProductPriceForm(Product product, String format) {
        
        while (true) {
            
             try {
                
                  System.out.printf("%sProduct Price: ", format);
                  float input = this.scan.nextFloat();
                  
                  if(product.setPrice(input)) {
                      break;
                  }
                    
                } catch (InputMismatchException e) {

                    System.out.println("Invalid: Enter an integer only");
                    this.scan.nextLine();
                    
            } 
            
        }
        
    }
    
     public void ProductIncreasePriceForm(Product product) {
        
        while (true) {
            
             try {
                
                  System.out.print("Increase Price By: ");
                  float input = this.scan.nextFloat();
                  
                  if(product.priceIn(input)) {
                      break;
                  }
                    
                } catch (InputMismatchException e) {

                    System.out.println("Invalid: Enter an integer only");
                    this.scan.nextLine();
                    
            } 
            
        }
        
    }
     
    public void ProductReducePriceForm(Product product) {
        
        while (true) {
            
             try {
                
                  System.out.print("Reduce Price By: ");
                  float input = this.scan.nextFloat();
                  
                  if(product.priceOut(input)) {
                      break;
                  }
                    
                } catch (InputMismatchException e) {

                    System.out.println("Invalid: Enter an integer only");
                    this.scan.nextLine();
                    
            } 
            
        }
        
    }
    
    public void AddProduct() {
                
        System.out.println("\n");
        System.out.println("--------------------------------------------");
        System.out.println("Add Product");
        System.out.println("--------------------------------------------");
        
        Product product = new Product();
        
        this.ProductNameForm(product, "");
        this.ProductStocksForm(product, "");
        this.ProductPriceForm(product, "");
        this.input = this.scan.nextLine();
         
        boolean loop = true;
        while (loop) {
            
            System.out.print("1 - Add | 2 - Cancel: ");
            this.input = this.scan.nextLine();
            
            switch (this.input) {
                case "1":
                    product.setDependency(authActive);
                    Storage.Products.add(product);
                    String productCode =  product.setCode(product);
                    this.Clear();
                    System.out.println("Product added successfully.");
                    System.out.println("Product Code for " + product.getName() + " is " + productCode);
                    loop = false;
                    break;
                case "2":
                    this.Clear();
                    System.out.println("Product not added.");
                    this.AddProduct();
                    loop = false;
                    break;
                default:
                    this.Clear();
                    System.out.println("Invalid input.");
                    break;
            }
            
        }
        
        loop = true;
        while (loop) {
            
            System.out.print("Go back (1 - Yes | 2 - No): ");
            this.input = this.scan.nextLine();

            switch (this.input) {
                case "1":
                    this.Clear();
                    this.ProductMaintenance();
                    loop = false;
                    break;
                case "2":
                    this.Clear();
                    this.AddProduct();
                    loop = false;
                    break;
                default:
                    this.Clear();
                    System.out.println("Invalid input.");
            }
            
        }
    }
    
    
    public void EditProduct() {
        
        System.out.println("\n");
        System.out.println("--------------------------------------------");
        System.out.println("Edit Product");
        System.out.println("--------------------------------------------");
        
        Product product = new Product();
        product.productsList();
        System.out.println("Input BACK if you want to back on Product Maintenance");
        System.out.print("Enter Product Code: ");
        String productCode = this.scan.nextLine();
        
        if (productCode.toLowerCase().equals("back")) {
            this.Clear();
            this.ProductMaintenance();
        }
        
        int productIndex;
        boolean found = false;
        Product productFound = null;
        for (Product item : Storage.Products ) {
            if (item.getCode().equals(productCode)) {
                found = true;
                productIndex = Storage.Products.indexOf(item);
                productFound = Storage.Products.get(productIndex);
            }
        }
        
        this.Clear();
        if (found) {
            
            boolean loop = true;
            while (loop) {
                
                System.out.println("\n");
                product.searchByCode(productCode);
                System.out.println("--------------------------------------------");
                System.out.printf("Edit Product %s By:%n", productFound.getName());
                System.out.println("--------------------------------------------");
                System.out.println("1. Name\n2. Stocks\n3. Increase Stocks\n4. Reduce Stocks\n5. Price\n6. Increase Price\n7. Reduce Price\n8. Back");
                System.out.println("--------------------------------------------");
                System.out.print("What do you want to do (?): ");
                this.input = this.scan.nextLine();

                switch(input) {
                    case "1":
                        this.Clear();
                        System.out.println("Current Name: " + productFound.getName());
                        this.ProductNameForm(productFound, "New ");
                        this.Clear();
                        System.out.println("Product name update successful.");
                        break;
                    case "2":
                        this.Clear();
                        System.out.println("Current Stocks: " + productFound.getStocks());
                        this.ProductStocksForm(productFound, "New ");
                        this.scan.nextLine();
                        this.Clear();
                        System.out.println("Product stocks update successful.");
                        break;
                    case "3":
                        this.Clear();
                        System.out.println("Current Stocks: " + productFound.getStocks());
                        this.ProductIncreaseStocksForm(productFound);
                        this.scan.nextLine();
                        this.Clear();
                        System.out.println("Product stocks increase successful.");
                        break;
                    case "4":
                        this.Clear();
                        System.out.println("Current Stocks: " + productFound.getStocks());
                        this.ProductReduceStocksForm(productFound);
                        this.scan.nextLine();
                        this.Clear();
                        System.out.println("Product stocks reduce successful.");
                        break;
                    case "5":
                        this.Clear();
                        System.out.println("Current Price: " + productFound.getPrice());
                        this.ProductPriceForm(productFound, "New ");
                        this.scan.nextLine();
                        this.Clear();
                        System.out.println("Product price update successful.");
                        break;
                    case "6":
                        this.Clear();
                        System.out.println("Current Price: " + productFound.getPrice());
                        this.ProductIncreasePriceForm(productFound);
                        this.scan.nextLine();
                        this.Clear();
                        System.out.println("Product price increase successful.");
                        break;
                    case "7":
                        this.Clear();
                        System.out.println("Current Price: " + productFound.getPrice());
                        this.ProductReducePriceForm(productFound);
                        this.scan.nextLine();
                        this.Clear();
                        System.out.println("Product price reduce successful.");
                        break;
                    case "8":
                        this.Clear();
                        this.EditProduct();
                        loop = false;
                        break;
                    default:
                        this.Clear();
                        System.out.println("Invalid input.");
                        break;
                }
                
            }
            
        } else {
            this.Clear();
            System.out.println("Product not found.");
        }
        
        boolean loop = true;
        while (loop) {
            
            System.out.print("Go back (1 - Yes | 2 - No): ");
            this.input = this.scan.nextLine();

            switch (this.input) {
                case "1":
                    this.Clear();
                    this.ProductMaintenance();
                    loop = false;
                    break;
                case "2":
                    this.Clear();
                    this.EditProduct();
                    loop = false;
                    break;
                default:
                    this.Clear();
                    System.out.println("Invalid input.");
            }
            
        }
        
    }
    
    public void DeleteProduct() {
        
        System.out.println("\n");
        System.out.println("--------------------------------------------");
        System.out.println("Delete Product");
        System.out.println("--------------------------------------------");
        
        Product product = new Product();
        product.productsList();
        System.out.println("Input BACK if you want to back on Product Maintenance");
        System.out.print("Enter Product Code: ");
        String productCode = this.scan.nextLine();
        
        if (productCode.toLowerCase().equals("back")) {
            this.Clear();
            this.ProductMaintenance();
        }
        
        boolean loop = true;
        while (loop) {
            
            System.out.print("1 - Delete | 2 - Cancel: ");
            this.input = this.scan.nextLine();
            
            switch (this.input) {
                case "1":
                    
                    int productIndex = 0;
                    boolean found = false;
                    for (Product item : Storage.Products ) {
                        if (item.getCode().equals(productCode)) {
                            found = true;
                            productIndex = Storage.Products.indexOf(item);
                        }
                    }
                    
                    if (found) {
                        Storage.Products.remove(productIndex);
                        this.Clear();
                        System.out.println("Product delete successful.");
                    } else {
                        this.Clear();
                        System.out.println("Product not found.");
                    }
                    
                    loop = false;
                    break;
                case "2":
                    this.Clear();
                    System.out.println("Product not deleted.");
                    this.DeleteProduct();
                    loop = false;
                    break;
                default:
                    this.Clear();
                    System.out.println("Invalid input.");
                    break;
            }
            
        }
        
        loop = true;
        while (loop) {
            
            System.out.print("Go back (1 - Yes | 2 - No): ");
            this.input = this.scan.nextLine();

            switch (this.input) {
                case "1":
                    this.Clear();
                    this.ProductMaintenance();
                    loop = false;
                    break;
                case "2":
                    this.Clear();
                    this.DeleteProduct();
                    loop = false;
                    break;
                default:
                    this.Clear();
                    System.out.println("Invalid input.");
            }
            
        }
        
    }
    
    public void SearchProduct() {
        
        System.out.println("\n");
        System.out.println("--------------------------------------------");
        System.out.println("Search Product");
        System.out.println("--------------------------------------------");
        
        Product product = new Product();
        
        System.out.println("\n");
        System.out.println("--------------------------------------------");
        System.out.println("Search Product By:");
        System.out.println("--------------------------------------------");
        System.out.println("1. Code\n2. Name\n3. Specific Stocks\n4. Stocks Range\n5. Specific Price\n6. Price Range\n7. Back");
        System.out.println("--------------------------------------------");
        
        System.out.print("What do you want to do (?): ");
        this.input = this.scan.nextLine();
        
        switch (this.input) {
            case "1":
                
                    System.out.print("Enter Product Code: ");
                    String productCode = this.scan.nextLine();
                    product.searchByCode(productCode);
                    
                    boolean loop = true;
                    while (loop) {
                    
                    System.out.print("Go back (1 - Yes | 2 - No): ");
                    this.input = this.scan.nextLine();

                    switch (this.input) {
                        case "1":
                            this.Clear();
                            this.SearchProduct();
                            loop = false;
                            break;
                        case "2":
                            this.Clear();
                            break;
                        default:
                            this.Clear();
                            System.out.println("Invalid input.");
                    }

                }
                break;
                
            case "2":
                
                    System.out.print("Enter Product Name: ");
                    String productName = this.scan.nextLine();
                    product.searchByName(productName);

                    loop = true;
                    while (loop) {
                    
                    System.out.print("Go back (1 - Yes | 2 - No): ");
                    this.input = this.scan.nextLine();

                    switch (this.input) {
                        case "1":
                            this.Clear();
                            this.SearchProduct();
                            loop = false;
                            break;
                        case "2":
                            this.Clear();
                            break;
                        default:
                            this.Clear();
                            System.out.println("Invalid input.");
                    }
               }
                break;
                
            case "3":
                
                    int productStocks;
                    while (true) {
                        
                         try {
                            
                             System.out.print("Enter Specific Stocks: ");
                             productStocks = this.scan.nextInt();
                             
                           break;
                             
                        } catch (InputMismatchException e) {
                            
                            System.out.println("Invalid: Enter an integer only");
                            this.scan.nextLine();
                            
                        }
                        
                    }
                     this.scan.nextLine();
                
                    product.searchBySpecificStocks(productStocks);

                    loop = true;
                    while (loop) {
                        
                    System.out.print("Go back (1 - Yes | 2 - No): ");
                    this.input = this.scan.nextLine();

                    switch (this.input) {
                        case "1":
                            this.Clear();
                            this.SearchProduct();
                            loop = false;
                            break;
                        case "2":
                            this.Clear();
                            break;
                        default:
                            this.Clear();
                            System.out.println("Invalid input.");
                    }
               }
                break;
            case "4":
                    
                    int stocksMinimun;
                    int stocksMaximum;
                    while (true) {
                        
                         try {
                            
                            System.out.print("Enter Minimun Stocks: ");
                            stocksMinimun = this.scan.nextInt();
                            System.out.print("Enter Maximum Stocks: ");
                            stocksMaximum = this.scan.nextInt();
                             
                           break;
                             
                        } catch (InputMismatchException e) {
                            
                            System.out.println("Invalid: Enter an integer only");
                            this.scan.nextLine();
                            
                        }
                        
                    }
                     this.scan.nextLine();
                
                    product.searchByStocksRange(stocksMinimun, stocksMaximum);

                    loop = true;
                    while (loop) {
                    
                    System.out.print("Go back (1 - Yes | 2 - No): ");
                    this.input = this.scan.nextLine();

                    switch (this.input) {
                        case "1":
                            this.Clear();
                            this.SearchProduct();
                            loop = false;
                            break;
                        case "2":
                            this.Clear();
                            break;
                        default:
                            this.Clear();
                            System.out.println("Invalid input.");
                    }
               }
                break;
                
            case "5":
                
                    float productPrice;
                    while (true) {
                        
                         try {
                            
                             System.out.print("Enter Specific Price: ");
                             productPrice = this.scan.nextFloat();
                             
                           break;
                             
                        } catch (InputMismatchException e) {
                            
                            System.out.println("Invalid: Enter an integer only");
                            this.scan.nextLine();
                            
                        }
                        
                    }
                     this.scan.nextLine();
                
                    product.searchBySpecificPrice(productPrice);

                    loop = true;
                    while (loop) {
                    
                    System.out.print("Go back (1 - Yes | 2 - No): ");
                    this.input = this.scan.nextLine();

                    switch (this.input) {
                        case "1":
                            this.Clear();
                            this.SearchProduct();
                            loop = false;
                            break;
                        case "2":
                            this.Clear();
                            break;
                        default:
                            this.Clear();
                            System.out.println("Invalid input.");
                    }
               }
                break;
                
            case "6": 
                
                    float priceMinimun;
                    float priceMaximum;
                    while (true) {
                        
                         try {
                            
                            System.out.print("Enter Minimun Price: ");
                            priceMinimun = this.scan.nextFloat();
                            System.out.print("Enter Maximum Price: ");
                            priceMaximum = this.scan.nextFloat();
                             
                           break;
                             
                        } catch (InputMismatchException e) {
                            
                            System.out.println("Invalid: Enter an integer only");
                            this.scan.nextLine();
                            
                        }
                        
                    }
                     this.scan.nextLine();
                
                    product.searchByPriceRange(priceMinimun, priceMaximum);

                    loop = true;
                    while (loop) {
                    
                    System.out.print("Go back (1 - Yes | 2 - No): ");
                    this.input = this.scan.nextLine();

                    switch (this.input) {
                        case "1":
                            this.Clear();
                            this.SearchProduct();
                            loop = false;
                            break;
                        case "2":
                            this.Clear();
                            break;
                        default:
                            this.Clear();
                            System.out.println("Invalid input.");
                    }
               }
                break;
            case "7":
                this.Clear();
                this.ProductMaintenance();
                break;
            default:
                this.Clear();
                System.out.println("Invalid input.");
                this.SearchProduct();
                break;
        }
        
    }
    
    
        public void TransactionModule() {
            
            System.out.println("\n");
            System.out.println("--------------------------------------------");
            System.out.println("Transaction Module");
            System.out.println("--------------------------------------------");

            System.out.println("\n");
            System.out.println("Menu");
            
            if (Storage.Products.isEmpty()) {
                this.Clear();
                System.out.println("The product inventory is empty add a products.");
                this.AddProduct();
            }
            
            Product product = new Product();
            product.productsList();
            System.out.println("\n");
            
            int orders = 0;
            while (true) {
                try {
                    
                    System.out.print("How many orders?: ");
                    orders = this.scan.nextInt();
                    
                    break;
                    
                } catch (InputMismatchException e) {

                    System.out.println("Invalid: Enter an integer only");
                    this.scan.nextLine();

                } 
            }
            
            Transaction transaction = new Transaction();
            
            System.out.println("\n");
            for (int i = 0; i < orders; i++) {
                
                this.scan.nextLine();
                
                boolean loop = true;
                while (loop) {
                    
                    System.out.printf("Enter menu code [%s]: ", i + 1);
                    String code = this.scan.nextLine();
                    
                    int qty;
                    while (true) {
                        
                         try {
                            
                            System.out.printf("Enter QTY [%s]: ", i + 1);
                            qty = this.scan.nextInt();
                            break;
                             
                        } catch (InputMismatchException e) {
                            
                            System.out.println("Invalid: Enter an integer only");
                            this.scan.nextLine();
                            
                        }
                         
                    }
                    
                    if (transaction.setTransaction(code, qty)) {
                        transaction.setCashier(authActive);
                        loop = false;
                    } else {
                        System.out.println(transaction.setTransactionMessage);
                        this.scan.nextLine();
                    }
                    
                } 
                
            }
             this.scan.nextLine();
           
            this.Clear();
            System.out.println("\n");
            transaction.subTransactionList();
            System.out.println("\n");
            
            boolean loop = true;
            while (loop) {
                
                System.out.println("Make sure the customer paid right before you continue.");
                System.out.print("Continue to payment? ( Yes - 1 | No - 2 ): ");
                this.input = this.scan.nextLine();
                
                switch (this.input) {
                    case "1":

                        float payment = 0;
                        while (true) {
                          try {

                                System.out.print("Enter payment: ");
                                payment = this.scan.nextFloat();
                                transaction.Calculate(payment);

                                if (payment >= Math.round(transaction.getTotal())) {

                                    System.out.println("\n");
                                    System.out.printf("Change: %.1f%n", transaction.getChange());
                                    
                                    transaction.setCode();
                                    Storage.Transactions.add(transaction);
                                    
                                    System.out.println("\n");
                                    System.out.println("Transaction Successful.");
                                    break;

                                } else {

                                     System.out.println("The payment is not enough.");
                                     System.out.println("Transaction Failed.");

                                }

                            } catch (InputMismatchException e) {

                                 System.out.println("Invalid: Enter an integer only");
                                 this.scan.nextLine();

                            }  
                        }
                        this.scan.nextLine();
                        loop = false;
                        break;

                    case "2":
                        this.Clear();
                        loop = false;
                        break;
                    default: 
                        System.out.println("Invalid input.");
                        break;
                
                }
                
            }
            
            
            loop = true;
            while (loop) {
                
                System.out.print("Another Transaction (1 - Yes | 2 - No): ");
                this.input = this.scan.nextLine();
                
                switch(this.input) {
                    case "1":
                        this.Clear();
                        this.TransactionModule();
                        loop = false;
                        break;
                    case "2":
                        this.Clear();
                        this.MainMenu();
                        loop = false;
                        break;
                    default:
                        this.Clear();
                        System.out.println("Invalid input.");
                       break;
                }
                
            }
            
        }
        
        public void ViewTransactions() {
            
            System.out.println("\n");
            System.out.println("--------------------------------------------");
            System.out.println("View Transactions");
            System.out.println("--------------------------------------------");
            
            System.out.println("\n");
            System.out.println("List of Transactions");
            
            if (Storage.Products.isEmpty()) {
                this.Clear();
                System.out.println("The product inventory is empty add a products.");
                this.AddProduct();
            }
            
            Transaction transaction = new Transaction();
            transaction.TransactionList();
            System.out.println("\n");
            
            Transaction transactionChoose = null;
            
            boolean loop = true;
            while (loop) { 
                
                System.out.println("Input BACK if you want to back in Main Menu: ");
                System.out.print("Enter Transaction No: ");
                String transactionCode = this.scan.nextLine();
                
                if (transactionCode.toUpperCase().equals("BACK")) {
                    this.Clear();
                    this.MainMenu();
                }

                boolean isSuccess = false;
                for (Transaction item : Storage.Transactions) {

                    if (item.getNoCode().equals(transactionCode)) {
                        isSuccess = true;
                        transactionChoose = item;
                        break;
                    } 

                }

                if (isSuccess) {
                    loop = false;
                } else {
                    System.out.println("Invalid: Transaction code is incorrect.");
                }
                
            }
            
            this.Clear();
            transactionChoose.subTransactionList();
            System.out.println("\n");
            System.out.printf("Entered Payment: %.1f%nChange: %.1f%nCashier: %s%n", transactionChoose.getPayment(), transactionChoose.getChange(), transactionChoose.getCashier().getFullname());
            System.out.println("\n");
            
            loop = true;
            while (loop) {
                
                System.out.print("View Another Transaction (1 - Yes | 2 - No): ");
                this.input = this.scan.nextLine();
                
                switch(this.input) {
                    case "1":
                        this.Clear();
                        this.ViewTransactions();
                        loop = false;
                        break;
                    case "2":
                        this.Clear();
                        this.MainMenu();
                        loop = false;
                        break;
                    default:
                        this.Clear();
                        System.out.println("Invalid input.");
                       break;
                }
                
            }
            
        }
    
}
