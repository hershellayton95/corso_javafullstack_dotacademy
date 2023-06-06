package lezione3.forme;

public interface IShape {
	int getX();
	int getY();
	boolean isVisible();
	abstract void hide();
	abstract void show();
	void move(int i, int j);
	public ShapesType getType();
	
}