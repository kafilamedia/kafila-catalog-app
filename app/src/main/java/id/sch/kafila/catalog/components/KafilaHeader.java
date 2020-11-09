package id.sch.kafila.catalog.components;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import id.sch.kafila.catalog.R;
import id.sch.kafila.catalog.util.Logs;

public class KafilaHeader extends RelativeLayout {


    private TextView youtubeLink, facebookLink, instagramLink;

    public KafilaHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.component_kafila_header, this);
        initComponents();
        for (int i = 0; i< attrs.getAttributeCount(); i++){

            Logs.log(i," attr: ", attrs.getAttributeName(i),"=",attrs.getAttributeValue(i));
        }

    }

    private void initComponents() {
        youtubeLink = findViewById(R.id.header_yt);
        facebookLink = findViewById(R.id.header_fb);
        instagramLink = findViewById(R.id.header_ig);

        setLink(youtubeLink, "https://www.youtube.com/channel/UCJLplQGjTQ1dYQ17fIylXxQ");
        setLink(facebookLink, "https://www.facebook.com/KafilaSchool");
        setLink(instagramLink, "https://www.instagram.com/kafilajakarta/");
    }

    static void setLink(TextView tv, String link){
        tv.setText(Html.fromHtml("<a href=\""+link+"\">"+tv.getText()+"</a>"));
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        stripUnderlines(tv);
    }

    static void stripUnderlines(TextView textView) {
        Spannable s = new SpannableString(textView.getText());
        URLSpan[] spans = s.getSpans(0, s.length(), URLSpan.class);
        for (URLSpan span: spans) {
            int start = s.getSpanStart(span);
            int end = s.getSpanEnd(span);
            s.removeSpan(span);
            span = new URLSpanNoUnderline(span.getURL());
            s.setSpan(span, start, end, 0);
        }
        textView.setText(s);
    }
    static class URLSpanNoUnderline extends URLSpan {
        public URLSpanNoUnderline(String url) {
            super(url);
        }
        @Override public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
        }
    }

}
