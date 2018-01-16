package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.FoodItem;

public class Order {

    private String deliveryAddress;
    private int deliveryTime;
    private String deliveryDate;
    private String packageChoice;
    private String mealType;
    private List<FoodItem> food;
    
    public Order() {
        food = new ArrayList<FoodItem>();
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getPackageChoice() {
        return packageChoice;
    }

    public void setPackageChoice(String packageChoice) {
        this.packageChoice = packageChoice;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public List<FoodItem> getFood() {
        return food;
    }

    public void setFood(List<FoodItem> food) {
        this.food = food;
    }
    
}
