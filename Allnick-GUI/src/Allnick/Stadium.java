package Allnick;

public class Stadium {

	private String Name;
	private String City;
	private String State;
	private int Capacity;
	private String Surface;
	private int Year_Opened;
		
	public Stadium(String name, String city, String state, int capacity, String surface, int year_Opened) {
		super();
		this.Name = name;
		this.City = city;
		this.State = state;
		this.Capacity = capacity;
		this.Surface = surface;
		this.Year_Opened = year_Opened;
	}
	
	public String getName() {
		return Name;
	}
	
	public void setName(String name) {
		Name = name;
	}
	
	public String getCity() {
		return City;
	}
	
	public void setCity(String city) {
		City = city;
	}
	
	public String getState() {
		return State;
	}
	
	public void setState(String state) {
		State = state;
	}
	
	public int getCapacity() {
		return Capacity;
	}
	
	public void setCapacity(int capacity) {
		Capacity = capacity;
	}
	
	public String getSurface() {
		return Surface;
	}
	
	public void setSurface(String surface) {
		Surface = surface;
	}
	
	public int getYear_Opened() {
		return Year_Opened;
	}
	
	public void setYear_Opened(int year_Opened) {
		Year_Opened = year_Opened;
	}	
}
