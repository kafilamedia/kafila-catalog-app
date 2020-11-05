package id.sch.kafila.catalog.builder;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import id.sch.kafila.catalog.contents.Content;
import id.sch.kafila.catalog.contents.Dimension;

public class TextViews {

    public static TextView commonTextView(Object content, int color, Dimension dimension, Context context){
        return ContentHelper.buildTextView(content, color, 20, false, dimension, context);
    }

    public static TextView commonTextView(Content content, Context context){
        return commonTextView(content.getBody(), content.getTextColor(),
                content.getDimension(), context);
    }

    public static TextView titleTextView(Object content, int color, Dimension dimension, Context context){
        dimension.setGravity(Gravity.CENTER);
//        if(dimension.getWidth() <=0 ) dimension.setWidth(200);
        return ContentHelper.buildTextView(content, color, 40, false, dimension, context);
    }

    public static TextView titleTextView(Content content, Context context){
        return titleTextView(content.getBody(), content.getTextColor(),
                content.getDimension(), context);
    }
}
