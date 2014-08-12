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


/**
 * Created by Lewis on 12/08/2014.
 */
public class CustomDialog extends AlertDialog {

    private Context mContext;
    private View mRootView;
    private String mTitle, mContent, mConfirm, mCancel;
    private TextView mTitleView, mContentView;
    private Button mConfirmView, mCancelView;
    private Typeface mTypeface;
    private ClickListener mCallbacks;

    public CustomDialog(Context _context){
        super(_context);
        mContext = _context;
        init();
    }

    public CustomDialog(Context _context, String _title, String _content, String _confirm, String _cancel){
        super(_context);
        mContext = _context;
        init();
        setTitle(_title);
        setContent(_content);
        setConfirm(_confirm);
        setCancel(_cancel);
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

        mTitleView = (TextView) mRootView.findViewById(R.id.dialog_custom_title);
        mContentView = (TextView) mRootView.findViewById(R.id.dialog_custom_content);
        mConfirmView = (Button) mRootView.findViewById(R.id.dialog_custom_confirm);
        mCancelView = (Button) mRootView.findViewById(R.id.dialog_custom_cancel);

        mTypeface = Typeface.createFromAsset(getContext().getResources().getAssets(), "Roboto-Medium.ttf");

        mTitleView.setTypeface(mTypeface);
        mContentView.setTypeface(mTypeface);
        mConfirmView.setTypeface(mTypeface);
        mCancelView.setTypeface(mTypeface);

        setListeners();

        super.setView(mRootView);
    }

    private void setListeners(){
        mConfirmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Code when positive button is clicked.
                if(mCallbacks != null)
                    mCallbacks.onConfirmClick();
                dismiss();
            }
        });

        mCancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Code when negative button is clicked.
                if(mCallbacks != null)
                    mCallbacks.onCancelClick();
                dismiss();
            }
        });
    }

    public void setConfirmColour(String _hex){
        mConfirmView.setTextColor(Color.parseColor(_hex));
    }

    public void setTitle(String _title){
        mTitle = _title;
        mTitleView.setText(mTitle);
    }

    public void setContent(String _content){
        mContent = _content;
        mContentView.setText(mContent);
    }

    public void setConfirm(String _confirm){
        mConfirm = _confirm;
        mConfirmView.setText(mConfirm);
    }

    public void setCancel(String _cancel){
        mCancel = _cancel;
        mCancelView.setText(mCancel);
    }

    public String getTitle(){
        return mTitle;
    }

    public String getContent(){
        return mContent;
    }

    public String getConfirm(){
        return mConfirm;
    }

    public String getCancel(){
        return mCancel;
    }

    public interface ClickListener{
        public void onConfirmClick();
        public void onCancelClick();
    }
}
