package view;


import java.util.*;

import com.syntel.*;

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
				
				System.out.println("Enter Name: ");
				userInput = scanner.nextLine();
				fItem.setName(userInput);
				
				System.out.println("Enter Description: ");
				userInput = scanner.nextLine();
				fItem.setDescription(userInput);
				
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
				
				
				System.out.println("Enter Type: ");
				userInput = scanner.nextLine();
				fItem.setType(userInput);
				
				valid = false;
				while(!valid) {
					System.out.println("Vegetarian? (yes/no): ");
					userInput = scanner.nextLine();
					if(userInput.toUpperCase().equals("YES") || userInput.toUpperCase().equals("NO")) {
						fItem.setVeg(userInput);
						valid = true;
					}else {
						System.out.println("Invalid Input");
					}
					
				}
				
				System.out.println("Image URL: ");
				userInput = scanner.nextLine();
				fItem.setImage(userInput);
				
				createLocation();
				
				//Add location values
				
				
				controller.createNewItem(fItem);				
				
				System.out.println("Created: "+fItem);
				
				break;
					
			case "Update Existing Food Item":				
				System.out.print("Enter name of Food Item: ");
				name=scanner.nextLine();
				fItem.setName(name);
				controller.getFoodItem(fItem);	
				
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
					System.out.print("Enter New Name: ");
					fItem.setName(scanner.nextLine());
					break;
				
				case "Description":
					System.out.print("Enter New Description: ");
					fItem.setDescription(scanner.nextLine());
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
					System.out.print("Enter New Type: ");
					fItem.setType(scanner.nextLine());
					break;
					
				case "Veg (yes/no":
					while(!valid) {
						System.out.println("Vegetarian? (yes/no): ");
						userInput = scanner.nextLine();
						if(userInput.toUpperCase().equals("YES") || userInput.toUpperCase().equals("NO")) {
							fItem.setVeg(userInput);
							valid = true;
						}else {
							System.out.println("Invalid Input");
						}
						
					}
					break;
					
				case "Availability Info":
					
					ArrayList<Availability> locs = fItem.getAvailability();
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
			
			System.out.println("Start Date: ");
			userInput = scanner.nextLine();
			loc.setStart_date(userInput);
			
			System.out.println("End Date: ");
			userInput = scanner.nextLine();
			loc.setEnd_date(userInput);		
			
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
			
			fItem.addAvailability(loc);
		
		}
		
	}
	
	public void updateAvailability(int index) {
		validChoices = new ArrayList<String>();
		
		List<String> locChoices = new ArrayList<>();
		ArrayList<Availability> locs = fItem.getAvailability();
		
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
				System.out.print("Enter New Start Date: ");
				locs.get(index).setStart_date(scanner.nextLine());
				break;
				
			case "End Date":
				System.out.print("Enter New End Date: ");
				locs.get(index).setEnd_date(scanner.nextLine());
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
