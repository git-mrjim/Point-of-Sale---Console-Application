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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

public class InOut {
    
    private String in = "";
    private String out = "";
    private String workDuration = "";
    private Auth cashier;
    
    InOut(Auth cashier) {
        this.cashier = cashier;
    }
    
    public void in() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.in = now.format(formatter);
    }
    
    public void out() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.out = now.format(formatter);
    }

    public void calculateWorkDuration() {
        
        // Parse the strings into LocalDateTime objects
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime inTime = LocalDateTime.parse(in, formatter);
        LocalDateTime outTime = LocalDateTime.parse(out, formatter);
        
        // Calculate the duration
        Duration duration = Duration.between(inTime, outTime);
        
        // Format the duration as hours, minutes, and seconds
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        // Save the formatted work duration
        this.workDuration = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        
    }

    public String getIn() {
        return in;
    }

    public String getOut() {
        return out;
    }

    public Auth getCashier() {
        return cashier;
    }

    public String getWorkDuration() {
        return workDuration;
    }
    
    public void displayInOut() {
        for (InOut item : Storage.InOut) {
            System.out.println("\n");
            System.out.println("--------------------------------------------");
            System.out.println("Cashier Fullname: " + item.getCashier().getFullname());
            System.out.println("Cashier Username: " + item.getCashier().getUsername());
            System.out.println("--------------------------------------------");
            System.out.println("In: " + item.getIn());
            System.out.println("Out: " + item.getOut());
            System.out.println("Work Duration: " + item.getWorkDuration());
            System.out.println("--------------------------------------------");
        }
    }
    
     public void displayInOut(String username) {
        for (InOut item : Storage.InOut) {
            if (item.getCashier().getUsername().equals(username)) {
                System.out.println("\n");
                System.out.println("--------------------------------------------");
                System.out.println("Cashier Fullname: " + item.getCashier().getFullname());
                System.out.println("Cashier Username: " + item.getCashier().getUsername());
                System.out.println("--------------------------------------------");
                System.out.println("In: " + item.getIn());
                System.out.println("Out: " + item.getOut());
                System.out.println("Work Duration: " + item.getWorkDuration());
                System.out.println("--------------------------------------------");
            }
        }
    }
    
}
