/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.ArrayList;
import java.util.List;
import controller.AreaManagementController;

/**
 *
 * @author syntel
 */
public class AreaManageScene extends Scene {

    @Override
    public Scene transitionNext() {
        return new HomeScene();
    }

    @Override
    public void process() {
        // Create choices
        List<String> choices = new ArrayList<>();
        choices.add( "Display Areas" );
        choices.add( "Add Area" );
        choices.add( "Remove Area" );
        choices.add( "Edit Package Availability" );
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
            case "Display Areas":
                displayAreas();
                break;
            case "Add Area":
                promptAddArea();
                break;
            case "Remove Area":
                promptRemoveArea();
                break;
            case "View Package Availability":
                promptPackageAvailability();
                break;
            case "Back":
                requestTransition = true;
                break;
            default:
                //TODO: throw exception because choice is invalid
                System.out.println( "Invalid choice: " + selectedChoice );
                break;
        }
    }

    public void displayAreas()
    {
        AreaManagementController amc = new AreaManagementController();
        List<String> deliverableAreas;
        deliverableAreas = amc.getAreas();
        for ( int i = 0; i < deliverableAreas.size(); i++ )
        {
            System.out.println( deliverableAreas.get( i ) );
        }  
    }
    
    public void promptRemoveArea()
    {
        AreaManagementController amc = new AreaManagementController();
        System.out.print( "Enter zip code to remove: " );
        String zip = scanner.nextLine();
        //TODO: need to put together when Database functionality in place
        if (!amc.removeArea(zip)){
            System.out.print("Unable to remove area. Current Orders Exist");
        };
        //System.out.println( "TODO: need remove functionality" );
    }
    
    public void promptAddArea()
    {
        AreaManagementController amc = new AreaManagementController();
        System.out.print( "Enter new zip code: " );
        String zip = scanner.nextLine();
        if (amc.addArea(zip))
        {
            System.out.println( "Added new delivery area: " + zip );
        }
        else
        {
            System.out.println( "Could not add area: " + zip );
            //TODO: throw exception
        }
    }
    
    public void promptPackageAvailability()
    {
        //TODO: display packages
        AreaManagementController amc = new AreaManagementController();
        System.out.print( "Enter zip code: " );
        String zip = scanner.nextLine();
        List<String> packages = amc.getFoodInAreas(zip);
        for(int i = 0;i<packages.size();i++){
            System.out.println(packages.get(i));
        }
        
        //TODO: put together when database has functionality
        //System.out.println( "TODO: need package availability functionality" );
    }
}

