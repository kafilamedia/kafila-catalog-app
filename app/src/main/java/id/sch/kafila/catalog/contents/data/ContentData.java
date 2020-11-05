package id.sch.kafila.catalog.contents.data;

import android.graphics.Color;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.sch.kafila.catalog.contents.Content;
import id.sch.kafila.catalog.contents.ContentType;
import id.sch.kafila.catalog.contents.Dimension;
import id.sch.kafila.catalog.util.CollectionUtil;

public class ContentData {
    public static final String VISI_MISI = "visi_misi";

    private static final Map<String, Content> REPOSITORY = new HashMap<String, Content>(){
        {
            put(VISI_MISI, Content.builder().textColor(Color.WHITE)
                .body("Visi Misi").contentType(ContentType.TITLE)//.dimension(new Dimension(500,0))
                    .children(new HashMap<Integer, List<Content>>()
                    {
                        {
                            put(1, CollectionUtil.arrayToList(
                                    Content.builder().body("Content Visi").textColor(Color.WHITE)// .dimension(new Dimension(300,100))
                                            .build(),
                                    Content.builder().body("Content Misi").textColor(Color.WHITE) //.dimension(new Dimension(300,100))
                                            .build()
                            ));
                        }
                    }).build()
            );
        }
    };

    public static Content getContent(String key){
        return REPOSITORY.get(key);
    }
}
