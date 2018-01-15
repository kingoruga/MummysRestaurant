/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.Scanner;

/**
 *
 * @author syntel
 */
public class MainView {

    public static void main(String[] args) {
        MealOptionsView mealOptions = new MealOptionsView();
        LoginView loginView = new LoginView();
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to Mummy's Restaurant");
        System.out.println("---------------------------------------------");

        while (true) {
            System.out.println("Enter 1 to Login");
            System.out.println("Enter 2 to view Restaurant Menu");
            System.out.println("Enter 3 to Register"); 
            System.out.println("Enter 4 to Exit");
            
            int response = Integer.parseInt(input.nextLine());
            
            if (response == 1) {
                System.out.println("Enter email below:");
                String email = input.nextLine();
                System.out.println("Enter password below:");
                String password = input.nextLine();
                loginView.login(email, password);  
            }
            
            if (response == 2) {
                mealOptions.getMenuItems();
            }

            if (response == 3) {
                //go to register 
                System.out.println("Please Enter your First Name below:");
                String fname = input.nextLine();
                System.out.println("\nPlease Enter your Last Name below:");
                String lname = input.nextLine();
                System.out.println("\nPlease Enter your email below:");
                String email = input.nextLine();
                System.out.println("\nPlease Enter your password below:");
                String passWrd = input.nextLine();
                System.out.println("\nPlease Enter your Street address below:");
                String strAddress = input.nextLine();
                System.out.println("\nPlease Enter your City below:");
                String city = input.nextLine();
                System.out.println("\nPlease Enter your State below (abbreviated like 'TX'):");
                String state = input.nextLine();
                System.out.println("\nPlease Enter your zip code below:");
                int zipCode = Integer.parseInt(input.nextLine());
                loginView.register(fname, lname, email, passWrd, strAddress, city, state, zipCode);
                
            }

            if (response == 4) {
                System.out.println("Thank you for visiting Mummy's Restaurant. Please come again soon.");
                System.exit(0);
            }

        }
    }
}
     
