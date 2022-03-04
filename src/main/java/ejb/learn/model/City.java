package ejb.learn.model;

public class City {

	private Integer id;
	private String name;
	
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "[id=" + id + ", name=" + name + "]";
	}
}