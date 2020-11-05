package id.sch.kafila.catalog.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.annotation.StyleableRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import id.sch.kafila.catalog.R;
import id.sch.kafila.catalog.util.Logs;

public class MenuButton extends RelativeLayout {
    @StyleableRes
    int index0 = 0;
    @StyleableRes
    int index1 = 1;
    private ImageView imageView;
    private TextView label;
    public MenuButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.image_button, this);
        initComponents();
        int[] sets = {R.attr.drawableId, R.attr.buttonLabel};
        TypedArray typedArray = context.obtainStyledAttributes(attrs, sets);
        for (int i = 0; i< attrs.getAttributeCount(); i++){
            String attributeName = attrs.getAttributeName(i);
            switch (attributeName){
                case "drawableId":
                    int value = attrs.getAttributeResourceValue(i, R.drawable.ic_home_black_24dp);
                    imageView.setImageResource( value);
                    break;
                case "buttonLabel":
                    String labelText = attrs.getAttributeValue(i);
                    label.setText(labelText);
                    break;
            }
            Logs.log(i," attr: ", attrs.getAttributeName(i),"=",attrs.getAttributeValue(i));
        }

        typedArray.recycle();
    }

    public void setOnClickListener(OnClickListener listener){
        imageView.setOnClickListener(listener);
    }

    private void initComponents() {
        imageView = (ImageView) findViewById(R.id.image_view);
        label = (TextView) findViewById(R.id.button_label);

    }
}
