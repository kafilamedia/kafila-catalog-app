package id.sch.kafila.catalog.components;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import id.sch.kafila.catalog.R;
import id.sch.kafila.catalog.util.Logs;

public class KafilaFooter extends RelativeLayout {

    private ImageView webIcon;
    private ImageView mailIcon;
    private ImageView phoneIcon;

    private TextView webLabel;
    private TextView phoneLabel;
    private TextView mailLabel;

    public KafilaFooter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.component_kafila_footer, this);
        initComponents();
        for (int i = 0; i< attrs.getAttributeCount(); i++){

            Logs.log(i," attr: ", attrs.getAttributeName(i),"=",attrs.getAttributeValue(i));
        }

    }

    private void initComponents() {
        webIcon = findViewById(R.id.footer_web_icon);
        mailIcon = findViewById(R.id.footer_email_icon);
        phoneIcon = findViewById(R.id.footer_phone_icon);

        webLabel = findViewById(R.id.footer_web_label);
        mailLabel = findViewById(R.id.footer_email_label);
        phoneLabel = findViewById(R.id.footer_phone_label);
    }


}
