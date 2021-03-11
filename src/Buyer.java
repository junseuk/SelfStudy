
import java.util.*;

public class Buyer {
	int money = 1000;
	int bonuspoint = 0;
	void Buysell(Product p) {
		money -= p.price;
		bonuspoint += p.bonuspoint;
		System.out.println("구입하셨습니다!");	
		item.add(p);
	}
	Vector item = new Vector();
	int sum = 0;
	void summary() {
		String itemList="";
		if (item.isEmpty()) {
			System.out.println("구입하신게 없어요");
			return;
		}
		for (int i=0;i<item.size();i++) {
			Product p = (Product) item.get(i);
			sum += p.price;
			itemList += (i==0) ? "" + p : "," + p;
		}
		System.out.println("총 돈쓴거 = " + sum);
		System.out.println("산것들 = " + itemList);
	}
}

class Product {
	Product(int Price) {
		this.price = Price;
		this.bonuspoint = (int) (0.1 * Price);
	}
	int price;
	int bonuspoint;
	
}

class Tv extends Product {
	Tv(){
		super(100);
	}
	public String toString() {
		return "tv";
	}
}

class Computer extends Product {
	Computer(){
		super(50);
	}
	public String toString() {
		return "Computer";
	}
}

class Buysell{
	public static void main(String args[]) {
		Buyer b = new Buyer();
		b.Buysell(new Tv());
		b.Buysell(new Computer());
		System.out.println("남은 잔돈과 보너스 포인트는 "+b.money+" "+b.bonuspoint);
		b.summary();
	}
}

