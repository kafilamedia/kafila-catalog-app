package id.sch.kafila.catalog.activities.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import id.sch.kafila.catalog.R;
import id.sch.kafila.catalog.util.Logs;
import lombok.Data;
 
public class BaseFragment extends Fragment {

    protected Integer fragmentId = null;
    private static HashMap<Integer, Class> customFragments = initCustomFragments();
    public BaseFragment(){
    }

    public static BaseFragment newInstance(int fragmentId){
        return newInstance(fragmentId, null);
    }
    public static BaseFragment newInstance(int fragmentId, Class<?> _class) {
        if(_class == null && customFragments.get(fragmentId)!=null){
            _class = customFragments.get(fragmentId);
        }
        if(null == _class){
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
        return inflater.inflate(fragmentId == null ? R.layout.fragment_base:fragmentId, container, false);
    }

    private static HashMap<Integer, Class> initCustomFragments() {
        HashMap<Integer, Class> customFragments = new HashMap<Integer, Class>();
        customFragments.put(R.layout.fragment_syarat_pendaftaran, SyaratPendaftaranFragment.class );

        return customFragments;
    }

    public void setFragmentId(int fragmentId) {
        this.fragmentId = fragmentId;
    }
}

