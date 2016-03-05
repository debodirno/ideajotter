package xenophobe.applications.com.ideasjotter;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Debodirno on 05-Mar-16.
 */
public class NoteManager {
    private Context context;
    private String path = Environment.getExternalStorageDirectory().toString() + "/Notes/";
    public NoteManager(Context _context){
        this.context = _context;
    }
    public NoteManager(){
    }

    public void CreateNewDirectory(){
        File mydir = new File(this.path);
        if(!mydir.exists())
            mydir.mkdirs();
        else
            Log.d("error", "directory already exists");
    }

    public void saveNote(String sFileName, String sBody){
        try
        {
            File root = new File(this.path);
            if (!root.exists()) {
                root.mkdirs();
            }
            File file = new File(root, sFileName);
            FileWriter writer = new FileWriter(file);
            writer.append(sBody);
            writer.flush();
            writer.close();
            Toast.makeText(this.context, "Saved", Toast.LENGTH_SHORT).show();
        }
        catch(IOException e)
        {
            e.printStackTrace();

        }
    }

    public String readNote(String filename){
        StringBuilder text = new StringBuilder();
        try {
            File file = new File(this.path, filename);

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            } }
        catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }

    public ArrayList<FileName> GetNotesList(){

        File f = new File(this.path);
        File file[] = f.listFiles();

        ArrayList<FileName> filenames = new ArrayList<FileName>();
        for (int i=0; i < file.length; i++)
        {
            String desc = new NoteManager().readNote(file[i].getName());
            FileName filename = new FileName(file[i].getName(), desc);
            filenames.add(filename);
        }
        return filenames;
    }

    public void deleteFile(String name) {
        File file = new File(Environment.getExternalStorageDirectory().toString() + "/Notes/" + name);
        boolean deleted = file.delete();
    }
}
