
public class Computation {
    private boolean convert;
    private Integer radix;
    private String operation;
    private String xstring;
    private String ystring;
    private IOHandler handler = new IOHandler();
    private Integer singleSteps;

    private boolean isxneg = (false), isyneg = (false), isxmodlarger = (false);

    private Computation() {
        this.singleSteps = 0;
        this.convert = false;
        this.radix = handler.getRadix();
        if (this.radix > 10) convert = true;
        try {
            if (radix > 16 || radix < 2) {
                throw new NumberException("");
            }
        } catch (NumberException exception) {
            System.out.println("Radix must be between 2 and 16.");
            System.exit(0);
        }

        this.operation = handler.getOperation();
        try {
            if (!(operation.equals("[addition]")) && !(operation.equals("[subtraction]")) &&
                    !(operation.equals("[multiplication]")) && !operation.equals("[karatsuba]") && !(operation.equals("test"))) {
                throw new NumberException("");
            }
        } catch (NumberException exception) {
            System.out.println("Operation must be [add],[subtraction],[multiplication] or [karatsuba].");
            System.exit(0);
        }
        this.xstring = handler.getFirstNumber();
        this.ystring = handler.getSecondNumber();
        //change change so it works with big numbers

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
            case "[addition]":
                result = addition(xstring, ystring);

                handler.output(result);
                break;
            case "[subtraction]":
                result = subtraction(xstring, ystring);

                handler.output(result);
                break;
            case "[multiplication]":
                if (!isxneg && !isyneg) {
                    result = multiplication(xstring, ystring);

                }
                if (!isxneg && isyneg) {
                    result = "-" + multiplication(xstring, ystring.substring(1, ystring.length()));

                }
                if (isxneg && !isyneg) {
                    result = "-" + multiplication(xstring.substring(xstring.length()), ystring);

                }
                if (isxneg && isyneg) {
                    System.out.println(multiplication(xstring.substring(1, xstring.length()), ystring.substring(1, ystring.length())));
                    result = multiplication(xstring.substring(1, xstring.length()), ystring.substring(1, ystring.length()));

                }
                handler.outputSteps(singleSteps);
                handler.output(result);
                break;
            case "[karatsuba]":
                result = "";
                if (!isxneg && !isyneg) {
                    result = karatsuba(xstring, ystring);

                }
                if (!isxneg && isyneg) {
                    result = "-" + karatsuba(xstring, ystring.substring(1, ystring.length()));

                }
                if (isxneg && !isyneg) {
                    result = "-" + karatsuba(xstring.substring(xstring.length()), ystring);

                }
                if (isxneg && isyneg) {
                    result = karatsuba(xstring.substring(1, xstring.length()), ystring.substring(1, ystring.length()));

                }
                handler.outputSteps(singleSteps);
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
        //j is the length of either x or y depending on which is bigger
        singleSteps++;
        String a1, b1;
        int a, b, f, c = 0, r = 0;
        int j = Math.max(xstring.length(), ystring.length());
        String result = new String();
        for (double i = 0; i < j; i++) {
            if ((xstring.length()) > 0) {
                a1 = xstring.substring(xstring.length() - 1);
                a = Integer.valueOf(hexdecimal(a1));
                xstring = xstring.substring(0, xstring.length() - 1);
            } else a = 0;
            if ((ystring.length()) > 0) {
                b1 = ystring.substring(ystring.length() - 1);
                b = Integer.valueOf(hexdecimal(b1));
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
            //System.out.println(a + "  " + b + " res" + result + "r " + r);
        }
        //while (result.charAt(0)== 0) result= result.substring(1,result.length());
        if (c != 0) return c + result;
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
        singleSteps++;
        int a, b, f, c = 0;
        String r = new String(), a1, b1;
        int j = Math.max(xstring.length(), ystring.length());

        for (double i = 0; i < j; i++) {
            if ((xstring.length()) > 0) {
                a1 = (xstring.substring(xstring.length() - 1, xstring.length()));
                a = Integer.valueOf(hexdecimal(a1));
                System.out.println("a is" + a);
                xstring = xstring.substring(0, xstring.length() - 1);
            } else a = 0;
            if ((ystring.length()) > 0) {
                b1 = (ystring.substring(ystring.length() - 1, ystring.length()));
                b = Integer.valueOf(hexdecimal(b1));
                System.out.println("b is" + b);
                ystring = ystring.substring(0, ystring.length() - 1);
            } else b = 0;
            if (a > (b + c)) {
                f = (a - b - c) % radix;


            } else {
                if (i < j - 1) {
                    f = (radix - (b - a + c)) % radix;
                    //c=1;
                } else f = (a - b - c) % radix;
            }
            //f = (a - b - c) % 10;

            if (convert) {
                System.out.println("f is " + f + "a is" + a + " b is:" + b);
                System.out.println(decimalhex(String.valueOf(f)));
                String num = (decimalhex(String.valueOf(f)));
                r = num + r;
            } else {
                r = (Integer.toString(f) + r);
            }
            //System.out.println(f + "  " + a + b);
            if ((b + c) > a) c = 1;
            else c = 0;

        }
//        if (y > x) r = -r;
//        r = x - y;
        //if (c==1) return "-"+r;
        // System.out.println(r.charAt(0));
        if (r.substring(0, 1).equals("0")) {
            r = r.substring(1, r.length());
            //System.out.println(r.substring(2,r.length()));
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

    private String multiplication(String xstring, String ystring) {
        int power, power2;
        String r, result, a, b, r2, a1, b1;
        r = new String();
        String ystring2 = ystring, xstring2 = xstring;
        for (int i = 0; i < xstring.length(); i++) {
            power = i;
            a1 = xstring2.substring(xstring2.length() - 1, xstring2.length());
            a = hexdecimal(a1);
            xstring2 = xstring2.substring(0, xstring2.length() - 1);
            for (int p = 0; p < ystring.length(); p++) {
                power2 = p;
                b1 = ystring2.substring(ystring2.length() - 1, ystring2.length());
                b = hexdecimal(b1);

                for (int t = 0; t < power2; t++) {
                    b = b + "0";
                }
                r2 = new String();

                ystring2 = ystring2.substring(0, ystring2.length() - 1);
                for (int i2 = 0; i2 < Integer.valueOf(a); i2++) {

                    r2 = simpleAddition(b, r2);
                }


                for (int t = 0; t < power; t++) {
                    r2 = r2 + "0";
                }

                r = simpleAddition(r, r2);
            }
            ystring2 = ystring;
        }
        return r;
    }


    private String karatsuba(String xstring, String ystring) {
        //both numbers are only with one digit
        if (xstring.length() == 1 && ystring.length() == 1) {
            return multiplication(xstring, ystring);
        }
        int length = Math.max(xstring.length(), ystring.length());
        length = ((length + 1) / 2) * 2;
        //lower half of the first number
        while (xstring.length() < length) {
            xstring = "0" + xstring;
        }
        while (ystring.length() < length) {
            ystring = "0" + ystring;
        }
        String x1 = xstring.substring(0, length / 2);
        String x2 = xstring.substring(length / 2, length);
        String y1 = ystring.substring(0, length / 2);
        String y2 = ystring.substring(length / 2, length);
        String p1 = karatsuba(x1, y1);
        String p2 = karatsuba(x2, y2);
        String p3 = simplesubtraction(karatsuba(simpleAddition(x1, x2), simpleAddition(y1, y2)), simpleAddition(p1, p2));
        System.out.println("xstring:" + xstring + "ystring:" + ystring + " p3:" + p3 + "karatsuba ofsmt" + (karatsuba(simpleAddition(x1, x2), simpleAddition(y1, y2))));
        for (int i = 0; i < length; i++) {
            p1 = p1 + "0";
        }

        for (int i = 0; i < ((length / 2)); i++) {
            p3 = p3 + "0";
        }

        return simpleAddition(simpleAddition(p1, p3), p2);

    }

    private String hexdecimal(String num) {
        switch (num) {
            case "a":
                num = "10";
                break;
            case "b":
                num = "11";
                break;
            case "c":
                num = "12";
                break;
            case "d":
                num = "13";
                break;
            case "e":
                num = "14";
                break;
            case "f":
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