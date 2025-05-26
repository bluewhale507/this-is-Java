package sec11_basicAPI.example.Class.reflection;

public class Car {
	private String model;
	private String owner;
	private String name;
	private String sex;
	
	public Car() {
		
	}
	
	public Car(String model) {
		this.model = model;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getOwner() {
		return owner;
	}

	private void setOwner(String owner, String name, String sex) {
		this.owner = owner;
		this.name = name;
		this.sex = sex;
	}
}
