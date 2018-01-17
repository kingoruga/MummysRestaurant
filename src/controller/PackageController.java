package controller;


import model.Connector;
import model.Fooditem;

public class PackageController {
	
	
	private Connector conn = new Connector();
	
	
	public void createNewItem(Fooditem item) {
		
		conn.createFoodQuery(item);		
	}
	
	public void deleteFoodItem(String name) {
		
		conn.removeFoodQuery(name);
	}
	
	public void getFoodItem(Fooditem item) {
		
		conn.getFoodQuery(item);			
	}	


}
