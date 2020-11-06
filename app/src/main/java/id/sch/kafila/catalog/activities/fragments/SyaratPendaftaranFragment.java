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

public class SyaratPendaftaranFragment extends BaseFragment {

    private ExpandableListView listViewUmum;

    private ArrayList<ListGroupInfo> requirement = new ArrayList<>();
    private ArrayList<ListChildInfo> listItemsUmum = new ArrayList<>();
    private ArrayList<ListChildInfo> listItemsMts = new ArrayList<>();
    private ArrayList<ListChildInfo> listItemsMa = new ArrayList<>();
    private ArrayList<ListChildInfo> listItemsDocument = new ArrayList<>();

    private View view;

    public SyaratPendaftaranFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_syarat_pendaftaran, container, false);
        listViewUmum = view.findViewById(R.id.list_syarat_umum);
        
        populateListViews();
//        listViewUmum.getLayoutParams().width = Dimension.getScreenHeight(view.getContext());
//        listViewUmum.requestLayout();
        return view;
    }

    private void populateListViews() {
        populateListByArray(listItemsUmum, umumContents);
        populateListByArray(listItemsMts, mtsContents);
        populateListByArray(listItemsMa, maContents);
        populateListByArray(listItemsDocument, documents);

        requirement.add(ListGroupInfo.builder().name("Umum").childInfos(listItemsUmum).build());
        requirement.add(ListGroupInfo.builder().name("Berkas dan Dokumen").childInfos(listItemsDocument).build());
        requirement.add(ListGroupInfo.builder().name("MTs").childInfos(listItemsMts).build());
        requirement.add(ListGroupInfo.builder().name("MA").childInfos(listItemsMa).build());

        setListViewAdapters(view.getContext(), listViewUmum, requirement );


    }

    private static void setListViewAdapters(Context context, ExpandableListView listView, ArrayList<ListGroupInfo> list){
        CustomExpandableListAdapter adapter = new CustomExpandableListAdapter(context, list);
        listView.setAdapter(adapter);
    }

    private static <T> void populateListByArray(ArrayList<ListChildInfo> list, T[] array){
        for (int i = 0; i<array.length;i++){
            String childName =  String.valueOf(array[i]);
            list.add(ListChildInfo.builder().number(i+1).name(childName).build());
        }
    }

    static String[] umumContents = new String[]{
        "Laki-Laki", "Mengisi pendaftaran online dengan melengkapi data dan berkas",
            "Membayar biaya pendaftaran sebesar Rp 303.000 (Jalur Bea-siswa Mts) atau Rp 606.000 (Jalur MTs Reguler dan MA)"
    };
    static String[] mtsContents = new String[]{
        "Usia maksimal per Juli 2021 adalah 14 tahun",
        "Nilai rapor minimal: 7 (tujuh), pada mata pelajaran USBN/UN",
        "Mampu membaca Al Quran dengan baik dan benar"
    };
    static String[] maContents = new String[]{
        "Usia maksimal per Juli 2021 adalah 17 tahun",
        "Nilai rapor minimal: 7 (tujuh), pada mata pelajaran USBN/UN",
        "Hafal Al-Qur’an minimal 5 juz dengan bacaan yang baik dan benar"
    };

    static String[] documents = new String[]{
            "Pas Foto (size: 4 x 6), dengan warna background MTs biru, MA merah",
            "Scan akta kelahiran, surat keterangan dokter, kartu keluarga",
            "Scan rapor halaman biodata dan nilai",
            "Scan KTP orangtua",
            "Surat keterangan kelakuan baik dari sekolah (Khusus Beasiswa MTs)",
            "Foto rumah, slip gaji, rekening listrik (Khusus Beasiswa MTs)",
            "Surat keterangan tidak mampu (SKTM) (Khusus Beasiswa MTs)",
    };

}

