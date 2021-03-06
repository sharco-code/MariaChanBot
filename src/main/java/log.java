import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class log {

    public void log(String User_name, Integer User_id,String Title, String User_text, int kind) {

        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            if (Title == null) {
                fw = new FileWriter(User_name + ".txt", true);
            } else {
                String Title_modified = Title.replace(" ", "-");
                fw = new FileWriter(Title_modified + ".txt", true);
            }
            String text = User_name + ": " + User_text + "\n";
            bw = new BufferedWriter(fw);
            bw.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            try {
                if (bw != null)
                    bw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
