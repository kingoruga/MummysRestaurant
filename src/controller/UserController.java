/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Connector;
import model.OnlineUser;




/**
 *
 * @author syntel
 */
public class UserController {

    private Connector connector;
    private OnlineUser user;

    public void updateModelEnableUser(String userEmail) {
       connector = new Connector();
       connector.enableUserQuery(userEmail);
    }

    public void updateModelDisableUser(String email) {
         connector = new Connector();
         connector.disableUserQuery(email);
    }

    public void updateModelDeleteUser(String email) {
       connector = new Connector();
       connector.deleteUserQuery(email);
    }

    public void updateModelChangePassword(String userEmail, String password) {
       connector = new Connector();
       connector.changePasswordQuery(userEmail, password);
    }

    public void userControllerLogin(String email, String password) {
        connector = new Connector();
       // loginResponse = new LoginView();

        user = connector.loginQuery(email, password);

        if (user != null) {
            if (!user.getIsAdmin() && !user.getIsBanned()) {
                //go to html page for non-admin
               // loginResponse.nonAdminUser(user);
            } else if (user.getIsAdmin()) {
                //go to html page for admin
               // loginResponse.adminUser();
            } else {
                //toast a login failed on same page
               // loginResponse.printUpdatedResponse(7);
            }
        }
    }

    public void userControllerRegister(String fname, String lname, String email, String passWrd, String strAddress, 
                                                     String city, String state, int zipCode) {
       connector = new Connector();
       connector.registerNewUserQuery( fname, lname, email, passWrd, strAddress, city, state, zipCode);
    }

    public void userSuccessfullyUpdated(int i) {
        /*switch (i) {
            case 0: loginResponse.printUpdatedResponse(0); break;
            case 1: loginResponse.printUpdatedResponse(1); break;
            case 2: loginResponse.printUpdatedResponse(2); break;
            case 3: loginResponse.printUpdatedResponse(3); break;
            case 4: loginResponse.printUpdatedResponse(4); break;
            case 5: loginResponse.printUpdatedResponse(5); break;
            case 6: loginResponse.printUpdatedResponse(6); break;
            default: break;
        }*/
    }


}
