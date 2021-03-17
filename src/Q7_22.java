public class Q7_22 {
	static double sumAreaa(myShape[] arr) {
		double sum = 0;
		for (int i=0;i<arr.length;i++) {
			sum += arr[i].calcArea();
		}
		return sum;
	}
	public static void main(String[] args) {
		myShape[] arr = {new myCircle(5.0), new myRectangle(3,4), new myCircle(1)};
		System.out.println("면적의 합:"+sumAreaa(arr));
	}
}

class myCircle extends myShape{
	double r;
	myCircle(double x) {
		this(new Createpoint(0,0), x);
	}
	myCircle(Createpoint p, double r) {
		super(p);
		this.r = r;
	}
	double calcArea() {
		return r * r * Math.PI;
	}
	
}

class myRectangle extends myShape{
	double width;
	double height;
	myRectangle(double x, double y) {
		this(new Createpoint(0,0), x, y);
	}
	myRectangle(Createpoint p, double x, double y) {
		super(p);
		this.width = x;
		this.height = y;
	}	
	boolean isSquare() {
		return width * height != 0 && width == height;
	}
	double calcArea() {
		return width * height;
	}
}

abstract class myShape {
	Createpoint p;
	
	myShape() {
		this(new Createpoint(0,0));
	}
	myShape(Createpoint p) {
		this.p = p;
	}
	
	abstract double calcArea();
	
	Createpoint getPosition() {
		return p;
	}
	void setPosition(Createpoint p) {
		this.p = p;
	}
}

class Createpoint {
	int x;
	int y;
	Createpoint() {
		this(0,0);
	}
	Createpoint(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}