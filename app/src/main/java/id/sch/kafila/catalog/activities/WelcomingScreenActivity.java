package id.sch.kafila.catalog.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import java.io.Serializable;

import id.sch.kafila.catalog.constants.Extras;
import id.sch.kafila.catalog.util.Logs;
import id.sch.kafila.catalog.util.Navigate;
import id.sch.kafila.catalog.R;
import lombok.val;

public class WelcomingScreenActivity extends BaseActivity {


    public WelcomingScreenActivity() {
        super(R.layout.activity_splash_screen);
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkIntentExras();
        goToHomePage();
    }


    private void checkIntentExras() {
        Intent intent = getIntent();
        //if exist
        Serializable exitExtras = intent.getSerializableExtra(Extras.EXIT_APP_KEY.value);
        Logs.log("exitExtras: ", exitExtras);
        if (null != exitExtras && exitExtras.equals(Extras.EXIT_APP_VALUE)) {
            Intent mainActivity = new Intent(Intent.ACTION_MAIN);
            mainActivity.addCategory(Intent.CATEGORY_HOME);
            mainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mainActivity);
            finish();
            int id = android.os.Process.myPid();
            android.os.Process.killProcess(id);
        }
    }

    @Override
    protected void initComponent() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onBackPressed() {
        finishActivity(1);
        System.exit(1);
    }

    private void goToHomePage() {
        new GoHome().execute(this);
    }


    private class GoHome extends AsyncTask<Context, String, String> {

        @Override
        protected String doInBackground(Context... contexts) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Navigate.navigate(contexts[0], HomeActivity.class);
            return null;
        }
    }
}
