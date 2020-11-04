package id.sch.kafila.catalog.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.Random;

public class ComponentUtil {

    static final Random rand = new Random();
    static final int WRAP_CONTENT = LinearLayout.LayoutParams.WRAP_CONTENT;
    static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    private static final double SLIDE_SHOW_TITLE_LAYOUT = 0.7;
    private static final double LAYOUT_WIDTH = 0.9;


    public static LinearLayout buildVerticalLayout(int width, Context context, View... views) {
        LinearLayout mainLayout = new LinearLayout(context);

        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setId(rand.nextInt(999999999) + 10000000);
        for (View view : views) {
            mainLayout.addView(view);
        }

        mainLayout = setLinearLayoutParams(mainLayout, width, WRAP_CONTENT);
        mainLayout.requestLayout();

        return mainLayout;
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
    public static int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }




    private static LinearLayout buildHorizontalLayout(Activity context) {

        LinearLayout subLayout = new LinearLayout(context);

        subLayout = setLinearLayoutParams(subLayout, WRAP_CONTENT, WRAP_CONTENT);
        subLayout.setOrientation(LinearLayout.HORIZONTAL);

        return subLayout;
    }

    public static TextView buildTextView(String text, Dimension dimension, Context context) {

        LoggingUtil.log("Building textView with value: ", text);

        TextView textView = new TextView(context);
        textView.setId(rand.nextInt(999999999) + 19999999);
        textView.setTextColor(Color.BLACK);
        textView.setText(Html.fromHtml(text));

        if (null != dimension) {

            /**
             * position
             */
            textView.setX(dimension.getX());
            textView.setY(textView.getY());
            if (dimension.getWidth() > 0)
                textView.setWidth(dimension.getWidth());
            if (dimension.getHeight() > 0)
                textView.setHeight(dimension.getHeight());

            /**
             * padding & margin
             */
            textView = (TextView) setPadding(textView, dimension);
            textView = (TextView) setMargin(textView, dimension);
        }

        textView.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE); //must be called FIRST
        textView.setSingleLine(false);
        textView.setHorizontallyScrolling(false);
        textView.requestLayout();

        return textView;
    }

    public static View setMargin(View view, Dimension dimension) {

        LoggingUtil.log("Will set layout param");

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
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
        view.setLayoutParams(layoutParams);

        LoggingUtil.log("success set layout param");
        return view;
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



    public static String constructH3(String str) {
        return "<h3><b>" + str + "</b></h3>";
    }

    public static String constructListItem(String... items) {

        StringBuilder sb = new StringBuilder("<ul>");

        for (String item :
                items) {
            sb = sb.append("<li>").append(item.trim()).append("</li>");
        }

        sb = sb.append("</ul>");
        return sb.toString();

    }

    static final int NAV_BUTTON_SIZE = 30;

    public static int percentageSizeWidth(Context context, double percentage){
        return (int) (getScreenWidth(context) * percentage);
    }

    public static LinearLayout buildSlideShow(Activity context, View... views) {


        //  LinearLayout titleLayout = buildHorizontalLayout(context);
        //  titleLayout.setLayoutParams(new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT,  LinearLayout.LayoutParams.WRAP_CONTENT));

        //TITLE SECTION WILL HAVE BUTTONS
        View titleView = views[0];
        titleView.setLayoutParams(new LinearLayout.LayoutParams((int)(percentageSizeWidth(context, SLIDE_SHOW_TITLE_LAYOUT)), LinearLayout.LayoutParams.WRAP_CONTENT));

        //   titleLayout.addView(titleView);
        LinearLayout titleLayout = buildHorizontalLayout(context);

        titleLayout = setLinearLayoutParams(titleLayout, MATCH_PARENT, WRAP_CONTENT);
       // titleLayout.setBackgroundColor(Color.BLUE);
        titleLayout.setPadding(5, 5, 5, 5);

//        ImageButton nextBtn = createNavButton(R.drawable.next_btn, context.onClickNextMenu(), context);
//
//        ImageButton backBtn = createNavButton(R.drawable.back_btn, context.onClickPrevMenu(), context);
//
//        ImageButton homeBtn = createNavButton(R.drawable.home_btn, context.onClickEndSlideShow(), context);
//
//        titleLayout.addView(titleView);
//        titleLayout.addView(homeBtn);
//        titleLayout.addView(backBtn);
//        titleLayout.addView(nextBtn);

        titleLayout.requestLayout();

        //Swap
        views[0] = titleLayout;

        LinearLayout mainLayout = buildVerticalLayout(MATCH_PARENT, context, views);
        mainLayout.setPadding(5,5,5,5);

        return mainLayout;
    }

    public static LinearLayout setLinearLayoutParams(LinearLayout LinearLayout, int w, int h) {
        LinearLayout.setLayoutParams(new LinearLayout.LayoutParams(w, h));
        return LinearLayout;
    }

    public static LinearLayout setLinearLayoutParamsPercentage(LinearLayout View, double w, double h) {

        int ScreenWidth = getScreenWidth(View.getContext());
        int ScreenHeight = getScreenHeight(View.getContext());

        return setLinearLayoutParams(View, (int)(w * ScreenWidth), (int)(h * ScreenHeight));
    }

    public static <T extends View>  T  setViewLayoutParams(View View, int w, int h) {
        View.setLayoutParams(new LinearLayout.LayoutParams(w, h));
        View.requestLayout();
        return (T)View;
    }

    public static <T extends View>  T setViewLayoutParamsPercentage(View View, double w, double h) {

        int ScreenWidth = getScreenWidth(View.getContext());
        int ScreenHeight = getScreenHeight(View.getContext());

        return setViewLayoutParams(View, (int)(w * ScreenWidth), (int)(h * ScreenHeight));
    }

    public static ImageButton createNavButton(int drawableId, View.OnClickListener onClickListener, Context context) {

        ImageButton imageButton = new ImageButton(context);
        imageButton.setImageResource(drawableId);
        imageButton.setOnClickListener(onClickListener);
        imageButton.setBackgroundColor(Color.WHITE);
        imageButton.setScaleType(ImageView.ScaleType.CENTER_CROP);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(NAV_BUTTON_SIZE, NAV_BUTTON_SIZE);

        layoutParams.setMargins(7, 7, 7, 7);

        imageButton.setLayoutParams(layoutParams);
        return imageButton;
    }
}
