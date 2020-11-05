package id.sch.kafila.catalog.activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import id.sch.kafila.catalog.constants.SharedPreferencesConstants;
import id.sch.kafila.catalog.contents.Content;
import id.sch.kafila.catalog.contents.data.ContentData;
import id.sch.kafila.catalog.util.Logs;
import id.sch.kafila.catalog.util.Navigate;
import id.sch.kafila.catalog.R;

import static android.view.View.*;

public class HomeActivity extends BaseActivity {

    private Button exitButton;
    private Button navigateVisiMisi;
    private Button navigateProgramPendidikan;
    private Button navigateProgramPengembangan;
    private Button navigateNilaiUnggul;
    private Button navigateSebaranAlumni;
    private Button navigateFasilitas;
    private Button navigateBiayaPendidikan;
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
    }
    @Override
    protected void initEvent(){
        exitButton.setOnClickListener(exit());
        navigateVisiMisi.setOnClickListener(gotoMenu(R.layout.fragment_visi_misi));
        navigateProgramPendidikan.setOnClickListener(gotoMenu(R.layout.fragment_program_pendidikan));
        navigateProgramPengembangan.setOnClickListener(gotoMenu(R.layout.fragment_program_pengembangan));
        navigateNilaiUnggul.setOnClickListener(gotoMenu(R.layout.fragment_visi_misi));
        navigateSebaranAlumni.setOnClickListener(gotoMenu(R.layout.fragment_visi_misi));
        navigateProgramPengembangan.setOnClickListener(gotoMenu(R.layout.fragment_visi_misi));
        navigateFasilitas.setOnClickListener(gotoMenu(R.layout.fragment_visi_misi));
        navigateBiayaPendidikan.setOnClickListener(gotoMenu(R.layout.fragment_visi_misi));
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
        return new OnClickListener(){

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
                try {
                    finishAffinity();
                }catch (Exception e){ }
                System.exit(1);
            }
        };
    }
    @Override
    public void onBackPressed() {
        return;
    }
}
