package id.sch.kafila.catalog.activities.fragments;

import id.sch.kafila.catalog.models.PostResponse;

public interface PostContentPage {
    public PostResponse getPost();

    public void handleGetPost(PostResponse postResponse, Exception getPostError);
}
