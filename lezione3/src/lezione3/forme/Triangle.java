package lezione3.forme;

public class Triangle extends Shape{


	private double base;
	private double height;

	public Triangle(int x, int y, double base, double height) {
		super(x, y, ShapesType.TRIANGLE);
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
		return "Triangle [x=" + getX() + ", y=" + getY() + ", base=" + base + ", height=" + height + ", isVisible=" + isVisible()
				+ "]";
	}
	
	@Override
	public ShapesType getType() {
		return ShapesType.TRIANGLE;
	}


	@Override
	public double area() {
		
		return base*height/2;
	}
	
}
