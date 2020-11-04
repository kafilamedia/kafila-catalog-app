package id.sch.kafila.catalog.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import id.sch.kafila.catalog.util.Navigate;
import id.sch.kafila.catalog.R;

public class HomeActivity extends BaseActivity {

    private Button exitButton;
    public HomeActivity(){
        super(R.layout.activity_home);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void initComponent() {
        exitButton = findViewById(R.id.btn_home_exit);
    }
    @Override
    protected void initEvent(){
        exitButton.setOnClickListener(exit());
    }

    private View.OnClickListener exit() {
        return new View.OnClickListener(){

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
                try {
                    finishAffinity();
                }catch (Exception e){ }
                System.exit(1);
            }
        };
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
