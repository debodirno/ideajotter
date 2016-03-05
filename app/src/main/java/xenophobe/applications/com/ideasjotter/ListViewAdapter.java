package xenophobe.applications.com.ideasjotter;


import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

/**
 * Created by Debodirno on 05-Mar-16.
 */
public class ListViewAdapter extends ArrayAdapter<FileName> {
    // Declare Variables
    Context context;
    LayoutInflater inflater;
    List<FileName> filenames;
    private SparseBooleanArray mSelectedItemsIds;

    public ListViewAdapter(Context context, int resourceId, List<FileName> filenames) {
        super(context, resourceId, filenames);
        mSelectedItemsIds = new SparseBooleanArray();
        this.context = context;
        this.filenames = filenames;
        inflater = LayoutInflater.from(context);
    }

    private class ViewHolder {
        TextView filename;
        TextView shorttext;
    }

    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_item, null);
            // Locate the TextViews in listview_item.xml
            holder.filename = (TextView) view.findViewById(R.id.item_title);
            holder.shorttext = (TextView) view.findViewById(R.id.item_desc);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Capture position and set to the TextViews
        holder.filename.setText(filenames.get(position).getName());
        holder.shorttext.setText(filenames.get(position).getShorttext());
        return view;
    }

    @Override
    public void remove(FileName object) {
        filenames.remove(object);
        notifyDataSetChanged();
    }

    public List<FileName> getFilenames() {
        return filenames;
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }
}
