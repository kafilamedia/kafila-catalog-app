package id.sch.kafila.catalog.activities.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import id.sch.kafila.catalog.R;
import id.sch.kafila.catalog.components.ImageLabel;
import id.sch.kafila.catalog.components.adapters.CustomExpandableListAdapter;
import id.sch.kafila.catalog.contents.Dimension;
import id.sch.kafila.catalog.models.ListChildInfo;
import id.sch.kafila.catalog.models.ListGroupInfo;

public class FasilitasFragment extends BaseFragment {



    private View view;
    private List<ImageLabel> imageLabels = new ArrayList<>();

    public FasilitasFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fasilitas, container, false);
        

//        listViewUmum.getLayoutParams().width = Dimension.getScreenHeight(view.getContext());
//        listViewUmum.requestLayout();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initComponents();
        adjustComponent();
        super.onViewCreated(view, savedInstanceState);
    }

    private void adjustComponent() {

        for (ImageLabel imageLabel :
                imageLabels) {
            imageLabel.setParentActivity(getActivity());
            imageLabel.initEvents();
        }
    }

    private void initComponents() {

       addImageLabel(R.id.fasilitas_1);
       addImageLabel(R.id.fasilitas_2);
       addImageLabel(R.id.fasilitas_3);
       addImageLabel(R.id.fasilitas_4);
       addImageLabel(R.id.fasilitas_5);
       addImageLabel(R.id.fasilitas_6);

    }

    private void addImageLabel(int id) {
        imageLabels.add(view.findViewById(id));
    }

    private static void setListViewAdapters(Context context, ExpandableListView listView, ArrayList<ListGroupInfo> list){
        CustomExpandableListAdapter adapter = new CustomExpandableListAdapter(context, list, listView);
        listView.setAdapter(adapter);
    }

}

