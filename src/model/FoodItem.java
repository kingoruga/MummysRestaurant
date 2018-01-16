/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.util.*;

/**
 *
 * @author syntel
 */
public class FoodItem {
    private int foodItemId;
    private String name;
    private String description;
    private float price;
    private String type;
    private String isVeg;
    private String image;
    private ArrayList<Availability> availability = new ArrayList<Availability>();

    
	public int getFoodItemId() {
		return foodItemId;
	}
	public void setFoodItemId(int foodItemId) {
		this.foodItemId = foodItemId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVeg() {
		return isVeg;
	}
	public void setVeg(String isVeg) {
		this.isVeg = isVeg;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public ArrayList<Availability> getAvailability(){
		return availability;
	}
	
	public void addAvailability(Availability a) {
		
		availability.add(a);
	}
	@Override
	public String toString() {
		return "FoodItem [foodItemId=" + foodItemId + ", name=" + name + ", description=" + description + ", price="
				+ price + ", type=" + type + ", isVeg=" + isVeg + ", image=" + image + ", availability=" + availability
				+ "]";
	}
	
	
    
    
    

    
    
}
