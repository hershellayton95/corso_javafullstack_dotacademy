package lezione3.forme;

public class Rectangle extends Shape{

	private double base;
	private double height;

	public Rectangle(int x, int y, double base, double height) {
		super(x, y, ShapesType.RECTANGLE);
		this.base = base;
		this.height = height;
	}

	public double getBase() {
		return base;
	}

	public void setBase(double base) {
		this.base = base;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public void show() {
		if(!this.isVisible()) {
			this.setVisible(true);
			System.out.println("La figura "+ this.getClass()+" è ora visibile");
		}
	}

	public void hide() {
		if(this.isVisible()) {
			this.setVisible(false);
			System.out.println("La figura "+ this.getClass()+" è ora nascosta");
		}
	}

	@Override
	public String toString() {
		return "Rectangle [x=" + getX() + ", y=" + getY() + ", base=" + base + ", height=" + height + ", isVisible=" + isVisible()
				+ "]";
	}

	@Override
	public ShapesType getType() {
		return ShapesType.RECTANGLE;
	}
	
}
