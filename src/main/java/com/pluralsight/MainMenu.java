package com.pluralsight;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MainMenu {
    private static final Scanner scanner = new Scanner(System.in);
    public static void homeScreen(){
        boolean running = true;
        while(running){
            System.out.println("Choose one of the following options: ");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make payment(Debit)");
            System.out.println("L) Display the ledger screen");
            System.out.println("X) Exit");
            char input = scanner.next().toUpperCase().charAt(0);
            scanner.nextLine();

            switch(input){
                case 'D' -> addDeposit();
                case 'P' -> makePayment();
                case 'L' -> displayLedgerScreen();
                case 'X' -> {running = false;
                    System.out.println("Closing the application");
                }
                default -> System.out.println("Invalid character. Please try again");
            }
        }

    }
    private static void addDeposit(){
        System.out.print("Enter description: ");
        String description  = scanner.nextLine();
        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine();
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss");
        String formattedDate = dateTime.format(formatter);
        try{
            FileWriter fileWriter = new FileWriter("transactions.csv", true);
            BufferedWriter bufWriter = new BufferedWriter(fileWriter);
            String line = formattedDate + "|" + description + "|" + vendor + "|" + String.format("%.2f", amount);
            bufWriter.write(line);
            bufWriter.write("\n");
            bufWriter.close();
        }
        catch (IOException e){
            System.out.println("An unexpected error occurred");
            e.printStackTrace();
        }

    }
    private static void makePayment(){
        System.out.print("Enter description: ");
        String description  = scanner.nextLine();
        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine();
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        if (amount>0){
            amount = -amount;
        }
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss");
        String formattedDate = dateTime.format(formatter);
        try{
            FileWriter fileWriter = new FileWriter("transactions.csv", true);
            BufferedWriter bufWriter = new BufferedWriter(fileWriter);
            String line = formattedDate + "|" + description + "|" + vendor + "|" + String.format("%.2f", amount);
            bufWriter.write(line);
            bufWriter.write("\n");
            bufWriter.close();
        }
        catch (IOException e) {
            System.out.println("An unexpected error occurred");
            e.printStackTrace();
        }
    }
    private static void displayLedgerScreen(){
        boolean running = true;
        while(running) {
            System.out.println("Choose one of the following options: ");
            System.out.println("A) Display all entries");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");
            char input = scanner.next().toUpperCase().charAt(0);
            scanner.nextLine();

            switch (input){
                case 'A' -> displayAllEntries();
                case 'D' -> deposits();
                case 'P' -> negativeEntries();
                case 'R' -> reports();
                case 'H' -> running = false;
                default -> System.out.println("Invalid character. Please try again");
            }
        }
    }
    public static void displayAllEntries(){
        ArrayList<String> entries = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"));
            String line;
            while((line = reader.readLine()) != null) {
                entries.add(line);
            }
            reader.close();
            for (int i = entries.size()-1; i>=0; i--){
                System.out.println(entries.get(i));
            }
        }
        catch (IOException e) {
            System.out.println("An unexpected error occurred");
            e.printStackTrace();
        }
    }
    public static void deposits(){
        ArrayList<Transactions> list = new ArrayList<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"));
            String line;
            while((line = reader.readLine()) != null){
                String [] parts = line.split(Pattern.quote("|"));
                if (parts.length == 5) {
                    double amount = Double.parseDouble(parts[4]);
                    if (amount > 0) {
                        Transactions t = new Transactions(parts[0],parts[1], parts[2],parts[3], amount);
                        list.add(t);
                    }
                }
            }
            for (int i = list.size() - 1; i >= 0; i--) {
                Transactions t = list.get(i);
                System.out.printf("%s|%s|%s|%s|%.2f\n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
            }
        }
        catch(IOException e){
            System.out.println("An unexpected error occurred");
            e.printStackTrace();
        }
    }
    public static void negativeEntries(){
        ArrayList<Transactions> list = new ArrayList<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"));
            String line;
            while((line = reader.readLine()) != null){
                String [] parts = line.split(Pattern.quote("|"));
                if (parts.length == 5) {
                    double amount = Double.parseDouble(parts[4]);
                    if (amount < 0) {
                        Transactions t = new Transactions(parts[0],parts[1], parts[2],parts[3], amount);
                        list.add(t);
                    }
                }
            }
            for (int i = list.size() - 1; i >= 0; i--) {
                Transactions t = list.get(i);
                System.out.printf("%s|%s|%s|%s|%.2f\n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
            }
        }
        catch(IOException e){
            System.out.println("An unexpected error occurred");
            e.printStackTrace();
        }
    }
    public static void reports(){
        boolean running = true;
        while(running) {
            System.out.println("Choose one of the following options: ");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");
            char input = scanner.next().charAt(0);
            scanner.nextLine();

            switch (input){
                case '1' -> monthToDate();
                case '2' -> previousMonth();
                case '3' -> yearToDate();
                case '4' -> previousYear();
                case '5' -> searchByVendor();
                case '0' -> running = false;
                default -> System.out.println("Invalid character. Please try again");
            }
        }
    }
    public static void monthToDate(){

    }
    public static void previousMonth(){

    }
    public static void yearToDate(){

    }
    public static void previousYear(){

    }
    public static void searchByVendor(){

    }

}
