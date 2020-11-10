package id.sch.kafila.catalog.activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import id.sch.kafila.catalog.constants.SharedPreferencesConstants;
import id.sch.kafila.catalog.util.Logs;
import id.sch.kafila.catalog.util.Navigate;
import id.sch.kafila.catalog.R;

public abstract class BaseActivity extends AppCompatActivity {

    protected final int LAYOUT_ID;

    protected SharedPreferences sharedpreferences;
    public BaseActivity(int layoutId){
        LAYOUT_ID = layoutId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logs.log("Super onCreate LAYOUT_ID: ", LAYOUT_ID);
        try {


            setContentView(LAYOUT_ID);
            sharedpreferences = getSharedPreferences(SharedPreferencesConstants.SHARED_CONTENT.value, MODE_PRIVATE);

            initComponent();
            initEvent();
        }catch (Exception e){
            Logs.log("ERROR : ", e);
            e.printStackTrace();
            throw e;
        }
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
