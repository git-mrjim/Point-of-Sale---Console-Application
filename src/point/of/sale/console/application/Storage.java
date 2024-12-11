/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package point.of.sale.console.application;

import java.util.ArrayList;

/**
 *
 * @author Admin
 */

// This class serve as the storage of the entire system
public class Storage {
    
    public static ArrayList<Auth> Users = new ArrayList<>();
    public static ArrayList<Product> Products = new ArrayList<>();
    public static ArrayList<Transaction> Transactions = new ArrayList<>();
    public static ArrayList<InOut> InOut = new ArrayList<>();
    
    Storage() {
        
    }
    
}
