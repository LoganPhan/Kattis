package logan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SortOfSorting {
	  // Driver method
    public static void main(String args[]) throws NumberFormatException, IOException {
        BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
        boolean flag = true;
        while (flag) {
            int count = Integer.parseInt(inp.readLine());
            if (count != 0) {
                String[] arrayStr = new String[count];
                for (int i = 0; i < count; i++) {
                    String str = inp.readLine();
                    solved(arrayStr, str, i);
                }
                for (String string : arrayStr) {
                    System.out.println(string);
                }

            } else {
                flag = false;
                inp.close();
            }
        }
    }

    static void solved(String arr[], String insertValue, int index) {
        int first = insertValue.charAt(0);
        int second = insertValue.charAt(1);
        int count = 0;
        for (int i = index - 1; i >= 0; i--) {
            int firstSe = arr[i].charAt(0);
            int secondSe = arr[i].charAt(1);
            if (firstSe > first || (firstSe == first && secondSe > second)) {
                arr[i + 1] = arr[i];
                count++;
            }
            else if (i > 0 && arr[i - 1].charAt(0) != first) {
                break;
            }else {
                i--;
            }
        }
        arr[index - count] = insertValue;
        return;
    }

}
