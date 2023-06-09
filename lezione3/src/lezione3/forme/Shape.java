package lezione3.forme;

public abstract class Shape implements IShape, IArea{

	private int x;
	private int y;
	
	private boolean isVisible = false;

	private ShapesType type = null;
	
	public Shape(int x, int y, ShapesType type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	
	protected void setType(ShapesType type) {
		this.type = type;
	}

	public ShapesType getType() {
		return type;
	}

	public abstract void show();

	public abstract void hide();
	
	public void move(int x, int y) {
		System.out.println(this);
		this.hide();
		setX(x);
		setY(y);
		this.show();
		System.out.println(this);
	}
	
	public abstract double area();
}
