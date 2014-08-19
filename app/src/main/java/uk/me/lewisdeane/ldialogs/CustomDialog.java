package uk.me.lewisdeane.ldialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CustomDialog extends AlertDialog {

    private Context mContext;
    private View mRootView;
    private View[] mViews = new View[4];
    private String[] mStrings = new String[]{"", "", "", ""};
    private Typeface mTypeface;
    private ClickListener mCallbacks;

    /* Passing in '' as any value will result in that textview or button being hidden.

    /* Basic constructor */
    public CustomDialog(Context _context){
        super(_context);
        mContext = _context;
        init();
    }

    /* Constructor for title, content, confirm text, cancel text */
    public CustomDialog(Context _context, String _title, String _content, String _confirm, String _cancel){
        super(_context);
        mContext = _context;
        init();
        setTitle(_title);
        setContent(_content);
        setConfirm(_confirm);
        setCancel(_cancel);
    }

    /* Constructor for title, content and one button. */
    public CustomDialog(Context _context, String _title, String _content, String _confirm){
        super(_context);
        mContext = _context;
        init();
        setTitle(_title);
        setContent(_content);
        setConfirm(_confirm);
    }

    private void init(){
        mRootView = LayoutInflater.from(mContext).inflate(
                R.layout.dialog_custom,
                null);

        try {
            mCallbacks = (ClickListener) mContext;
        } catch (ClassCastException e) {
            Log.e("L DIALOGS", mContext.toString() + " must implement ClickListener");
        }

        mViews[0] = mRootView.findViewById(R.id.dialog_custom_title);
        mViews[1] = mRootView.findViewById(R.id.dialog_custom_content);
        mViews[2] = mRootView.findViewById(R.id.dialog_custom_confirm);
        mViews[3] = mRootView.findViewById(R.id.dialog_custom_cancel);

        mTypeface = Typeface.createFromAsset(getContext().getResources().getAssets(), "Roboto-Medium.ttf");

        setListeners();

        super.setView(mRootView);
    }

    private void setListeners(){
        mViews[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Code when positive button is clicked.
                if(mCallbacks != null)
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

    public void setConfirmColour(String _hex){
        ((Button)mViews[2]).setTextColor(Color.parseColor(_hex));
    }

    public void setViewProperties(View[] _view, String[] _text){
        for(int i = 0; i < _view.length; i++) {

            int index = getIndexFromView(_view[i]);
            mViews[index].setVisibility(View.GONE);
            mStrings[index] = _text[i];

            if (!(_text[i].equals("") || _text[i] == null))
                mViews[index].setVisibility(View.VISIBLE);

            if(index/2 > 0){
                Button button = (Button)mViews[index];
                button.setText(mStrings[index].toUpperCase());
                button.setTypeface(mTypeface);
            } else{
                TextView textView = (TextView) mViews[index];
                textView.setText(mStrings[index]);
                textView.setTypeface(mTypeface);
            }
        }
    }

    private int getIndexFromView(View _view){
        for(int i = 0; i < mViews.length; i++){
            if(mViews[i] == _view)
                return i;
        }
        return 0;
    }

    public void setTitle(String _title){
        setViewProperties(new View[]{ mViews[0] } , new String[]{ _title });
    }

    public void setContent(String _content){
        setViewProperties(new View[]{ mViews[1] } , new String[]{ _content });
    }

    public void setConfirm(String _confirm){
        setViewProperties(new View[]{mViews[2]}, new String[]{_confirm});
    }

    public void setCancel(String _cancel){
        setViewProperties(new View[]{ mViews[3] } , new String[]{ _cancel });
    }

    public void setClickListener(ClickListener mCallbacks) {
        this.mCallbacks = mCallbacks;
    }

    public String getTitle(){
        return mStrings[0];
    }

    public String getContent(){
        return mStrings[1];
    }

    public String getConfirm(){
        return mStrings[2];
    }

    public String getCancel(){
        return mStrings[3];
    }

    public interface ClickListener{
        public void onConfirmClick();
        public void onCancelClick();
    }
}
