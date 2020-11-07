package id.sch.kafila.catalog.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.annotation.StyleableRes;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import id.sch.kafila.catalog.R;
import id.sch.kafila.catalog.models.Post;
import id.sch.kafila.catalog.service.ImageViewContents;
import id.sch.kafila.catalog.util.Logs;

public class NewsItem extends LinearLayout {
    private ImageView imageThumbnail;
    private TextView newsTitle;
    private TextView newsDate;
    public NewsItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    public NewsItem(Context context, Post post){
        super(context);
        init(context, null);
        populateContent(post);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.component_news_item, this);
        initComponents();
        if(null!=attrs) {
            int[] sets = {R.attr.imageLabelDrawableId, R.attr.imageLabelText};
            TypedArray typedArray = context.obtainStyledAttributes(attrs, sets);
            for (int i = 0; i < attrs.getAttributeCount(); i++) {
                String attributeName = attrs.getAttributeName(i);
                switch (attributeName) {
                    case "newsItemThumbnail":
                        int value = attrs.getAttributeResourceValue(i, R.drawable.ic_home_black_24dp);
                        imageThumbnail.setImageResource(value);
                        break;
                    case "newsItemTitle":
                        String labelText = attrs.getAttributeValue(i);
                        newsTitle.setText(labelText);
                        break;
                    case "newsItemDate":
                        labelText = attrs.getAttributeValue(i);
                        newsTitle.setText(labelText);
                        break;
                }
                Logs.log(i, " attr: ", attrs.getAttributeName(i), "=", attrs.getAttributeValue(i));
            }
            typedArray.recycle();
        }
    }

    public void populateContent(Post post){
        setImageUrl(post.getImages().getThumbnail());
        setTitle(post.getTitle());
        setNewsDate(post.getDate());
    }

    public void setImageUrl(String url){
        ImageViewContents imageViewContents = new ImageViewContents(imageThumbnail, url);
        imageViewContents.populate();
    }

    public void setTitle(String title){
        newsTitle.setText(title);
    }

    public void setNewsDate(String date){
        newsDate.setText(date);
    }

    private void initComponents() {
        imageThumbnail = (ImageView) findViewById(R.id.newsThumbnail);
        newsTitle = (TextView) findViewById(R.id.news_title);
        newsDate = (TextView) findViewById(R.id.news_date);

    }
}
