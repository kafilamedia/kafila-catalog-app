package id.sch.kafila.catalog.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
    private LoadingDialog loadingDialog;
    private Button buttonLoadAgenda;
    LinearLayout agendaListLayout;

    public AgendaFragment(){  }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Logs.log("layout.fragment_agenda on create view");
        view = inflater.inflate(R.layout.fragment_agenda, container, false);

        agendaListLayout = view.findViewById(R.id.agenda_list);
        buttonLoadAgenda = view.findViewById(R.id.agenda_btn_load_agenda);
        buttonLoadAgenda.setOnClickListener(loadAgendaListener());
        return view;
    }

    private View.OnClickListener loadAgendaListener() {
        buttonLoadAgenda.setText("Loading");
        Logs.log("loadAgendaListener");
        return new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                getAgenda();
            }
        };
    }

    private void getAgenda() {
        Logs.log("LOAD AGENDA");
        loadingDialog = LoadingDialog.start(getActivity());
        ThreadUtil.runAndStart(new Runnable() {
            @Override
            public void run() {

                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        PostResponse response = NewsService.instance().getAgenda();
                        handleGetAgenda(response);
                        loadingDialog.dismiss();
                    }
                });
            }
        });
    }

    private void handleGetAgenda(PostResponse response){
        List<Post> agendas = response.getAgendas();
        agendaListLayout.removeAllViews();
        for (Post post: agendas) {
            NewsItem title = new NewsItem(getActivity(),post);
            agendaListLayout.addView(title);
        }
        buttonLoadAgenda.setText("Load Agenda");
    }




}

