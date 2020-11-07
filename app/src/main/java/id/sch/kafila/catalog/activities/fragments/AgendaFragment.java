package id.sch.kafila.catalog.activities.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import id.sch.kafila.catalog.R;
import id.sch.kafila.catalog.components.adapters.CustomExpandableListAdapter;
import id.sch.kafila.catalog.contents.Dimension;
import id.sch.kafila.catalog.models.ListChildInfo;
import id.sch.kafila.catalog.models.ListGroupInfo;

public class AgendaFragment extends BaseFragment {

    private View view;

    public AgendaFragment(){  }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_agenda, container, false);
        
        populateListViews();
        adjustComponent();
        return view;
    }

    private void adjustComponent() {
    }
    private void populateListViews() {

    }



}

