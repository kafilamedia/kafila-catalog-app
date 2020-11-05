package id.sch.kafila.catalog.activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import id.sch.kafila.catalog.constants.SharedPreferencesConstants;
import id.sch.kafila.catalog.contents.Content;
import id.sch.kafila.catalog.contents.data.ContentData;
import id.sch.kafila.catalog.util.Logs;
import id.sch.kafila.catalog.util.Navigate;
import id.sch.kafila.catalog.R;

import static android.view.View.*;

public class HomeActivity extends BaseActivity {

    private Button exitButton;
    private Button navigateVisiMisi;
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
        navigateVisiMisi = findViewById(R.id.home_btn_visi_misi);
    }
    @Override
    protected void initEvent(){
        exitButton.setOnClickListener(exit());
        navigateVisiMisi.setOnClickListener(gotoMenu(ContentData.VISI_MISI));
    }

    private OnClickListener gotoMenu(final String contentDataKey) {
        final Context context = this;
        return new OnClickListener(){
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(SharedPreferencesConstants.KEY_CONTENT, contentDataKey);

                editor.commit();
                Navigate.navigate(context ,CommonContent.class);
            }
        };

    }

    private OnClickListener exit() {
        return new OnClickListener(){

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
