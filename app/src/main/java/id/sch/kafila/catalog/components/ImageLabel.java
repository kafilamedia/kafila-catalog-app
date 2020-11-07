package id.sch.kafila.catalog.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.annotation.StyleableRes;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import id.sch.kafila.catalog.R;
import id.sch.kafila.catalog.util.Logs;

public class ImageLabel extends ConstraintLayout {
    @StyleableRes
    int index0 = 0;
    @StyleableRes
    int index1 = 1;
    private ImageView imageView;
    private TextView label;
    public ImageLabel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.component_image_with_text, this);
        initComponents();
        int[] sets = {R.attr.imageLabelDrawableId, R.attr.imageLabelText};
        TypedArray typedArray = context.obtainStyledAttributes(attrs, sets);
        for (int i = 0; i< attrs.getAttributeCount(); i++){
            String attributeName = attrs.getAttributeName(i);
            switch (attributeName){
                case "imageLabelDrawableId":
                    int value = attrs.getAttributeResourceValue(i, R.drawable.ic_home_black_24dp);
                    imageView.setImageResource( value);
                    break;
                case "imageLabelText":
                    String labelText = attrs.getAttributeValue(i);
                    label.setText(labelText);
                    break;
            }
            Logs.log(i," attr: ", attrs.getAttributeName(i),"=",attrs.getAttributeValue(i));
        }
        imageView.setAdjustViewBounds(true);
        typedArray.recycle();
    }

    public void setOnClickListener(OnClickListener listener){
        imageView.setOnClickListener(listener);
    }

    private void initComponents() {
        imageView = (ImageView) findViewById(R.id.image_with_text_img);
        label = (TextView) findViewById(R.id.image_with_text_label);

    }
}
