package id.sch.kafila.catalog.activities.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.sch.kafila.catalog.R;
import id.sch.kafila.catalog.util.Logs;

public class BaseFragment extends Fragment {

    private Integer fragmentId = null;
    public BaseFragment(){

    }

    public static BaseFragment newInstance(int fragmentId) {
        BaseFragment myFragment = new BaseFragment();

        Bundle args = new Bundle();
        args.putInt("fragmentId", fragmentId);
        myFragment.setArguments(args);
        myFragment.fragmentId = fragmentId;
        return myFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Logs.log("BaseFragment fragmentId: ", fragmentId);
        return inflater.inflate(fragmentId == null ? R.layout.fragment, container, false);
    }

}

