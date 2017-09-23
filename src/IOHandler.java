import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class IOHandler {
    //path of the file is C:\\Users and the name of the file is text.txt
    String path = "C:/Users/Delyana/Desktop/example.txt";
    ReadFile read = new ReadFile(path);
    ArrayList<String> lines = new ArrayList<>();
    //String OutputPath="C:/Users/Delyana/Desktop/output.txt";

    public IOHandler() {
        try {
            this.lines = read.Open();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    int getRadix() {
        int radix = 0;
        for (String string : lines) {
            //print the lines form the file
            //System.out.println(string);
            if (string.contains("[radix]") && !string.contains("#")) {
                   if(string.substring(8,9).equals("1")) {
                       radix = Integer.parseInt(string.substring(8,10));
                   } else{
                       radix=Integer.parseInt(string.substring(8,9));
                   }

            }
        }
        System.out.println("[radix] " +radix);
        return radix;
    }

    String getOperation() {
        String operation = "";
        Iterator<String> iterator = lines.iterator();
        while (iterator.hasNext()) {
            //print the lines form the file
            String string = iterator.next();
            if (string.contains("[radix]") && !string.contains("#")) {
                string = iterator.next();
                operation = string;
                break;
            }
        }
        System.out.println("[operation]  " +operation);
        return operation;
    }

    String getFirstNumber() {
        String number = "";
        for (String string : lines) {
            //print the lines form the file
            if (string.contains("[x]")) {
                number = string.substring(4);
            }
        }
        System.out.println("[x] " +number);
        return number;
    }

    String getSecondNumber() {
        String number = "";
        for (String string : lines) {
            //print the lines form the file
            //System.out.println(string);
            if (string.contains("[y]")) {
                number = string.substring(4);
            }
        }
        System.out.println("[y] " +number);
        return number;
    }

    void output(String result) {
        FileWriter writer = null;
        BufferedWriter bufferwriter = null;
        try {
            writer = new FileWriter(path, true);
            bufferwriter = new BufferedWriter(writer);
            System.out.println("[result] " + result);
            bufferwriter.write("\n [result] " + result);

        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            try {
                if (bufferwriter != null) {
                    bufferwriter.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    void outputSteps(Integer steps) {
        FileWriter writer = null;
        BufferedWriter bufferwriter = null;
        try {

            writer = new FileWriter(path, true);
            bufferwriter = new BufferedWriter(writer);
            if (steps != 0) {
                System.out.println("[individual steps] " + steps);
                bufferwriter.write("\n [individual steps] " + steps);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            try {
                if (bufferwriter != null) {
                    bufferwriter.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }



    public static void main(String[] args) throws IOException {
        IOHandler handler = new IOHandler();

    }
}
