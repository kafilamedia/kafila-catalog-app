package id.sch.kafila.catalog.components;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import id.sch.kafila.catalog.R;
import id.sch.kafila.catalog.util.Logs;

public class KafilaHeader extends RelativeLayout {


    public KafilaHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.kafila_header, this);
        initComponents();
        for (int i = 0; i< attrs.getAttributeCount(); i++){

            Logs.log(i," attr: ", attrs.getAttributeName(i),"=",attrs.getAttributeValue(i));
        }

    }

    private void initComponents() {
    }


}
