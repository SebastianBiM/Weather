package City;


public class City {
	
	private String id;
	private String name;
	private String country;
	
	
	public City(String id, String name, String country) {
		super();
		this.id = id;
		this.name = name;
		this.country = country;
	}
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getCountry() {
		return country;
	}
	
}
