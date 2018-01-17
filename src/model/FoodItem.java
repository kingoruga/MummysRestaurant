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
public class FoodItem {
   private byte[] FoodItemId;
    private String Name;
    private String Description;
    private float Price;
    private String Type;
    private boolean IsVeg;
    private String Image;
    private int Availability;

    public FoodItem() {}

    public FoodItem(byte[] id, String name, String description, float price, String type, boolean veg, String image, int availability){
        this.FoodItemId = id;
        this.Name = name;
        this.Description = description;
        this.Price = price;
        this.Type = type;
        this.IsVeg = veg;
        this.Image = image;
        this.Availability = availability;
    }
    
    public byte[] getFoodItemId() {
        return FoodItemId;
    }

    public void setFoodItemId(byte[] FoodItemId) {
        this.FoodItemId = FoodItemId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float Price) {
        this.Price = Price;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public boolean getIsVeg() {
        return IsVeg;
    }

    public void setIsVeg(boolean IsVeg) {
        this.IsVeg = IsVeg;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }
    
    public int getAvailability(){
        return Availability;
    }
    
    public void setAvailability(int Availability){
        this.Availability = Availability;
    }

    @Override
    public String toString() {
        String veg = getIsVeg() ? "is" : "isnt";
        return Name + " - " + "$" + Price + " - " + Type + " - " + veg + " veg" + "\n  " + Description;
    }

}
