/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author syntel
 */
public class UserInfo {

    Connector user;

    public void adminDisableUser(String userEmail) {
        user = new Connector();
        user.disableUserQuery(userEmail);
    }

    public void adminEnableUser(String userEmail) {
        user = new Connector();
        user.enableUserQuery(userEmail);
    }

    public String isAdminOrCustomer(String email, String password) {
        //if user is admin
        return "Admin";
    }

    public void adminDeleteUser(String email) {
        user = new Connector();
        user.deleteUserQuery(email);
    }

    public void adminChangeUserPassword(String email, String password) {
        user = new Connector();
        user.changePasswordQuery(email, password);
    }

    public String loginUser(String email, String password) {
         user = new Connector();
         return user.loginQuery(email, password);
    }

    public void registerNewUser(String fname, String lname, String email, String passWrd, String strAddress, String city, String state, int zipCode) {
       user = new Connector();
       user.registerNewUserQuery( fname, lname, email, passWrd, strAddress, city, state, zipCode);
    }

}
