package logan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EncodedMessage {

	public static void main(String[] args) throws NumberFormatException, IOException {

		BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
		int count = Integer.parseInt(inp.readLine());
		for (int i = 0; i < count; i++) {
			decodeMessage(inp.readLine());
			System.out.println();
		}
	}

	static void decodeMessage(String str) {
		char[] ch = str.toCharArray();
		int chLenght = str.length();
		int length = (int) Math.sqrt(chLenght);
		StringBuilder builder = new StringBuilder();
		for (int i = 1; i <= length; i++) {
			int count = length - i;
			while (count < chLenght) {
				builder.append(ch[count]);
				count += length;
			}
		}
		System.out.println(builder.toString());
	}
}
