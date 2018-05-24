import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class log {

    private ArrayList<Integer> userIDs = new ArrayList<Integer>();
    private ArrayList<String> userNames = new ArrayList<String>();

    public void log(String User_name, Integer User_id,String Title, String User_text, int kind) {

        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            String text = User_name + ": " + User_text + "\n";
            fw = new FileWriter(Title + ".txt", true);
            bw = new BufferedWriter(fw);
            bw.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }

    }
}