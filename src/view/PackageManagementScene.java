/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author syntel
 */


package view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import controller.PackageController; 
import model.Availability; 
import model.FoodItem;




public class PackageManagementScene extends Scene {
	
	private FoodItem fItem;
	private Availability loc;
	private String userInput;
	private String name;
	private Float num;
	private int zip;
	private boolean valid;
	private PackageController controller;
	private ArrayList<String> validChoices;
	
	
	@Override
	public Scene transitionNext() {
		
		controller = new PackageController();
		fItem = new FoodItem();
		
		
		switch(selectedChoice) {
		
			//collect new food item fields from user
			case "Create New Food Item":				
				
                                valid=false;
                                while(!valid){                                    
                                    System.out.println("Enter Name: ");
                                    userInput = scanner.nextLine();
                                    if(userInput.length() <= 10){
                                        fItem.setName(userInput);
                                        valid=true;
                                    }else{
                                        System.out.println("Name to Long!");
                                    }                         
                                }
				
                                valid=false;
                                while(!valid){
                                    System.out.println("Enter Description: ");
                                    userInput = scanner.nextLine();
                                    if(userInput.length()<=60){
                                        fItem.setDescription(userInput);
                                        valid=true;
                                    }else{
                                        System.out.println("description to long!");
                                    }
                                }
                                    
				
				valid = false;
				while(!valid) {
					try {
						System.out.println("Enter Price: ");
						num = scanner.nextFloat();
						scanner.nextLine();
						fItem.setPrice(num);
						valid = true;
					}catch(InputMismatchException e) {
						System.out.println("Please enter a number!");
						scanner.nextLine();
					}
				}
                                
				valid=false;
                                while(!valid){
                                    System.out.println("Enter Type: ");
                                    userInput = scanner.nextLine();
                                    if(userInput.length() <= 8){
                                        fItem.setType(userInput);
                                        valid=true;
                                    }else{
                                        
                                        System.out.println("Type to long!");
                                    }                                   
                                }
				
								
				valid = false;
				while(!valid) {
					System.out.println("Vegetarian? (yes/no): ");
					userInput = scanner.nextLine();
					if(userInput.toUpperCase().equals("YES") || userInput.toUpperCase().equals("NO")) {
						fItem.setIsVeg(userInput.equalsIgnoreCase("yes"));
						valid = true;
					}else {
						System.out.println("Invalid Input");
					}
					
				}
				
				System.out.println("Image URL: ");
				userInput = scanner.nextLine();
				fItem.setImage(userInput);
				
				createLocation(); //Add location values				
							
				controller.createNewItem(fItem);//add new item to database				
				
				System.out.println("Created: "+fItem);
				
				break;
					
			case "Update Existing Food Item":				
				System.out.print("Enter name of Food Item: ");
				name=scanner.nextLine();
				fItem.setName(name);
				controller.getFoodItem(fItem);	
                                
                                if(fItem.getDescription() == null){
                                    System.out.println(name+" Doesnt Exist!");
                                    break;
                                }
				
				System.out.println("Retrieved Food Item: "+fItem);
				
				updateFood();					
				
				
				//to update the food item we first delete the old record then add a new one
				controller.deleteFoodItem(name);
				controller.createNewItem(fItem);	
				
				break;
				
			case "Delete Food Item":
				System.out.print("Enter name of Food Item: ");
				name = scanner.nextLine();
				controller.deleteFoodItem(name);
				break;				
						
			case "Back":
				return new HomeScene();			
				
		}		
				
		return new PackageManagementScene();
		
	}
	
	
	public void updateFood() {
		
		List<String> choices = new ArrayList<>();
		
		List<String> zips = new ArrayList<>();
		
		
		choices.add("Name");
		choices.add("Description");
		choices.add("Price");		
		choices.add("Type");
		choices.add("Veg (yes/no");
		choices.add("Availability Info");
		choices.add("Commit Changes");
		
			
		
		boolean commit = false;
		
		while(!commit) {
		
			do {
				System.out.println("SELCET FIELD TO EDIT");
				for(int i=0;i<choices.size();i++) {
					System.out.println("(" + (i) + ")" +" "+ choices.get(i));
				}
				
				selectedChoice = matchInputWithChoice(scanner.nextLine(), choices);
				
			}while(selectedChoice == null);		
			
			switch(selectedChoice) {
				
				case "Name":
                                        valid=false;
                                        while(!valid){                                            
                                            System.out.print("Enter New Name: ");
                                            String name = scanner.nextLine();
                                            if(name.length()<=10){
                                                fItem.setName(scanner.nextLine());
                                                valid=true;
                                            }else{
                                                System.out.println("Name to Long!");
                                            }
                                        }
                                        break;
				
				case "Description":
                                    
                                    valid=false;
                                    while(!valid){
                                        System.out.println("Enter Description: ");
                                         userInput = scanner.nextLine();
                                        if(userInput.length()<=60){
                                            fItem.setDescription(userInput);
                                            valid=true;
                                        }else{
                                            System.out.println("description to long!");
                                        }
                                    }
                                    break;
					
				case "Price":
					
					valid = false;
					while(!valid) {
						try {
							System.out.print("Enter New Price: ");
							fItem.setPrice(scanner.nextFloat());
							scanner.nextLine();
							valid = true;
							break;							
						}catch(InputMismatchException e) {
							System.out.println("Please enter a number!");
							scanner.nextLine();
						}
					}
					break;
					
					
				case "Type":
                                        valid=false;
					while(!valid) {
						System.out.print("Enter New Type: ");
						userInput = scanner.nextLine();
						if(userInput.length()<=8) {
							fItem.setType(userInput);
							valid = true;
						}else {
							System.out.println("Type to long!");
						}
                                        }
                                    
					
					break;
					
				case "Veg (yes/no":
                                        valid=false;
					while(!valid) {
						System.out.println("Vegetarian? (yes/no): ");
						userInput = scanner.nextLine();
						if(userInput.toUpperCase().equals("YES") || userInput.toUpperCase().equals("NO")) {
							fItem.setIsVeg(userInput.equalsIgnoreCase("yes"));
							valid = true;
						}else {
							System.out.println("Invalid Input");
						}
						
					}
					break;
					
				case "Availability Info":
					
					List<Availability> locs = connector.getAvailabilities(fItem);
					zips.clear();
					for(int i=0;i<locs.size();i++) {
						zips.add(Integer.toString(locs.get(i).getZip()));
					}
					zips.add("New Area");	
					
					do {
						System.out.println("SELCET AREA TO EDIT");
						for(int i=0;i<zips.size();i++) {
							System.out.println("(" + (i) + ")" +" "+ zips.get(i));
						}
						
						selectedChoice = matchInputWithChoice(scanner.nextLine(), zips);
						
					}while(selectedChoice == null);	
					
					if(selectedChoice.equals("New Area")) {
						createLocation();
					}else {
						int index = zips.indexOf(selectedChoice);
						updateAvailability(index);
					}	
					break;
				
					
				case "Commit Changes":
					commit=true;
					break;				
					
			}
		}
	}
	
	public void createLocation() {
		
		validChoices = new ArrayList<String>();
                ArrayList<String> validMonths = new ArrayList<String>();               
		
                
		boolean addingLoc = true;				
		while(addingLoc) {
			loc = new Availability();
			valid = false;
			while(!valid) {
				try {
					System.out.println("Zip Code: ");
					zip = scanner.nextInt();	
					scanner.nextLine();
					loc.setZip(zip);							
					valid = true;
				}catch(InputMismatchException e) {
					System.out.println("Please enter a number!");
					scanner.nextLine();
				}
			}					
			
			validChoices.add("BREAKFAST");
			validChoices.add("LUNCH");
			validChoices.add("DINNER");
			valid=false;
			while(!valid) {
				System.out.println("Meal Availability (Breakfast/Lunch/Dinner): ");
				userInput = scanner.nextLine();
				if(validChoices.contains(userInput.toUpperCase())) {
					loc.setMeal(userInput);	
					valid=true;
				}else {
					System.out.println("Invalid Input");
				}						
			}
                        valid =false;
			while(!valid){
                            try{                                
                                SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yy");
                                System.out.println("Start Date: ");
                                userInput = scanner.nextLine();
                                format.parse(userInput);
                                loc.setStart_date(userInput);
                                valid=true;
                            }catch(Exception e){
                                System.out.println("Invalid date format!");
                            }
                        }
                        
                            
                            
                        valid =false;
			while(!valid){
                            try{                                
                                SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yy");
                                System.out.println("End Date: ");
                                userInput = scanner.nextLine();
                                format.parse(userInput);
                                loc.setEnd_date(userInput);
                                valid=true;
                            }catch(Exception e){
                                System.out.println("Invalid date format!");
                            }
                        }             
                        
                        	
			
			valid = false;
			while(!valid) {
				System.out.println("Enter Another Location? (yes/no): ");
				userInput = scanner.nextLine();
				if(userInput.toUpperCase().equals("YES"))  {							
					valid = true;							
				}else if(userInput.toUpperCase().equals("NO")){
					valid = true;
					addingLoc = false;
					
				}else {
					System.out.println("Invalid Input");
				}
				
			}					


			// fItem.addAvailability(loc);
		
		}
		
	}
	
	public void updateAvailability(int index) {
		validChoices = new ArrayList<String>();
		
		List<String> locChoices = new ArrayList<>();
		List<Availability> locs = connector.getAvailabilities(fItem);
		
		locChoices.add("Zip");
		locChoices.add("Meal Time (Breakfast/Lunch/Dinner)");		
		locChoices.add("Start Date");
		locChoices.add("End Date");
		
		do {
			System.out.println("SELCET FIELD TO EDIT");
			for(int i=0;i<locChoices.size();i++) {
				System.out.println("(" + (i) + ")" +" "+ locChoices.get(i));
			}
			
			selectedChoice = matchInputWithChoice(scanner.nextLine(), locChoices);
			
		}while(selectedChoice == null);		
		
		switch(selectedChoice) {			
		
			case "Zip":
				valid = false;
				while(!valid) {
					try {
						System.out.print("Enter New Zip: ");
						locs.get(index).setZip(scanner.nextInt());
						scanner.nextLine();
						valid=true;
						break;													
					}catch(InputMismatchException e) {
						System.out.println("Please enter a number!");
						scanner.nextLine();
					}
				}	
				break;
				
			case "Meal Time (Breakfast/Lunch/Dinner)":
				
				validChoices.add("BREAKFAST");
				validChoices.add("LUNCH");
				validChoices.add("DINNER");
				valid=false;
				while(!valid) {
					System.out.println("Enter Meal Availability (Breakfast/Lunch/Dinner): ");
					userInput = scanner.nextLine();
					if(validChoices.contains(userInput.toUpperCase())) {
						locs.get(index).setMeal(userInput);	
						valid=true;
					}else {
						System.out.println("Invalid Input");
					}						
				}
				break;
				
			case "Start Date":
                            
                            valid =false;
                            while(!valid){
                                try{                                
                                    SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yy");
                                    System.out.println("Enter New Start Date: ");
                                    userInput = scanner.nextLine();
                                    format.parse(userInput);
                                    locs.get(index).setStart_date(userInput);
                                    valid=true;
                                }catch(Exception e){
                                    System.out.println("Invalid date format!");
                                }
                            }
                            break;
				
				
			case "End Date":
                            
                            valid =false;
                            while(!valid){
                                try{                                
                                    SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yy");
                                    System.out.println("Enter New End Date: ");
                                    userInput = scanner.nextLine();
                                    format.parse(userInput);
                                    locs.get(index).setEnd_date(userInput);
                                    valid=true;
                                }catch(Exception e){
                                    System.out.println("Invalid date format!");
                                }
                            }
                            break;
                            
		}
		
	}
	
	@Override
	public void process() {
		
		List<String> choices = new ArrayList<>();
		
		choices.add("Create New Food Item");
		choices.add("Update Existing Food Item");
		choices.add("Delete Food Item");		
		choices.add("Back");
		
		do {
			for(int i=0;i<choices.size();i++) {
				System.out.println("(" + (i) + ")" +" "+ choices.get(i));
			}
			
			selectedChoice = matchInputWithChoice(scanner.nextLine(), choices);
			
		}while(selectedChoice == null);		
			
		
		requestTransition = true;
		
		
	}
	
	
	
	
	

}

