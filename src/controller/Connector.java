/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

//import controller.MenuController;
import model.Availability;
import model.FoodItem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author syntel
 */
public class Connector {
    
    Connection conn;
   MenuController meals;
   List<MenuController> mealsList = new ArrayList<>();
    
    

    public Connector() {
    	
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            conn
                    = DriverManager.getConnection(
                            "jdbc:oracle:thin:@localhost:1521:XE", "hr", "hr");
        } catch (Exception e) {
            System.out.println(e);
        }
        
        
    }
    
    
    
    public void createFoodQuery(FoodItem item) {    	
    
    	
    	try {
	    	ArrayList<Availability> loc = item.getAvailability(); 
	    	
	    	//Insert into FoodItem table
	    	//Doesnt insert image yet	    
	    	
	    	PreparedStatement pstmt = conn.prepareStatement("Insert into Food_item (name,description,price,type,is_veg) values(?,?,?,?,?)");
	    	pstmt.setString(1, item.getName());
	    	pstmt.setString(2,item.getDescription());
	    	pstmt.setString(3, Float.toString(item.getPrice()));
	    	pstmt.setString(4, item.getType());
	    	pstmt.setString(5, item.getVeg());	    	
	    	pstmt.executeUpdate();
	    	
	    	pstmt = conn.prepareStatement("Select Food_item_id from Food_item where name=?");
	    	pstmt.setString(1, item.getName());
	    	ResultSet rs = pstmt.executeQuery();
	    	
	    	int id = rs.getInt(1);
	    	
	    	//Insert into availability table for each zip code using fooditemid from above insert    		
	    	pstmt = conn.prepareStatement("Insert into Availability (food_item_id,zip_code,time,begin_date,end_date) values (?,?,?,?,?)");
	    	for(int i=0;i<loc.size();i++) {		    	
		    	pstmt.setString(1, Integer.toString(id));
		    	pstmt.setString(2, Integer.toString(loc.get(i).getZip()));
		    	pstmt.setString(3, loc.get(i).getMeal());		    	
		    	pstmt.setString(4, loc.get(i).getStart_date());
		    	pstmt.setString(5, loc.get(i).getEnd_date());	    	
		    	pstmt.executeUpdate();
	    	}
	    	
    	}catch(SQLException ex) {
    		Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
    	}    	
    	
    	
    	    			
    	
    }
    
    public void getFoodQuery(FoodItem item) {       	
    	
    	try{
    		
    		//retrieve info from food table first 
    		PreparedStatement pstmt = conn.prepareStatement("Select food_item_id,description, price, type, is_veg from food_item where name=?");
    		pstmt.setString(1, item.getName());
            ResultSet rs = pstmt.executeQuery();
            int id = rs.getInt(1);
            item.setDescription(rs.getString(2));
            item.setPrice(rs.getFloat(3));
            item.setType(rs.getString(4));
            item.setVeg(rs.getString(5));
            
            //retrieve corresponding availability info using food id next
            
            pstmt = conn.prepareStatement("Select zip_code,time,begin_date,end_date from Availability where food_item_id=?");
    		pstmt.setString(1, Integer.toString(id));
            rs = pstmt.executeQuery();
            while(rs.next()) {
            	Availability loc = new Availability();
	            loc.setZip(rs.getInt(1));
	            loc.setMeal(rs.getString(2));	            
	            loc.setStart_date(rs.getString(3));
	            loc.setEnd_date(rs.getString(4));  
	            item.addAvailability(loc);
            }
                           
               		
    	}catch(SQLException ex) {
    		Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
    	}   	
    	
    	
    }
    
   
    
    
    public void removeFoodQuery(String name) {   	    	
    	
    	try {
	    	PreparedStatement pstmt = conn.prepareStatement("Delete from Food_item where name=?");
	    	pstmt.setString(1, name);
	    	pstmt.executeQuery();
	    	
    	}catch(SQLException ex) {
    		Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
    	}   	
    	
    	
    }

    public void disableUserQuery(String cmd) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("Update Online_user set status = 'Disabled' where email=?");
            pstmt.setString(1, cmd);
            int count = pstmt.executeUpdate();
            if (count == 1) {
                System.out.println("User" + cmd + ": status changed to disabled!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void enableUserQuery(String cmd) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("Update Online_user set status = 'Enabled' where email=?");
            pstmt.setString(1, cmd);
            int count = pstmt.executeUpdate();
            if (count == 1) {
                System.out.println("User" + cmd + ": status changed to enabled!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List showMenuQuery() {
        try {
            //PreparedStatement pstmt = conn.prepareStatement("Select Food_item, description, price from food_item");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select name, description, price, type, is_veg from food_item");
            while (rs.next()) {
                String name = rs.getString(1);
                String desc = rs.getString(2);
                double price = rs.getDouble(3);
                String type = rs.getString(4);
                String veg = rs.getString(5);
                meals = new MenuController(name, desc, price, type, veg);
                mealsList.add(meals);
            }

            //meals.toString();
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mealsList;
    }
    

    public void deleteUserQuery(String cmd) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("Delete from Online_user where email=?");
            pstmt.setString(1, cmd);
            int count = pstmt.executeUpdate();
            if (count == 1) {
                System.out.println("User " + cmd + " has been deleted!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void changePasswordQuery(String cmd, String password) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("Update Online_user set password = ? where email=?");
            pstmt.setString(1, password);
            pstmt.setString(2, cmd);
            int count = pstmt.executeUpdate();
            if (count == 1) {
                System.out.println("User " + cmd + " password has been updated!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
