package entities;

public class Employee extends Person{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String smartphoneKind;

	public Employee() {
		super.setJob("employee");
	}
	
	public String getSmartphoneKind() {
		return smartphoneKind;
	}

	public void setSmartphoneKind(String smartphoneKind) {
		this.smartphoneKind = smartphoneKind;
	}

	@Override
	public String toString() {
		return "Employee ["+ super.toString() + "smartphoneKind=" + smartphoneKind + "]";
	}
	
	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return super.toJson()+",\"smartphoneKind\":"+"\""+smartphoneKind+"\"";
	}
	
}
