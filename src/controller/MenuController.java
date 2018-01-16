/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.MenuInfo;
//import view.MealOptionsView;

/**
 *
 * @author syntel
 */
public class MenuController {
    MenuInfo info;
    //MealOptionsView mealsView;
    //private String foodItemId;
    private String name;
    private String desc;
    private double price;
    private String type;
    private String veg;
    
    public MenuController(){
        
    }

    public MenuController(String Name, String desc, double price, String type, String veg) {
        this.name = Name;
        this.desc = desc;
        this.price = price;
        this.type = type;
        this.veg = veg;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVeg() {
        return veg;
    }

    public void setVeg(String veg) {
        this.veg = veg;
    }
    
    /* public void showMenu() {
    info = new MenuInfo();
    mealsView = new MealOptionsView();
    List<MenuController> items = info.getMenuOptions();
    items.forEach((mealItem) -> {
    mealsView.printMenuItems(mealItem.toString());
    });
    }*/
     
     
    @Override
    public String toString() {
        String result = "-----------------------------------------------------------\n"
                         + "Name: " + name + "\n" + "Description: " + desc + "\n" + "Price: $" + price + "\n" + "Type: " + type + "\n" ;             
        return result;
    }
     
     
}
