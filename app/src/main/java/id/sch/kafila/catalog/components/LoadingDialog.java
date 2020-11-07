package id.sch.kafila.catalog.components;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import id.sch.kafila.catalog.R;

public class LoadingDialog    extends Dialog {
        public LoadingDialog(  Context context) {
            super(context);
        }

        public void show(){
            if(!this.isShowing()){
                super.show();
            }
        }

        public void dismiss() {
            if (this.isShowing())
                super.dismiss();

        }

    public static LoadingDialog start(Context ctx) {
        LoadingDialog loadingDialog = new LoadingDialog(ctx);
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingDialog.setContentView(R.layout.component_loading);
        loadingDialog.setCancelable(false);
        return loadingDialog;

    }
}
