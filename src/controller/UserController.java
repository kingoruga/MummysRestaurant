/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.UserInfo;
import view.LoginView;

/**
 *
 * @author syntel
 */
public class UserController {

    private String email;
    UserInfo info = new UserInfo();
    LoginView loginResponse = new LoginView();

    public void updateModelEnableUser(String userEmail) {
        info.adminEnableUser(email);
    }

    public void updateModelDisableUser(String email) {
        info.adminDisableUser(email);
    }

    public void updateModelDeleteUser(String email) {
        info.adminDeleteUser(email);
    }

    public void updateModelChangePassword(String userEmail, String password) {
        info.adminChangeUserPassword(userEmail, password);
    }

    public void userControllerLogin(String email, String password) {
        info = new UserInfo();
        loginResponse = new LoginView();
        String result = info.loginUser(email, password);
        if(result.equals(" ")){
            loginResponse.printUpdatedResponse(7);
        }
        else if(result.equals("admin")){
            loginResponse.adminUser();
        }
        else
             loginResponse.nonAdminUser(result);
    }

    public void userControllerRegister(String fname, String lname, String email, String passWrd, String strAddress, 
                                                     String city, String state, int zipCode) {
        info = new UserInfo();
        info.registerNewUser( fname, lname, email, passWrd, strAddress, city, state, zipCode);
    }



    public void userSuccessfullyUpdated(int i) {
        switch (i) {
            case 0: loginResponse.printUpdatedResponse(0); break;
            case 1: loginResponse.printUpdatedResponse(1); break;
            case 2: loginResponse.printUpdatedResponse(2); break;
            case 3: loginResponse.printUpdatedResponse(3); break;
            case 4: loginResponse.printUpdatedResponse(4); break;
            case 5: loginResponse.printUpdatedResponse(5); break;
            case 6: loginResponse.printUpdatedResponse(6); break;
            default: break;
        }
    }

}
