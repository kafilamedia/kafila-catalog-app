package id.sch.kafila.catalog.contents;

import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Content implements Serializable{

    private Object body;
    @Builder.Default
    private ContentType contentType = ContentType.TEXT;
    @Builder.Default
    private Dimension dimension = new Dimension();
    private View generatedView;
    @Builder.Default
    @Setter(value= AccessLevel.NONE)
    @Getter(value= AccessLevel.NONE)
    private Map<Integer, List<Content>> children = new HashMap<>();

    public boolean isWrappedByLayout(){
        return children.isEmpty() == false;
    }

    public Set<Integer> getChildrenRows(){
        return children.keySet();
    }

    public List<Content> getChildernAtRow(int row){
        return children.get(row);
    }

    public void addChild(int row, Content content){
        if(getChildernAtRow(row) == null){
            children.put(row, new ArrayList<Content>());
        }
        children.get(row).add(content);
    }
    
    public int getMaxHeightAtRow(int row){
        int height = 0;
        List<Content> contents = getChildernAtRow(row);
        for (Content content :
                contents) {
            if(content.getDimension().getHeight() > height){
                height = content.getDimension().getHeight();
            }


        }
        return height;
    }
    public int getMaxWidthtAtRow(int row){
        int width = 0;
        List<Content> contents = getChildernAtRow(row);
        for (Content content :
                contents) {
            if(content.getDimension().getWidth() > width){
                width = content.getDimension().getWidth();
            }


        }
        return width;
    }

}
