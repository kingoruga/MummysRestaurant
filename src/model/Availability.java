package model;

public class Availability {
	
	private int zip;
	private String meal;
	private String start_date;
	private String end_date;
	
	
	public int getZip() {
		return zip;
	}
	public void setZip(int zip) {
		this.zip = zip;
	}
	public String getMeal() {
		return meal;
	}
	public void setMeal(String meal) {
		this.meal = meal;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	@Override
	public String toString() {
		return "Availability [zip=" + zip + ", meal=" + meal + ", start_date=" + start_date + ", end_date=" + end_date
				+ "]";
	}
	
	
	

}
