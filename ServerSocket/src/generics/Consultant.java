package generics;

public class Consultant extends Person{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String computerKind;
	
	public Consultant() {
		super.setJob("counsultant");
	}

	public String getComputerKind() {
		return computerKind;
	}

	public void setComputerKind(String computerKind) {
		this.computerKind = computerKind;
	}

	@Override
	public String toString() {
		return "Consultant ["+ super.toString() + "computerKind=" + computerKind + "]";
	}

	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return super.toJson()+",\"computerKind\":"+"\""+computerKind+"\"";
	}
	
}
