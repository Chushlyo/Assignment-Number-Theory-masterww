import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class IOHandler {

    String path;
    ReadFile read;
    ArrayList<String> lines = new ArrayList<>();

    public IOHandler() {
        try {
            //path of the file is C:\\Users\\"name of user"\\Desktop and the name of the file is example.txt
            this.path = "C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\example.txt";
        } catch (SecurityException exception) {
            System.out.println(" There is a problem with accessing the user name of the computer.");
            System.exit(0);
        }
        this.read = new ReadFile(path);
        try {
            //Open the file
            this.lines = read.Open();
            while (lines.get(0).contains("#")) lines.remove(0);
        } catch (IOException exception) {
            //catch exception of something is wrong with opening the file
            System.out.println(exception.getMessage());
        }
    }

    int getRadix() {
        int radix = 0;
        for (String string : lines) {
            //loop through all lines of the file
            if (string.contains("[radix]") && !string.contains("#")) {
                // if there is [radix] word then the following number is the radix
                if (string.substring(8, 9).equals("1")) {
                    //if the radix contains two digits
                    radix = Integer.parseInt(string.substring(8, 10));
                } else {
                    //if the radix contains one digit
                    radix = Integer.parseInt(string.substring(8, 9));
                }
                break;

            }
        }
        if(radix!=0) System.out.println("[radix]  " + radix);
        return radix;
    }

    String getOperation() {
        String operation = "";
        Iterator<String> iterator = lines.iterator();
        while (iterator.hasNext()) {
            //print the lines form the file
            String string = iterator.next();
            if (string.contains("[radix]") && !string.contains("#")) {
                //whenever the line with the radix is reached, go to the next one of the operation
                string = iterator.next();
                operation = string;
                break;
            }
        }
        System.out.println("[operation]  " + operation);
        return operation;
    }

    String getFirstNumber() {
        String number = "";
        for (String string : lines) {
            //loop through all lines of the file
            if (string.contains("[x]") && !string.contains("#")) {
                //the first number is after [x] word
                number = string.substring(4);
                //System.out.println("xis " + number);
                break;
            }
        }
        System.out.println("[x] " + number);
        return number;
    }

    String getSecondNumber() {
        String number = "";
        for (String string : lines) {
            //loop through all lines of the file
            if (string.contains("[y]") && !string.contains("#")) {
                //the second number is after [y] word
                number = string.substring(4);
                //System.out.println("yis " + number);
                break;
            }
        }
        System.out.println("[y] " + number);
        return number;
    }

    public boolean isLinesEmpty() {
        Iterator<String> iterator = lines.iterator();
        if (iterator.hasNext()) {
            if (!(iterator.next().contains("[result]"))) {
                return true;
            }
        }

        return false;
    }

    public void trimmer() {
        lines.remove(0);
        Iterator<String> iterator = lines.iterator();
        while (iterator.hasNext()) {
            String string = iterator.next();
            iterator.remove();
            if (string.equals("")) {
                break;
            }

        }
    }

    void output(String result) {
        //filewriter to open the file for writing
        FileWriter writer = null;
        BufferedWriter bufferwriter = null;
        try {
            writer = new FileWriter(path, true);
            bufferwriter = new BufferedWriter(writer);
            System.out.println(result);
            //write the result from computation into new line
            bufferwriter.write("\n " + result);

        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            try {
                //close the buffers
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
            //filewriter to open the file for writing
            writer = new FileWriter(path, true);
            bufferwriter = new BufferedWriter(writer);
            if (steps != 0) {
                //write the result from computing the steps into new line
                System.out.println("[individual steps] " + steps);
                bufferwriter.write("\n [individual steps] " + steps);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            try {
                //close the buffers
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
//       handler.trimmer();
//        for(String string:handler.lines){
//            System.out.println(string);
//        }


    }
}
