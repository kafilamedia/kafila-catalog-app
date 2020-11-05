package id.sch.kafila.catalog.builder;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

import id.sch.kafila.catalog.contents.Content;
import id.sch.kafila.catalog.contents.Dimension;
import id.sch.kafila.catalog.util.Logs;

import static id.sch.kafila.catalog.contents.Dimension.setLinearLayoutParams;
import static id.sch.kafila.catalog.contents.Dimension.setLinearLayoutParamsPercentage;

public final class ContentHelper {

    static final int WRAP_CONTENT = LinearLayout.LayoutParams.WRAP_CONTENT;
    static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    static Random rand = new Random();

    public static LinearLayout buildVerticalLayout(int width, Context context, View... views) {
        LinearLayout mainLayout = new LinearLayout(context);

        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setId(randomId());
        if(null != views) {
            for (View view : views) {
                mainLayout.addView(view);
            }
        }
        mainLayout = setLinearLayoutParamsPercentage(mainLayout, 100.0, 100.0);
        mainLayout.requestLayout();

        return mainLayout;
    }

    public static LinearLayout buildHorizontalLayout(int height, Context context, View... views) {
        LinearLayout mainLayout = new LinearLayout(context);

        mainLayout.setOrientation(LinearLayout.HORIZONTAL);
        mainLayout.setId(randomId());
        if(null != views) {
            for (View view : views) {
                mainLayout.addView(view);
            }
        }
        mainLayout = setLinearLayoutParamsPercentage(mainLayout, 100.0, height > 0 ? height : 100.0);
        mainLayout.requestLayout();

        return mainLayout;
    }

    private static int randomId() {
        return rand.nextInt(999999999) + 10000000;
    }


    public static LinearLayout buildHorizontalLayout(Activity context) {

        LinearLayout subLayout = new LinearLayout(context);

        subLayout = setLinearLayoutParams(subLayout, WRAP_CONTENT, WRAP_CONTENT);
        subLayout.setOrientation(LinearLayout.HORIZONTAL);

        return subLayout;
    }



    public static TextView buildTextView(Object content, int color, float fontSize, boolean singleLine, Dimension dimension, Context context) {
        String text = String .valueOf(content);
        Logs.log("Building textView with value: ", text);

        TextView textView = new TextView(context);
        textView.setId(randomId());
        textView.setTextColor(color);
        textView.setTextSize(fontSize);
        textView.setText(Html.fromHtml(text));

        if (null != dimension) {

            /**
             * position
             */
//            textView.setX(dimension.getX());
//            textView.setY(textView.getY());
            if (dimension.getWidth() > 0) {
                textView.setWidth(dimension.getWidth());
            }
            if (dimension.getHeight() > 0) {
                textView.setHeight(dimension.getHeight());
            }
            Logs.log("DIMENSION: ", dimension);
            /**
             * padding & margin
             */
            textView = (TextView) setPadding(textView, dimension);
            textView = (TextView) setMargin(textView, dimension);
        }

        textView.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE); //must be called FIRST
        textView.setSingleLine(singleLine);
        textView.setHorizontallyScrolling(false);
        textView.requestLayout();

        return textView;
    }

    public static View setPadding(View view, Dimension dimension) {
        if (dimension.getAllPadding() == 0) {
            view.setPadding(
                    dimension.getPaddingLeft(),
                    dimension.getPaddingTop(),
                    dimension.getPaddingRight(),
                    dimension.getPaddingBottom());
        } else {
            view.setPadding(
                    dimension.getAllPadding(),
                    dimension.getAllPadding(),
                    dimension.getAllPadding(),
                    dimension.getAllPadding());
        }

        return view;
    }

    public static View setMargin(View view, Dimension dimension) {

        Logs.log("Will set layout param");

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                dimension.getWidth()>0?dimension.getWidth():
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                dimension.getHeight()>0?dimension.getHeight():
                        LinearLayout.LayoutParams.WRAP_CONTENT
        );


        if (dimension.getAllMargin() == 0) {
            layoutParams.setMargins(
                    dimension.getMarginLeft(),
                    dimension.getMarginTop(),
                    dimension.getMarginRight(),
                    dimension.getMarginBottom());
        } else {
            layoutParams.setMargins(
                    dimension.getAllMargin(),
                    dimension.getAllMargin(),
                    dimension.getAllMargin(),
                    dimension.getAllMargin());
        }
        layoutParams.gravity = dimension.getGravity();
        view.setLayoutParams(layoutParams);

        Logs.log("success set layout param");
        return view;
    }
}
