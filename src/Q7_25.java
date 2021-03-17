class Outer {
	int value = 10;
	class Inner {
		int value = 20;
		void method1() {
			int value = 30;			
			
			System.out.println(value);
			System.out.println(this.value);
			System.out.println(Outer.this.value);
			
		}
	}
	static class staticInner {
		int siv = 40;
	}
}
public class Q7_25 {
	public static void main(String args[]) {
		Outer.staticInner staticinner = new Outer.staticInner();
		System.out.println(staticinner.siv);
		Outer outer = new Outer();
		Outer.Inner inner = outer.new Inner();
		inner.method1();
	}
}
