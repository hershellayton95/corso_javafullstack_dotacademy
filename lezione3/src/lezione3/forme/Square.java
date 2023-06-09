package lezione3.forme;

public class Square extends Rectangle{


	public Square(int x, int y, double lenght) {
		super(x, y, lenght, lenght);
		super.setType(ShapesType.SQUARE);
	}

	@Override
	public void setBase(double base) {
		// TODO Auto-generated method stub
		super.setBase(base);
		super.setHeight(base);
	}

	@Override
	public void setHeight(double height) {
		// TODO Auto-generated method stub
		super.setHeight(height);
		super.setBase(height);
	}

	@Override
	public String toString() {
		return "Square [x=" + getX() + ", y=" + getY() + ", lenght=" + getBase() + ", isVisible=" + isVisible()
				+ "]";
	}
	
	@Override
	public ShapesType getType() {
		return ShapesType.SQUARE;
	}
	
}
