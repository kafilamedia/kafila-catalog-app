package id.sch.kafila.catalog.activities.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;

import java.util.ArrayList;

import id.sch.kafila.catalog.R;
import id.sch.kafila.catalog.components.adapters.CustomExpandableListAdapter;
import id.sch.kafila.catalog.models.ListChildInfo;
import id.sch.kafila.catalog.models.ListGroupInfo;

public class ProgramPengembanganFragment extends BaseFragment {

    private ExpandableListView listViewUmum;

    private ArrayList<ListGroupInfo> listContents = new ArrayList<>();
    private ArrayList<ListChildInfo> extrakulikuler = new ArrayList<>();
    private ArrayList<ListChildInfo> kegiatan = new ArrayList<>();

    private View view;

    public ProgramPengembanganFragment(){  }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_program_pengembangan, container, false);
        listViewUmum = view.findViewById(R.id.list_program_pengembangan);
        
        populateListViews();
//        listViewUmum.getLayoutParams().width = Dimension.getScreenHeight(view.getContext());
//        listViewUmum.requestLayout();
        return view;
    }

    private void populateListViews() {
        populateListByArray(extrakulikuler, contentsEkstakulikuler);
        populateListByArray(kegiatan, contentsKegiatan);

        listContents.add(ListGroupInfo.builder().name("Extrakulikuler").childInfos(extrakulikuler).build());
        listContents.add(ListGroupInfo.builder().name("Kegiatan").childInfos(kegiatan).build());
        setListViewAdapters(view.getContext(), listViewUmum, listContents );

    }



    private static void setListViewAdapters(Context context, ExpandableListView listView, ArrayList<ListGroupInfo> list){
        CustomExpandableListAdapter adapter = new CustomExpandableListAdapter(context, list, listView);
        listView.setAdapter(adapter);
    }

    private static <T> void populateListByArray(ArrayList<ListChildInfo> list, T[] array){
        for (int i = 0; i<array.length;i++){
            String childName =  String.valueOf(array[i]);
            list.add(ListChildInfo.builder().number(i+1).name(childName).build());
        }
    }

    static String[] contentsEkstakulikuler = new String[]{
            "Bela diri Thifan, Kepanduan/Pramuka",
            "Panahan", "Futsal", "Renang", "KINEMATIKA (Komunitas Studi Sains dan Matematika)",
            "IT Club (programming dan desain grafis)",
            "Lain-lain"
    };
    static String[] contentsKegiatan = new String[]{
            "Muhadharah (pidato bahasa Arab/Inggris)", "Outbound", "Studi Ekskursi",
            "PDPU (Praktik Dakwah dan Pengembangan Ummat)",
            "LDKS (Latihan Dasar Kepemimpinan Santri)", "Outing Class", "Lain-lain"
    };

}

