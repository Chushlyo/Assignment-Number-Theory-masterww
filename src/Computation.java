import java.util.Scanner;
import java.util.*;
import java.math.BigInteger;

public class Computation {
    private Integer radix;
    private String operation;
    private String xstring;
    private String ystring;
    private ArrayList xs = new ArrayList();
    private ArrayList ys = new ArrayList();
    private String change;

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
                    !(operation.equals("[multiplication]")) && !operation.equals("[karatsuba]") && !(operation.equals("test"))) {
                throw new NumberException("");
            }
        } catch (NumberException exception) {
            System.out.println("Operation must be [add],[subtraction],[multiplication] or [karatsuba].");
            System.exit(0);
        }
        this.xstring = scanner.next();
        this.ystring = scanner.next();
        if ((Integer.valueOf(ystring))> (Integer.valueOf(xstring))){
            change = xstring;
            xstring = ystring;
            ystring = change;
        }


    }

    private void compute() {
        // Integer x;
        // int y;
        Integer x = Integer.parseInt(xstring, radix);
        Integer y = Integer.parseInt(ystring, radix);
//    BigInteger x = new BigInteger(xstring,radix);
//    BigInteger y = new BigInteger(ystring,radix);
        //    int a, b, c = 0, r = 0, f;
        //System.out.println("f: " + x);
        //System.out.println("s: " + y);
        switch (operation) {
            case "[add]":
                System.out.println(addition(xstring, ystring));
                break;
            case "[subtraction]":
                System.out.println(subtraction(xstring, ystring));
                break;
            //result = Integer.toString(Integer.parseInt(Integer.toString(f), 10), radix);
            case "[multiplication]":
                System.out.println(multiplication(x, y));
                break;
            case "[karatsuba]":
                System.out.println(Integer.toString(karatsuba(x, y), radix));
                break;
            case "test":
                System.out.println(xs.get(0));
                break;
            default:
                break;
        }
    }


    private String addition(String xstring, String ystring) {
        if (ystring.contains("-") && !xstring.contains("-")) {
            return subtraction(xstring, ystring.substring(1,ystring.length()));


        }
        else if (ystring.contains("-") && xstring.contains("-")){
            xstring = xstring.substring(1,xstring.length());
            ystring = ystring.substring(1,ystring.length());
            int a, b, f, c = 0, r = 0;
            int j = Math.max(xstring.length(), ystring.length());
            String result = new String();
            for (double i = 0; i < j + 1; i++) {
                if ((xstring.length()) > 0) {
                    a = Integer.valueOf(xstring.substring(xstring.length() - 1));
                    xstring = xstring.substring(0, xstring.length() - 1);
                } else a = 0;
                if ((ystring.length()) > 0) {
                    b = Integer.valueOf(ystring.substring(ystring.length() - 1));
                    ystring = ystring.substring(0, ystring.length() - 1);
                } else b = 0;
                f = (a + b + c) % radix; //radix
                c = (a + b + c) / radix; //radix
                r = (f);
                result = (Integer.toString(r) + result);
                System.out.println(a + "  " + b + " res" + result + "r " + r);
            }
            return ("-" + result);

        }
        {
            //a and b will be used as the digits of x and y
            //c is the carry
            //r is the result
            //f is the digit of the result
            //j is the length of either x or y depending on which is bigger
            int a, b, f, c = 0, r = 0;
            int j = Math.max(xstring.length(), ystring.length());
            String result = new String();
            for (double i = 0; i < j + 1; i++) {
                if ((xstring.length()) > 0) {
                    a = Integer.valueOf(xstring.substring(xstring.length() - 1));
                    xstring = xstring.substring(0, xstring.length() - 1);
                } else a = 0;
                if ((ystring.length()) > 0) {
                    b = Integer.valueOf(ystring.substring(ystring.length() - 1));
                    ystring = ystring.substring(0, ystring.length() - 1);
                } else b = 0;
                f = (a + b + c) % radix; //radix
                c = (a + b + c) / radix; //radix
                r = (f);
                result = (Integer.toString(r) + result);
                System.out.println(a + "  " + b + " res" + result + "r " + r);
            }
            return (result);

        }
    }

    private String subtraction(String xstring, String ystring) {
        if (ystring.contains("-") && !xstring.contains("-")) {
            return addition(xstring, ystring.substring(1,ystring.length()));

        }
        else if (ystring.contains("-") && xstring.contains("-")) {
            return subtraction(ystring.substring(1,ystring.length()),xstring.substring(1,xstring.length()));

        }
        //a and b will be used as the digits of x and y
        //c is the carry
        //r is the result
        //f is the digit of the result
        //j is the length of either x or y depending on which is bigger
        int a, b, f, c = 0;
        String r = new String();
        int j = Math.max(xstring.length(),ystring.length());

        for (double i = 0; i < j + 1; i++) {
            if ((xstring.length()  ) > 0 ){
                a = Integer.valueOf(xstring.substring(0,1));
                xstring = xstring.substring(1,xstring.length());
            }
            else a = 0;
            if ((ystring.length()  ) > 0 ){
                b = Integer.valueOf(ystring.substring(0,1));
                ystring = ystring.substring(1,ystring.length());
            }
            else b = 0;
            f = (a - b - c) % 10;
            if(f<0) f = 10-f;
            r = (r + Integer.toString(f));
            System.out.println(f + "  " + a + b);
            if (b > a) c = 1;
            else c = 0;

        }
//        if (y > x) r = -r;
//        r = x - y;
        return r;
    }

    /**
     * @param x
     * @param y
     * @return
     */
    private int multiplication(int x, int y) {
        return 1;
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