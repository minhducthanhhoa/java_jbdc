package util;

import java.util.Scanner;

public class InputUtil {
    private static final Scanner sc = new Scanner(System.in);

    public static String inputString(String msg) {
        System.out.print(msg);
        return sc.nextLine().trim();
    }

    public static int inputInt(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Sai định dạng! Vui lòng nhập số nguyên.");
            }
        }
    }

    public static double inputDouble(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Double.parseDouble(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Sai định dạng! Vui lòng nhập số thực.");
            }
        }
    }
}
