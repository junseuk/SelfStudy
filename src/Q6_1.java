
public class Q6_1 {
	public static void main(String args[]) {
		SutdaDeck deck = new SutdaDeck();
		for(int i=0; i<deck.cards.length;i++) {
			System.out.print(deck.cards[i]+",");
		}
		System.out.println();
		System.out.println(deck.pick());
		System.out.println(deck.pick(0));
		deck.shuffle();
		System.out.println(deck.pick(0));
	}
}

class SutdaCard {
	SutdaCard(){
		this(1, true);
	}
	SutdaCard(int num, boolean isKwang) {
		this.NUM = num;
		this.ISKWANG = isKwang;
	}
	final int NUM;
	final boolean ISKWANG;
	public String toString() {
		return NUM + (ISKWANG ? "K" : "");
	}
}

class SutdaDeck {
	final int CARD_NUM = 20;
	SutdaCard[] cards = new SutdaCard[CARD_NUM];
	SutdaDeck() {
		for(int i=0;i<CARD_NUM;i++) {
			int number = i+1;
			boolean isKwang = false;
			if (i+1 == 1|| i+1 ==3 || i+1 ==8) {
				isKwang = true;
			}
			if (number>10) number = number - 10;
			cards[i] = new SutdaCard(number, isKwang);
		}
	}
	void shuffle() {
		for(int i=0;i<CARD_NUM;i++) {
			SutdaCard tmp = new SutdaCard();
			int shuffleindex = (int) (Math.random() * CARD_NUM);
			tmp = cards[i];
			cards[i] = cards[shuffleindex];
			cards[shuffleindex] = tmp;
		}
	}
	SutdaCard pick(int index) {
		if (index < 0 || index >= CARD_NUM) return null;
		return cards[index];
	}
	SutdaCard pick() {
		int randomindex = (int) (Math.random() * CARD_NUM);
		return cards[randomindex];
	}
	
}
