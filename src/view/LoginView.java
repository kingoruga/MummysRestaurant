/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.UserController;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author syntel
 */
public class LoginView {

    UserController userController;
    MealOptionsView mealOptions ;
    
    public void login(String email, String password) {
       userController  = new UserController();
        userController.userControllerLogin(email, password);
    }
    
    public void register(String fname, String lname, String email, String passWrd, String strAddress, String city, String state, int zipCode) {
        userController  = new UserController();
        userController.userControllerRegister(
                fname, lname, email, passWrd, strAddress, city, state, zipCode);
    }

    public void adminUser() {
        Scanner input = new Scanner(System.in);
        userController  = new UserController();
        System.out.println("Welcome, admin!");

        System.out.println("Enter 5 to enable user");
        System.out.println("Enter 6 to disable user");
        System.out.println("Enter 7 to delete user");
        System.out.println("Enter 8 to change user password");
        System.out.println("Enter 9 to go back to main menu");
        System.out.println("Enter 10 to exit");
        int response = input.nextInt();

        if (response == 5) {
            System.out.println("Enter User Email to enable");
            String userEmail = input.next();
            userController.updateModelEnableUser(userEmail);
        }

        if (response == 6) {
            System.out.println("Enter User Email to disable");
            String userEmail = input.next();
            userController.updateModelDisableUser(userEmail);
        }

        if (response == 7) {
            System.out.println("Enter User Email to delete");
            String userEmail = input.next();
            userController.updateModelDeleteUser(userEmail);
        }

        if (response == 8) {
            System.out.println("Enter User Email to change password");
            String userEmail = input.next();
            System.out.println("Enter new password");
            String newPassword = input.next();
            userController.updateModelChangePassword(userEmail, newPassword);
        }

        if (response == 10) {
            System.exit(0);
        }
    }

    public void nonAdminUser(String name) {
        //From here, user can see options to order a meal or package
        mealOptions = new MealOptionsView();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.now();
        System.out.println(dtf.format(localDate));
        System.out.println("\nHello, " + name + ". You are logged, in.");
        System.out.println("\nHere are the Menu Items for today, " + dtf.format(localDate) +".");
        System.out.println("---------------------------------");
        mealOptions.getMenuItems();

    }

    public void printUpdatedResponse(int i) {
        switch(i){
            case 0: System.out.println("\nUser successfully disabled!"); break;
            case 1: System.out.println("\nUser successfully enabled!"); break;
            case 2: System.out.println("\nUser successfully deleted!"); break;
            case 3: System.out.println("\nPassword changed successfully!"); break;
            case 4: System.out.println("\nEmail already exists. Please try another one."); break;
            case 5: System.out.println("\nYou have successfully registered!"); break;
            case 6: System.out.println("\nEmail already exists. Please try another one."); break;
            case 7: System.out.println("\nInvalid email and password combination."); break;
            default: break;
        }
    }

 

}
