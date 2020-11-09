package id.sch.kafila.catalog.activities.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import id.sch.kafila.catalog.R;
import id.sch.kafila.catalog.activities.fragments.post.AgendaFragment;
import id.sch.kafila.catalog.activities.fragments.post.NewsFragment;
import id.sch.kafila.catalog.activities.fragments.post.NewsFragmentView;
import id.sch.kafila.catalog.util.Logs;

public class BaseFragment extends Fragment {

    protected Integer fragmentId = null;
    protected SharedPreferences sharedpreferences;
    private static HashMap<Integer, Class> customFragments = initCustomFragments();

    public BaseFragment() {
    }

    @Override
    public void onStart() {
        Logs.log("Fragment on Start: ", getClass().getSimpleName());
        super.onStart();
    }

    @Override
    public void onStop() {
        Logs.log("Fragment on Start: ", getClass().getSimpleName());
        super.onStop();
    }

    public static BaseFragment newInstance(int fragmentId) {
        return newInstance(fragmentId, null);
    }

    public static BaseFragment newInstance(int fragmentId, Class<?> _class) {
        if (_class == null && customFragments.get(fragmentId) != null) {
            _class = customFragments.get(fragmentId);
        }
        if (null == _class) {
            _class = BaseFragment.class;
        }
        BaseFragment myFragment = null;
        try {
            myFragment = (BaseFragment) _class.newInstance();
            Bundle args = new Bundle();
            args.putInt("fragmentId", fragmentId);
            myFragment.setArguments(args);
            myFragment.setFragmentId(fragmentId);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }
        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Logs.log("BaseFragment fragmentId: ", fragmentId);
        return inflater.inflate(fragmentId == null ? R.layout.fragment_base : fragmentId, container, false);
    }

    private static HashMap<Integer, Class> initCustomFragments() {
        HashMap<Integer, Class> customFragments = new HashMap<Integer, Class>();
        customFragments.put(R.layout.fragment_syarat_pendaftaran, SyaratPendaftaranFragment.class);
        customFragments.put(R.layout.fragment_catalog, CatalogFragment.class);
        customFragments.put(R.layout.fragment_program_pengembangan, ProgramPengembanganFragment.class);
        customFragments.put(R.layout.fragment_agenda, AgendaFragment.class);
        customFragments.put(R.layout.fragment_news, NewsFragment.class);
        customFragments.put(R.layout.fragment_base_news, NewsFragmentView.class);
        customFragments.put(R.layout.fragment_fasilitas, FasilitasFragment.class);

        return customFragments;
    }

    public void setFragmentId(int fragmentId) {
        this.fragmentId = fragmentId;
    }
}

