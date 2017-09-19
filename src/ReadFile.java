import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.LineNumberReader;
import java.util.ArrayList;

public class ReadFile {
    private String location_file;

    public ReadFile(String location) {
        location_file = location;
    }

    public ArrayList<String> Open() throws IOException {
        //file reader contains the name of the file to be opened
        FileReader file = new FileReader(location_file);
        //Buffer reader stores all characters from the file
        BufferedReader reader = new BufferedReader(file);
        ArrayList<String> data = new ArrayList<>();
        String current = "";
        // while loop append each line of text to the arraylist
        while ((current = reader.readLine()) != null) {
            data.add(current);
        }
        reader.close();
        return data;
    }

    int getRadix() throws IOException {
        int radix = 0;
        //file reader contains the name of the file to be opened
        FileReader file = new FileReader(location_file);
        //Buffer reader stores all characters from the file
        BufferedReader reader = new BufferedReader(file);
        ArrayList<String> data = new ArrayList<>();
        String current = "";
        // while loop append each line of text to the arraylist
        while ((current = reader.readLine()) != null) {
            data.add(current);
        }

        reader.close();
        return radix;
    }

}
