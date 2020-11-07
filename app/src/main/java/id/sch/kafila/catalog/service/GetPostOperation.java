package id.sch.kafila.catalog.service;

import android.os.AsyncTask;

import id.sch.kafila.catalog.activities.fragments.PostContentPage;
import id.sch.kafila.catalog.models.PostResponse;

public class GetPostOperation extends AsyncTask<String, Void, PostResponse> {
    final PostContentPage parent;
    private  Exception getPostError;

    public GetPostOperation(PostContentPage parent){
        this.parent = parent;
    }
    @Override
    protected PostResponse doInBackground(String... strings) {
        try {
            return parent.getPost();
        }catch (Exception e){
            getPostError = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(PostResponse postResponse) {
        parent.handleGetPost(postResponse, getPostError);
    }
}
