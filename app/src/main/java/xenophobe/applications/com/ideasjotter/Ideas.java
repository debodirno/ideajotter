package xenophobe.applications.com.ideasjotter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.MultiChoiceModeListener;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

/**
 * Created by Debodirno on 05-Mar-16.
 */
public class Ideas extends Activity {
    ArrayList<FileName> filenames;
    ListViewAdapter adapter;
    ListView lv_filenames;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        NoteManager nm = new NoteManager(getApplicationContext());
        nm.CreateNewDirectory();
        filenames = nm.GetNotesList();
        adapter = new ListViewAdapter(this, R.layout.listview_item, filenames);
        lv_filenames = (ListView) findViewById(R.id.lv_filename);
        lv_filenames.setAdapter(adapter);
        lv_filenames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Ideas.this, Note.class);
                intent.putExtra("filename", filenames.get(position).getName());
                startActivity(intent);
            }
        });

        lv_filenames.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        lv_filenames.setMultiChoiceModeListener(new MultiChoiceModeListener() {
                @Override
                public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                    final int checkedCount = lv_filenames.getCheckedItemCount();
                    if (checkedCount > 1)
                        mode.setTitle(checkedCount + " items selected");
                    else
                        mode.setTitle(checkedCount + " item selected");
                    adapter.toggleSelection(position);
                }

                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    mode.getMenuInflater().inflate(R.menu.menu_context, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.delete:
                            SparseBooleanArray selected = adapter.getSelectedIds();
                            for(int i = (selected.size() - 1); i >= 0; i --){
                                if(selected.valueAt(i)){
                                    FileName selectedItem = adapter.getItem(selected.keyAt(i));
                                    adapter.remove(selectedItem);
                                    new NoteManager().deleteFile(selectedItem.getName());
                                }
                            }
                            mode.finish();
                            return true;
                        default:
                            return false;
                    }
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    adapter.removeSelection();
                }
            }
        );

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ideas, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(Ideas.this, Note.class);
                startActivity(intent);
                return true;

            case R.id.action_about:
                AlertDialog.Builder builder = new AlertDialog.Builder(Ideas.this);
                builder.setTitle("About");
                builder.setMessage("Debodirno Chandra\n©2016\nAll rights reserved.");
                AlertDialog dialog = builder.show();

                TextView messageView = (TextView)dialog.findViewById(android.R.id.message);
                messageView.setGravity(Gravity.CENTER);

                TextView titleView = (TextView)dialog.findViewById(this.getResources().getIdentifier("alertTitle", "id", "android"));
                if (titleView != null) {
                    titleView.setGravity(Gravity.CENTER);
                }
                return true;

            case R.id.action_share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");

                sharingIntent.putExtra(Intent.EXTRA_TEXT, "Click on this link to download the app : https://raw.githubusercontent.com/debodirno/ideas-jotter/master/app-debug.apk");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Ideas Jotter - An on-the-fly app to jot down ideas and notes");

                startActivity(Intent.createChooser(sharingIntent, "How do you want to share?"));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
