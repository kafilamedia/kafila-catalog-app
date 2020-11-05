package id.sch.kafila.catalog.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import id.sch.kafila.catalog.R;
import id.sch.kafila.catalog.builder.ContentBuilder;
import id.sch.kafila.catalog.builder.ContentHelper;
import id.sch.kafila.catalog.constants.SharedPreferencesConstants;
import id.sch.kafila.catalog.contents.Content;
import id.sch.kafila.catalog.contents.Dimension;
import id.sch.kafila.catalog.contents.data.ContentData;
import id.sch.kafila.catalog.util.Logs;

public class CommonContent extends BaseActivity {

    private LinearLayout mainLayout;
    public CommonContent(){
        super(R.layout.activity_common_content);
    }


    @Override
    protected void initComponent() {
        mainLayout = findViewById(R.id.common_content_container);
        buildComponents();
    }

    private void buildComponents() {
        String configName = sharedpreferences.getString(SharedPreferencesConstants.KEY_CONTENT, null);
        Logs.log("CONFIG NAME: ", configName);
        if(null == configName){
            return;
        }
        Content content = ContentData.getContent(configName);
        ContentBuilder contentBuilder  = new ContentBuilder(content, this);
        View view = contentBuilder.buildContent();

        mainLayout.addView(view);
        mainLayout.setLayoutParams(new FrameLayout.LayoutParams( Dimension.WRAP_CONTENT, Dimension.WRAP_CONTENT));
        Logs.log("mainLayout.getChildCount(): ", mainLayout.getChildCount());
    }

    @Override
    protected void initEvent() {

    }
}
