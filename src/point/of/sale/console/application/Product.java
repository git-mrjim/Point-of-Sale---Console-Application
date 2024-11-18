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

// This class is the blueprint of products it's responsible for manipulating a products
public class Product {
    
    private String productCode;
    private String productName;
    private int productStocks;
    private float productPrice;
    private Auth productDependency;
    
    Product () {
        
    }
    
    Product (String code, String name, int stocks, float price, Auth dependency) {
        
        this.productCode = code;
        this.productName = name;
        this.productStocks = stocks;
        this.productPrice = price;
        this.productDependency = dependency;
        
    }
    
   public void productsList(Auth dependency) {
       
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("|    No.    |    Code    |        Name        |    Stocks    |    Price    |");
        System.out.println("---------------------------------------------------------------------------");

        int productCounter = 0;
        for (Product product : Storage.Products) {
            if (product.productDependency.getUsername().equals(dependency.getUsername())) {
                productCounter++;
                System.out.printf(
                    "| %-8d | %-10s | %-18s | %-12d | %-10.1f |\n",
                    productCounter, 
                    product.getCode(), 
                    product.getName(), 
                    product.getStocks(), 
                    product.getPrice()
                );
            }
        }
        System.out.println("---------------------------------------------------------------------------");
        
}

    
    public String getCode() {
        
        return this.productCode;
    }
    
     public String getName() {
        
        return this.productName;
    }
     
     public int getStocks() {
        
        return this.productStocks;
    }
     
     public float getPrice() {
        
        return this.productPrice;
    }
    
}
