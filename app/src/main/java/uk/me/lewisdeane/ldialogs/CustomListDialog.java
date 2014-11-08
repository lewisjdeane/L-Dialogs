package uk.me.lewisdeane.ldialogs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Lewis on 17/08/2014
 */
public class CustomListDialog extends BaseDialog {

    // Context used to create the dialog.
    private final Context mContext;

    // String containing the title text.
    private final String mTitle;

    // Array of string containing the items for the list.
    private final String[] mItems;

    // Colour of title.
    private final int mTitleColour;

    // Colour of items.
    static int mItemColour;

    // Text size of the title
    private final int mTitleTextSize;

    // Text size of the items
    static int mItemTextSize;

    // Alignment containing where to align the title
    private final Alignment mTitleAlignment;

    // Alignment containing where to align the items
    static Alignment mItemAlignment;

    // View containing the layout view.
    private View mRootView;

    // Text view to be populated with the title text.
    private TextView mTitleView;

    // List view to be populated with the items array.
    private ListView mListView;

    // List Adapter used to populate the list view.
    private CustomListAdapter mCustomListAdapter;

    // Listener that listens for item click events and then dismisses dialog.
    private ListClickListener mCallbacks;

    // Typeface containing the font to use in dialog.
    static Typeface mTypeface;

    // Theme containing the chosen theme.
    static Theme mTheme = Theme.LIGHT;

    /**
     * TODO: Add ability for multi choice mode.
     * TODO: Add radio buttons for single choice and checkboxes for multi choice.
     * TODO: Add ability to set selected item(s).
     */

    // Make this class private so it can only be built through the builder.
    private CustomListDialog(Builder _builder) {

        // Call super class constructor to create our dialog.
        super(new ContextThemeWrapper(_builder.mContext, _builder.mIsDark ? R.style.LDialogs_Dark : R.style.LDialogs_Light));

        // Set correct properties.
        this.mContext = _builder.mContext;
        this.mTheme = _builder.mIsDark ? Theme.DARK : Theme.LIGHT;
        this.mTitle = _builder.mTitle;
        this.mItems = _builder.mItems;
        this.mTitleColour = _builder.mTitleColour != 0 ? _builder.mTitleColour : (this.mTheme == Theme.DARK ? Color.parseColor(DarkColours.TITLE.mColour) : Color.parseColor(LightColours.TITLE.mColour));
        this.mItemColour = _builder.mItemColour != 0 ? _builder.mItemColour : (this.mTheme == Theme.DARK ? Color.parseColor(DarkColours.ITEM.mColour) : Color.parseColor(LightColours.ITEM.mColour));
        this.mTitleAlignment = _builder.mTitleAlignment;
        this.mItemAlignment = _builder.mItemAlignment;
        this.mTitleTextSize = _builder.mTitleTextSize;
        this.mItemTextSize = _builder.mItemTextSize;
        this.mTypeface = _builder.mTypeface == null ? Typeface.createFromAsset(getContext().getResources().getAssets(), "Roboto-Medium.ttf") : _builder.mTypeface;

        // Reference everything needed and set the dialog view.
        init();

        // Sets the listener for the list.
        setListeners();

        // Set the properties of the title only since items in handled in the adapter.
        setTitleProperties();
    }

    private void init() {
        // Reference the root view from inflated layout file.
        mRootView = LayoutInflater.from(mContext).inflate(
                R.layout.dialog_list_custom,
                null);

        // Reference views needed in dialog.
        mTitleView = (TextView) mRootView.findViewById(R.id.dialog_list_custom_title);
        mListView = (ListView) mRootView.findViewById(R.id.dialog_list_custom_list);

        // Reference adapter used to populate the list view and then set it.
        mCustomListAdapter = new CustomListAdapter(mContext, R.layout.item_dialog_list, mItems);
        mListView.setAdapter(mCustomListAdapter);

        // Sets the view with the root view.
        super.setView(mRootView);
    }

    private void setListeners() {
        // Set the on item click listener for our list view.
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // If there is a listener available call onListItemSelected.
                if (mCallbacks != null)
                    mCallbacks.onListItemSelected(i, mItems, mItems[i]);

                dismiss();
            }
        });
    }

    private CustomListDialog setTitleProperties() {
        // Apply correct properties if title view is available
        if (mTitleView != null) {
            mTitleView.setText(this.mTitle);
            mTitleView.setTextColor(this.mTitleColour);
            mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTitleTextSize);
            mTitleView.setTypeface(this.mTypeface);
            mTitleView.setGravity(getGravityFromAlignment(this.mTitleAlignment) | Gravity.CENTER_VERTICAL);
        }
        return this;
    }

    public ListView getListView() {
        return mListView;
    }

    public CustomListDialog setListClickListener(ListClickListener mCallbacks) {
        // Set the list click listener.
        this.mCallbacks = mCallbacks;
        return this;
    }

    public interface ListClickListener {
        public void onListItemSelected(int position, String[] items, String item);
    }

    public static class Builder {

        // Required fields in constructor.
        private final Context mContext;
        private final String mTitle;
        private final String[] mItems;

        // Our builder constructor used to generate each dialog.
        public Builder(Context _context, String _title, String[] _items) {
            this.mContext = _context;
            this.mTitle = _title;
            this.mItems = _items;
        }

        public Builder(Context _context, int _titleResId, int _itemsResId) {
            this.mContext = _context;
            this.mTitle = mContext.getString(_titleResId);
            this.mItems = mContext.getResources().getStringArray(_itemsResId);
        }

        // Optional parameters initialised with default values.
        private Alignment mTitleAlignment = Alignment.LEFT, mItemAlignment = Alignment.LEFT;
        private int mTitleColour = 0, mItemColour = 0, mTitleTextSize = 22, mItemTextSize = 18;
        private boolean mIsDark = false;
        private Typeface mTypeface;

        public Builder typeface(Typeface _typeface){
            this.mTypeface = _typeface;
            return this;
        }

        public Builder titleAlignment(Alignment _alignment) {
            this.mTitleAlignment = _alignment;
            return this;
        }

        public Builder itemAlignment(Alignment _alignment) {
            this.mItemAlignment = _alignment;
            return this;
        }

        public Builder titleColor(String _colour) {
            this.mTitleColour = Color.parseColor(_colour);
            return this;
        }

        public Builder itemColor(String _colour) {
            this.mItemColour = Color.parseColor(_colour);
            return this;
        }

        public Builder titleColor(int _colour) {
            this.mTitleColour = _colour;
            return this;
        }

        public Builder itemColor(int _colour) {
            this.mItemColour = _colour;
            return this;
        }

        public Builder titleColorRes(int _colour) {
            this.mTitleColour = mContext.getResources().getColor(_colour);
            return this;
        }

        public Builder itemColorRes(int _colour) {
            this.mItemColour = mContext.getResources().getColor(_colour);
            return this;
        }

        public Builder darkTheme(boolean _isDark) {
            this.mIsDark = _isDark;
            return this;
        }

        public  Builder titleTextSize(int _textSize){
            this.mTitleTextSize = _textSize;
            return this;
        }

        public Builder itemTextSize(int _textSize){
            this.mItemTextSize = _textSize;
            return this;
        }

        public Builder rightToLeft(boolean _rightToLeft){
            if(_rightToLeft){
                this.mTitleAlignment = Alignment.RIGHT;
                this.mItemAlignment = Alignment.RIGHT;
            }
            return this;
        }

        public CustomListDialog build() {
            return new CustomListDialog(this);
        }
    }
}
