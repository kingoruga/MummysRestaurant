/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import model.Connector;
import model.Orders;

/**
 *
 * @author syntel
 */
public class OrderController {
     public ArrayList<Orders> getOrdersBy( String columnName )
    {
        return new Connector().selectOrdersSortColumn( columnName );
    }
    
    public ArrayList<Orders> getOrdersByCustomerColumn( String columnName )
    {
        return new Connector().selectOrdersSortCustomerColumn( columnName );
    }
    
    public ArrayList<Orders> getOrdersByAddressZip()
    {
        return new Connector().selectOrdersSortAddressZip();
    }

}
