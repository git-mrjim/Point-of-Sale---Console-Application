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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
    private Auth cashier;
    private String transactionTime;

    public Transaction() {
        
        this.transactionDate = LocalDate.now().toString();
        this.transactionTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        
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
        for (int i = 0; i < subTransactions.size(); i++) {
            SubTransaction subtrans = subTransactions.get(i);
            int productNo = i + 1; // Correctly number subtransactions
            total += subtrans.subTotal; // Accumulate total

            System.out.printf(
                "| %-8d | %-10s | %-20s | %-8d | %-10.2f | %-14.2f |\n",
                productNo,
                subtrans.product.getCode(),
                subtrans.product.getName(),
                subtrans.qty,
                subtrans.product.getPrice(),
                subtrans.subTotal
            );
        }

        System.out.println("------------------------------------------------------------------------------------------");
        System.out.printf("Total:                                                                      %.2f\n", total);
        
}

    
    public void TransactionList() {
        System.out.println("---------------------------------------------------------------------------------------------------------");
        System.out.println("|    No.    |    Transaction No.    |     Total     |     Date     |     Time     |        Cashier       |");
        System.out.println("---------------------------------------------------------------------------------------------------------");
        for (int i = Storage.Transactions.size() - 1; i >= 0; i--) {
            Transaction transaction = Storage.Transactions.get(i);
            int itemNo = Storage.Transactions.size() - i;
            System.out.printf(
                "| %-10d | %-20s | %-14.2f | %-12s | %-10s | %-20s |\n",
                itemNo, 
                transaction.getNoCode(),
                transaction.getTotal(),
                transaction.getDate(),
                transaction.getTime(),
                transaction.getCashier().getFullname()
            );
        }
        System.out.println("---------------------------------------------------------------------------------------------------------");
}

     
    public void Calculate(float payment) {
        
        float total = 0;
        for (SubTransaction subtrans : subTransactions) {
            total = total + subtrans.subTotal;
            Storage.Products.get(Storage.Products.indexOf(subtrans.product)).stacksOut(subtrans.qty);
        }
        this.transactionTotal = total;
        
        this.transactionPayment = payment;
        this.transactionChange = Math.round(this.transactionPayment - this.transactionTotal);
        
    }
    
    public void setCode() {
        
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
        
    }

    public void setCashier(Auth casher) {
        this.cashier = casher;
    }

    public Auth getCashier() {
        return cashier;
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

    public String getTime() {
        return transactionTime;
    }
    
    

    public float getPayment() {
        return transactionPayment;
    }
    
    
    
}
