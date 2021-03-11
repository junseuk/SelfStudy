public class Q6_6 {
	public static void main(String args[]) {
		mypoint p = new mypoint(1,1);
		System.out.println(p.getDistance(2,2));
	}
}
class mypoint {
	int x;
	int y;
	
	mypoint(int x, int y) {
		this.x = x;
		this.y = y;
	}
	double getDistance(int x2, int y2) {
		double distance = Math.sqrt((x - x2) * (x-x2) + (y-y2) * (y-y2));
		return distance;
	}
}