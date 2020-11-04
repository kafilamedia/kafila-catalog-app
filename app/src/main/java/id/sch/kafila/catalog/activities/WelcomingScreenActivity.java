package id.sch.kafila.catalog.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import id.sch.kafila.catalog.util.LoggingUtil;
import id.sch.kafila.catalog.util.Navigate;
import id.sch.kafila.catalog.R;

public class WelcomingScreenActivity extends BaseActivity {


    public WelcomingScreenActivity(){
        super(R.layout.activity_splash_screen);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goToHomePage();
    }

    @Override
    protected void initComponent() {

    }

    @Override
    protected void initEvent() {

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
