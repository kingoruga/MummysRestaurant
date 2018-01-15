/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.MenuController;

/**
 *
 * @author syntel
 */
public class MealOptionsView {
    //MenuController menu;

    public void getMenuItems() {
       MenuController menuController = new MenuController();
       menuController.showMenu(); 
    }

    public void printMenuItems(String items) {
       // menu = new MenuController();
        System.out.println(items);
    }

}
