package view;

import controller.Order;

/**
 * This would be the held sessions state, maybe represented by
 * cookies of a user logging in
 */
public class SessionState {

    private SessionState() {}

    public static boolean loggedIn() {
        return customerEmail != null && !customerEmail.equals(" ");
    }

    public static String customerEmail = null;
    
    public static Order ongoingOrder = null;

}
