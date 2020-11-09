package id.sch.kafila.catalog.activities.fragments.post;

import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.sch.kafila.catalog.R;
import id.sch.kafila.catalog.activities.fragments.BaseFragment;
import id.sch.kafila.catalog.models.PostResponse;
import id.sch.kafila.catalog.service.SharedPreferenceUtil;
import id.sch.kafila.catalog.util.Logs;

public abstract class PostFragment extends BaseFragment implements PostContentPage {
    protected View view;
    protected Button buttonLoadAgenda;
    protected ProgressBar rollingLoader;
    protected LinearLayout postListLayout, infoLayout;

    protected boolean loadedFromSharedPreference;

    private final List<AsyncTask> tasks = new ArrayList<>();

    public void addTask(AsyncTask task){
        tasks.add(task);
    }

    @Override
    public void onAttach(Context context) {
        tasks.clear();
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        Logs.log("FRAGMENTS ",getClass().getSimpleName(), " ON DETACH will clear task: ", tasks.size());
        for (AsyncTask task:tasks){
            try{
                if(null!=task)
                task.cancel(true);
            }catch (Exception e){

            }
        }
        Logs.log("cleared task: ", tasks.size());
        super.onDetach();
    }

    public void setLoadedFromSharedPreference(boolean loadedFromSharedPreference){
        this.loadedFromSharedPreference = loadedFromSharedPreference;
    }

    public boolean isLoadedFromSharedPreference(){
        return loadedFromSharedPreference;
    }

    protected abstract void initComponents();

    protected abstract void setDefaultAttributes();

    protected abstract PostResponse getPostFromSharedPreferences();

    protected void checkStoredAgendas() {
        if (SharedPreferenceUtil.isAgendaExist(sharedpreferences)) {
            setLoadedFromSharedPreference(true);
            startLoading();
            newsLayoutConstructionOperation(this).execute("");
        }
    }

    protected void startLoading() {
        try {
            rollingLoader.setVisibility(View.VISIBLE);
            buttonLoadAgenda.setVisibility(View.INVISIBLE);
            infoLayout.removeAllViews();
            postListLayout.removeAllViews();
        } catch (Exception e) {
            Logs.log("Error start loading: ", e);
        }
    }

    protected void stopLoading() {
        try {
            rollingLoader.setVisibility(View.INVISIBLE);
            buttonLoadAgenda.setVisibility(View.VISIBLE);
            buttonLoadAgenda.setText("Reload");
        } catch (Exception e) {
            Logs.log("Error stop loading: ", e);
        }
    }

    protected void populateInfo(String title, String message) {
        infoLayout.removeAllViews();
        ImageView imageView = new ImageView(view.getContext());
        imageView.setImageResource(R.drawable.exclamation);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 60));

        TextView titleTextView = new TextView(view.getContext());
        titleTextView.setText(title);
        titleTextView.setTextSize(17);
        TextView messageTextView = new TextView(view.getContext());
        messageTextView.setText(message);

        adjustLabelLayout(titleTextView);
        adjustLabelLayout(messageTextView);

        infoLayout.addView(imageView);
        infoLayout.addView(titleTextView);
        infoLayout.addView(messageTextView);
    }

    public static void adjustLabelLayout(TextView v) {
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        v.setGravity(Gravity.CENTER);
    }

    /**
     * used when loaded from shared preference
     * @param parent
     * @return
     */
    protected AsyncTask<String, Void, PostResponse> newsLayoutConstructionOperation(final PostFragment parent) {
        return new AsyncTask<String, Void, PostResponse>() {
            @Override
            protected PostResponse doInBackground(String... strings) {
                Logs.log("construct new doInBackground ", parent.getClass());
                PostResponse agendaData = parent.getPostFromSharedPreferences();
                if (null != agendaData) {
                    return agendaData;
                }

                return null;
            }


            @Override
            protected void onPostExecute(PostResponse postResponse) {
                stopLoading();
                if (null != postResponse) {
                    parent.handleGetPost(postResponse, null);
                    parent.setLoadedFromSharedPreference(false);
                } else {
                    parent.populateInfo("Tidak ada agenda untuk ditampilkan", "Cek koneksi internet Anda sebelum memuat agenda");
                }

            }
        };
    }
}
