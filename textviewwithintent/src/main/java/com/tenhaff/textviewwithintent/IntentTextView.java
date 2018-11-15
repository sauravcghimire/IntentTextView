package com.tenhaff.textviewwithintent;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Toast;

public class IntentTextView extends AppCompatTextView {

    private static final String WEB = "web";
    private static final String ADDRESS = "address";
    private static final String PHONE = "phone";
    String fontName;
    Context context;


    public IntentTextView(Context context) {
        super(context);
        this.context = context;
    }

    public IntentTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    public IntentTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.IntentTextView);
            fontName = a.getString(R.styleable.IntentTextView_intentType);
            a.recycle();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (fontName) {
            case WEB:
                openWebPage(getText().toString());
                break;
            case PHONE:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+getText().toString()));
                context.startActivity(intent);
                break;
            case ADDRESS:
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + getText().toString());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);
                break;
            default:
                Toast.makeText(context, "CLicked", Toast.LENGTH_SHORT).show();

        }
        return super.onTouchEvent(event);

    }

    public void openWebPage(String url) {
        try {
            Uri webpage = Uri.parse(url);
            Intent myIntent = new Intent(Intent.ACTION_VIEW, webpage);
            context.startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No application can handle this request. Please install a web browser or check your URL.",  Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


}
