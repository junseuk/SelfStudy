public class Q5_10 {
	public static void main(String args[]) {
		char[] abcCode = {'`','~','!','@','#','$','%','^','&','*','(',')','-','_','+','=','|','[',']','{', '}',';',':',',','.','/'};
		char[] numCode = {'q','w','e','r','t','y','u','i','o','p'};
		
		String src = "abc123";
		String result = "";
		
		for(int i=0; i < src.length();i++) {
			char ch = src.charAt(i);
			if (ch < 58) {
				int index = ch - '0';
				char changed = numCode[index];
				result += changed;
			}
			
			else {int index = ch - 97;

			char changed = abcCode[index];
			result += changed;
			}
		}
		System.out.println("src:"+src);
		System.out.println("result:"+result);
	}
}
