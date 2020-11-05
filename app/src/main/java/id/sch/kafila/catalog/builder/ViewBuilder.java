package id.sch.kafila.catalog.builder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import id.sch.kafila.catalog.contents.Content;
import id.sch.kafila.catalog.util.Logs;

class ViewBuilder {

    private final Content content;
    private final Context context;
    public ViewBuilder(Content content, Context context){
        this.content = content;
        this.context = context;
    }

    public View buildView(){
        final View view;

        switch (content.getContentType()){
            case TITLE:
                view = TextViews.titleTextView(content, context);
                break;
            default:
                view = TextViews.commonTextView(content, context);
        }
        addPhysicalLogInfo(view);

        return view;
    }

    public static void addPhysicalLogInfo(final View view){
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int[] location = new int[2];
                view.getLocationOnScreen(location);
                int x = location[0];
                int y = location[1];
                if(view instanceof  LinearLayout){
                    Logs.log("LinearLayout orientataion ", ((LinearLayout) view).getOrientation());
                }
                Logs.log("view size: ", view.getId(), view.getClass(), x,",",y," ", view.getWidth(), "x", view.getHeight());
            }
        });
    }

    public static LinearLayout horizontalWrapContenLinearLayout(Context context){
        LinearLayout layout2 = new LinearLayout(context);
        layout2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout2.setOrientation(LinearLayout.HORIZONTAL);
        return layout2;
    }
    public static LinearLayout verticalWrapContenLinearLayout(Context context){
        LinearLayout layout2 = new LinearLayout(context);
        layout2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout2.setOrientation(LinearLayout.VERTICAL);
        return layout2;
    }
}
