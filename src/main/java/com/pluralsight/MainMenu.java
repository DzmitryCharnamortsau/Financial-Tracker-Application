package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MainMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String transactionsFile = "transactions.csv";
    public static void homeScreen(){
        boolean running = true;
        while(running){
            System.out.println("Choose one of the following options: ");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make payment(Debit)");
            System.out.println("B) Balance");
            System.out.println("L) Display the ledger screen");
            System.out.println("X) Exit");
            char input = scanner.next().toUpperCase().charAt(0);
            scanner.nextLine();

            switch(input){
                case 'D' -> addDeposit();
                case 'P' -> makePayment();
                case 'B' -> totalBalance();
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
        scanner.nextLine();
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedDate = date.format(dateFormatter);
        String formattedTime = time.format(timeFormatter);
        try{
            FileWriter fileWriter = new FileWriter(transactionsFile, true);
            BufferedWriter bufWriter = new BufferedWriter(fileWriter);
            String line = formattedDate + "|" + formattedTime + "|" + description + "|" + vendor + "|" + String.format("%.2f", amount);
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
        scanner.nextLine();
        if (amount>0){
            amount = -amount;
        }
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedDate = date.format(dateFormatter);
        String formattedTime = time.format(timeFormatter);
        try{
            FileWriter fileWriter = new FileWriter(transactionsFile, true);
            BufferedWriter bufWriter = new BufferedWriter(fileWriter);
            String line = formattedDate + "|" + formattedTime + "|" + description + "|" + vendor + "|" + String.format("%.2f", amount);
            bufWriter.write(line);
            bufWriter.write("\n");
            bufWriter.close();
        }
        catch (IOException e) {
            System.out.println("An unexpected error occurred");
            e.printStackTrace();
        }
    }
    private static void totalBalance(){
        double balance = 0;
        try{
            BufferedReader reader = new BufferedReader(new FileReader(transactionsFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("|"));
                if (parts.length == 5) {
                    try {
                        double amount = Double.parseDouble(parts[4]);
                        balance += amount;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid amount in line: " + line);
                    }
                }
            }
            System.out.printf("Total Balance: %.2f\n", balance);
        }
        catch(IOException e){
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
            BufferedReader reader = new BufferedReader(new FileReader(transactionsFile));
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
            BufferedReader reader = new BufferedReader(new FileReader(transactionsFile));
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
                System.out.println(t.toString());
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
            BufferedReader reader = new BufferedReader(new FileReader(transactionsFile));
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
                System.out.println(t.toString());
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
        String currentMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        boolean found = false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(transactionsFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("|"));
                if (parts.length == 5) {
                    if (parts[0].startsWith(currentMonth)) {
                        System.out.println(line);
                        found = true;
                    }
                }
            }
            if(!found) {
                System.out.println("No transactions found for this month.");
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("An unexpected error occurred");
            e.printStackTrace();
        }
    }
    public static void previousMonth(){
        String previousMonth = LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM"));
        boolean found = false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(transactionsFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("|"));
                if (parts.length == 5) {
                    if (parts[0].startsWith(previousMonth)) {
                        System.out.println(line);
                        found = true;
                    }
                }
            }
            if(!found) {
                System.out.println("No transactions found for this month.");
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("An unexpected error occurred");
            e.printStackTrace();
        }
    }
    public static void yearToDate(){
        String currentYear = String.valueOf(LocalDate.now().getYear());
        boolean found = false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(transactionsFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("|"));
                if (parts.length == 5) {
                    if (parts[0].startsWith(currentYear)) {
                        System.out.println(line);
                        found = true;
                    }
                }
            }
            if(!found) {
                System.out.println("No transactions found for this year.");
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("An unexpected error occurred");
            e.printStackTrace();
        }
    }
    public static void previousYear(){
        String previousYear = String.valueOf(LocalDate.now().getYear() - 1);
        boolean found = false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(transactionsFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("|"));
                if (parts.length == 5) {
                    if (parts[0].startsWith(previousYear)) {
                        System.out.println(line);
                        found = true;
                    }
                }
            }
            if(!found) {
                System.out.println("No transactions found for this year.");
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("An unexpected error occurred");
            e.printStackTrace();
        }
    }
    public static void searchByVendor(){
        System.out.print("Enter a vendor name ");
        String vendorName = scanner.nextLine().toLowerCase();
        ArrayList<Transactions> list = new ArrayList<>();
        boolean found = false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(transactionsFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("|"));
                if (parts.length == 5) {
                    String vendor = parts[3].toLowerCase();
                    if (vendor.contains(vendorName)) {
                        double amount = Double.parseDouble(parts[4]);
                        Transactions t = new Transactions(parts[0], parts[1], parts[2], parts[3], amount);
                        list.add(t);
                        found = true;
                    }
                }
            }
            reader.close();
            if (!found) {
                System.out.println("No transactions found for " + vendorName);
            } else {
                for (int i = list.size() - 1; i >= 0; i--) {
                    Transactions t = list.get(i);
                    System.out.println(t.toString());
                }
            }

        } catch (IOException e) {
            System.out.println("An unexpected error occurred");
            e.printStackTrace();
        }

    }

}
