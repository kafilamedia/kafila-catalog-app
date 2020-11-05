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

        int[] sets = {R.attr.drawableId, R.attr.buttonLabel};
        TypedArray typedArray = context.obtainStyledAttributes(attrs, sets);
        int drawableId = typedArray.getResourceId(index0, R.drawable.ic_home_black_24dp);
        CharSequence labelText = typedArray.getText(index1);
        typedArray.recycle();

        initComponents();
        imageView.setImageResource( drawableId);
        label.setText(labelText);
    }

    public void setOnClickListener(OnClickListener listener){
        imageView.setOnClickListener(listener);
    }

    private void initComponents() {
        imageView = (ImageView) findViewById(R.id.image_view);
        label = (TextView) findViewById(R.id.button_label);

    }
}
