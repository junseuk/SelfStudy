import java.util.*;

public class Q8_8 {
	public static void main(String args[]) {
		int answer = (int) (Math.random() * 100) + 1;
		int input = 0;
		int count = 0;

		do {
			count++;
			System.out.print("input between 1 and 100");
			System.out.println();
			try {
				input = new Scanner(System.in).nextInt();
			} catch (Exception e) {
				System.out.println("input valid number plz");
				continue;
			}
			if (answer < input)
				System.out.println("It's bigger than the answer");
			else if (answer > input)
				System.out.println("It's smaller than the answer");
			else {
				System.out.println("got it right");
				System.out.println("trials: " + count);
				break;

			}
		} while (true);

	}
}
