import java.util.*;

public class Main {

    public static String getDayOfWeekName(int number) throws IllegalArgumentException {
        // write your code here
        String dayName;
        switch (number) {
            case 1: {
                dayName = "Mon";
                break;
            } case 2: {
                dayName = "Tue";
                break;
            } case 3: {
                dayName = "Wed";
                break;
            } case 4: {
                dayName = "Thu";
                break;
            } case 5: {
                dayName = "Fri";
                break;
            } case 6: {
                dayName = "Sat";
                break;
            } case 7: {
                dayName = "Sun";
                break;
            } default: {
                throw new IllegalArgumentException();
            }
        }
        return dayName;
    }

    /* Do not change code below */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int dayNumber = scanner.nextInt();
        try {
            System.out.println(getDayOfWeekName(dayNumber));
        } catch (Exception e) {
            System.out.println(e.getClass().getName());
        }
    }
}