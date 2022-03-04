package ejb.learn.model;

public class Address {

	private Integer id;
	private Integer building;
	private Integer road;
	private Integer block;
	private City city = new City();			
	
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}
	
	public void setBuilding(Integer building) {
		this.building = building;
	}
	public Integer getBuilding() {
		return building;
	}
	
	public void setRoad(Integer road) {
		this.road = road;
	}
	public Integer getRoad() {
		return road;
	}
	
	public void setBlock(Integer block) {
		this.block = block;
	}
	public Integer getBlock() {
		return block;
	}
	
	public void setCity(City city) {
		this.city = city;
	}
	public City getCity() {
		return city;
	}
	
	@Override
	public String toString() {
		return "[id=" + id + ", building=" + building + ", road=" + road + ", block=" + block + ", city=" + city
				+ "]";
	}
}