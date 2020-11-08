package id.sch.kafila.catalog.service;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;

import id.sch.kafila.catalog.R;
import id.sch.kafila.catalog.util.ThreadUtil;

public class ImageViewWithURL {

    private final String url;
    private final ImageView imageView;

    public ImageViewWithURL(ImageView imageView, String url){
        this.imageView = imageView;
        this.url = url;
    }

    public void populate() {

        imageView.setImageResource(android.R.drawable.ic_menu_camera);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            try {
//                Resources res = imageView.getResources();
//
//                imageView.setImageDrawable(res.getDrawable(android.R.drawable.ic_menu_camera));
//            }catch (Exception e){ }
        }
        // show The Image in a ImageView
        ThreadUtil.runAndStart(()->{
            new DownloadImageTask(imageView)
                    .execute(url);
        });
    }



    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            if(null!=result) {
                bmImage.setImageBitmap(result);
            }else{
                bmImage.setImageResource(+android.R.drawable.ic_menu_camera);
            }
        }
    }
}
