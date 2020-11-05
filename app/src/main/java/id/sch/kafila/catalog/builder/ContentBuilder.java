package id.sch.kafila.catalog.builder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;
import java.util.Set;

import id.sch.kafila.catalog.contents.Content;
import id.sch.kafila.catalog.contents.Dimension;

import static id.sch.kafila.catalog.builder.ViewBuilder.addPhysicalLogInfo;

public class ContentBuilder {

    private final Content content;
    private final ViewBuilder viewBuilder;
    private final Context context;

    private LinearLayout mainLayout;

    public ContentBuilder(Content content, Context context){
        this.content = content;
        this.context = context;
        switch (content.getContentType()){
            default:
                viewBuilder = new ViewBuilder(this.content, this.context);
                break;
        }

        initialize();
    }

    private void initialize() {
        if(content.isWrappedByLayout()) {
            mainLayout = ViewBuilder.verticalWrapContenLinearLayout(context);
            mainLayout.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
            addPhysicalLogInfo(mainLayout);
        }
    }

    public Content getContent() {
        return content;
    }

    public View buildContent(){
        View mainView = viewBuilder.buildView();
        if(content.isWrappedByLayout()){
            populateLayout(mainView);
            return mainLayout;
        }
        return mainView;
    }

    private void populateLayout(View mainView) {
        mainLayout.addView(mainView);
        Set<Integer> rows = content.getChildrenRows();
        for (Integer row : rows) {
            List<Content> contentEachRows = content.getChildernAtRow(row);

            LinearLayout horizontalLayout = ViewBuilder.horizontalWrapContenLinearLayout(context);
            horizontalLayout.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
            addPhysicalLogInfo(horizontalLayout);

            View[] rowViews = new View[contentEachRows.size()];
            for (int i = 0; i < contentEachRows.size(); i++) {
                Content _content = contentEachRows.get(i);
                ContentBuilder builder = new ContentBuilder(_content, context);
                rowViews[i] = builder.buildContent();
                horizontalLayout.addView(rowViews[i]);
            }
            mainLayout.addView(horizontalLayout);

        }
    }
}
