package id.sch.kafila.catalog.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import id.sch.kafila.catalog.components.MenuButton;
import id.sch.kafila.catalog.constants.SharedPreferencesConstants;
import id.sch.kafila.catalog.contents.Content;
import id.sch.kafila.catalog.contents.Dimension;
import id.sch.kafila.catalog.contents.data.ContentData;
import id.sch.kafila.catalog.util.AlertUtil;
import id.sch.kafila.catalog.util.Logs;
import id.sch.kafila.catalog.util.Navigate;
import id.sch.kafila.catalog.R;

import static android.view.View.*;

public class HomeActivity extends BaseActivity {

    private MenuButton exitButton;
    private MenuButton navigateVisiMisi;
    private MenuButton navigateProgramPendidikan;
    private MenuButton navigateProgramPengembangan;
    private MenuButton navigateNilaiUnggul;
    private MenuButton navigateSebaranAlumni;
    private MenuButton navigateFasilitas;
    private MenuButton navigateBiayaPendidikan;
    private MenuButton navigateSyaratPendaftaran;

    private BottomNavigationView bottomNavigationView;

    private LinearLayout prefaceContent;

    public HomeActivity(){
        super(R.layout.activity_home);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void initComponent() {
        exitButton = findViewById(R.id.btn_home_exit);
        navigateVisiMisi = findViewById(R.id.home_btn_visi_misi);
        navigateProgramPendidikan = findViewById(R.id.home_btn_program_pendidikan);
        navigateProgramPengembangan = findViewById(R.id.home_btn_program_pengembangan);
        navigateNilaiUnggul = findViewById(R.id.home_btn_nilai_unggul);
        navigateSebaranAlumni= findViewById(R.id.home_btn_sebaran_alumni);
        navigateFasilitas= findViewById(R.id.home_btn_fasilitas);
        navigateBiayaPendidikan= findViewById(R.id.home_btn_biaya_pendidikan);
        prefaceContent= findViewById(R.id.preface_content);
        navigateSyaratPendaftaran= findViewById(R.id.home_btn_syarat_pendaftaran);
        bottomNavigationView= findViewById(R.id.bottom_navigation);


        adjustSize();
    }

    private void adjustSize() {

    }

    @Override
    protected void initEvent(){
        exitButton.setOnClickListener(exit());
        navigateVisiMisi.setOnClickListener(gotoMenu(R.layout.fragment_visi_misi));
        navigateProgramPendidikan.setOnClickListener(gotoMenu(R.layout.fragment_program_pendidikan));
        navigateProgramPengembangan.setOnClickListener(gotoMenu(R.layout.fragment_program_pengembangan));
        navigateNilaiUnggul.setOnClickListener(gotoMenu(R.layout.fragment_nilai_unggul));
        navigateSebaranAlumni.setOnClickListener(gotoMenu(R.layout.fragment_sebaran_alumni));
        navigateProgramPengembangan.setOnClickListener(gotoMenu(R.layout.fragment_program_pengembangan));
        navigateFasilitas.setOnClickListener(gotoMenu(R.layout.fragment_fasilitas));
        navigateBiayaPendidikan.setOnClickListener(gotoMenu(R.layout.fragment_biaya_pendidikan));
        navigateSyaratPendaftaran.setOnClickListener(gotoMenu(R.layout.fragment_syarat_pendaftaran));
    }
    private OnClickListener gotoMenu(final int fragmentId) {
        final Context context = this;
        return new OnClickListener(){
            @Override
            public void onClick(View v) {
                Logs.log("gotoMenu fragmentId: ", fragmentId);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(SharedPreferencesConstants.KEY_CONTENT, String.valueOf(fragmentId));

                editor.commit();
                Navigate.navigate(context ,CommonContent.class);
            }
        };
    }
    private OnClickListener exit() {
        final Context ctx = this;
        return new OnClickListener(){

            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener callback = new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exitApplication();
                    }
                };
                AlertUtil.confirm(ctx, "Exit?", callback);

            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void exitApplication(){
        android.os.Process.killProcess(android.os.Process.myPid());
        try {
            finishAffinity();
        }catch (Exception e){ }
        System.exit(1);
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
