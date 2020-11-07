package id.sch.kafila.catalog.activities.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import id.sch.kafila.catalog.R;
import id.sch.kafila.catalog.components.LoadingDialog;
import id.sch.kafila.catalog.components.NewsItem;
import id.sch.kafila.catalog.service.NewsService;
import id.sch.kafila.catalog.models.Post;
import id.sch.kafila.catalog.models.PostResponse;
import id.sch.kafila.catalog.util.Logs;
import id.sch.kafila.catalog.util.ThreadUtil;
import lombok.SneakyThrows;

public class AgendaFragment extends BaseFragment {

    private View view;
    private Button buttonLoadAgenda;
    private ProgressBar rollingLoader;
    private TextView generalInfoLabel;
    LinearLayout agendaListLayout, fragmentLayout;

    public AgendaFragment(){  }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Logs.log("layout.fragment_agenda on create view");
        view = inflater.inflate(R.layout.fragment_agenda, container, false);

        agendaListLayout = view.findViewById(R.id.agenda_list);
        buttonLoadAgenda = view.findViewById(R.id.agenda_btn_load_agenda);
        fragmentLayout = view.findViewById(R.id.fragment_agenda_layout);
        rollingLoader = view.findViewById(R.id.agenda_loader);
        generalInfoLabel = view.findViewById(R.id.agenda_info);

        //attributes
        rollingLoader.setVisibility(View.INVISIBLE);
        buttonLoadAgenda.setOnClickListener(new LoadAgendaListener(this));
        return view;
    }

    private void startLoading(){
        rollingLoader.setVisibility(View.VISIBLE);
        buttonLoadAgenda.setVisibility(View.INVISIBLE);
    }

    private void stopLoading(){
        rollingLoader.setVisibility(View.INVISIBLE);
        buttonLoadAgenda.setVisibility(View.VISIBLE);
    }

    private class LoadAgendaListener implements  View.OnClickListener{
        final AgendaFragment parent;
        public LoadAgendaListener(AgendaFragment parent){
            this.parent = parent;
        }
        @Override
        public void onClick(View v) {
            Logs.log("CLICK GET AGENDA");
            parent. buttonLoadAgenda.setText("Loading");
            parent. startLoading();
            new GetAgendaOperation(parent).execute("");
        }
    }

    private void handleGetAgenda(PostResponse response){
        List<Post> agendas = response.getAgendas();
        agendaListLayout.removeAllViews();
        for (Post post: agendas) {
            NewsItem title = new NewsItem(getActivity(),post);
            agendaListLayout.addView(title);
        }
        //fragmentLayout.removeView(buttonLoadAgenda);
    }


    private class GetAgendaOperation extends AsyncTask<String, Void, PostResponse> {

        final AgendaFragment parent;
        Exception webServiceError;
        public GetAgendaOperation(AgendaFragment parent){
            this.parent = parent;
        }

        @Override
        protected PostResponse doInBackground(String... strings) {
            try {
                PostResponse response = NewsService.instance().getAgenda();
                return response;
            }catch (Exception e){
                webServiceError = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(PostResponse postResponse) {
            parent.stopLoading();
            if(null==postResponse){
                parent.handleErrorGetAgenda(webServiceError);
                return;
            }
            parent.handleGetAgenda(postResponse);
            super.onPostExecute(postResponse);
        }
    }

    private void handleErrorGetAgenda(Exception webServiceError) {
        generalInfoLabel.setText(webServiceError.getMessage());
    }


}

