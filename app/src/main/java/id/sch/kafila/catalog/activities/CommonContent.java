package id.sch.kafila.catalog.activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

import java.util.HashMap;

import id.sch.kafila.catalog.R;
import id.sch.kafila.catalog.activities.fragments.BaseFragment;
import id.sch.kafila.catalog.constants.SharedPreferencesConstants;
import id.sch.kafila.catalog.util.Logs;

public class CommonContent extends  FragmentActivity {
//
    SharedPreferences sharedpreferences;



    public CommonContent(){
        super();
        Logs.log("Constructor CommonContent...");
    }





    //    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
//        super.onCreate(savedInstanceState, persistentState);
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logs.log("ONCREATE....");
        setContentView(R.layout.activity_common_content2);
        initComponent();
        switchFragment();
    }

    protected void initComponent() {
        Logs.log("CommonContent...");
        switchFragment();
        buildComponents();
    }

    private void switchFragment(){
        sharedpreferences = getSharedPreferences(SharedPreferencesConstants.SHARED_CONTENT, MODE_PRIVATE);

        String fragmentId = sharedpreferences.getString(SharedPreferencesConstants.KEY_CONTENT, null);
        Logs.log("switchFragment: ", fragmentId);
        if(null == fragmentId){
            return;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        BaseFragment fragment = BaseFragment.newInstance(Integer.parseInt(fragmentId));

        fragmentTransaction.replace(R.id.common_content_container, fragment);
        fragmentTransaction.commit();

    }

    private void buildComponents() {

    }

    protected void initEvent() { }
}
