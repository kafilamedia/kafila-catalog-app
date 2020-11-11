package id.sch.kafila.catalog.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.StrictMode;
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
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import id.sch.kafila.catalog.activities.fragments.BaseFragment;
import id.sch.kafila.catalog.components.MenuButton;
import id.sch.kafila.catalog.constants.Extras;
import id.sch.kafila.catalog.constants.SharedPreferencesConstants;
import id.sch.kafila.catalog.contents.Dimension;
import id.sch.kafila.catalog.util.AlertUtil;
import id.sch.kafila.catalog.util.Logs;
import id.sch.kafila.catalog.util.Navigate;
import id.sch.kafila.catalog.R;

import static android.view.View.*;

public class HomeActivity extends FragmentActivity {

    private int currentFragment;

    private BottomNavigationView bottomNavigationView;
    private TextView breadCumb;
    private boolean insideCatalogPage;

    private Map<Object, Bitmap> postBitmaps = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        startActivityForResult(getIntent(), TheApplication.REQUEST_CODE.value);
        StrictMode.setThreadPolicy(policy);
        Logs.log("ONCREATE....");
        setContentView(R.layout.activity_home);
        initComponent();
        initEvent();

    }

    public void clearPostBitmaps() {
        postBitmaps.clear();
    }


    public void addPostBitmap(Object postId, Bitmap bitmap) {
        postBitmaps.put(postId, bitmap);
    }

    public Bitmap getPostBitmap(Object postId) {
        return postBitmaps.get(postId);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        switchFragment(R.layout.fragment_preface);
        super.onPostCreate(savedInstanceState);
    }

    protected void initComponent() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        breadCumb = findViewById(R.id.home_breadcumb);
    }

    public void setBreadCumbText(String text) {
        breadCumb.setText(text);
        if (null != breadCumb && text != null) {
            breadCumb.setVisibility(VISIBLE);
            Logs.log("setBreadCumbText ", text);
        } else {
            breadCumb.setVisibility(GONE);
            Logs.log("NOT setBreadCumbText ", text);
        }

    }

    protected void initEvent() {
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener());
        setBreadCumbText(null);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener() {
        return new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case R.id.navigation_preface:
                        switchFragment(R.layout.fragment_preface);

                        break;
                    case R.id.navigation_catalog:
                        switchFragment(R.layout.fragment_catalog, "Menu");
                        break;

                    case R.id.navigation_agenda:
                        switchFragment(R.layout.fragment_base_news);
                        break;
                }

                setInsideCatalogPage(false);
                return false;
            }
        };
    }

    public void switchFragmentInCatalogPage(int fragmentId, String breadCumbLabel) {
        setInsideCatalogPage(true);
        switchFragment(fragmentId, breadCumbLabel);
    }

    public void switchFragment(int fragmentId) {
        switchFragment(fragmentId, null);
    }

    public void switchFragment(int fragmentId, String breadCumbLabel) {
        Logs.log("switchFragment: ", fragmentId);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().
                setCustomAnimations( //https://developer.android.com/training/basics/fragments/animate
                        R.anim.anim_slide_in,  // enter
                        R.anim.anim_fade_out,  // exit
                        R.anim.anim_fade_in,   // popEnter
                        R.anim.anim_slide_out  // popExit
                );
        /**
         *
         * R.anim.slide_in,  // enter
         R.anim.fade_out,  // exit
         R.anim.fade_in,   // popEnter
         R.anim.slide_out
         *
         */

        BaseFragment fragment = BaseFragment.newInstance(fragmentId, null, breadCumbLabel);

        fragmentTransaction.replace(R.id.home_common_content_container, fragment);
        fragmentTransaction.commit();

        currentFragment = fragmentId;
    }

    @Override
    public void onBackPressed() {

        //if in catalog
        if (isInsideCatalogPage()) {
            setInsideCatalogPage(false);
            bottomNavigationView.setSelectedItemId(R.id.navigation_catalog);

        } else if (currentFragment != R.layout.fragment_preface) {
            switchFragment(R.layout.fragment_preface);
            bottomNavigationView.setSelectedItemId(R.id.navigation_preface);
        } else if (currentFragment == R.layout.fragment_preface) {
            AlertUtil.confirm(this, "Exit Application?",this::exitApplication);
        }

    }


    private void exitApplication(DialogInterface dialog, int which) {
        Navigate.navigate(this, WelcomingScreenActivity.class, Extras.EXIT_APP_KEY.value, Extras.EXIT_APP_VALUE);
    }

    public void setInsideCatalogPage(boolean insideCatalogPage) {
        this.insideCatalogPage = insideCatalogPage;
    }

    public boolean isInsideCatalogPage() {
        return insideCatalogPage;
    }
}
