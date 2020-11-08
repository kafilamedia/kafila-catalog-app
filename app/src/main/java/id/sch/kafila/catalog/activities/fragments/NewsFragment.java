package id.sch.kafila.catalog.activities.fragments;

import android.app.Activity;
import android.os.AsyncTask;
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
import id.sch.kafila.catalog.constants.SharedPreferencesConstants;
import id.sch.kafila.catalog.models.Post;
import id.sch.kafila.catalog.models.PostResponse;
import id.sch.kafila.catalog.service.GetPostOperation;
import id.sch.kafila.catalog.service.NewsService;
import id.sch.kafila.catalog.service.SharedPreferenceUtil;
import id.sch.kafila.catalog.util.Logs;

public class NewsFragment extends BaseFragment implements PostContentPage {

    private View view;
    private Button buttonLoadAgenda;
    private ProgressBar rollingLoader;
    private LinearLayout agendaListLayout, fragmentLayout, infoLayout;
    private String buttonLoadLabel = "Muat Berita";

    private PostResponse newsData;

    public NewsFragment(){  }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Logs.log("layout.fragment_news on create view");
        view = inflater.inflate(R.layout.fragment_news, container, false);

        initComponents();
        initDefaultAttributes();

        return view;
    }

    private void initDefaultAttributes() {
        agendaListLayout.removeAllViews();
        rollingLoader.setVisibility(View.INVISIBLE);
        buttonLoadAgenda.setOnClickListener(loadAgendaListener());
        buttonLoadAgenda.setText(buttonLoadLabel);
        checkStoredAgendas();

    }

    private void checkStoredAgendas() {
        if(SharedPreferenceUtil.isNewsExist(sharedpreferences)) {
            startLoading();
            newsLayoutConstructionOperation().execute("");
        }
    }


    private void initComponents(){

        sharedpreferences = getActivity().getSharedPreferences(SharedPreferencesConstants.SHARED_CONTENT, Activity.MODE_PRIVATE);


        infoLayout = view.findViewById(R.id.news_info_wrapper);
        agendaListLayout = view.findViewById(R.id.news_list);
        buttonLoadAgenda = view.findViewById(R.id.news_btn_load);
        fragmentLayout = view.findViewById(R.id.fragment_news_layout);
        rollingLoader = view.findViewById(R.id.news_loader);

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
        PostResponse response = NewsService.instance().getNews(getCurrentPage());
        return response;
    }

    private int getCurrentPage() {

        if(null!=newsData){
            return newsData.getCurrentPage();
        }
        return 1;
    }

    private int getTotalData(){
        if(null!=newsData){
            return newsData.getTotal();
        }
        return 0;
    }

    private int getDisplayedData(){
        if(null!=newsData){
            return newsData.getPerPage();
        }
        return 0;
    }

    @Override
    public void handleGetPost(PostResponse response, Exception e){
        stopLoading();
        if(null!= e){
            handleErrorGetAgenda( e);
            return;
        } else if(null == response.getNewsPost()){
            e = new RuntimeException("Post Not Found");
            handleErrorGetAgenda(e);
            return;

        }
        SharedPreferenceUtil.storeNewsData(sharedpreferences, response);
        newsData = response;
        Logs.log("newsData: ", newsData);
        List<Post> agendas = response.getNewsPost().getRemains();
        agendaListLayout.removeAllViews();
        infoLayout.removeAllViews();
        for (Post post: agendas) {
            try {
                NewsItem title = new NewsItem(getActivity(), post);
                agendaListLayout.addView(title);
            }catch (Exception ex){  }
        }
        //fragmentLayout.removeView(buttonLoadAgenda);
    }

    private void handleErrorGetAgenda(Exception webServiceError) {
        populateInfo("Error Saat Memuat Berita", webServiceError.getMessage());
    }

    private AsyncTask<String, Void, PostResponse> newsLayoutConstructionOperation() {
        final NewsFragment parent = this;
        return new AsyncTask<String, Void, PostResponse>() {
            @Override
            protected PostResponse doInBackground (String...strings){
                PostResponse agendaData = SharedPreferenceUtil.getNewsData(sharedpreferences);
                if(null!=agendaData){
                    return agendaData;
                }
                return null;
            }

            @Override
            protected void onPostExecute(PostResponse postResponse) {
                stopLoading();
                if(null!=postResponse) {
                    handleGetPost(postResponse, null);
                } else {
                    populateInfo("Tidak ada berita untuk ditampilkan", "Cek koneksi internet Anda sebelum memuat agenda" );
                }

            }
        };
    }


}

