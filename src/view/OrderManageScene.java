/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.OrderController;
import java.util.ArrayList;
import java.util.List;
import model.Orders;

/**
 *
 * @author syntel
 */
public class OrderManageScene extends Scene {

    private OrderController control;
    public OrderManageScene()
    {
        control = new OrderController();
    }

    @Override
    public Scene transitionNext() {
        return new HomeScene();
    }

    @Override
    public void process() {
        // Create choices
        System.out.println( "View orders by?" );
        List<String> choices = new ArrayList<>();
        choices.add( "Mode of payment" );
        choices.add( "Date" );
        choices.add( "Zip Code" );
        choices.add( "Price" );
        choices.add( "OrderID" );
        choices.add( "Customer Email" );
        choices.add( "Customer Name" );
        choices.add( "Back" );
        
        do {
            // Display all choices
            for (int i = 0; i < choices.size(); i++)
                System.out.println("(" + i + ")" + " " + choices.get(i));

            // Match user input with choice
            selectedChoice = matchInputWithChoice(scanner.nextLine(), choices);

        } while (selectedChoice == null);
        
        switch ( selectedChoice )
        {
            case "Mode of payment":
                displayOrdersByColumn( "payment_type" , "payment type" );
                break;
            case "Date":
                displayOrdersByColumn( "delivery_date" , "delivery date" );
                break;
            case "Price":
                displayOrdersByColumn( "price" , "price" );
                break;
            case "Zip Code":
                displayOrdersByArea();
                break;
            case "OrderID":
                displayOrdersByColumn( "order_id" , "order id" );
                break;
            case "Customer Name":
                displayOrdersByCustomerColumn( "first_name" , "customer name" );
                break;
            case "Customer Email":
                displayOrdersByCustomerColumn( "email" , "customer email" );
                break;
            case "Back":
                requestTransition = true;
                break;
            default:
                //TODO: throw exception since choice is invalid
                System.out.println( "Unknown choice: " + selectedChoice );
                break;
        }
    }
    
    public void displayOrdersByCustomerColumn( String columnName, String description )
    {
        System.out.println( "Showing orders sorted by " + description );
        displayOrders( control.getOrdersByCustomerColumn( columnName ) );
    }
    
    public void displayOrdersByColumn( String columnName, String description )
    {
        System.out.println( "Showing orders sorted by " + description );
        displayOrders( control.getOrdersBy( columnName ) );
    }
    
    public void displayOrdersByArea()
    {
        System.out.println( "Showing orders sorted by zip code" );
        displayOrders( control.getOrdersByAddressZip() );
    }
    
    public static void main( String[] args )
    {
        /*
        //Seeing that selectAllFoodItems actually returns something without error
        new Connector().selectAllFoodItems().forEach((foodItem) -> {
            System.out.println( foodItem );
        });
        */
        OrderManageScene scene = new OrderManageScene();
        while ( true )
        {
            scene.process();
            if ( scene.requestTransition )
            {
                System.out.println( "DONE" );
                break;
            }
        }
    }

    public void displayOrders(ArrayList<Orders> orderList ) 
    {
        System.out.println( "Order_Id\t\t\t\tPayment\tOrder Date\t\tDelivery Date\t\tPrice\tZip Code\tUser Email\tUser Name" );
        orderList.forEach((order) -> {
            System.out.println( order );
        });
    }

    
}
