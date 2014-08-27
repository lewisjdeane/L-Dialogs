package uk.me.lewisdeane.ldialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomDialog extends AlertDialog {

    private Context mContext;
    private View mRootView;
    private View customView;
    private View[] mViews = new View[4];
    private LinearLayout[] mButtonContainers = new LinearLayout[2];
    private String[] mStrings = new String[]{"", "", "", ""};
    private Typeface mTypeface;
    private ClickListener mCallbacks;

    private enum Theme {LIGHT, DARK}

    private Theme mTheme = Theme.LIGHT;
    private String mConfirmColour;

    private enum LightColours {
        TITLE("#474747"), CONTENT("#999999"), BUTTON("#212121");

        private final String mColour;

        private LightColours(String _colour) {
            this.mColour = _colour;
        }
    }

    private enum DarkColours {
        TITLE("#CCCCCC"), CONTENT("#808080"), BUTTON("#CCCCCC");

        private final String mColour;

        private DarkColours(String _colour) {
            this.mColour = _colour;
        }
    }

    /*
    This class will be written shortly to be more efficient with regard to the new features implemented.
     */

    /* Basic constructor */
    public CustomDialog(Context _context) {
        super(_context);
        mContext = _context;
        init();
    }

    public CustomDialog(Builder _builder) {
        super(new ContextThemeWrapper(_builder.mContext, _builder.mDarkTheme ? android.R.style.Theme_Holo : android.R.style.Theme_Holo_Light));
        mContext = _builder.mContext;
        init();
        setIsDarkTheme(_builder.mDarkTheme);
        setTitle(_builder.mTitle);
        setContent(_builder.mContent);
        setConfirm(_builder.mPositiveText);
        setCancel(_builder.mNegativeText);
        setConfirmColour(_builder.mPositiveColour);
    }

    private void init() {
        mRootView = LayoutInflater.from(mContext).inflate(
                R.layout.dialog_custom,
                null);

        try {
            if (mCallbacks == null) {
                mCallbacks = (ClickListener) mContext;
            }
        } catch (ClassCastException e) {
            Log.w("L Dialogs", mContext.toString() + " should implement ClickListener or use CustomDialog.setClickListener(...)");
        }

        mViews[0] = mRootView.findViewById(R.id.dialog_custom_title);
        mViews[1] = mRootView.findViewById(R.id.dialog_custom_content);
        mViews[2] = mRootView.findViewById(R.id.dialog_custom_confirm);
        mViews[3] = mRootView.findViewById(R.id.dialog_custom_cancel);

        mButtonContainers[0] = (LinearLayout) mRootView.findViewById(R.id.dialog_custom_alongside_buttons);
        mButtonContainers[1] = (LinearLayout) mRootView.findViewById(R.id.dialog_custom_stacked_buttons);

        checkIfButtonStackingNeeded();

        mTypeface = Typeface.createFromAsset(getContext().getResources().getAssets(), "Roboto-Medium.ttf");

        setListeners();

        super.setView(mRootView);
    }

    private void setListeners() {
        mViews[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Code when positive button is clicked.
                if (mCallbacks != null)
                    mCallbacks.onConfirmClick();
                dismiss();
            }
        });

        mViews[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Code when negative button is clicked.
                if (mCallbacks != null)
                    mCallbacks.onCancelClick();
                dismiss();
            }
        });
    }

    private void checkIfButtonStackingNeeded() {
        boolean isStackingNeeded = ((Button) mViews[2]).getPaint().measureText(((Button) mViews[2]).getText().toString()) > convertToPx(56) || ((Button) mViews[2]).getPaint().measureText(((Button) mViews[3]).getText().toString()) > convertToPx(56);
        mButtonContainers[0].setVisibility(isStackingNeeded ? View.GONE : View.VISIBLE);
        mButtonContainers[1].setVisibility(isStackingNeeded ? View.VISIBLE : View.GONE);
        updateButtonReferences(isStackingNeeded);
    }

    private void updateButtonReferences(boolean _isStackingNeeded) {
        mViews[2] = mRootView.findViewById(_isStackingNeeded ? R.id.dialog_custom_confirm_stacked : R.id.dialog_custom_confirm);
        mViews[3] = mRootView.findViewById(_isStackingNeeded ? R.id.dialog_custom_cancel_stacked : R.id.dialog_custom_cancel);
        setViewProperties(mViews, mStrings);
        setListeners();
        setConfirmColour(mConfirmColour);
    }

    private float convertToPx(float _dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _dp, mContext.getResources().getDisplayMetrics());
    }

    public CustomDialog setConfirmColour(String _hex) {
        mConfirmColour = mTheme == Theme.LIGHT ? LightColours.BUTTON.mColour : DarkColours.BUTTON.mColour;
        try {
            ((Button) mViews[2]).setTextColor(Color.parseColor(_hex.equals("") ? mConfirmColour : _hex));
            mConfirmColour = _hex;
        } catch (Exception e) {
        }
        return this;
    }

    private void setViewProperties(View[] _view, String[] _text) {
        for (int i = 0; i < _view.length; i++) {

            int index = getIndexFromView(_view[i]);
            mViews[index].setVisibility(View.GONE);
            mStrings[index] = _text[i];

            if (!_text[i].equals(""))
                mViews[index].setVisibility(View.VISIBLE);

            if (index / 2 > 0) {
                Button button = (Button) mViews[index];
                button.setText(mStrings[index].toUpperCase());
                button.setTypeface(mTypeface);
            } else {
                TextView textView = (TextView) mViews[index];
                textView.setText(mStrings[index]);
                textView.setTypeface(mTypeface);
            }
        }
        applyTheme();
    }

    private int getIndexFromView(View _view) {
        for (int i = 0; i < mViews.length; i++) {
            if (mViews[i] == _view)
                return i;
        }
        return 0;
    }

    private void applyTheme() {
        mRootView.setBackgroundColor(mTheme == Theme.LIGHT ? Color.parseColor("#FFFFFF") : Color.parseColor("#333333"));
        ((TextView)mViews[0]).setTextColor(Color.parseColor(mTheme == Theme.LIGHT ? LightColours.TITLE.mColour : DarkColours.TITLE.mColour));
        ((TextView)mViews[1]).setTextColor(Color.parseColor(mTheme == Theme.LIGHT ? LightColours.CONTENT.mColour : DarkColours.CONTENT.mColour));
        ((Button)mViews[3]).setTextColor(Color.parseColor(mTheme == Theme.LIGHT ? LightColours.BUTTON.mColour : DarkColours.BUTTON.mColour));
    }

    public CustomDialog setTitle(String _title) {
        setViewProperties(new View[]{mViews[0]}, new String[]{_title});
        return this;
    }

    public CustomDialog setContent(String _content) {
        setViewProperties(new View[]{mViews[1]}, new String[]{_content});
        return this;
    }

    public CustomDialog setConfirm(String _confirm) {
        setViewProperties(new View[]{mViews[2]}, new String[]{_confirm});
        checkIfButtonStackingNeeded();
        return this;
    }

    public CustomDialog setCancel(String _cancel) {
        setViewProperties(new View[]{mViews[3]}, new String[]{_cancel});
        checkIfButtonStackingNeeded();
        return this;
    }

    private CustomDialog setIsDarkTheme(boolean _isDark) {
        mTheme = _isDark ? Theme.DARK : Theme.LIGHT;
        applyTheme();
        return this;
    }

    public CustomDialog setClickListener(ClickListener mCallbacks) {
        this.mCallbacks = mCallbacks;
        return this;
    }

    public CustomDialog setCustomView(View view) {
        if (customView != null) {
            // Remove the current custom View
            ((ViewGroup) mViews[0].getParent()).removeView(customView);
        }

        customView = view;
        ((ViewGroup) mViews[0].getParent()).addView(customView, 2);
        return this;
    }

    public String getTitle() {
        return mStrings[0];
    }

    public String getContent() {
        return mStrings[1];
    }

    public String getConfirm() {
        return mStrings[2];
    }

    public String getCancel() {
        return mStrings[3];
    }

    public View getCustomView() {
        return customView;
    }

    public interface ClickListener {
        public void onConfirmClick();

        public void onCancelClick();
    }

    public static class Builder {

        private final Context mContext;

        public Builder(Context _context) {
            this.mContext = _context;
        }

        private String mTitle = "", mContent = "", mPositiveText = "", mNegativeText = "", mPositiveColour = "";
        private boolean mDarkTheme = false;

        public Builder title(String _title) {
            this.mTitle = _title;
            return this;
        }

        public Builder content(String _content) {
            this.mContent = _content;
            return this;
        }

        public Builder positiveText(String _positiveText) {
            this.mPositiveText = _positiveText;
            return this;
        }

        public Builder negativeText(String _negativeText) {
            this.mNegativeText = _negativeText;
            return this;
        }

        public Builder positiveColor(String _positiveColour) {
            this.mPositiveColour = _positiveColour;
            return this;
        }

        public Builder darkTheme(boolean _isDark) {
            this.mDarkTheme = _isDark;
            return this;
        }

        public CustomDialog build() {
            return new CustomDialog(this);
        }
    }
}
