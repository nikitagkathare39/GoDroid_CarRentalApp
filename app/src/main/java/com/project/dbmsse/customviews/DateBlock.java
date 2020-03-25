package com.project.dbmsse.customviews;


import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.dbmsse.R;

public class DateBlock extends LinearLayout{

    private static final String TAG = DateBlock.class.getSimpleName();

    private Context context;

    private TextView dayText;
    private TextView monthText;
    private TextView timeText;


    public DateBlock(Context context) {
        super(context);
        this.context = context;
        initializeViewComponents();
    }

    public DateBlock(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initializeViewComponents();
    }

    public DateBlock(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initializeViewComponents();
    }

    private void initializeViewComponents(){
        View view = inflate(getContext(), R.layout.box_date, this);
        dayText = (TextView)view.findViewById(R.id.day_in_text);
        monthText = (TextView)view.findViewById(R.id.month_in_text);
        timeText = (TextView)view.findViewById(R.id.time_in_text);
    }

    public void setTextContent(TextView anyText, String textContent){
        anyText.setText(textContent);
    }
}
