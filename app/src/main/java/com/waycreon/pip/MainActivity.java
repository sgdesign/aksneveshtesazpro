package com.waycreon.pip;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.io.IOException;
import co.ronash.pushe.Pushe;


public class MainActivity extends AppCompatActivity {
    protected static final int REQUEST_CAMERA = 111;
    protected static final int SELECT_FILE = 222;

    Global mGlobal;
    TinyDB tinyDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Pushe.initialize(this,false);
        tinyDB = new TinyDB(getBaseContext());

        //Check Application Update
        if(tinyDB.getString("update").equals("true")){
            //Update Mojode Va Json Send Shode
            if(tinyDB.getString("ejbari").equals("yes")){
                //Update Ejbariam Hast
                showEjbariUpdate();
            }else if (tinyDB.getString("ejbari").equals("no")){
                //Update Ejbari Nist
                showUpdateMamooli();

            }
        }

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContentView(R.layout.activity_main);


        mGlobal = ((Global) getApplication());

        findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(Environment
                        .getExternalStorageDirectory(), "temp.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(f));
                startActivityForResult(intent, REQUEST_CAMERA);

                Toast.makeText(Global.context,"عکس بگیرید و بلافاصله ویرایش کنید",Toast.LENGTH_LONG).show();

            }
        });

        findViewById(R.id.galery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(
                        Intent.createChooser(intent, "انتخاب عکس از گالری"),
                        SELECT_FILE);

                Toast.makeText(Global.context,"یک عکس از گالری انتخاب کنید",Toast.LENGTH_LONG).show();
            }
        });


        findViewById(R.id.btnrate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    intent.setData(Uri.parse("bazaar://details?id=ir.saeedgh.aksneveshteh"));
                    intent.setPackage("com.farsitel.bazaar");
                    startActivity(intent);
                    Toast.makeText(Global.context,"لطفا با امتیاز 5ستاره از ما حمایت کنید",Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(Global.context,"خطا! برنامه کافه بازار روی گوشی شما نصب نیست",Toast.LENGTH_LONG).show();
                }
            }
        });


        findViewById(R.id.btninfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent (MainActivity.this , about_us.class);
                    startActivity(intent);
                Toast.makeText(Global.context,"برنامه نویسی شده توسط گروه جوان کامپیوتر",Toast.LENGTH_LONG).show();
            }
        });



        findViewById(R.id.btnotherapps).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("bazaar://collection?slug=by_author&aid=javancomputer"));
                    intent.setPackage("com.farsitel.bazaar");
                    startActivity(intent);
                    Toast.makeText(Global.context,"لطفا از تمام برنامه های ما دیدن کنید",Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Intent next = new Intent(Intent.ACTION_VIEW);
                    next.setData(Uri.parse("https://cafebazaar.ir/developer/754498987128/?l=fa"));
                    startActivity(next);
                    Toast.makeText(Global.context,"لطفا از تمام برنامه های ما دیدن کنید",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    File f;
    Bitmap m_bitmap1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        // /==========================

        if (resultCode == RESULT_OK) {

            final Intent i = new Intent(MainActivity.this, SelectedImageActivity.class);


            if (requestCode == REQUEST_CAMERA) {
                f = new File(Environment.getExternalStorageDirectory()
                        .toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {

                    int rotate = 0;
                    ExifInterface exif;
                    try {
                        exif = new ExifInterface(
                                f.getAbsolutePath());
                        int orientation = exif.getAttributeInt(
                                ExifInterface.TAG_ORIENTATION,
                                ExifInterface.ORIENTATION_NORMAL);
                        switch (orientation) {
                            case ExifInterface.ORIENTATION_ROTATE_270:
                                rotate = 270;
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_180:
                                rotate = 180;
                                break;
                            case ExifInterface.ORIENTATION_ROTATE_90:
                                rotate = 90;
                                break;
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                    final int finalRotate = rotate;

                    m_bitmap1 = BitmapFactory.decodeFile(f.getAbsolutePath());
                    int nh = (int) (m_bitmap1.getHeight() * (400.0 / m_bitmap1.getWidth()));
                    m_bitmap1 = Bitmap.createScaledBitmap(m_bitmap1, 400, nh, true);

                    Matrix matrix = new Matrix();
                    matrix.postRotate(finalRotate);

                    mGlobal.setImage(Bitmap.createBitmap(m_bitmap1, 0, 0, m_bitmap1.getWidth(), m_bitmap1.getHeight(), matrix, true));

                    startActivity(i);
                    finish();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();

                try {

                    f = FileUtils.getFile(this, (selectedImageUri));


                    Glide.with(this).load(f)
                            .asBitmap()
                            .into(new SimpleTarget<Bitmap>(400, 400) {
                                @Override
                                public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {

                                    mGlobal.setImage(bitmap);
                                    startActivity(i);
                                    finish();
                                }
                            });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    protected void onDestroy() {


        try {
            m_bitmap1.recycle();
            m_bitmap1 = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.gc();
        super.onDestroy();


    }

    public void showEjbariUpdate(){
        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).setMessage(tinyDB.getString("text")).setPositiveButton("الان آپدیت میکنم", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(tinyDB.getString("link")));
                startActivity(browserIntent);
                System.exit(0);
                finish();
            }
        }).setNegativeButton("بیخیال", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"توجه! آپدیت اجباری است، اگر برنامه را آپدیت نکنید نمی توانید وارد آن شوید.",Toast.LENGTH_LONG).show();
                System.exit(0);
                finish();
            }
        }).setCancelable(false).show();
    }
    public void showUpdateMamooli(){
        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).setMessage(tinyDB.getString("text")).setPositiveButton("الان آپدیت میکنم", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(tinyDB.getString("link")));
                startActivity(browserIntent);
            }
        }).setNegativeButton("بیخیال", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"بهتره بعدا برنامه را آپدیت کرده تا همیشه از امکانات جدیدتر بهرمند شوید.",Toast.LENGTH_LONG).show();

            }
        }).setCancelable(false).show();
    }

}
