package lezione3.forme;

public class Circle extends Shape implements IOval{
	private double radius;


	public Circle(int x, int y, double radius) {
		super(x, y, ShapesType.CIRCLE);
		this.radius = radius;
	}


	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
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
	
	public void ovalizza() {
		System.out.println("Ovalizzo "+ this.getClass()+" "+this);
	}

	@Override
	public String toString() {
		return "Circle [x=" + getX() + ", y=" + getY() + ", radius=" + radius + ", isVisible=" + isVisible() + "]";
	}


	@Override
	public ShapesType getType() {
		return ShapesType.CIRCLE;
	}


	@Override
	public double area() {
		return Math.PI*Math.pow(radius, 2);
	}
	
}
