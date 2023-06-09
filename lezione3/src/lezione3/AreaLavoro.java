package lezione3;

import lezione3.forme.Circle;
import lezione3.forme.IArea;
import lezione3.forme.IOval;
import lezione3.forme.IShape;
import lezione3.forme.Rectangle;
import lezione3.forme.Shape;
import lezione3.forme.ShapesType;
import lezione3.forme.Square;
import lezione3.forme.Triangle;

public class AreaLavoro {

	public static void main(String[] args) {

		Rectangle rectangle1 = new Rectangle(0, 0, 2.0, 1.0);
		Square square1 = new Square(0, 0, 1.0);
		Triangle triangle1 = new Triangle(0, 0, 2.0, 1.0);
		Circle circle1 = new Circle(0, 0, 1.0);

		rectangle1.show();
		rectangle1.move(1, 1);
		rectangle1.hide();
		System.out.println("------------");
		square1.show();
		square1.move(1, 1);
		rectangle1.hide();
		System.out.println("------------");
		triangle1.show();
		triangle1.move(1, 1);
		rectangle1.hide();
		System.out.println("------------");
		circle1.show();
		circle1.move(1, 1);
		rectangle1.hide();
		System.out.println("------------");
		System.out.println("------------");

		Shape shapes[] = new Shape[4];

		// uppercasting tipo un boxing da una forma passo a shape
		shapes[0] = rectangle1;
		shapes[1] = square1;
		shapes[2] = triangle1;
		shapes[3] = circle1;

		for (int i = 0; i < shapes.length; i++) {
			System.out.println("shape[" + i + "]=" + shapes[i]);
		}
		System.out.println("------------");
		System.out.println("------------");
		for (int i = 0; i < shapes.length; i++) {
			System.out.println("shape[" + i + "]=" + shapes[i]);
			// shapes[i].move() esiste nei figli di shape e quindi non posso accedere

			if (shapes[i] instanceof Circle) {
				System.out.println("Circle");
				Circle instance = (Circle) shapes[i]; // downcasting
				instance.show();
				instance.move(0, 0);
				instance.ovalizza();
			} else if (shapes[i] instanceof Rectangle) {
				Rectangle instance = (Rectangle) shapes[i]; // downcasting
				if (shapes[i] instanceof Square) {
					System.out.println("Square");
					Square square = (Square) shapes[i]; // downcasting
					square.show();
					square.move(0, 0);
				} else {
					System.out.println("Rectangle");
					instance.show();
					instance.move(0, 0);
				}
			} else if (shapes[i] instanceof Triangle) {
				System.out.println("Triangle");
				Triangle instance = (Triangle) shapes[i]; // downcasting
				instance.show();
				instance.move(0, 0);
			} else {
				System.out.println("Forma non supportata");
			}
			System.out.println("------------");

		}
		System.out.println("------------");
		System.out.println("------------");

		// astraiamo shape
		//		Shape shapes2[] = new Shape[5];
		Shape shapes2[] = new Shape[4];
		shapes2[0] = rectangle1;
		shapes2[1] = square1;
		shapes2[2] = triangle1;
		shapes2[3] = circle1;
		//		shapes2[4] = new Shape(0,0);
		for (int i = 0; i < shapes2.length; i++) {

			shapes2[i].hide();
			shapes2[i].show();
			shapes2[i].move(22, 22);
			System.out.println("------------");
		}
		System.out.println("------------");
		System.out.println("------------");

		IShape shapes3[] = new IShape[4];
		shapes3[0] = (IShape) rectangle1;
		shapes3[1] = (IShape) square1;
		shapes3[2] = (IShape) triangle1;
		shapes3[3] = (IShape) circle1;
		for (int i = 0; i < shapes3.length; i++) {

			shapes3[i].hide();
			shapes3[i].show();
			shapes3[i].move(22, 22);

			if (shapes3[i] instanceof IOval) {
				IOval oval = (IOval) shapes3[i];
				oval.ovalizza();
			}

			System.out.println("------------");
		}
		System.out.println("------------");
		System.out.println("------------");

		Shape shapes4[] = new Shape[4];

		// uppercasting tipo un boxing da una forma passo a shape
		shapes4[0] = rectangle1;
		shapes4[1] = square1;
		shapes4[2] = triangle1;
		shapes4[3] = circle1;

		System.out.println("------------");
		System.out.println("------------");
		for (int i = 0; i < shapes4.length; i++) {
			System.out.println("shape[" + i + "]=" + shapes[i]);
			// shapes[i].move() esiste nei figli di shape e quindi non posso accedere

			if (shapes4[i].getType() == ShapesType.CIRCLE) {
				System.out.println("Circle");
				Circle instance = (Circle) shapes4[i]; // downcasting
				instance.show();
				instance.move(0, 0);
				instance.ovalizza();


				System.out.println("mio qualcosa = " + instance.getType().getQualcosa());

			} else if (shapes4[i].getType() == ShapesType.RECTANGLE) {
				Rectangle instance = (Rectangle) shapes4[i]; // downcasting
				System.out.println("Rectangle");
				instance.show();
				instance.move(0, 0);

				System.out.println("mio qualcosa = " + instance.getType().getQualcosa());
			} else if (shapes[i].getType() == ShapesType.SQUARE) {
				System.out.println("Square");
				Square instance = (Square) shapes[i]; // downcasting
				instance.show();
				instance.move(0, 0);

				System.out.println("mio qualcosa = " + instance.getType().getQualcosa());
			} else if (shapes4[i].getType() == ShapesType.TRIANGLE) {
				System.out.println("Triangle");
				Triangle instance = (Triangle) shapes4[i]; // downcasting
				instance.show();
				instance.move(0, 0);

				System.out.println("mio qualcosa = " + instance.getType().getQualcosa());
			} else {
				System.out.println("Forma non supportata");
			}
			System.out.println("------------");

		}
		System.out.println("------------");
		System.out.println("------------");


		Shape shapes5[] = new Shape[4];

		// uppercasting tipo un boxing da una forma passo a shape
		shapes5[0] = rectangle1;
		shapes5[1] = square1;
		shapes5[2] = triangle1;
		shapes5[3] = circle1;

		System.out.println("------------");
		System.out.println("------------");

		for (int i = 0; i < shapes5.length; i++) {
			System.out.println("shape[" + i + "]=" + shapes5[i]);
			// shapes[i].move() esiste nei figli di shape e quindi non posso accedere

			if (shapes5[i].getType().equalsTo("circle")) {
				System.out.println("Circle");
				Circle instance = (Circle) shapes5[i]; // downcasting
				instance.show();
				instance.move(0, 0);
				instance.ovalizza();

				System.out.println("mio qualcosa = " + instance.getType().getQualcosa());

			} else if (shapes5[i].getType().equalsTo("rectangle")) {
				Rectangle instance = (Rectangle) shapes5[i]; // downcasting
				System.out.println("Rectangle");
				instance.show();
				instance.move(0, 0);

				System.out.println("mio qualcosa = " + instance.getType().getQualcosa());
			} else if (shapes5[i].getType().equalsTo("square")) {
				System.out.println("Square");
				Square instance = (Square) shapes5[i]; // downcasting
				instance.show();
				instance.move(0, 0);

				System.out.println("mio qualcosa = " + instance.getType().getQualcosa());
			} else if (shapes5[i].getType().equalsTo("triangle")) {
				System.out.println("Triangle");
				Triangle instance = (Triangle) shapes5[i]; // downcasting
				instance.show();
				instance.move(0, 0);

				System.out.println("mio qualcosa = " + instance.getType().getQualcosa());
			} else {
				System.out.println("Forma non supportata");
			}
			System.out.println("------------");

		}
		System.out.println("------------");
		System.out.println("------------");

		for(int i=0; i<shapes5.length; i++) {
			try {
				doOval(shapes5[i]);

			}catch(Exception e) {
				e.printStackTrace();
			}
		}

		for(int i=0; i<shapes5.length; i++) {
			double area =  ((IArea)shapes5[i]).area();
			System.out.println(area);
		}
	}

	public static void doOval(Object oval) throws Exception {
		if(oval instanceof IOval) {
			((IOval) oval).ovalizza();
		} else {
			throw new Exception("Non IOval");
		}
	}
}
