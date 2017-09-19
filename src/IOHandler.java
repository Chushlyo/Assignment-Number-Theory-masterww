import java.io.IOException;
import java.util.ArrayList;

public class IOHandler {

    void getRadix() {
        int radix = 0;
        //path of the file is C:\\Users and the name of the file is text.txt
        String path = "C:/Users/Delyana/Desktop/example.txt";
        try {
            ReadFile read = new ReadFile(path);
            ArrayList<String> lines = read.Open();
            for (String string : lines) {
                //print the lines form the file
                System.out.println(string);
                if (string.equals("[add]")) {
                    System.out.println("ZZZZZZZZZZZZZZZ");
                }
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        IOHandler handler = new IOHandler();
        handler.getRadix();
    }
}
