package id.sch.kafila.catalog.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import id.sch.kafila.catalog.util.Navigate;
import sop.mpimedia.net.catalog.R;

public abstract class BaseActivity extends AppCompatActivity {

    protected final int LAYOUT_ID;

    public BaseActivity(int layoutId){
        LAYOUT_ID = layoutId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT_ID);
        initComponent();
        initEvent();
    }

    protected View.OnClickListener navigate(final Class<? extends Activity>  activityClass) {

        final Context context = this;

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigate.navigate(context, activityClass);
            }
        };
    }


    protected abstract void initComponent();
    protected abstract void initEvent();
}
