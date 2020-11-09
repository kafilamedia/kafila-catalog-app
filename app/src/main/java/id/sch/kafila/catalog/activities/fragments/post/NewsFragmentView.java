package id.sch.kafila.catalog.activities.fragments.post;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.sch.kafila.catalog.R;
import id.sch.kafila.catalog.activities.fragments.BaseFragment;
import id.sch.kafila.catalog.util.Logs;

public class NewsFragmentView extends BaseFragment{

    private NewsPagerAdapter newsPagerAdapter;
    private ViewPager viewPager;
    private View view;
    private TabLayout tabLayout;
    private List<PostFragment> postFragments = new ArrayList<>();

    public NewsFragmentView(){
    }

    private void addChildFragments() {
        postFragments.clear();
        addChildFragment(new AgendaFragment());
        addChildFragment(new NewsFragment());
    }

    private void addChildFragment(PostFragment postFragment) {
        Logs.log("addChildFragment:", postFragment.getClass().getSimpleName());
        postFragments.add(postFragment);
    }

    @Override
    public View onCreateView(  LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Logs.log("NewsFragmentView onCreateView");
        this.view = inflater.inflate(R.layout.fragment_base_news, container, false);
        return view;
    }

    @Override
    public void onViewCreated(  View view,  Bundle savedInstanceState) {
        addChildFragments();
        newsPagerAdapter = new NewsPagerAdapter(getChildFragmentManager());
        viewPager = view.findViewById(R.id.news_pager);
        viewPager.setAdapter(newsPagerAdapter);

        tabLayout = view.findViewById(R.id.tab_news);
        tabLayout.setupWithViewPager(viewPager);
        initEvents();
    }

    protected  void initEvents(){

//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//
//                TextView text = (TextView) tab.getCustomView();
//
//                text.setTypeface(null, Typeface.BOLD);
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
////                TextView text = (TextView) tab.getCustomView();
////                text.setTypeface(null, Typeface.NORMAL);
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//
//        });


    }

    private class NewsPagerAdapter extends FragmentStatePagerAdapter {
        public NewsPagerAdapter(FragmentManager childFragmentManager) {
            super(childFragmentManager);
            Logs.log("childFragmentManager: ", childFragmentManager.getFragments().size());
        }

        @Override
        public Fragment getItem(int i) {
            PostFragment selectedFragment = postFragments.get(i);
            Logs.log("Tab Index: ", i, " ", selectedFragment.getTabName());
            return selectedFragment;
        }

        @Override
        public int getCount() {
            return postFragments.size();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return ((PostFragment) postFragments.get(position)).getTabName();
        }

    }
}
