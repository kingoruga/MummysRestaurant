package view;

import java.util.ArrayList;
import java.util.List;

public class LoginScene extends Scene {

    private String email;
    private String password;
    private boolean loggingIn = false;

    @Override
    public Scene transitionNext() {

        if (!loggingIn)
            return new HomeScene();

        SessionState.user = connector.loginQuery(email, password);
        
        if (connector.userIsDisabledQuery(email)) {
            System.out.println("This account is disabled.");
            SessionState.user = null;
        }
        
        else if (!SessionState.loggedIn()) {
            System.out.println("Login was unsuccessful.");
        }

        return new HomeScene();
    }

    @Override
    public void process() {

        loggingIn = false;

        // Create choices
        List<String> choices = new ArrayList<>();
        choices.add(email == null ? "Set email" : "Unset email") ;
        choices.add(password == null ? "Set password" : "Unset password") ;
        if (email != null && password != null)
            choices.add("Attempt login");
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

            case "Set password":
                System.out.print("Password: ");
                password = scanner.nextLine();
                if (password.length() == 0)
                    password = null;
                break;

            case "Unset password":
                password = null;
                break;

            case "Attempt login":
                loggingIn = true;

            case "Back":
                requestTransition = true;
                break;
        }
    }

}
