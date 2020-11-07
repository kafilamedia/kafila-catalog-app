package id.sch.kafila.catalog.service;

import android.content.SharedPreferences;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import id.sch.kafila.catalog.models.Post;
import id.sch.kafila.catalog.models.PostResponse;
import id.sch.kafila.catalog.util.Logs;
import id.sch.kafila.catalog.util.MapUtil;

public class SharedPreferenceUtil {

    private static final String SHARED_AGENDA = "shared_agenda";
    private static final String SHARED_NEWS = "shared_news";
    private static final String IS_EXIST_SHARED_AGENDA = "is_shared_agenda_exist";
    private static final String IS_EXIST_SHARED_NEWS = "is_shared_news_exist";
    private static final String EXIST = "EXIST";
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void storeAgendaData(SharedPreferences sharedPreferences, PostResponse agendaData){
        try {
            putString(sharedPreferences, SHARED_AGENDA, objectMapper.writeValueAsString(agendaData));
            putString(sharedPreferences, IS_EXIST_SHARED_AGENDA, EXIST);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public static boolean isAgendaExist(SharedPreferences sharedPreferences){
        return getValue(sharedPreferences, IS_EXIST_SHARED_AGENDA).equals(EXIST);
    }

    public static PostResponse getAgendaData(SharedPreferences sharedPreferences){
        String rawValue = getValue(sharedPreferences, SHARED_AGENDA);

        if("".endsWith(rawValue)){
            return null;
        }
        try{
            PostResponse response = objectMapper.readValue(rawValue, PostResponse.class);
            if (response.getPosts() instanceof List) {
                List rawPosts = (List) response.getPosts();
                List<Post> posts = (MapUtil.convertMapList(rawPosts, Post.class));
                response.setAgendas(posts);

            }
            return response;
        }catch (Exception e){
            Logs.log("ERROR get agenda: ", e);
        }

        return null;
    }

    private static String getValue(SharedPreferences sharedPreferences, String key){
        return sharedPreferences.getString(key, "");
    }

    private  static void putString(SharedPreferences sharedPreferences, String key, String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(key, value);
        editor.commit();

    }
}
