package id.sch.kafila.catalog.activities.fragments.post;

import id.sch.kafila.catalog.models.PostResponse;

public interface PostContentPage {
    PostResponse getPost(Object...params);

    void handleGetPost(PostResponse postResponse, Exception getPostError);
}
