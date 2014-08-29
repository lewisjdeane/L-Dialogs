package uk.me.lewisdeane.ldialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
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
public class CustomListDialog extends AlertDialog {

    // Context used to create the dialog.
    private Context mContext;

    // String containing the title text.
    private String mTitle = "";

    // Array of string containing the items for the list.
    private String[] mItems;

    // String containing hex colour of title.
    private String mTitleColour = "";

    // String containing hex colour of items.
    public static String mItemColour = "";

    // Alignment containing where to align the title
    private Alignment mTitleAlignment = Alignment.LEFT;

    // Alignment containing where to align the items
    public static Alignment mItemAlignment = Alignment.LEFT;

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
    public static Typeface mTypeface;

    // Boolean containing whether or not dark dialog should be shown.
    public static boolean mIsDark = false;

    public static enum Alignment {
        LEFT, CENTER, RIGHT;
    }

    public static enum LightColours {
        TITLE("#474747"), ITEM("#999999");

        public final String mColour;

        private LightColours(String _colour) {
            this.mColour = _colour;
        }
    }

    public static enum DarkColours {
        TITLE("CCCCCC"), ITEM("#808080");

        public final String mColour;

        private DarkColours(String _colour) {
            this.mColour = _colour;
        }
    }

    public CustomListDialog(Builder _builder) {
        super(new ContextThemeWrapper(_builder.mContext, _builder.mIsDark ? android.R.style.Theme_Holo : android.R.style.Theme_Holo_Light));
        this.mContext = _builder.mContext;
        this.mIsDark = _builder.mIsDark;
        this.mTitle = _builder.mTitle;
        this.mItems = _builder.mItems;
        this.mTitleColour = _builder.mTitleColour.length() > 0 ? _builder.mTitleColour : (this.mIsDark ? DarkColours.TITLE.mColour : LightColours.TITLE.mColour);
        this.mItemColour = _builder.mItemColour;
        this.mTitleAlignment = _builder.mTitleAlignment;
        this.mItemAlignment = _builder.mItemAlignment;
        init();
        setListeners();
        setTitleProperties();
    }

    private void init() {
        // Reference the root view from inflated layout file.
        mRootView = LayoutInflater.from(mContext).inflate(
                R.layout.dialog_list_custom,
                null);

        // Reference typeface from assets.
        mTypeface = Typeface.createFromAsset(getContext().getResources().getAssets(), "Roboto-Medium.ttf");

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
            mTitleView.setTextColor(Color.parseColor(this.mTitleColour));
            mTitleView.setTypeface(this.mTypeface);
            mTitleView.setGravity(getGravityFromAlignment(this.mTitleAlignment));
        }
        return this;
    }

    public static int getGravityFromAlignment(Alignment _alignment) {
        switch (_alignment) {
            case LEFT:
                return Gravity.LEFT;
            case CENTER:
                return Gravity.CENTER;
            case RIGHT:
                return Gravity.RIGHT;
            default:
                return Gravity.LEFT;
        }
    }

    public CustomListDialog setListClickListener(ListClickListener mCallbacks) {
        this.mCallbacks = mCallbacks;
        return this;
    }

    public interface ListClickListener {
        public void onListItemSelected(int position, String[] items, String item);
    }

    public static class Builder {

        private final Context mContext;
        private final String mTitle;
        private final String[] mItems;

        public Builder(Context _context, String _title, String[] _items) {
            this.mContext = _context;
            this.mTitle = _title;
            this.mItems = _items;
        }

        private Alignment mTitleAlignment = Alignment.LEFT, mItemAlignment = Alignment.LEFT;
        private String mTitleColour = "", mItemColour = "";
        private boolean mIsDark = false;

        public Builder titleAlignment(Alignment _alignment) {
            this.mTitleAlignment = _alignment;
            return this;
        }

        public Builder itemAlignment(Alignment _alignment) {
            this.mItemAlignment = _alignment;
            return this;
        }

        public Builder titleColour(String _colour) {
            this.mTitleColour = _colour;
            return this;
        }

        public Builder itemColour(String _colour) {
            this.mItemColour = _colour;
            return this;
        }

        public Builder darkTheme(boolean _isDark) {
            this.mIsDark = _isDark;
            return this;
        }

        public CustomListDialog build() {
            return new CustomListDialog(this);
        }
    }
}
