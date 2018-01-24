/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import controller.UserController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

/**
 *
 * @author syntel
 */
public class Connector {

    Connection conn;
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
                //send response back to view
                // response.userSuccessfullyUpdated(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteUserQuery(String cmd) {
        try (PreparedStatement pstmt = conn.prepareStatement("Delete from Online_user where email=?")) {
            pstmt.setString(1, cmd);
            int count = pstmt.executeUpdate();
            if (count == 1) {
                 //send response back to view
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
                 //send response back to view
                 //response.userSuccessfullyUpdated(3);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public OnlineUser loginQuery(String email, String password) {
        int userid=0;
        String firstName = " ";
        String lastName = "";
        String isAdmin = "";
        int addressId=0;
        String pw = "";
        String status = "";
        try (PreparedStatement pstmt = conn.prepareStatement("Select user_id, first_name, last_name, is_admin, password, "
                + " address_id, status from online_user where email=?")) {
            pstmt.setString(1, email);
            pstmt.executeQuery();
            ResultSet rs = pstmt.getResultSet();
            while (rs.next()) {
                userid = rs.getInt(1);
                firstName = rs.getString(2);
                lastName = rs.getString(3);
                isAdmin = rs.getString(4);
                pw = rs.getString(5);
                addressId = rs.getInt(6);
                status = rs.getString(7);
            }
            
            if (pw.equals(password) && status.equals("Enabled")) {
                Address address = getAddressById(addressId);
                user = new OnlineUser(userid,firstName,lastName,isAdmin, email, addressId, status); 
                user.setAddress(address);
            }
            else {
                //return a login failed response
            }

        } catch (Exception ex) {
            ex.getMessage();
        }
        return user;
        
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

    public boolean addZipToServiceArea(int zip){
        try{
            PreparedStatement pstmt = conn.prepareStatement("insert into service_areas (zip_code) values (?)");
            pstmt.setInt(1,zip);
            int count = pstmt.executeUpdate();
            if (count == 1){
                return true;
            }
        }catch(SQLException ex){
            return false;
        }
        return false;
    }

    public boolean removeZipFromServiceArea(int zip){
        try{
            PreparedStatement pstmt = conn.prepareStatement("Delete from service_areas where zip_code = ?");
            pstmt.setInt(1,zip);
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
    
    public List getFoodItemsInArea(int zip){
        List<String> foodInArea = new ArrayList();
        try{
            PreparedStatement pstmt = conn.prepareStatement("select fi.food_item_id,name || ' - ' || description as Food_Item from food_item fi join availability a on fi.food_item_id = a.food_item_id join service_areas se on a.zip_code = se.zip_code where se.zip_code = ?");
            pstmt.setInt(1, zip);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                foodInArea.add(rs.getString(1)+" "+rs.getString(2));
            }
        }catch(SQLException ex){
            System.out.println("Unable to get food in " + zip);
        }
        return foodInArea;
    }

    public List<FoodItem> foodAvailableFor(String email) {

        // get the users zipcode
        int zipcode = 0;

        try {
            PreparedStatement pstmt
                    = conn.prepareStatement("select a.zip_code "
                    + "from online_user o, address a "
                    + "where o.address_id = a.address_id "
                    + "and o.email = ?");
            pstmt.setString(1, email);
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                zipcode = result.getInt("zip_code");
            }
        } catch (SQLException se) {
        }

        // get the food
        List<FoodItem> foods = new ArrayList<>();

        try {
            PreparedStatement pstmt
                    = conn.prepareStatement("select f.food_item_id, f.name, f.description, f.price, f.type, f.is_veg "
                    + "from food_item f, availability a "
                    + "where f.FOOD_ITEM_ID = a.FOOD_ITEM_ID "
                    + "and a.zip_code = ?");
            pstmt.setInt(1, zipcode);
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                FoodItem food = new FoodItem();
                int id = Integer.parseInt(result.getString("food_item_id"));
                food.setFoodItemId(result.getInt("food_item_id"));
                food.setName(result.getString("name"));
                food.setDescription(result.getString("description"));
                food.setPrice(result.getFloat("price"));
                food.setType(result.getString("type"));
                food.setIsVeg(result.getString("is_veg").equalsIgnoreCase("yes"));
                foods.add(food);
            }

        } catch (SQLException ex) {
        }

        return foods;
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
    public OnlineUser getUserById( int userId )
    {
        try
        {
            //PreparedStatement pstmt = conn.prepareStatement("Select Food_item, description, price from food_item");
            PreparedStatement st = conn.prepareStatement( "SELECT * FROM online_user WHERE user_id=?" );
            //select the orders and order them by the column given
            st.setInt(1, userId );
            //st.setShort( 1, addressId );
            ResultSet rs = st.executeQuery();
            if (rs.next())
            {
                //OnlineUser(byte[] id, String fname, String lname, String isAdmin, String pword, String email, int addressId, String status){
                OnlineUser toReturn = new OnlineUser(
                        rs.getInt( "user_id" ),
                        rs.getString( "first_name" ),
                        rs.getString( "last_name" ),
                        rs.getString( "is_admin" ),
                        rs.getString( "email" ),
                        rs.getInt( "address_id" ),
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
    public Address getAddressById( int addressId )
    {
        try
        {
            //PreparedStatement pstmt = conn.prepareStatement("Select Food_item, description, price from food_item");
            PreparedStatement st = conn.prepareStatement( "SELECT * FROM address WHERE address_id=?" );
            //select the orders and order them by the column given
            st.setInt(1, addressId );
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
                     rs.getInt( "order_id" ),
                     rs.getInt( "user_id" ),
                     rs.getInt( "address_id" ),
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
                ArrayList a = new ArrayList();
                FoodItem foodItem = new FoodItem( 
                        rs.getInt( "food_item_id" ),
                        rs.getString( "name" ),
                        rs.getString( "description" ),
                        rs.getFloat( "price" ),
                        rs.getString( "type" ),
                        rs.getString( "is_veg" ).equalsIgnoreCase( "yes" ),
                        "",
                        a
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



    public boolean addPackagetoArea(int zip,String packageNo){
       try{
           PreparedStatement pstmt = conn.prepareStatement("Insert into Availability (food_item_id,zip_code) values (?,?)");
           pstmt.setString(1,packageNo);
           pstmt.setInt(2,zip);
           int count = pstmt.executeUpdate();
           if (count == 1)
               return true;
       }catch(SQLException ex){
           return false;
       }
       return false;
   }
   
    public boolean removePackageFromArea(int zip,String packageNo){
       try{
           PreparedStatement pstmt = conn.prepareStatement("Delete from Availability where zip_code = ? and food_item_id = ?");
           pstmt.setString(2,packageNo);
           pstmt.setInt(1,zip);
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
    
    

    public void getFoodQuery(FoodItem item) {
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
                item.setIsVeg(rs.getString(4).equalsIgnoreCase("yes"));
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
                    item.setAvailability(loc);
                }
            }
        }catch(Exception ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void createFoodQuery(FoodItem item) {
        try {
            ArrayList<Availability> loc = item.getAvailability();
            PreparedStatement pstmt = conn.prepareStatement("Insert into Food_item (name,description,price,type,is_veg) values(?,?,?,?,?)");
            pstmt.setString(1, item.getName());
            pstmt.setString(2,item.getDescription());
            pstmt.setString(3, Float.toString(item.getPrice()));
            pstmt.setString(4, item.getType());
            pstmt.setString(5, item.getIsVeg() == false? "No":"Yes");
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
    
    public void addOrder(Orders order, Address address, OnlineUser user) {
        
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        Date orderDate = new Date();
        Date deliveryDate;

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        try {
            deliveryDate = df.parse(order.getDeliveryDate());
        } catch (ParseException e) {
            deliveryDate = new Date();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(deliveryDate);
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(order.getTime()));
        Date deliveryTime = cal.getTime(); // returns new date object, one hour in the future

        try {

            // insert into orders

            PreparedStatement pstmt = conn.prepareStatement("Insert into ORDERS "
                    + "(user_id, address_id, payment_type, order_date, price, delivery_date, delivery_time) "
                    + "values (?,?,?,?,?,?,?)");

            pstmt.setInt(1, user.getUserId());
            pstmt.setInt(2, address.getAddressId());
            pstmt.setString(3, order.getPaymentMethod());
            pstmt.setDate(4, new java.sql.Date(orderDate.getTime()));
            pstmt.setDouble(5, order.getPrice());
            pstmt.setDate(6, new java.sql.Date(deliveryDate.getTime()));
            pstmt.setDate(7, new java.sql.Date(deliveryTime.getTime()));
            int count = pstmt.executeUpdate();
            if (count == 1) {
                System.out.println("An order has been placed.");
            }

            // Add all to order_items
            // create a food item -> count of food item map, not adding
            // any duplicates based on their key id

            Map<FoodItem, Integer> items = new HashMap<>();
            Set<Integer> ids = new HashSet<>();
            order.getItems().forEach(f -> {
                int id = f.getFoodItemId();
                if (!ids.contains(id)) {
                    items.put(f, 0);
                    ids.add(id);
                }
                items.put(f, items.get(f) + 1);
            });

            for(Map.Entry<FoodItem, Integer> entry : items.entrySet()) {
                pstmt = conn.prepareStatement("INSERT INTO " +
                        "ORDER_ITEMS (order_id, food_item_id, quantity)" +
                        "VALUES( (SELECT order_id FROM orders WHERE price=? AND order_date=? AND user_id=? and ROWNUM = 1), ?, ?)");

                pstmt.setDouble(1, order.getPrice());
                pstmt.setDate(2, new java.sql.Date(orderDate.getTime()));
                pstmt.setInt(3, user.getUserId());
                pstmt.setInt(4, entry.getKey().getFoodItemId());
                pstmt.setInt(5, entry.getValue());
                pstmt.executeUpdate();
            }


        } catch (Exception e) {
            System.out.println("Order failed.");
        }
    }
    
    public List<Availability> getAvailabilities(FoodItem item) {
        List<Availability> availabilities = new ArrayList<>();
        try {

            PreparedStatement statement = conn.prepareStatement("" +
                    "select a.* from AVAILABILITY a, FOOD_ITEM f where f.FOOD_ITEM_ID = ?");
            statement.setInt(1, item.getFoodItemId());

            ResultSet rs = statement.executeQuery();
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            while (rs.next()){
                Availability a = new Availability();
                a.setZip(rs.getInt("zip_code"));
                a.setStart_date(df.format(rs.getDate("begin_date")));
                a.setEnd_date(df.format(rs.getDate("end_date")));
                a.setMeal(rs.getString("time"));
            }
        }catch(SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }

        return availabilities;
    }
    
    
    
   
}

