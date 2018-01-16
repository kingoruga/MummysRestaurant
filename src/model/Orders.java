package model;

import com.syntel.Models.FoodItem;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Orders {
    private byte[] OrderId;
    private byte[] UserId;
    private byte[] AddressId;
    private String PaymentMethod;
    private String OrderDate;
    private String DeliveryDate;
    private String Time;
    private float Price;
    private ArrayList<FoodItem> Items; 
    private Address orderAddress;
    private OnlineUser user;

    public OnlineUser getUser() {
        return user;
    }

    public void setUser(OnlineUser user) {
        this.user = user;
    }
     
    public Orders(byte[] orderId, byte[] userId, byte[] addressId, String payment, String oDate, float price, String dDate, String dTime){
         this.OrderId = orderId;
         this.UserId = userId;
         this.AddressId = addressId;
         this.PaymentMethod = payment;
         this.OrderDate = oDate;
         this.DeliveryDate = dDate;
         this.Time = dTime;
         this.Price = price;
    }
     
    
    public byte[] getOrderId() {
        return OrderId;
    }

    public void setOrderId(byte[] OrderId) {
        this.OrderId = OrderId;
    }

    public byte[] getUserId() {
        return UserId;
    }

    public void setUserId(byte[] UserId) {
        this.UserId = UserId;
    }

    public byte[] getAddressId() {
        return AddressId;
    }

    public void setAddressId(byte[] AddressId) {
        this.AddressId = AddressId;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String PaymentMethod) {
        this.PaymentMethod = PaymentMethod;
    }
    
    public String getOrderDate() {
        return OrderDate;
    }
     
    public String getDeliveryDate() {
         return DeliveryDate;
    }
    
    public void setDeliveryDate(String DeliveryDate) {
         this.DeliveryDate = DeliveryDate;
    }
    
     public void setOrderDate(String OrderDate) {
        this.OrderDate = OrderDate;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float Price) {
        this.Price = Price;
    }

    public ArrayList<FoodItem> getItems() {
        return Items;
    }

    public void setItems(ArrayList<FoodItem> Items) {
        this.Items = Items;
    }
    
    public void addItem(FoodItem Item){
        //this.setItems(this.getItems().add(Item));
        this.getItems().add(Item);
    }
    
    public void removeItem(FoodItem Item){
        //this.setItems(this.getItems().remove(Item)); 
        this.getItems().remove(Item);
    }
    
    public void setOrderAddress( Address address )
    {
        this.orderAddress = address;
    }
    
    public Address getOrderAddress()
    {
        return this.orderAddress;
    }

    @Override
    public String toString()
    {
        StringBuilder toReturn = new StringBuilder();
        toReturn.append( ByteId.bytesToHex(OrderId) );
        toReturn.append( "\t" );
        toReturn.append( PaymentMethod );
        toReturn.append( "\t" );
        toReturn.append( OrderDate );
        toReturn.append( "\t" );
        toReturn.append( DeliveryDate );
        toReturn.append( "\t" );
        toReturn.append( Price );
        toReturn.append( "\t" );
        if ( orderAddress != null )
        {
            toReturn.append( orderAddress.getZip() );
            toReturn.append( "\t" );
        }
        else
        {
            toReturn.append( "NULL ADDRESS" );
            toReturn.append( "\t" );
        }
        
        if ( user != null )
        {
            toReturn.append( user.getEmail() );
            toReturn.append( "\t" );
            toReturn.append( user.getFirstName() );
            toReturn.append( " " );
            toReturn.append( user.getLastName() );
        }
        else
        {
            toReturn.append( "NULL USER" );
            toReturn.append( "\t" );
        }
        return toReturn.toString();
    }

}
