
public class Computation {
    private Integer radix;
    private String operation;
    private String xstring;
    private String ystring;

    private boolean isxneg = (false), isyneg = (false), isxmodlarger = (false);
    private IOHandler handler = new IOHandler();
    private boolean convert;

    private Computation() {
        this.convert = false;
        this.radix = handler.getRadix();
        System.out.println(this.radix);
        if (this.radix > 10) {
            convert = true;
        }
        try {
            if (radix > 16 || radix < 1) {
                throw new NumberException("");
            }
        } catch (NumberException exception) {
            System.out.println("Radix must be between 2 and 16.");
            System.exit(0);
        }
        this.operation = handler.getOperation();
        System.out.println(this.operation);
        try {
            if (!(operation.equals("[add]")) && !(operation.equals("[subtraction]")) &&
                    !(operation.equals("[multiplication]")) && !operation.equals("[karatsuba]") && !(operation.equals("test"))) {
                throw new NumberException("");
            }
        } catch (NumberException exception) {
            System.out.println("Operation must be [add],[subtraction],[multiplication] or [karatsuba].");
            System.exit(0);
        }
        this.xstring = handler.getFirstNumber();
        System.out.println("ss" + this.xstring);
        this.ystring = handler.getSecondNumber();
        System.out.println("ss" + this.ystring);
        if (xstring.contains("-")) isxneg = (true);
        if (ystring.contains("-")) isyneg = (true);

        if (!isxneg && !isyneg) {
            if (xstring.length() > ystring.length()) isxmodlarger = (true);
            if (xstring.length() == ystring.length() && xstring.charAt(0) > ystring.charAt(0)) isxmodlarger = (true);
        }
        if (!isxneg && isyneg) {
            if (xstring.length() > (ystring.length() - 1)) isxmodlarger = (true);
            if (xstring.length() == (ystring.length() - 1) && xstring.charAt(0) > ystring.charAt(1))
                isxmodlarger = (true);
        }
        if (isxneg && !isyneg) {
            if ((xstring.length() - 1) > ystring.length()) isxmodlarger = (true);
            if ((xstring.length() - 1) == ystring.length() && xstring.charAt(1) > ystring.charAt(0))
                isxmodlarger = (true);
        }
        if (isxneg && isyneg) {
            if (xstring.length() > ystring.length()) isxmodlarger = (true);
            if (xstring.length() == ystring.length() && xstring.charAt(1) > ystring.charAt(1)) isxmodlarger = (true);
        }


    }


    private void compute() {
        String result = "";
        switch (operation) {
            case "[add]":
                result = addition(xstring, ystring);
                System.out.println(result);
                handler.output(result);
                break;
            case "[subtraction]":
                result = subtraction(xstring, ystring);
                System.out.println(result);
                handler.output(result);
                break;
            //result = Integer.toString(Integer.parseInt(Integer.toString(f), 10), radix);
            case "[multiplication]":
                if (!isxneg && !isyneg) {
                    result = multiplication(xstring, ystring);
                    System.out.println(result);
                }
                if (!isxneg && isyneg) {
                    result = "-" + multiplication(xstring, ystring.substring(1, ystring.length()));
                    System.out.println(result);
                }
                if (isxneg && !isyneg) {
                    result = "-" + multiplication(xstring.substring(xstring.length()), ystring);
                    System.out.println(result);
                }
                if (isxneg && isyneg) {
                    System.out.println(multiplication(xstring.substring(1, xstring.length()), ystring.substring(1, ystring.length())));
                    result = multiplication(xstring.substring(1, xstring.length()), ystring.substring(1, ystring.length()));
                    System.out.println(result);
                }
                handler.output(result);
                break;
            case "[karatsuba]":
                result = "";
//                if (!isxneg && !isyneg) {
//                    result = karatsuba(xstring, ystring);
//                    System.out.println(result);
//                }
//                if (!isxneg && isyneg) {
//                    result = "-" + karatsuba(xstring, ystring.substring(1, ystring.length()))
//                    System.out.println(result);
//                }
//                if (isxneg && !isyneg) {
//                    result = "-" + karatsuba(xstring.substring(xstring.length()), ystring);
//                    System.out.println(result);
//                }
//                if (isxneg && isyneg) {
//                    result = karatsuba(xstring.substring(1, xstring.length()), ystring.substring(1, ystring.length()));
//                    System.out.println(result);
//                }

                handler.output(result);
                break;
            default:
                break;
        }
    }

    private String simpleAddition(String xstring, String ystring) {
        //a and b will be used as the digits of x and y
        //c is the carry
        //r is the result
        //f is the digit of the result
        //j is the length of either x or y depending on which is bigger;
        xstring = xstring.toUpperCase();
        ystring = ystring.toUpperCase();
        int a, b, f, c = 0, r = 0;
        String a1, b1;
        int j = Math.max(xstring.length(), ystring.length());
        String result = new String();
        for (double i = 0; i < j + 1; i++) {
            if ((xstring.length()) > 0) {
                a1 = (xstring.substring(xstring.length() - 1));
                a = Integer.valueOf(hexdecimal(a1));
                System.out.println("num" + a);
                xstring = xstring.substring(0, xstring.length() - 1);
            } else a = 0;
            if ((ystring.length()) > 0) {

                b1 = (ystring.substring(ystring.length() - 1));
                b = Integer.valueOf(hexdecimal(b1));
                System.out.println("num" + b);
                ystring = ystring.substring(0, ystring.length() - 1);
            } else b = 0;
            f = (a + b + c) % radix; //radix
            c = (a + b + c) / radix; //radix
            if (convert) {
                System.out.println("f is " + f);
                System.out.println(decimalhex(String.valueOf(f)));
                String num = (decimalhex(String.valueOf(f)));
                result = num + result;
            } else {
                r = (f);
                result = (Integer.toString(r) + result);
            }

            System.out.println(a + "  " + b + " res" + result + "r " + r);
        }
        return (result);
    }


    private String addition(String xstring, String ystring) {

        if (!isxneg && !isyneg) {
            return simpleAddition(xstring, ystring);
        }

        if (isxneg && !isyneg) {
            if (isxmodlarger) {
                return "-" + simplesubtraction(xstring.substring(1, xstring.length()), ystring);
            } else return simplesubtraction(ystring, xstring.substring(1, xstring.length()));
        }

        if (!isxneg && isyneg) {
            if (isxmodlarger) {
                return simplesubtraction(xstring, ystring.substring(1, ystring.length()));
            } else return "-" + simplesubtraction(ystring.substring(1, ystring.length()), xstring);
        }

        if (isxneg && isyneg) {
            return "-" + simpleAddition(xstring.substring(1, xstring.length()), ystring.substring(1, ystring.length()));
        }

        return "Something went wrong";
    }


    private String simplesubtraction(String xstring, String ystring) {
        //a and b will be used as the digits of x and y
        //c is the carry
        //r is the result
        //f is the digit of the result
        //j is the length of either x or y depending on which is bigger
        int a, b, f, c = 0;
        String r="",a1,b1;
        int j = Math.max(xstring.length(), ystring.length());
        xstring = xstring.toUpperCase();
        ystring = ystring.toUpperCase();

        for (double i = 0; i < j; i++) {
            if ((xstring.length()) > 0) {
                a1 = (xstring.substring(xstring.length() - 1));
                System.out.println("num aa1 " + a1);
                a = Integer.valueOf(hexdecimal(a1));
                System.out.println("numa " + a);
                xstring = xstring.substring(0, xstring.length() - 1);
            } else a = 0;
            if ((ystring.length()) > 0) {
                b1 = (ystring.substring(ystring.length() - 1));
                System.out.println("num aa1 " + b1);
                b = Integer.valueOf(hexdecimal(b1));
                System.out.println("numb " + b);
                ystring = ystring.substring(0, ystring.length() - 1);
            } else b = 0;
            if (a > b) {
                f = (a - b - c) % radix;
            } else {
                if (i < j - 1) {
                    f = (10 - (b - a - c)) % radix;
                    System.out.println("inside f " + f);
                } else f = (a - b - c) % radix;
            }
            if (convert) {
                System.out.println("f is " + f);
                System.out.println(decimalhex(String.valueOf(f)));
                String num = (decimalhex(String.valueOf(f)));
                r = num + r;
            } else {
                r = (Integer.toString(f) + r);
            }

            System.out.println("current f " +f + "  " + a + b);
            if (b > a) c = 1;
            else c = 0;

        }
        if (r.substring(0, 1).equals("0")) {
            return r.substring(1, r.length());
        }
        return r;
    }

    private String subtraction(String xstring, String ystring) {

        if (!isxneg && !isyneg) {
            if (isxmodlarger) {
                return simplesubtraction(xstring, ystring);
            } else return "-" + simplesubtraction(ystring, xstring);
        }

        if (isxneg && !isyneg) {
            return "-" + simpleAddition(xstring.substring(1, xstring.length()), ystring);
        }
        if (!isxneg && isyneg) {
            return simpleAddition(xstring, ystring.substring(1, ystring.length()));
        }
        if (isxneg && isyneg) {
            if (isxmodlarger) {
                return "-" + (simplesubtraction(xstring.substring(1, xstring.length()), ystring.substring(1, ystring.length())));
            } else
                return simplesubtraction(ystring.substring(1, ystring.length()), xstring.substring(1, xstring.length()));
        }
        return "Something went wrong";
    }

    /**
     * @param x
     * @param y
     * @return
     */
    private String multiplication(String x, String y) {
        int power, power2;
        String r, a, b, r2;
        r = new String();
        String ystring2 = ystring, xstring2 = xstring;
        xstring = xstring.toUpperCase();
        ystring = ystring.toUpperCase();

        int j = Math.max(xstring.length(), ystring.length());
        for (int i = 0; i < xstring.length(); i++) {
            power = i;
            a = xstring2.substring(xstring2.length() - 1);
            xstring2 = xstring2.substring(0, xstring2.length() - 1);
            for (int p = 0; p < ystring.length(); p++) {
                power2 = p;
                b = ystring2.substring(ystring2.length() - 1);
                for (int t = 0; t < power2; t++) {
                    b = b + "0";
                }
                r2 = new String();
                System.out.println("a:" + a + "b:" + b);
                ystring2 = ystring2.substring(0, ystring2.length() - 1);
                for (int i2 = 0; i2 < Integer.valueOf(a); i2++) {
                    r2 = simpleAddition(b, r2);
                }
                System.out.println("r2:" + r2);

                for (int t = 0; t < power; t++) {
                    r2 = r2 + "0";
                }

                r = simpleAddition(r, r2);
            }
            ystring2 = ystring;
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
    private String hexdecimal(String num) {
        switch (num) {
            case "A":
                num = "10";
                break;
            case "B":
                num = "11";
                break;
            case "C":
                num = "12";
                break;
            case "D":
                num = "13";
                break;
            case "E":
                num = "14";
                break;
            case "F":
                num = "15";
                break;
            default:
                break;
        }
        return num;
    }

    private String decimalhex(String num) {
        switch (num) {
            case "10":
                num = "a";
                break;
            case "11":
                num = "b";
                break;
            case "12":
                num = "c";
                break;
            case "13":
                num = "d";
                break;
            case "14":
                num = "e";
                break;
            case "15":
                num = "f";
                break;
            default:
                break;
        }

        return num;
    }


    public static void main(String[] args) {
        Computation program = new Computation();
        program.compute();
    }
}