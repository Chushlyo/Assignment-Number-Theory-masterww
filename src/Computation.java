import java.util.Scanner;
import java.util.*;
import java.math.BigInteger;

public class Computation {
    private Integer radix;
    private String operation;
    private String xstring;
    private String ystring;

    private Computation() {
        Scanner scanner = new Scanner(System.in);
        this.radix = scanner.nextInt();
        try {
            if (radix > 16 || radix < 2) {
                throw new NumberException("");
            }
        } catch (NumberException exception) {
            System.out.println("Radix must be between 2 and 16.");
            System.exit(0);
        }

        this.operation = scanner.next();
        try {
            if (!(operation.equals("[add]")) && !(operation.equals("[subtraction]")) &&
                    !(operation.equals("[multiplication]")) && !(operation.equals("[karatsuba]"))) {
                throw new NumberException("");
            }
        } catch (NumberException exception) {
            System.out.println("Operation must be [add],[subtraction],[multiplication] or [karatsuba].");
            System.exit(0);
        }
        this.xstring = scanner.next();
        this.ystring = scanner.next();

    }

    private void compute() {
        Integer x = Integer.parseInt(xstring, radix);
        Integer y = Integer.parseInt(ystring, radix);
//    BigInteger x = new BigInteger(xstring,radix);
//    BigInteger y = new BigInteger(ystring,radix);
        //    int a, b, c = 0, r = 0, f;
        System.out.println("f: " + x);
        System.out.println("s: " + y);
        switch (operation) {
            case "[add]":
                System.out.println(addition(xstring,ystring));
                break;
            case "[subtraction]":
                System.out.println(subtraction(x, y));
                break;
            //result = Integer.toString(Integer.parseInt(Integer.toString(f), 10), radix);
            case "[multiplication]":
                System.out.println(multiplication(x, y));
                break;
            case "[karatsuba]":
                System.out.println(Integer.toString(karatsuba(x, y), radix));
                break;
            default:
                break;
        }
    }


//    private String addition(Integer x, Integer y) {
//        //a and b will be used as the digits of x and y
//        //c is the carry
//        //r is the result
//        //f is the digit of the result
//        //j is the length of either x or y depending on which is bigger
//        int a, b, f, c = 0, r = 0;
//        int j = String.valueOf(Math.max(x, y)).length();
//        for (double i = 0; i < j + 1; i++) {
//            a = x % 10;
//            b = y % 10;
//            f = (a + b + c) % 10; //radix
//            c = (a + b + c) / 10; //radix
//            r = r + (f) * ((int) Math.pow(10, i));
//            System.out.println(x + "  " + y);
//            x = x / 10;
//            y = y / 10;
//        }
//        return (Integer.toString(r, radix) + " " + r);
//    }

    private String addition(String xstring, String ystring) {
        //a and b will be used as the digits of x and y
        //c is the carry
        //r is the result
        //f is the digit of the result
        //j is the length of either x or y depending on which is bigger
        int a, b, f, c = 0, r = 0;
        int j = Math.max(xstring.length(), ystring.length());
        String result = new String();
        for (double i = 0; i < j + 1; i++) {
            if ((xstring.length()  ) > 0 ){
                a = Integer.valueOf(xstring.substring(xstring.length() - 1));
                xstring = xstring.substring(0,xstring.length()-1);
            }
            else a = 0;
            if ((ystring.length()  ) > 0 ){
                b = Integer.valueOf(ystring.substring(ystring.length() - 1 ));
                ystring = ystring.substring(0,ystring.length()-1);
            }
            else b = 0;
            f = (a + b + c) % radix; //radix
            c = (a + b + c) / radix; //radix
            r = (f) ;
            result = (Integer.toString(r)+result);
            System.out.println(a + "  " + b+ " res" + result + "r " + r);
        }
        return (result);
    }

    /**
     * @param x first number to subtract
     * @param y second number to subtract
     * @return
     */
    private int subtraction(int x, int y) {
        //a and b will be used as the digits of x and y
        //c is the carry
        //r is the result
        //f is the digit of the result
        //j is the length of either x or y depending on which is bigger
        int a, b, f, c = 0, r = 0;
        int j = String.valueOf(Math.max(x, y)).length();
        int x1;
        int y2;
        x1 = Math.max(x, y);
        y2 = Math.min(x, y);
        for (double i = 0; i < j + 1; i++) {

            a = x1 % 10;
            b = y2 % 10;
            f = (a - b - c) % 10;
            r = r + (10 - (f) * ((int) Math.pow(10, i)));
            System.out.println(x1 + "  " + y2);
            x1 = x1 / 10;
            y2 = y2 / 10;
            if (b > a) c = 1;
            else c = 0;

        }
        if (y > x) r = -r;
        r = x - y;
        return r;
    }

    /**
     * @param x
     * @param y
     * @return
     */
    private int multiplication(int x, int y) {

    //a and b will be used as the digits of x and y
    //c1 is the carry while multiplying
    //c is the carry of the final addition
    //r is the result
    //f is the digit of the result
    //j is the length of either x or y depending on which is bigger
    int a, b, f, c = 0, r = 0;
    int j = String.valueOf(Math.max(x, y)).length();
    int x1 = Math.max(x, y); //the biggest of the two numbers
    int y2 = Math.min(x, y); //the smallest of the two numebrs
    int index = 0;
    ArrayList multiplier = new ArrayList();
    ArrayList finalresult = new ArrayList();

    // split the smaller number into its digits
        while (y2 > 0) {
        multiplier.add(y2 % 10);
        y2 = y2 / 10;
    }
    int size = multiplier.size();
        for (int k = size; k > 0; k--) {
        a = (int) multiplier.get(index); //the number that we are gonna use to multiply
        r = 0;
        int max = x1;
        System.out.println(index + "index");
        System.out.println("a = " + a);
        for (double i = 0; i < j + 1; i++) {
            b = max % 10; //
            System.out.println("b = " + b);

            f = (a * b + c) % 10;
            c = (a * b + c) / 10;
            r = r + ((f) * ((int) Math.pow(10, i)));

            System.out.println(f + "f");
            System.out.println(c + "c");
            System.out.println(r + " the result");

            max = max / 10;
        }
        finalresult.add(r);
        index++;
    }

        return r;

}

    /**
     * @param x
     * @param y
     * @return
     */
    private int karatsuba(int x, int y) {
        //both numbers are only with one digit
        if (x < 10 && y < 10) {
            return x * y;
        }
        int length = String.valueOf(Math.max(x, y)).length();
        length = ((length + 1) / 2) * 2;
        //lower half of the first number
        int x2 = 0;
        for (double i = 0; i < length / 2; i++) {
            x2 = x2 + (x % 10) * (int) Math.pow(10, (i));
            if (x != 0) {
                x = x / 10;
            }
        }
        //higher half of the first number
        int x1 = 0;
        for (double i2 = 0; i2 < length; i2++) {
            x1 = x1 + (x % 10) * (int) Math.pow(10, (i2));
            if (x != 0) {
                x = x / 10;
            }
        }
        //lower part of the second number
        int y2 = 0;
        for (double i = 0; i < length / 2; i++) {
            y2 = y2 + (y % 10) * (int) Math.pow(10, (i));
            if (y != 0) {
                y = y / 10;
            }
        }
        //higher part of the second number
        int y1 = 0;
        for (double i2 = 0; i2 < length; i2++) {
            y1 = y1 + (y % 10) * (int) Math.pow(10, (i2));
            if (y != 0) {
                y = y / 10;
            }
        }
        System.out.println(x1 + "ss " + x2 + " ss " + y1 + y2);

        int p1 = karatsuba(x1, y1);
        int p2 = karatsuba(x2, y2);
        int p3 = karatsuba(x1 + x2, y1 + y2) - p1 - p2;
        return (p1 * (int) Math.pow(10, length) + p3 * (int) Math.pow(10, length / 2) + p2);
    }

    public static void main(String[] args) {
        Computation program = new Computation();
        program.compute();
    }
}