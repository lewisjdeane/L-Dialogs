package uk.me.lewisdeane.ldialogs;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lewis on 17/08/2014.
 */
public class CustomListAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private int mRes;
    private ArrayList<String> mItems;

    public CustomListAdapter(Context _context, int _res, ArrayList<String> _items){
        super(_context, _res, _items);

        this.mContext = _context;
        this.mRes = _res;
        this.mItems = _items;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            v = LayoutInflater.from(getContext()).inflate(mRes, null);
        }

        TextView item = (TextView) v.findViewById(R.id.item_dialog_list_item);
        item.setText(mItems.get(position));

        item.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);

        if(CustomListDialog.mIsCenterAligned)
            item.setGravity(Gravity.CENTER);

        try {
            item.setTextColor(Color.parseColor(CustomListDialog.mItemColour));
        } catch(Exception e){
            Log.e("L Dialogs", "Invalid colour passed into setListItemColour, try a valid hex code... Using default colour.");
            item.setTextColor(Color.parseColor("#999999"));
        }

        item.setTypeface(CustomListDialog.mTypeface);

        return v;
    }
}
