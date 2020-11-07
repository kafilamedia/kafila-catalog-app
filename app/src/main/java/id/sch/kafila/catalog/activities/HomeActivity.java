package id.sch.kafila.catalog.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import id.sch.kafila.catalog.activities.fragments.BaseFragment;
import id.sch.kafila.catalog.components.MenuButton;
import id.sch.kafila.catalog.constants.SharedPreferencesConstants;
import id.sch.kafila.catalog.contents.Content;
import id.sch.kafila.catalog.contents.Dimension;
import id.sch.kafila.catalog.contents.data.ContentData;
import id.sch.kafila.catalog.util.AlertUtil;
import id.sch.kafila.catalog.util.Logs;
import id.sch.kafila.catalog.util.Navigate;
import id.sch.kafila.catalog.R;

import static android.view.View.*;

public class HomeActivity extends FragmentActivity {

    private int currentFragment;

    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logs.log("ONCREATE....");
        setContentView(R.layout.activity_home);
        initComponent();
        initEvent();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        switchFragment(R.layout.fragment_preface);
        super.onPostCreate(savedInstanceState);
    }

    protected void initComponent() {
        bottomNavigationView= findViewById(R.id.bottom_navigation);

    }

    protected void initEvent(){
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener() {
        return new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case R.id.navigation_preface:
                        switchFragment(R.layout.fragment_preface);
                        break;
                    case R.id.navigation_catalog:
                        switchFragment(R.layout.fragment_catalog);
                        break;

                    case R.id.navigation_agenda:
                        switchFragment(R.layout.fragment_agenda);
                        break;
                        case R.id.navigation_news:
                        break;
                }

                return false;

            }
        };
    }

    private void switchFragment(int fragmentId){
        Logs.log("switchFragment: ", fragmentId);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        BaseFragment fragment = BaseFragment.newInstance( fragmentId);

        fragmentTransaction.replace(R.id.home_common_content_container, fragment);
        fragmentTransaction.commit();

        currentFragment = fragmentId;

    }

    @Override
    public void onBackPressed() {

        //if in catalog
        if(currentFragment == R.layout.fragment_catalog){
            switchFragment(R.layout.fragment_catalog);
        }

    }
}
