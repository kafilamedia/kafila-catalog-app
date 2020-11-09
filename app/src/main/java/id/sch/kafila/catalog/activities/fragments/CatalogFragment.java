package id.sch.kafila.catalog.activities.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.sch.kafila.catalog.R;
import id.sch.kafila.catalog.components.MenuButton;
import id.sch.kafila.catalog.constants.SharedPreferencesConstants;
import id.sch.kafila.catalog.util.AlertUtil;
import id.sch.kafila.catalog.util.Logs;

public class CatalogFragment extends BaseFragment{
    protected SharedPreferences sharedpreferences;
    private MenuButton curriculumButton;
    private MenuButton navigateVisiMisi;
    private MenuButton navigateProgramPendidikan;
    private MenuButton navigateProgramPengembangan;
    private MenuButton navigateNilaiUnggul;
    private MenuButton navigateSebaranAlumni;
    private MenuButton navigateFasilitas;
    private MenuButton navigateBiayaPendidikan;
    private MenuButton navigateSyaratPendaftaran;
    private View view;

    public CatalogFragment(){
        Logs.log("Catalog Fragment Created");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_catalog, container, false);
        sharedpreferences = getActivity().getSharedPreferences(SharedPreferencesConstants.SHARED_CONTENT, Context.MODE_PRIVATE);
        initComponents();
        initEvents();
        Logs.log("Catalog Fragment onCreateView");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
    }

    private <T extends View> T findViewById(int id){
        return view.findViewById(id);
    }

    private void initComponents() {
        curriculumButton = findViewById(R.id.home_btn_kurikulum);
        navigateVisiMisi = findViewById(R.id.home_btn_visi_misi2);
        navigateProgramPendidikan = findViewById(R.id.home_btn_program_pendidikan2);
        navigateProgramPengembangan = findViewById(R.id.home_btn_program_pengembangan2);
        navigateNilaiUnggul = findViewById(R.id.home_btn_nilai_unggul2);
        navigateSebaranAlumni= findViewById(R.id.home_btn_sebaran_alumni2);
        navigateFasilitas= findViewById(R.id.home_btn_fasilitas2);
        navigateBiayaPendidikan= findViewById(R.id.home_btn_biaya_pendidikan2);
        navigateSyaratPendaftaran= findViewById(R.id.home_btn_syarat_pendaftaran2);
    }

    private void initEvents(){
        curriculumButton.setOnClickListener(gotoMenu(R.layout.fragment_kurikulum));
        navigateVisiMisi.setOnClickListener(gotoMenu(R.layout.fragment_visi_misi));
        navigateProgramPendidikan.setOnClickListener(gotoMenu(R.layout.fragment_program_pendidikan));
        navigateProgramPengembangan.setOnClickListener(gotoMenu(R.layout.fragment_program_pengembangan));
        navigateNilaiUnggul.setOnClickListener(gotoMenu(R.layout.fragment_nilai_unggul));
        navigateSebaranAlumni.setOnClickListener(gotoMenu(R.layout.fragment_sebaran_alumni));
        navigateProgramPengembangan.setOnClickListener(gotoMenu(R.layout.fragment_program_pengembangan));
        navigateFasilitas.setOnClickListener(gotoMenu(R.layout.fragment_fasilitas));
        navigateBiayaPendidikan.setOnClickListener(gotoMenu(R.layout.fragment_biaya_pendidikan));
        navigateSyaratPendaftaran.setOnClickListener(gotoMenu(R.layout.fragment_syarat_pendaftaran));

        Logs.log("Catalog Fragment initEvents");
    }
    private View.OnClickListener gotoMenu(final int fragmentId) {
        final Context context = this.getContext();
        return new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switchFragment(fragmentId);
//
//                Logs.log("gotoMenu fragmentId: ", fragmentId);
//                SharedPreferences.Editor editor = sharedpreferences.edit();
//                editor.putString(SharedPreferencesConstants.KEY_CONTENT, String.valueOf(fragmentId));
//
//                editor.commit();
//                Navigate.navigate(context ,CommonContent.class);
            }
        };
    }
    private View.OnClickListener exit() {
        final Context ctx = this.getContext();
        return new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener callback = new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        exitApplication();
                    }
                };
                AlertUtil.confirm(ctx, "Exit?", callback);

            }
        };
    }
    private void switchFragment(int fragmentId){
        Logs.log("switchFragment: ", fragmentId);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        BaseFragment fragment = BaseFragment.newInstance( fragmentId);

        fragmentTransaction.replace(R.id.home_common_content_container, fragment);
        fragmentTransaction.commit();

    }
}
