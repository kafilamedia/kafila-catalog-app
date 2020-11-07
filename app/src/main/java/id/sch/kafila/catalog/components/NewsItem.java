package id.sch.kafila.catalog.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.annotation.StyleableRes;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import id.sch.kafila.catalog.R;
import id.sch.kafila.catalog.models.Post;
import id.sch.kafila.catalog.service.ImageViewContents;
import id.sch.kafila.catalog.util.Logs;
import id.sch.kafila.catalog.util.Navigate;

public class NewsItem extends LinearLayout {
    private ImageView imageThumbnail;
    private TextView newsTitle;
    private TextView newsDate;
    private  Button buttonNewsLink;
    private Post post;

    public NewsItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public NewsItem(Context context, Post post) {
        super(context);
        init(context, null);
        this.post = post;
        populateContent(post);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.component_news_item, this);
        initComponents();
        initEvents();
        if (null != attrs) {
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

    public void populateContent(Post post) {
        setImageUrl(post.getImages().getThumbnail());
        setTitle(post.getTitle());
        setNewsDate(post.getDate());
    }

    public void setImageUrl(String url) {
        ImageViewContents imageViewContents = new ImageViewContents(imageThumbnail, url);
        imageViewContents.populate();
    }

    public void setTitle(String title) {
        newsTitle.setText(title);
    }

    public void setNewsDate(String date) {
        newsDate.setText(date);
    }

    private void initComponents() {
        imageThumbnail = findViewById(R.id.newsThumbnail);
        newsTitle = findViewById(R.id.news_title);
        newsDate = findViewById(R.id.news_date);
        buttonNewsLink = findViewById(R.id.news_item_options);

    }

    private void initEvents() {
        buttonNewsLink.setOnClickListener(showPopupMenu());
    }

    private OnClickListener showPopupMenu() {

        return new OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getContext(), view);
                popupMenu.setOnMenuItemClickListener(popupMenuOnClick());
                popupMenu.inflate(R.menu.news_item_popup_menu);
                popupMenu.show();
            }

        };
    }

    private void openLink(){
        if(null == post){
            return;
        }
        String mainUrl = "https://kafila.sch.id/#!/read/";
        Navigate.openLink(mainUrl+post.getSlug(), getContext());
    }

    private PopupMenu.OnMenuItemClickListener popupMenuOnClick() {
        return new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_news_item_open_link:
                        openLink();
                        break;
                    case R.id.menu_news_item_share:
                        break;

                }

                return false;
            }
        };
    }
}
