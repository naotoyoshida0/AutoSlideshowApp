package jp.techacademy.yoshida.naoto.autoslideshowapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;




public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Timer mTimer;
    double mTimerSec = 0.0;

    Handler mHandler = new Handler();

    Cursor cursor ;
    Slide slide ;



        public  void main(){
            int fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
            Long id = cursor.getLong(fieldIndex);
            Uri imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);

            ImageView imageVIew = (ImageView) findViewById(R.id.imageView);
            imageVIew.setImageURI(imageUri);
        }


    private static final int PERMISSIONS_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);







        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);


        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(this);






        // 　Android 6.0以降の場合
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // パーミッションの許可状態を確認する
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                // 許可されている
                getContentsInfo();
            } else {//拒否されているときの処理
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                    //再度許可を求める
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                }else
                // 許可されていないので許可ダイアログを表示する
                Toast.makeText(this,"許可してください",Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }
            // Android 5系以下の場合
        } else {

            getContentsInfo();
        }
    }


    @Override     //選択結果を受け取る
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContentsInfo();
                }
                else {
                    break;
                } default:
                break;
        }
    }

    public void getContentsInfo() {

        // 画像の情報を取得する
        ContentResolver resolver = getContentResolver();
         cursor = resolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, // 外部ストレージの画像を指定
                null, // 項目(null = 全項目)
                null, // フィルタ条件(null = フィルタなし)
                null, // フィルタ用パラメータ
                null // ソート (null ソートなし)
        );


    }




    @Override

    public void onClick(View v) {

        if (v.getId() ==R.id.button2) {//button2に画像送り機能
            if (cursor.moveToNext()) {
              main();
            }


            else if(cursor.moveToFirst());
              main();
        }



        if (v.getId() ==R.id.button1) {//button1に画像戻り機能
            if (cursor.moveToPrevious()) {
                main();
            }


            else if(cursor.moveToLast());
              main();

        }

        if (v.getId() ==R.id.button3) {//button3にスライド機能
                Button button3 = (Button)v;
                button3.setText("停止");

            Button button1 = (Button) findViewById(R.id.button1);
            button1.setEnabled(false);
            Button button2 = (Button) findViewById(R.id.button2);
            button2.setEnabled(false);

                if (mTimer == null) {
                    mTimer = new Timer();
                    mTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            mTimerSec += 1;

                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (cursor.moveToNext()) {
                                        main();
                                    }

                                }


                            });
                        }
                    }, 2000, 2000);

                }
                else if (v.getId() ==R.id.button3) {//button3に停止機能

                    button3.setText("再生");

                    button1.setEnabled(true);
                    button2.setEnabled(true);



                    if (mTimer != null) {
                        mTimer.cancel();
                        mTimer = null;
                    }

            }



        }
    }

}