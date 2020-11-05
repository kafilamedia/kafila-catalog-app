package id.sch.kafila.catalog.builder;

import android.content.Context;
import android.view.View;
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
        TextView view = ContentHelper.buildTextView(content.getBody(), content.getDimension(), context);
        Logs.log("view dimension: ", view.getWidth(), "x", view.getHeight());
        return view;
    }
}
