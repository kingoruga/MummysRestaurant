package view;


import java.util.ArrayList;
import java.util.List;
import static view.SessionState.user;

public class RegisterScene extends Scene {

    String fname;
    String lname;
    String strAddress;
    String city;
    String state;
    int zipCode;
    String email;
    String password;
    boolean registering;

    @Override
    public Scene transitionNext() {
        
        if (registering) {
            boolean successful = connector.registerNewUserQuery(fname, lname, email, password, 
                    strAddress, city, state, zipCode);
        
            if (!successful) {
                System.out.println("Registration was unsuccessful. Enter all information again.");
                return new RegisterScene();
            }
            
            // log them in after success
            SessionState.user = user;
        }

        return new HomeScene();
    }

    @Override
    public void process() {

        registering = false;

        // Create choices
        List<String> choices = new ArrayList<>();
        choices.add(email == null ? "Set email" : "Unset email") ;
        choices.add(fname == null ? "Set first name" : "Unset first name") ;
        choices.add(lname == null ? "Set last name" : "Unset last name") ;
        choices.add(strAddress == null ? "Set address" : "Unset address") ;
        choices.add(city == null ? "Set city" : "Unset city") ;
        choices.add(state == null ? "Set state" : "Unset state") ;
        choices.add(zipCode == 0 ? "Set zipcode" : "Unset zipcode") ;
        choices.add(password == null ? "Set password" : "Unset password") ;
        if (email != null && password != null && fname != null && lname != null &&
                strAddress != null && city != null && state != null && zipCode != 0)
            choices.add("Attempt register");
        choices.add("Back");

        do {
            // Display all choices
            for (int i = 0; i < choices.size(); i++)
                System.out.println("(" + i + ")" + " " + choices.get(i));

            // Match user input with choice
            selectedChoice = matchInputWithChoice(scanner.nextLine(), choices);

        } while (selectedChoice == null);

        switch (selectedChoice) {
            case "Set email":
                System.out.print("Email: ");
                email = scanner.nextLine();
                if (email.length() == 0)
                    email = null;
                break;

            case "Unset email":
                email = null;
                break;
                
            case "Set first name":
                System.out.print("First name: ");
                fname = scanner.nextLine();
                if (fname.length() == 0)
                    fname = null;
                break;
            
            case "Unset first name":
                fname = null;
                break;
                
            case "Set last name":
                System.out.print("Last name: ");
                lname = scanner.nextLine();
                if (lname.length() == 0)
                    lname = null;
                break;
                
            case "Unset last name":
                lname = null;
                break;
                
            case "Set address":
                System.out.print("Address (street): ");
                strAddress = scanner.nextLine();
                if (strAddress.length() == 0)
                    strAddress = null;
                break;
                
            case "Unset address":
                strAddress = null;
                break;
                
            case "Set city":
                System.out.print("City: ");
                city = scanner.nextLine();
                if (city.length() == 0)
                    city = null;
                break;
                
            case "Unset city":
                city = null;
                break;
                
            case "Set state":
                System.out.print("State: ");
                state = scanner.nextLine();
                if (state.length() == 0)
                    state = null;
                break;
                
            case "Unset state":
                state = null;
                break;
                
            case "Set zipcode":
                System.out.print("Zipcode: ");
                try {
                    zipCode = Integer.parseInt(scanner.nextLine());
                } catch (Exception e) {
                    zipCode = 0;
                }
                break;
                
            case "Unset zipcode":
                zipCode = 0;
                break;
              
            case "Set password":
                System.out.print("Password: ");
                password = scanner.nextLine();
                if (password.length() == 0)
                    password = null;
                break;

            case "Unset password":
                password = null;
                break;

            case "Attempt register":
                registering = true;

            case "Back":
                requestTransition = true;
                break;
        }
    }
}
