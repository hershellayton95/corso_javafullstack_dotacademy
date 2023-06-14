package entities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient String transientVar;

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

	public String getTransientVar() {
		return transientVar;
	}
	public void setTransientVar(String transientVar) {
		this.transientVar = transientVar;
	}

	@Override
	public String toString() {
		return "Person [transientVar=" + transientVar + ", firstname=" + firstname + ", lastname=" + lastname + ", age="
				+ age + ", job=" + job + ", id=" + id + "]";
	}
	public String toJson() {
		return "\"firstname\":\"" + firstname + "\",\"lastname:\":\"" + lastname + "\",\"age\":\"" + age + "\",\"job\":\"" + job + "\",\"id\":\"" + id + "\"";
	}

	private void writeObject(ObjectOutputStream oos) throws IOException, ClassNotFoundException {
		oos.defaultWriteObject();
		oos.writeObject(this.transientVar);
	}
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		ois.defaultReadObject();
		this.transientVar = (String) ois.readObject();
	}
}

