package xenophobe.applications.com.ideasjotter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Created by Debodirno on 05-Mar-16.
 */
public class Note extends Activity {

    private NoteManager nm;
    private String filename = null;
    private Bundle extras;
    private EditText et_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note);
        et_note = (EditText) findViewById(R.id.et_note);
        extras = getIntent().getExtras();

        if(extras != null){
            filename = extras.getString("filename");
            nm = new NoteManager(Note.this);
            String text = nm.readNote(filename);
            et_note.setText(text);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_note, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_save:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote(){
        if(filename == null){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Subject");
            // Set an EditText view to get user input
            final EditText input = new EditText(this);
            input.setPadding(35, 20, 20, 15);
            input.setSingleLine(true);
            alert.setView(input);

            alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String value = input.getText().toString();
                    filename = value;
                    new NoteManager(Note.this).saveNote(filename, et_note.getText().toString());
                    Intent intent = new Intent(Note.this, Ideas.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Canceled.
                }
            });
            alert.show();

        }
        else{
            new NoteManager(Note.this).saveNote(filename, et_note.getText().toString());
            Intent intent = new Intent(Note.this, Ideas.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }
}
