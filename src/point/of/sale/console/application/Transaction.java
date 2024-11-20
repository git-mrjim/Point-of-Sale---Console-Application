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

import java.util.ArrayList;
import java.time.LocalDate;

class SubTransaction {

    public int qty;
    public Product product;
    public float subTotal;

    public SubTransaction(int qty, Product product) {
        
        this.qty = qty;
        this.product = product;
        
        this.subTotal = this.product.getPrice() * this.qty;
                
    }
    
    
   
}

public class Transaction {
    
    private int transactionNo;
    private String transactionNoCode;
    private String transactionDate;
    private ArrayList<SubTransaction> subTransactions = new ArrayList<>();
    private float transactionTotal;
    private float transactionPayment;
    private float transactionChange;
    public String setTransactionMessage = "";

    public Transaction() {
        
        boolean isLoopExecute = false;
        int currentNo = 0;
         
        for (Transaction item : Storage.Transactions) {
            
            isLoopExecute = true;
           
            if (item.getNo() > currentNo) {
                currentNo = item.getNo();
            }
            
        }
        
        if (isLoopExecute) {
            this.transactionNo = currentNo + 1;
        } else {
            this.transactionNo = 1;
        }
        
        this.transactionNoCode = "T" + this.transactionNo;
        this.transactionDate = LocalDate.now().toString();
        
    }
 
     public boolean setTransaction(String productCode, int qty) {
         
        boolean isSuccess = false;
        for (Product item : Storage.Products) {
            
            if (item.getCode().equals(productCode)) {
                
                int index = Storage.Products.indexOf(item);
                if (Storage.Products.get(index).getStocks() < qty) {
                    
                     this.setTransactionMessage =  "The product " + item.getName() + " with code of " + item.getCode() + " is currently at low stocks.\n" + item.getName() + " Stocks: " + item.getStocks();
                     isSuccess = false;
                     
                } else {
                    
                      
                    SubTransaction subTransaction = new SubTransaction(qty, item);
                    this.subTransactions.add(subTransaction);
                    Storage.Products.get(index).stacksOut(qty);
                    isSuccess = true;
                    
                }
               break;
            } else {
                this.setTransactionMessage = "Invalid: Product code is incorrect.";
            }
            
        }
        
        return isSuccess;
    }
     
    public void subTransactionList() {
        
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("|    No.    |    Code    |        Name        |    QTY    |    Price    |    Sub Total    |");
        System.out.println("------------------------------------------------------------------------------------------");

        float total = 0;
        for (SubTransaction subtrans : subTransactions) {
                int productNo = Storage.Products.indexOf(subtrans.product) + 1;
                total = total + subtrans.subTotal;
                System.out.printf(
                    "| %-8d | %-10s | %-18s | %-10d | %-10.1f | %-14.1f |\n",
                    productNo, 
                    subtrans.product.getCode(), 
                    subtrans.product.getName(), 
                    subtrans.qty, 
                    subtrans.product.getPrice(),
                    subtrans.subTotal
                );
        }
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.printf("Total:                                                                              %.1f   ", total);
    }
    
    public void TransactionList() {
        
        System.out.println("--------------------------------------------------------------------");
        System.out.println("|    No.    |    Transaction No.    |     Total     |     Date     |");
        System.out.println("--------------------------------------------------------------------");

        for (Transaction transaction : Storage.Transactions) {
                int productNo = Storage.Transactions.indexOf(transaction) + 1;
                System.out.printf(
                    "| %-10d | %-20s | %-14.1f | %-10s |\n",
                    productNo, 
                    transaction.getNoCode(),
                    transaction.getTotal(),
                    transaction.getDate()
                );
        }
        System.out.println("--------------------------------------------------------------------");
        
    }
     
    public void Calculate(float payment) {
        
        float total = 0;
        for (SubTransaction subtrans : subTransactions) {
            total = total + subtrans.subTotal;
        }
        this.transactionTotal = total;
        
        this.transactionPayment = payment;
        this.transactionChange = Math.round(this.transactionPayment - this.transactionTotal);
        
    }

    public String getNoCode() {
        return transactionNoCode;
    }

    public int getNo() {
        return transactionNo;
    }

    public float getChange() {
        return transactionChange;
    }

    public float getTotal() {
        return transactionTotal;
    }

    public String getDate() {
        return transactionDate;
    }

    public float getPayment() {
        return transactionPayment;
    }
    
    
    
}
