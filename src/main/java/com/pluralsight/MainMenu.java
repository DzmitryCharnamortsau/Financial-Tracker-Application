package com.pluralsight;

import java.util.Scanner;

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

    }
    private static void makePayment(){

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

    }
    public static void deposits(){

    }
    public static void negativeEntries(){

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
