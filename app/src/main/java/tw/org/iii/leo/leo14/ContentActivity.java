package tw.org.iii.leo.leo14;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.HttpURLConnection;
import java.net.URL;

public class ContentActivity extends AppCompatActivity {

    private ImageView img;
    private TextView content;
    private String strPic;
    private String strContent;

    private UIHandler uiHandler = new UIHandler();
    private Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content2);

        img = findViewById(R.id.content_img);
        content = findViewById(R.id.content_content);

        strPic = getIntent().getStringExtra("pic");
        strContent = getIntent().getStringExtra("content");

        Log.v("leo",strPic);

        content.setText(strContent);
        fetchImage();

    }



    private void fetchImage(){
        new Thread(){
            @Override
            public void run() {
                try{
                    URL url = new URL(strPic);

                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.connect();

                    //1. 我會取得conn.getInputStream()
                    //2. 我要顯示到ImageView
                    bmp = BitmapFactory.decodeStream(conn.getInputStream());

                        uiHandler.sendEmptyMessage(0);

                    Log.v("leo","123"+bmp);

                }
                catch (Exception e){
                    Log.v("leo",e.toString());

                }
            }
        }.start();

    }

    private class UIHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if(bmp == null) Log.v("leo","debug3");
            img.setImageBitmap(bmp);
        }
    }
}
