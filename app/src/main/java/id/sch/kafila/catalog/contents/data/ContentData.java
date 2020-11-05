package id.sch.kafila.catalog.contents.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.sch.kafila.catalog.contents.Content;
import id.sch.kafila.catalog.contents.Dimension;
import id.sch.kafila.catalog.util.CollectionUtil;

public class ContentData {
    public static final String VISI_MISI = "visi_misi";

    private static final Map<String, Content> REPOSITORY = new HashMap<String, Content>(){
        {
            put(VISI_MISI, Content.builder()
                .body("Visi Misi").dimension(new Dimension(100,20))
                    .children(new HashMap<Integer, List<Content>>()
                    {
                        {
                            put(1, CollectionUtil.arrayToList(
                                    Content.builder().body("Content Visi").dimension(new Dimension(100,20)).build()
                            ));
                            put(2, CollectionUtil.arrayToList(
                                    Content.builder().body("Content Misi").dimension(new Dimension(100,20)).build()
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
