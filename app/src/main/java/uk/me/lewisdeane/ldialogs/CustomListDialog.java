package uk.me.lewisdeane.ldialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Lewis on 17/08/2014.
 */
public class CustomListDialog extends AlertDialog {

    private Context mContext;
    private String mTitle = "";
    private ArrayList<String> mItems = new ArrayList<String>();

    private View mRootView;
    private TextView mTitleView;
    private ListView mListView;
    private CustomListAdapter mCustomListAdapter;

    private ListClickListener mCallbacks;
    public static Typeface mTypeface;

    public static boolean mIsCenterAligned = false;
    public static String mTitleColour = "#474747", mItemColour =  "#999999";

    public CustomListDialog(Context _context){
        super(_context);
        mContext = _context;
        mTitleColour = "#474747";
        mItemColour =  "#999999";
        mIsCenterAligned = false;
        init();
    }

    public CustomListDialog(Context _context, String _title, String[] _items){
        super(_context);
        mContext = _context;
        setTitle(_title);
        setItems(_items);
        mTitleColour = "#474747";
        mItemColour =  "#999999";
        mIsCenterAligned = false;
        init();
    }

    private void init(){
        mRootView = LayoutInflater.from(mContext).inflate(
                R.layout.dialog_list_custom,
                null);

        mTypeface = Typeface.createFromAsset(getContext().getResources().getAssets(), "Roboto-Medium.ttf");

        mTitleView = (TextView) mRootView.findViewById(R.id.dialog_list_custom_title);
        mListView = (ListView) mRootView.findViewById(R.id.dialog_list_custom_list);

        mCustomListAdapter = new CustomListAdapter(mContext, R.layout.item_dialog_list, mItems);
        mListView.setAdapter(mCustomListAdapter);

        setTitleProperties();
        setListeners();

        super.setView(mRootView);
    }

    private void setListeners(){
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try{
                    mCallbacks = (ListClickListener) mContext;
                    mCallbacks.onListItemSelected(i, mItems, mItems.get(i));
                    dismiss();
                } catch(ClassCastException e){
                    Log.e("L Dialogs", mContext.getClass() + " must implement ListClickListener");
                }
            }
        });
    }

    private void setTitleProperties(){
        if(mTitleView != null) {
            mTitleView.setVisibility(View.VISIBLE);

            if (mTitle.length() > 0) {
                mTitleView.setText(mTitle);
                mTitleView.setVisibility(View.VISIBLE);
                mTitleView.setTextColor(Color.parseColor(mTitleColour));
                mTitleView.setTypeface(mTypeface);
            }
        }
    }

    public void setTitle(String _title){
        mTitle = _title;
        setTitleProperties();
    }

    public void setItems(String[] _items){
        mItems.clear();
        mItems.addAll(Arrays.asList(_items));
        if(mCustomListAdapter != null)
            mCustomListAdapter.notifyDataSetChanged();
    }

    public void setItems(ArrayList<String> _items){
        mItems.clear();
        mItems.addAll(_items);
        if(mCustomListAdapter != null)
            mCustomListAdapter.notifyDataSetChanged();
    }

    public void setCenterAligned(boolean _centerAligned){
        mIsCenterAligned = _centerAligned;
        mTitleView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        if(_centerAligned)
            mTitleView.setGravity(Gravity.CENTER);
        mCustomListAdapter.notifyDataSetChanged();
    }

    public void setTitleColour(String _hex){
        mTitleColour = _hex;
        setTitleProperties();
    }

    public void setListItemColour(String _hex){
        mItemColour = _hex;
        setTitleProperties();
        mCustomListAdapter.notifyDataSetChanged();
    }

    public interface ListClickListener{
        public void onListItemSelected(int position, ArrayList<String> items, String item);
    }
}
