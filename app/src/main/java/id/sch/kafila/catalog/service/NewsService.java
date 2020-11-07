package id.sch.kafila.catalog.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import id.sch.kafila.catalog.models.NewsPost;
import id.sch.kafila.catalog.models.Post;
import id.sch.kafila.catalog.models.PostResponse;
import id.sch.kafila.catalog.util.Logs;
import id.sch.kafila.catalog.util.MapUtil;

public class NewsService {

    private static NewsService instance = null;
    private ObjectMapper objectMapper = new ObjectMapper();
    final RestTemplate restTemplate = new RestTemplate();
    private NewsService(){
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

        restTemplate.setErrorHandler(errorHandler());
    }


    private ResponseErrorHandler errorHandler() {
        return new CustomErrorHandler();
    }

    public static  NewsService instance(){
        if(instance == null){
            instance = new NewsService();
        }
        return instance;
    }

    public PostResponse getAgenda()  {
        Logs.log("Call get agenda");
        PostResponse response = callGetAgenda();

        if (response.getPosts() instanceof List) {
            List rawPosts = (List) response.getPosts();
            List<Post> posts = (MapUtil.convertMapList(rawPosts, Post.class));
            response.setAgendas(posts);

        }
        return response;
    }

    public PostResponse getNews(int page) {
        PostResponse response = callGetNews(page);
        if (response.getPosts() instanceof Map) {
            NewsPost newsPost = MapUtil.mapToObject((Map) response.getPosts(), NewsPost.class);
            response.setNewsPost(newsPost);
        }
        return response;
    }


   private HttpEntity<String> httpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setConnection("close");
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        headers.add("Accept-Encoding", "identity");
        headers.add("Accept", "application/json");
        headers.add("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<String>( headers);
        return entity;

    }

    private PostResponse callGetAgenda()   {
        String endPoint = "http://kafila.sch.id/index.php/api/homepage/agenda";
        System.out.println("callGetAgenda to: "+endPoint);
        try {
            ResponseEntity<PostResponse> response = restTemplate.exchange(endPoint, HttpMethod.GET, httpEntity(),
                    PostResponse.class);
            return   response.getBody();
        }catch ( Exception ex){
            Logs.log("ERROR get agenda: ", ex);
            throw ex;
        }
    }

    public PostResponse callGetNews(int page) {
        String endPoint = "http://kafila.sch.id/index.php/api/homepage/news?page=" + page;

        try{
//        String endPoint = "http://192.168.0.103/kafila/get_news.json";
        ResponseEntity<PostResponse> response = restTemplate.exchange(endPoint, HttpMethod.GET, httpEntity(),
                PostResponse.class);
        return response.getBody();
    }catch (Exception ex){
        Logs.log("ERROR get agenda: ", ex);
        throw ex;
    }
    }


}
