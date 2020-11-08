package id.sch.kafila.catalog.activities.fragments;

import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import id.sch.kafila.catalog.R;
import id.sch.kafila.catalog.models.PostResponse;
import id.sch.kafila.catalog.service.SharedPreferenceUtil;
import id.sch.kafila.catalog.util.Logs;

public abstract class PostFragment extends BaseFragment implements PostContentPage {
    protected View view;
    protected Button buttonLoadAgenda;
    protected ProgressBar rollingLoader;
    protected LinearLayout postListLayout, infoLayout;

    protected abstract void initComponents();

    protected abstract void setDefaultAttributes();

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




    protected AsyncTask<String, Void, PostResponse> newsLayoutConstructionOperation(final PostFragment parent) {
        return new AsyncTask<String, Void, PostResponse>() {
            @Override
            protected PostResponse doInBackground(String... strings) {
                Logs.log("construct new doInBackground ", parent.getClass());
                PostResponse agendaData;
                if(parent instanceof AgendaFragment) {
                    agendaData=SharedPreferenceUtil.getAgendaData(sharedpreferences);
                } else if(parent instanceof  NewsFragment){
                    agendaData = SharedPreferenceUtil.getNewsData(sharedpreferences);
                } else{
                    agendaData = null;
                }
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
                } else {
                    parent.populateInfo("Tidak ada agenda untuk ditampilkan", "Cek koneksi internet Anda sebelum memuat agenda");
                }

            }
        };
    }
}
