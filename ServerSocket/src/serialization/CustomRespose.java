package serialization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import generics.Person;

public class CustomRespose implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String status = "KO";
	private List<Person> result = new ArrayList<Person>();
	private String error = "";

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Person> getResult() {
		return result;
	}

	public void setResult(List<Person> result) {
		this.result = result;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
