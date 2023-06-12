package generics;

public class Person {

	private String firstname;
	private String lastname;
	private int age;
	private String job;
	private String id;
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Person [firstname=" + firstname + ", lastname=" + lastname + ", age=" + age + ", job=" + job + ", id="
				+ id + "]";
	}
	
	public String toJson() {
		return "\"firstname\":\"" + firstname + "\",\"lastname:\":\"" + lastname + "\",\"age\":\"" + age + "\",\"job\":\"" + job + "\",\"id\":\"" + id + "\"";
	}
	
}

