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

public class VisiMisiFragment extends BaseFragment {

    private ExpandableListView listViewUmum;

    private ArrayList<ListGroupInfo> listContents = new ArrayList<>();
    private ArrayList<ListChildInfo> listVisi = new ArrayList<>();
    private ArrayList<ListChildInfo> listMisi = new ArrayList<>();

    private View view;

    public VisiMisiFragment(){  }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_visi_misi, container, false);
        listViewUmum = view.findViewById(R.id.list_visi_misi);
        
        populateListViews();
        adjustComponent();
        return view;
    }

    private void adjustComponent() {
        listViewUmum.getLayoutParams().height = 400;
        listViewUmum.requestLayout();
    }
    private void populateListViews() {
        populateListByArray(listMisi, contentMisi);
        populateListByArray(listVisi, contentVisi);

        listContents.add(ListGroupInfo.builder().name("Visi").childInfos(listVisi).build());
        listContents.add(ListGroupInfo.builder().name("Misi").childInfos(listMisi).build());
        setListViewAdapters(view.getContext(), listViewUmum, listContents );

    }



    private static void setListViewAdapters(Context context, ExpandableListView listView, ArrayList<ListGroupInfo> list){
        CustomExpandableListAdapter adapter = new CustomExpandableListAdapter(context, list, listView, false);
        listView.setAdapter(adapter);
    }

    private static <T> void populateListByArray(ArrayList<ListChildInfo> list, T[] array){
        for (int i = 0; i<array.length;i++){
            String childName =  String.valueOf(array[i]);
            list.add(ListChildInfo.builder().number(i+1).name(childName).build());
        }
    }

    static String[] contentVisi = new String[]{
            "Menjadi lembaga pendidikan Islam internasional yang unggul, maju dan terpandang"
    };
    static String[] contentMisi = new String[]{
            "Mencetak generasi ahlussunnah wal jama’ah yang berjiwa wirausaha dan berwawasan global"
    };

}

