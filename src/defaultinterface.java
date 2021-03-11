public class defaultinterface {
	public static void main(String args[]) {
		Child c = new Child();
		c.method1();
		c.method2();
		Interface1.staticmethod();
		Interface2.staticmethod();
	}

}

interface Interface1 {
	default void method1() {
		System.out.println("method1() in Interface1");
	}
	default void method2() {
		System.out.println("method2() in Interface1");
	}
	static void staticmethod() {
		System.out.println("staticmethod() in Interface1");
	}
}

interface Interface2 {
	default void method1() {
		System.out.println("method1() in Interface2");
	}
	static void staticmethod() {
		System.out.println("staicmethod() in Interface2");
	}
}

class Parent {
	public void method2() {
		System.out.println("method2() in Parent");
	}
}

class Child extends Parent implements Interface1, Interface2 {
	public void method1() {
		System.out.println("method1() in Child");
	}
}