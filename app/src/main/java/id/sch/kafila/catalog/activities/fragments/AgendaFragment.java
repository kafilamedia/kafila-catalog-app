package id.sch.kafila.catalog.activities.fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import id.sch.kafila.catalog.R;
import id.sch.kafila.catalog.components.NewsItem;
import id.sch.kafila.catalog.service.GetPostOperation;
import id.sch.kafila.catalog.service.NewsService;
import id.sch.kafila.catalog.models.Post;
import id.sch.kafila.catalog.models.PostResponse;
import id.sch.kafila.catalog.util.Logs;

public class AgendaFragment extends BaseFragment implements PostContentPage {

    private View view;
    private Button buttonLoadAgenda;
    private ProgressBar rollingLoader;
    private TextView generalInfoLabel, getGeneralInfoTitle;
    LinearLayout agendaListLayout, fragmentLayout, infoLayout;
    String buttonLoadLabel = "Muat Agenda";

    public AgendaFragment(){  }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Logs.log("layout.fragment_agenda on create view");
        view = inflater.inflate(R.layout.fragment_agenda, container, false);

        initComponents();
        initDefaultAttributes();

        return view;
    }

    private void initDefaultAttributes() {
        agendaListLayout.removeAllViews();
        rollingLoader.setVisibility(View.INVISIBLE);
        buttonLoadAgenda.setOnClickListener(loadAgendaListener());
        buttonLoadAgenda.setText(buttonLoadLabel);

        populateInfo("Tidak ada agenda untuk ditampilkan", "Cek koneksi internet Anda sebelum memuat agenda" );
    }

    private void initComponents(){

        infoLayout = view.findViewById(R.id.agenda_info_wrapper);
        agendaListLayout = view.findViewById(R.id.agenda_list);
        buttonLoadAgenda = view.findViewById(R.id.agenda_btn_load_agenda);
        fragmentLayout = view.findViewById(R.id.fragment_agenda_layout);
        rollingLoader = view.findViewById(R.id.agenda_loader);
        generalInfoLabel = view.findViewById(R.id.agenda_info);
        getGeneralInfoTitle = view.findViewById(R.id.agenda_info_title);

    }

    private void populateInfo(String title, String message){
        infoLayout.removeAllViews();
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(R.drawable.exclamation);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 60));

        TextView titleTextView = new TextView(getContext());
        titleTextView.setText(title);
        titleTextView.setTextSize(17);
        TextView messageTextView = new TextView(getContext());
        messageTextView.setText(message);

        adjustLabelLayout(titleTextView);
        adjustLabelLayout(messageTextView);

        infoLayout.addView(imageView);
        infoLayout.addView(titleTextView);
        infoLayout.addView(messageTextView);
    }

    static void adjustLabelLayout(TextView v){
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        v.setGravity(Gravity.CENTER);
    }

    private View.OnClickListener loadAgendaListener() {
        return new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                getAgenda();
            }
        };
    }

    private void getAgenda() {
        startLoading();
        new GetPostOperation(this) .execute("");
    }

    private void startLoading(){
        rollingLoader.setVisibility(View.VISIBLE);
        buttonLoadAgenda.setVisibility(View.INVISIBLE);
        infoLayout.removeAllViews();
        agendaListLayout.removeAllViews();
    }

    private void stopLoading(){
        rollingLoader.setVisibility(View.INVISIBLE);
        buttonLoadAgenda.setVisibility(View.VISIBLE);
        buttonLoadAgenda.setText("Reload");
    }

    @Override
    public PostResponse getPost() {
        PostResponse response = NewsService.instance().getAgenda();
        return response;
    }

    @Override
    public void handleGetPost(PostResponse response, Exception e){
        stopLoading();
        if(null!= e){
            handleErrorGetAgenda( e);
            return;
        }
        List<Post> agendas = response.getAgendas();
        agendaListLayout.removeAllViews();
        for (Post post: agendas) {
            NewsItem title = new NewsItem(getActivity(),post);
            agendaListLayout.addView(title);
        }
        //fragmentLayout.removeView(buttonLoadAgenda);
    }

    private void handleErrorGetAgenda(Exception webServiceError) {
        populateInfo("Error", webServiceError.getMessage());
    }


}

