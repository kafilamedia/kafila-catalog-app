package id.sch.kafila.catalog.components;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import id.sch.kafila.catalog.R;
import id.sch.kafila.catalog.models.Post;
import id.sch.kafila.catalog.service.ImageViewWithURL;
import id.sch.kafila.catalog.util.Logs;
import id.sch.kafila.catalog.util.Navigate;

public class NewsItem extends LinearLayout {
    private ImageView imageThumbnail;
    private TextView newsTitle;
    private TextView newsDate;
    private ImageView buttonNewsLink;
    private Post post;

    private AsyncTask downloadImageTask;
    final boolean loadImage;

    public NewsItem(Context context, @Nullable AttributeSet attrs, boolean loadImage) {
        super(context, attrs);
        this.loadImage = loadImage;
        init(context, attrs);
    }

    public NewsItem(Context context, Post post, boolean loadImage) {
        super(context);
        this.loadImage = loadImage;
        init(context, null);
        this.post = post;
        populateContent(post);
    }

    public AsyncTask getDownloadImageTask() {
        return downloadImageTask;
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
        if(isLoadImage())
        {
            loadImage();
        }
        setTitle(post.getTitle());
        setNewsDate(post.getDate());
    }

    public void loadImage() {
        if(null == post || null == post.getImages()){
            return;
        }

        String url = post.getImages().getThumbnail();
        Logs.log("START load image:", url);
        ImageViewWithURL imageViewContents = new ImageViewWithURL(imageThumbnail, url);
        downloadImageTask = imageViewContents.populate();
        Logs.log("END load image:", url);
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
        buttonNewsLink.setImageResource( (android.R.drawable.ic_menu_info_details));


    }

    private void initEvents() {
        buttonNewsLink.setOnClickListener(showPopupMenu());
        imageThumbnail.setImageResource(android.R.drawable.ic_menu_camera);
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
        Navigate.openLink(post.newsLink(), getContext());
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
                        shareLink();
                        break;

                }

                return false;
            }
        };
    }

    private void shareLink() {
        Navigate.shareText(this.getContext(), post.getTitle()+" kunjungi link:"+ post.newsLink());
    }

    public boolean isLoadImage() {
        return loadImage;
    }
}
