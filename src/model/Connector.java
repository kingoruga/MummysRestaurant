/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.MenuController;
import controller.UserController;
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
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author syntel
 */
public class Connector {

    Connection conn;
    MenuController meals;
    List<MenuController> mealsList = new ArrayList<>();
    OnlineUser user;
    UserController response = new UserController();

    public Connector() {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "hr", "hr");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void disableUserQuery(String cmd) {
        try (PreparedStatement pstmt = conn.prepareStatement("Update Online_user set status = 'Disabled' where email=?")) {
            pstmt.setString(1, cmd);
            int count = pstmt.executeUpdate();
            if (count == 1) {
               // response.userSuccessfullyUpdated(0);
            }
        } catch (SQLException ex) {
            ex.getMessage();
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void enableUserQuery(String cmd) {
        try (PreparedStatement pstmt = conn.prepareStatement("Update Online_user set status = 'Enabled' where email=?")) {
            pstmt.setString(1, cmd);
            int count = pstmt.executeUpdate();
            if (count == 1) {
                // response.userSuccessfullyUpdated(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List showMenuQuery() {
        try (Statement st = conn.createStatement()) {
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
        try (PreparedStatement pstmt = conn.prepareStatement("Delete from Online_user where email=?")) {
            pstmt.setString(1, cmd);
            int count = pstmt.executeUpdate();
            if (count == 1) {
                // response.userSuccessfullyUpdated(2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean userIsDisabledQuery(String email) {
        try (PreparedStatement pstmt = conn.prepareStatement("Select status from Online_user where email=?")) {
            pstmt.setString(1, email);
            pstmt.executeQuery();
            ResultSet rs = pstmt.getResultSet();
            String status = null;
            while (rs.next())
                status = rs.getString(1);
            if (status != null)
                return status.equals("Disabled");
        } catch (Exception ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void changePasswordQuery(String cmd, String password) {
        try (PreparedStatement pstmt = conn.prepareStatement("Update Online_user set password = ? where email=?")) {
            pstmt.setString(1, password);
            pstmt.setString(2, cmd);
            int count = pstmt.executeUpdate();
            if (count == 1) {
                 //response.userSuccessfullyUpdated(3);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public OnlineUser loginQuery(String email, String password) {
        byte[] userid = null;
        String firstName = " ";
        String lastName = "";
        String isAdmin = "";
        byte[] addressId = null;
        String pw = "";
        String status = "";
        try (PreparedStatement pstmt = conn.prepareStatement("Select user_id, first_name, last_name, is_admin, password, "
                + ", address_id, status from online_user where email=?")) {
            pstmt.setString(1, email);
            pstmt.executeQuery();
            ResultSet rs = pstmt.getResultSet();
            while (rs.next()) {
                userid = rs.getBytes(1);
                firstName = rs.getString(2);
                lastName = rs.getString(3);
                isAdmin = rs.getString(4);
                pw = rs.getString(5);
                addressId = rs.getBytes(6);
                status = rs.getString(7);
            }
            
            if (pw.equals(password) && status.equals("Enabled")) {
                Address address = getAddressById(addressId);
                user = new OnlineUser(userid,firstName,lastName,isAdmin, email, addressId, status); 
                user.setAddress(address);
                return user;
            }

        } catch (Exception ex) {
            ex.getMessage();
        }
        return null;
    }

    public boolean registerNewUserQuery(String fname, String lname, String email, String passWrd,
            String strAddress, String city, String state, int zipCode) {
        try {
            PreparedStatement pstmt2 = conn.prepareStatement("Select first_name from online_user where email=? ");
            pstmt2.setString(1, email);
            ResultSet rs = pstmt2.executeQuery();
            if (rs.next()) {
                return false;
            }

            PreparedStatement pstmt = conn.prepareStatement("Insert into ADDRESS (street, city, zip_code, state) values (?,?,?,?)");
            pstmt.setString(1, strAddress);
            pstmt.setString(2, city);
            pstmt.setInt(3, zipCode);
            pstmt.setString(4, state);
            int count = pstmt.executeUpdate();

            if (count == 1) {
                int admin = 1;
                String status = "Enabled";
                PreparedStatement pstmt1 = conn.prepareStatement
                  ("Insert into ONLINE_USER (first_name, last_name, is_admin, password, email, address_id, status ) "
                          + "values (?,?,?,?,?,(Select address_id from address where street=? and zip_code=?),?)"); //password will be encrypted in web app
                pstmt1.setString(1, fname);
                pstmt1.setString(2, lname);
                pstmt1.setInt(3, admin);
                pstmt1.setString(4, passWrd);
                pstmt1.setString(5, email);                
                pstmt1.setString(6, strAddress);
                pstmt1.setInt(7, zipCode);
                pstmt1.setString(8, status);
  
                count = pstmt1.executeUpdate();
                if (count == 1)
                    return true;
            }
        } catch (SQLException ex) {
            System.out.println("Unable to Register");
        }
        return false;
    }
    
    public boolean addZipToServiceArea(String zip){
        try{
            PreparedStatement pstmt = conn.prepareStatement("insert into service_areas (zip_code) values (?)");
            pstmt.setString(1,zip);
            int count = pstmt.executeUpdate();
            if (count == 1){
                return true;
            }
        }catch(SQLException ex){
            return false;
        }
        return false;
    }
    
    public boolean removeZipFromServiceArea(String zip){
        try{
            PreparedStatement pstmt = conn.prepareStatement("Delete from service_areas where zip_code = ?");
            pstmt.setInt(1,Integer.parseInt(zip));
            int count = pstmt.executeUpdate();
            if (count == 1){
                return true;
            }
        }catch(SQLException ex){
            return false;
        }
        return false;
    }
  
    public List getAreas(){
        List<String> allAreas = new ArrayList<>();
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT zip_code from service_areas");
            while(rs.next()){
                allAreas.add(rs.getString(1));
            }
        }catch(SQLException ex){
            System.out.println("Unable to fetch areas from database.");
        }
        return allAreas;
    }
    
    public List getFoodItemsInArea(String zip){
        List<String> foodInArea = new ArrayList();
        try{
            PreparedStatement pstmt = conn.prepareStatement("select fi.food_item_id,name || ' - ' || description as Food_Item from food_item fi join availability a on fi.food_item_id = a.food_item_id join service_areas se on a.zip_code = se.zip_code where se.zip_code = ?");
            pstmt.setInt(1, Integer.parseInt(zip));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                foodInArea.add(rs.getString(1)+" "+rs.getString(2));
            }
        }catch(SQLException ex){
            System.out.println("Unable to get food in "+zip);
        }
        return foodInArea;
    }

   public void deleteMenuItem(String cmd) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("Delete from Food_item where name=?");
            pstmt.setString(1, cmd);
            int count = pstmt.executeUpdate();
            if (count == 1) {
                System.out.println(cmd + " has been deleted!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
   /*
        Returns the OnlineUser that corresponds to the id given.
    */
    public OnlineUser getUserById( byte[] userId )
    {
        try
        {
            //PreparedStatement pstmt = conn.prepareStatement("Select Food_item, description, price from food_item");
            PreparedStatement st = conn.prepareStatement( "SELECT * FROM online_user WHERE user_id=?" );
            //select the orders and order them by the column given
            st.setBytes(1, userId );
            //st.setShort( 1, addressId );
            ResultSet rs = st.executeQuery();
            if (rs.next())
            {
                //OnlineUser(byte[] id, String fname, String lname, String isAdmin, String pword, String email, int addressId, String status){
                OnlineUser toReturn = new OnlineUser(
                        rs.getBytes( "user_id" ),
                        rs.getString( "first_name" ),
                        rs.getString( "last_name" ),
                        rs.getString( "is_admin" ),
                        rs.getString( "email" ),
                        rs.getBytes( "address_id" ),
                        rs.getString( "status" )
                );
                
                toReturn.setAddress( getAddressById( toReturn.getAddressId() ) );
                return toReturn;
            } 
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    /*
        Returns the address that corresponds to the id given.
    */
    public Address getAddressById( byte[] addressId )
    {
        try
        {
            //PreparedStatement pstmt = conn.prepareStatement("Select Food_item, description, price from food_item");
            PreparedStatement st = conn.prepareStatement( "SELECT * FROM address WHERE address_id=?" );
            //select the orders and order them by the column given
            st.setBytes(1, addressId );
            ResultSet rs = st.executeQuery();
            if (rs.next())
            {
                Address toReturn = new Address();
                toReturn.setAddressId( addressId );
                toReturn.setCity( rs.getString( "city" ) );
                toReturn.setStreet1( rs.getString( "street" ) );
                toReturn.setZip( rs.getInt( "zip_code" ) + "" );
                toReturn.setState( rs.getString( "state" ) );
                return toReturn;
            } 
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public ArrayList<Orders> convertOrdersResultSet( ResultSet rs ) throws SQLException
    {
        ArrayList<Orders> ordersList = new ArrayList<>();
        while ( rs.next() )
        {
            //create an order object from database values object
             //public Orders(int orderId, int userId, int addressId, String payment, String oDate, float price, String dDate, String dTime)
             Orders order = new Orders(
                     //convert the order_id from bytes to an integer
                     rs.getBytes( "order_id" ),
                     rs.getBytes( "user_id" ),
                     rs.getBytes( "address_id" ),
                     rs.getString( "payment_type" ),
                     rs.getString( "order_date" ),
                     rs.getFloat( "price" ),
                     rs.getString( "delivery_date" ),
                     rs.getString( "delivery_time" )
             );
             order.setOrderAddress( getAddressById( order.getAddressId() ) );
             order.setUser( getUserById( order.getUserId() ) );
             //then add it to the orderlist
             ordersList.add( order );
        }
        
        return ordersList;
    }
    
    /*
        Returns list of all orders from database,they are ordered by the customer's columnName given
    */
    public ArrayList<Orders> selectOrdersSortCustomerColumn( String columnName )
    {
        try 
        {
            //PreparedStatement pstmt = conn.prepareStatement("Select Food_item, description, price from food_item");
            Statement st = conn.createStatement();
            //select the orders and order them by the column given
            ResultSet rs = st.executeQuery("SELECT orders.order_id,orders.user_id,orders.address_id,orders.payment_type,orders.order_date,orders.price,orders.delivery_date,orders.delivery_time FROM orders,online_user WHERE orders.user_id=online_user.user_id(+) ORDER BY online_user." + columnName );
            return convertOrdersResultSet( rs );
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new ArrayList<>();
    }
    
    /*
        Returns a list of all orders where they are ordered by the zip code
    */
    public ArrayList<Orders> selectOrdersSortAddressZip()
    {
        try 
        {
            //PreparedStatement pstmt = conn.prepareStatement("Select Food_item, description, price from food_item");
            Statement st = conn.createStatement();
            //select the orders and order them by the column given
            ResultSet rs = st.executeQuery("SELECT orders.order_id,orders.user_id,orders.address_id,orders.payment_type,orders.order_date,orders.price,orders.delivery_date,orders.delivery_time FROM orders,address WHERE orders.address_id=address.address_id(+) ORDER BY address.zip_code"  );
            return convertOrdersResultSet( rs );
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new ArrayList<>();
    }
       
    /*
        Returns list of all orders,ordered by the column name given
    */
    public ArrayList<Orders> selectOrdersSortColumn( String columnName )
    {
        try 
        {
            //PreparedStatement pstmt = conn.prepareStatement("Select Food_item, description, price from food_item");
            Statement st = conn.createStatement();
            //select the orders and order them by the column given
            ResultSet rs = st.executeQuery("SELECT * FROM orders ORDER BY " + columnName );
            return convertOrdersResultSet( rs );
                  
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        
        }
        
        return new ArrayList<>();
    }
    
    public ArrayList<FoodItem> selectAllFoodItems()
    {
        ArrayList<FoodItem> items = new ArrayList<>();
        try
        {
            //PreparedStatement pstmt = conn.prepareStatement("Select Food_item, description, price from food_item");
            PreparedStatement st = conn.prepareStatement( "SELECT * FROM food_item" );
            //select the orders and order them by the column given
            ResultSet rs = st.executeQuery();
            while (rs.next())
            {
                //byte[] id, String name, String description, float price, String type, boolean veg, String image, int availability){
                FoodItem foodItem = new FoodItem( 
                        rs.getBytes( "food_item_id" ),
                        rs.getString( "name" ),
                        rs.getString( "description" ),
                        rs.getFloat( "price" ),
                        rs.getString( "type" ),
                        rs.getString( "is_veg" ).equalsIgnoreCase( "yes" ),
                        "",
                        -1
                );
                items.add( foodItem );
            } 
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return items;   
    }



    public boolean addPackagetoArea(String zip,String packageNo){
       try{
           PreparedStatement pstmt = conn.prepareStatement("Insert into Availability (food_item_id,zip_code) values (?,?)");
           pstmt.setString(1,packageNo);
           pstmt.setString(2,zip);
           int count = pstmt.executeUpdate();
           if (count == 1)
               return true;
       }catch(SQLException ex){
           return false;
       }
       return false;
   }
   
    public boolean removePackageFromArea(String zip,String packageNo){
       try{
           PreparedStatement pstmt = conn.prepareStatement("Delete from Availability where zip_code = ? and food_item_id = ?");
           pstmt.setString(2,packageNo);
           pstmt.setString(1,zip);
           int count = pstmt.executeUpdate();
           if (count == 1)
               return true;
       }catch(SQLException ex){
           return false;
       }
       return false;
   }

    
    public boolean getAdmin(String email){
       try{
           PreparedStatement pstmt = conn.prepareStatement("select is_admin from online_user where email=?");
           pstmt.setString(1,email);
           ResultSet rs = pstmt.executeQuery();
           while (rs.next()){
               if(rs.getInt(1) == 1)
                    return true;
           }
       }catch(SQLException ex){
           return false;
       }
       return false;
    }
    
    
    public void getFoodQuery(Fooditem item) {  
        try{
            DateFormat output = new SimpleDateFormat("dd-MMM-yy");
            DateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date; 
            PreparedStatement pstmt = conn.prepareStatement("Select description, price, type, is_veg from food_item where name=?"); 
            pstmt.setString(1, item.getName());
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){ 
                item.setDescription(rs.getString(1));
                item.setPrice(rs.getFloat(2));
                item.setType(rs.getString(3));
                item.setVeg(rs.getString(4));
                pstmt = conn.prepareStatement("Select zip_code,time,begin_date,end_date from Availability where food_item_id=(Select food_item_id from food_item where name=?)");
                pstmt.setString(1, item.getName());
                rs = pstmt.executeQuery();
                while(rs.next()) {
                    Availability loc = new Availability();
                    loc.setZip(rs.getInt(1)); 
                    loc.setMeal(rs.getString(2));
                    date = input.parse(rs.getString(3));
                    loc.setStart_date(output.format(date));
                    date = input.parse(rs.getString(4));
                    loc.setEnd_date(output.format(date));
                    item.addAvailability(loc);
                }
            }
        }catch(Exception ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void createFoodQuery(Fooditem item) {  
        try {
            ArrayList<Availability> loc = item.getAvailability();
            PreparedStatement pstmt = conn.prepareStatement("Insert into Food_item (name,description,price,type,is_veg) values(?,?,?,?,?)");
            pstmt.setString(1, item.getName());
            pstmt.setString(2,item.getDescription());
            pstmt.setString(3, Float.toString(item.getPrice()));
            pstmt.setString(4, item.getType());
            pstmt.setString(5, item.getVeg());
            pstmt.executeUpdate();
            //Insert into availability table for each zip code using fooditemid from above insert
            pstmt = conn.prepareStatement("Insert into Availability (food_item_id,zip_code,time,begin_date,end_date) values ((select food_item_id from food_item where name=?),?,?,?,?)");
            for(int i=0;i<loc.size();i++) {
                System.out.println(loc.get(i));
                pstmt.setString(1, item.getName());
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
       
    
    public void removeFoodQuery(String name) { 
        try {
            PreparedStatement pstmt = conn.prepareStatement("Delete from Food_item where name=?");
            pstmt.setString(1, name);
            pstmt.executeQuery();
        }catch(SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex); 
        }  
    }
    
    
    
   
}




