package lezione3.forme;

public enum ShapesType {
	
	CIRCLE("circle"),RECTANGLE("rectangle"),SQUARE("square"),TRIANGLE("triangle");
	
	private String qualcosa = null;
	
	private ShapesType(String qualcosa) {
		this.qualcosa = qualcosa;
	}
	
	public String getQualcosa() {
		return qualcosa;
	}

	@Override
	public String toString() {
		return this.qualcosa;
	};
	
	public boolean equalsTo(String type) {
		return this.qualcosa.equals(type);
	}
}
