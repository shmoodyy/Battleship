import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] stringArray = scanner.nextLine().split(" ");
        int[] numberArray = new int[4];
        for (int i = 0; i < numberArray.length; i++) {
            numberArray[i] = Integer.parseInt(stringArray[i]);
            System.out.print(--numberArray[i] + " ");
        }
    }
}