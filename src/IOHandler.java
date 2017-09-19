import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class IOHandler {
    //path of the file is C:\\Users and the name of the file is text.txt
    String path = "C:/Users/Delyana/Desktop/example.txt";
    ReadFile read = new ReadFile(path);
    ArrayList<String> lines = new ArrayList<>();

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
            System.out.println(string);
            if (string.contains("[radix]")) {
                System.out.println(string.substring(8));
            }
        }

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

        return operation;
    }

    String getFirstNumber() {
        String number = "";
        for (String string : lines) {
            //print the lines form the file
            System.out.println(string);
            if (string.contains("[x]")) {
                number = string.substring(4);
            }
        }
        return number;
    }

    String getSecondNumber() {
        String number = "";
        for (String string : lines) {
            //print the lines form the file
            System.out.println(string);
            if (string.contains("[y]")) {
                number = string.substring(4);
            }
        }
        return number;
    }

    public static void main(String[] args) throws IOException {
        IOHandler handler = new IOHandler();
        handler.getRadix();
        System.out.println("operation is" + handler.getOperation());
        System.out.println("x is" + handler.getFirstNumber());
        System.out.println("y is" + handler.getSecondNumber());

    }
}
