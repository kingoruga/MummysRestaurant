package view;


import model.OnlineUser;
import model.Orders;



/**
 * This would be the held sessions state, maybe represented by
 * cookies of a user logging in
 */
public class SessionState {

    private SessionState() {}

    public static boolean loggedIn() {
        return user != null;
    }

    public static OnlineUser user = null;
    
    public static Orders ongoingOrder = null;

}
