package id.sch.kafila.catalog.activities.fragments.post;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import id.sch.kafila.catalog.R;
import id.sch.kafila.catalog.components.NewsItem;
import id.sch.kafila.catalog.constants.SharedPreferencesConstants;
import id.sch.kafila.catalog.service.GetPostOperation;
import id.sch.kafila.catalog.service.NewsService;
import id.sch.kafila.catalog.models.Post;
import id.sch.kafila.catalog.models.PostResponse;
import id.sch.kafila.catalog.service.SharedPreferenceUtil;
import id.sch.kafila.catalog.util.Logs;

public class AgendaFragment extends PostFragment {

    private String buttonLoadLabel = "Muat Agenda";

    public AgendaFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Logs.log("layout.fragment_agenda on create view");
        view = inflater.inflate(R.layout.fragment_agenda, container, false);

        initComponents();
        setDefaultAttributes();

        return view;
    }

    @Override
    public String getTabName() {
        return "Agenda";
    }

    @Override
    protected void setDefaultAttributes() {
        postListLayout.removeAllViews();
        rollingLoader.setVisibility(View.INVISIBLE);
        buttonLoadAgenda.setOnClickListener(loadAgendaListener());
        buttonLoadAgenda.setText(buttonLoadLabel);
        checkStoredAgendas();

    }


    @Override
    protected void initComponents() {

        sharedpreferences = getActivity().getSharedPreferences(SharedPreferencesConstants.SHARED_CONTENT, Activity.MODE_PRIVATE);


        infoLayout = view.findViewById(R.id.agenda_info_wrapper);
        postListLayout = view.findViewById(R.id.agenda_list);
        buttonLoadAgenda = view.findViewById(R.id.agenda_btn_load_agenda);
        rollingLoader = view.findViewById(R.id.agenda_loader);
    }

    @Override
    protected   PostResponse getPostFromSharedPreferences(){
        return SharedPreferenceUtil.getAgendaData(sharedpreferences);
    }


    private View.OnClickListener loadAgendaListener() {
        return  (View v)-> {getAgenda();   };
    }


    private void getAgenda() {
        startLoading();
        new GetPostOperation(this).execute("");
    }

    @Override
    public PostResponse getPost(Object... params) {
        PostResponse response = NewsService.instance().getAgenda();
        return response;
    }

    @Override
    public void handleGetPost(PostResponse response, Exception e) {
        stopLoading();
        if (null != e) {
            handleErrorGetAgenda(e);
            return;
        } else if (null == response.getAgendas()) {
            handleErrorGetAgenda(new RuntimeException("Agenda Not Found"));
            return;
        }
        SharedPreferenceUtil.storeAgendaData(sharedpreferences, response);
        List<Post> agendas = response.getAgendas();
        postListLayout.removeAllViews();
        infoLayout.removeAllViews();
        for (Post post : agendas) {
            try {
                NewsItem newsItem = new NewsItem(getActivity(), post, isLoadedFromSharedPreference()==false);
                addTask(newsItem.getDownloadImageTask());
                postListLayout.addView(newsItem);
            } catch (Exception ex) {
                Logs.log("error create news item: ", ex);
            }
        }
        if (agendas.size() == 0) {
            handleErrorGetAgenda(new RuntimeException("Data is Empty"));
        }
        //fragmentLayout.removeView(buttonLoadAgenda);
    }

    private void handleErrorGetAgenda(Exception webServiceError) {
        populateInfo("Error Saat Memuat Agenda", webServiceError.getMessage());
    }

}

