package com.waycreon.pip;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.commit451.nativestackblur.NativeStackBlur;
import com.myandroid.views.MultiTouchListener;
import com.squareup.picasso.Picasso;
import com.waycreon.pip.chiralcode.colorpicker.ColorPickerDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;

public class SelectedImageActivity extends Activity {


    ImageView imageview_id;
    ImageView mFrameIv;
    ImageView mMovImage;
    Global mGlobal;
    GalleryImageAdapter galImageAdapter;

    Spinner mSpinner_text_style;
    ImageButton mIbtn_color_text;
    AutoResizeTextView mTv_text;

    String style[] = {"1.ttf", "2.ttf", "3.ttf", "4.ttf", "5.ttf", "6.ttf",
            "7.ttf", "8.ttf", "9.ttf", "10.ttf", "11.ttf", "12.ttf", "13.ttf", "14.ttf", "15.ttf", "16.ttf", "17.ttf", "18.ttf", "19.ttf", "20.ttf", "21.ttf", "22.ttf", "23.ttf", "24.ttf", "25.ttf", "26.ttf", "27.ttf", "28.ttf", "29.ttf", "30.ttf", "31.ttf", "32.ttf", "33.ttf", "34.ttf", "35.ttf"};


    Integer[] iconImages = {
            R.drawable.i1,
            R.drawable.i2,
            R.drawable.i3,
            R.drawable.i4,
            R.drawable.i5,
            R.drawable.i6,
            R.drawable.i7,
            R.drawable.i8,
            R.drawable.i9,
            R.drawable.i10,
            R.drawable.i11,
            R.drawable.i12,
            R.drawable.i13,
            R.drawable.i14,
            R.drawable.i15,
            R.drawable.i16,
            R.drawable.i17,
            R.drawable.i18,
            R.drawable.i19,
            R.drawable.i20,
            R.drawable.i21,
            R.drawable.i22,
            R.drawable.i23,
            R.drawable.i24,
            R.drawable.i25,
            R.drawable.i26,
            R.drawable.i27
    };

    Integer[] frameImages = {
            R.drawable.f1,
            R.drawable.f2,
            R.drawable.f3,
            R.drawable.f4,
            R.drawable.f5,
            R.drawable.f6,
            R.drawable.f7,
            R.drawable.f8,
            R.drawable.f9,
            R.drawable.f10,
            R.drawable.f11,
            R.drawable.f12,
            R.drawable.f13,
            R.drawable.f14,
            R.drawable.f15,
            R.drawable.f16,
            R.drawable.f17,
            R.drawable.f18,
            R.drawable.f19,
            R.drawable.f20,
            R.drawable.f21,
            R.drawable.f22,
            R.drawable.f23,
            R.drawable.f24,
            R.drawable.f25,
            R.drawable.f26,
            R.drawable.f27
    };


    Integer[] maskImages = {
            R.drawable.f1m,
            R.drawable.f2m,
            R.drawable.f3m,
            R.drawable.f4m,
            R.drawable.f5m,
            R.drawable.f6m,
            R.drawable.f7m,
            R.drawable.f8m,
            R.drawable.f9m,
            R.drawable.f10m,
            R.drawable.f11m,
            R.drawable.f12m,
            R.drawable.f13m,
            R.drawable.f14m,
            R.drawable.f15m,
            R.drawable.f16m,
            R.drawable.f17m,
            R.drawable.f18m,
            R.drawable.f19m,
            R.drawable.f20m,
            R.drawable.f21m,
            R.drawable.f22m,
            R.drawable.f23m,
            R.drawable.f24m,
            R.drawable.f25m           //same mask for 25 26 27
    };

    int currentimg = 0;
    int currentalpha = 25;

    Bitmap mask;
    Bitmap result;
    Bitmap original;

    FrameLayout frotext;
    EditText et_view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_image);

        galImageAdapter = new GalleryImageAdapter(this);

        mTv_text = new AutoResizeTextView(getApplicationContext());

        frotext = (FrameLayout) findViewById(R.id.frame_txt);

        frotext.addView(mTv_text);

        imageview_id = (ImageView) findViewById(R.id.imageview_id);
        mMovImage = (ImageView) findViewById(R.id.iv_mov);
        mFrameIv = (ImageView) findViewById(R.id.mFrameIv);

        mGlobal = ((Global) getApplication());
        //RUNTIME

        mMovImage.setImageBitmap(mGlobal.getImage());
        makeMaskImage(imageview_id, maskImages[0], frameImages[0]);

        mMovImage.setOnTouchListener(new MultiTouchListener());

    }


    //Method of creating mask runtime
    public void makeMaskImage(ImageView mImageView, int maskimg, int frame) {

        mFrameIv.setBackgroundResource(frame); // frame of image


        try {
            result.recycle();
            result = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mask.recycle();
            mask = null;
        } catch (Exception e) {

        }


        mask = BitmapFactory.decodeResource(getResources(), maskimg);    //masking
        result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Config.ARGB_8888);
        bm = mGlobal.getImage();

        original = NativeStackBlur.process(getResizedBitmap(mask.getWidth(), mask.getHeight()), currentalpha);

        Canvas mCanvas = new Canvas(result);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        mCanvas.drawBitmap(original, 0, 0, null);
        mCanvas.drawBitmap(mask, 0, 0, paint);
        paint.setXfermode(null);

        imageview_id.setImageBitmap(result);
        mImageView.setScaleType(ScaleType.CENTER_INSIDE);


        try {
            mask.recycle();
            mask = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            bm.recycle();
            bm = null;
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            original.recycle();
            original = null;
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(SelectedImageActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    //adapter

    public class GalleryImageAdapter extends BaseAdapter {

        private Activity context;
        int imageBackground;
        private ImageView imageView;


        private ViewHolder holder;

        public GalleryImageAdapter(Activity context) {

            this.context = context;
            TypedArray ta = obtainStyledAttributes(R.styleable.Gallery1);
            imageBackground = ta.getResourceId(R.styleable.Gallery1_android_galleryItemBackground, 1);
            ta.recycle();

        }

        @Override
        public int getCount() {
            return iconImages.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {

                holder = new ViewHolder();

                imageView = new ImageView(this.context);

                imageView.setPadding(3, 3, 3, 3);

                convertView = imageView;

                holder.imageView = imageView;

                convertView.setTag(holder);

            } else {

                holder = (ViewHolder) convertView.getTag();
            }

            Picasso.with(context).load(iconImages[position]).into(holder.imageView);
            holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            holder.imageView.setLayoutParams(new Gallery.LayoutParams(200, 200));
            holder.imageView.setBackgroundResource(imageBackground);
            return imageView;
        }

        private class ViewHolder {
            ImageView imageView;
        }

    }


    Bitmap bm;

    public Bitmap getResizedBitmap(int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();

        float scaleWidth;
        float scaleHeight;


        if (width < height) {

            scaleWidth = ((float) newWidth) / width;
            scaleHeight = scaleWidth;

        } else {    // width >height

            scaleHeight = ((float) newHeight) / height;
            scaleWidth = scaleHeight;

        }

        Matrix matrix = new Matrix();
// RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        // RECREATE THE NEW BITMAP
        bm = Bitmap.createBitmap(bm, 0, 0, width, height,
                matrix, false);
        return bm;
    }


    public void back(View view) {
        onBackPressed();
    }

    public void save(View view) {
        captureImage();
    }

    public void share(View view) {


        File fileWithinMyDir = captureImage();

        Intent sharingIntent = new Intent(
                android.content.Intent.ACTION_SEND);
        Uri screenshotUri = Uri.fromFile(fileWithinMyDir);
        sharingIntent.setType("image/*");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        startActivity(Intent.createChooser(sharingIntent,
                "ارسال با کدام برنامه ؟"));
    }

    //
    private File captureImage() {
        // TODO Auto-generated method stub
        OutputStream output;

        Calendar cal = Calendar.getInstance();
        RelativeLayout capturelayout = (RelativeLayout) findViewById(R.id.rl);

        Bitmap bitmap = Bitmap.createBitmap(capturelayout.getWidth(), capturelayout.getHeight(),
                Bitmap.Config.ARGB_8888);

		/*
         * bitmap = ThumbnailUtils.extractThumbnail(bitmap, ll1.getWidth(),
		 * ll1.getHeight());
		 */
        Canvas b = new Canvas(bitmap);
        capturelayout.draw(b);

        // Find the SD Card path
        File filepath = Environment.getExternalStorageDirectory();

        // Create a new folder in SD Card
        File dir = new File(filepath.getAbsolutePath() + "/ShikoPic/");
        dir.mkdirs();

        String mImagename = "image" + cal.getTimeInMillis() + ".png";

        // Create a name for the saved image
        File file = new File(dir, mImagename);

        MediaScannerConnection.scanFile(this, new String[]{file.getPath()},
                null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        // now visible in gallery
                    }
                });

        // Show a toast message on successful save
        Toast.makeText(SelectedImageActivity.this, "تصویر در حافظه ذخیره شد",
                Toast.LENGTH_SHORT).show();

        try {

            output = new FileOutputStream(file);
            // Compress into png format image from 0% - 100%
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
            output.flush();
            output.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return file;

    }


    @Override
    protected void onDestroy() {

        imageview_id.setImageBitmap(null);
        mFrameIv.setImageBitmap(null);
        mMovImage.setImageBitmap(null);


        iconImages = null;
        frameImages = null;
        maskImages = null;


        try {
            mGlobal.getImage().recycle();
            mGlobal.setImage(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.gc();
        super.onDestroy();
    }


    public void addtxt(View v) {
        String off_id = "", unm = "", pass = "";
        // test dialog

        RelativeLayout.LayoutParams mParams1;

        final Dialog dialog = new Dialog(SelectedImageActivity.this);
        // Include dialog.xml file

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));

        dialog.setContentView(R.layout.text_custom_dialog);
        dialog.setCancelable(false);
        // Set dialog title

        et_view = (EditText) dialog.findViewById(R.id.et_view);

        et_view.setText("" + mTv_text.getText().toString().trim());
        dialog.setTitle("ویرایشگر متن");
        dialog.show();
        mSpinner_text_style = (Spinner) dialog
                .findViewById(R.id.spinner_text_style);
        mIbtn_color_text = (ImageButton) dialog
                .findViewById(R.id.ibtn_color_text);

        MyAdapter adapter = new MyAdapter(SelectedImageActivity.this,
                R.layout.spinner_row, style);
        mSpinner_text_style.setAdapter(adapter);

        mSpinner_text_style
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0,
                                               View arg1, int arg2, long arg3) {
                        // TODO Auto-generated method stub
                        mGlobal.setPosition(arg2);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });

        mIbtn_color_text.setBackgroundColor(mGlobal.getColor());

        mIbtn_color_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showColorPickerDialogDemo();

            }
        });


        Button declineButton = (Button) dialog
                .findViewById(R.id.btn_cancel);
        // if decline button is clicked, close the custom dialog
        declineButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();
            }
        });

        Button Ok = (Button) dialog.findViewById(R.id.btn_ok);
        // if decline button is clicked, close the custom dialog
        Ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Close dialog

                mTv_text.setTextColor(mGlobal.getColor());
                Typeface face = Typeface.createFromAsset(getAssets(),
                        style[mGlobal.getPosition()]);

                mTv_text.setTypeface(face);

                String s = et_view.getText().toString().trim();

                mTv_text.setText(s);
                dialog.dismiss();


            }
        });

        // ===========================================

        mTv_text.setTextSize(58);
        mTv_text.setOnTouchListener(new MultiTouchListener());

    }


    public void showColorPickerDialogDemo() {

        int initialColor = mGlobal.getColor();

        ColorPickerDialog colorPickerDialog = new ColorPickerDialog(this,
                initialColor, new ColorPickerDialog.OnColorSelectedListener() {

            @Override
            public void onColorSelected(int color) {
                mIbtn_color_text.setBackgroundColor(color);
                mGlobal.setColor(color);

            }

        });
        colorPickerDialog.show();

    }


    public class MyAdapter extends ArrayAdapter<String> {

        public MyAdapter(Context context, int textViewResourceId,
                         String[] objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView,
                                  ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.spinner_row, parent, false);

            TextView label = (TextView) row.findViewById(R.id.textView1);
            label.setText("Abc - آب\u200Cپ");

            Typeface face = Typeface.createFromAsset(getAssets(),
                    style[position]);

            label.setTypeface(face);

            return row;
        }

    }


    class ViewHolder {
        ImageView imageView;
    }


    public void selectframe(View view) {


        // Prepare grid view
        GridView gridView = new GridView(this);

        gridView.setAdapter(new ArrayAdapter(this, R.layout.single_image, iconImages) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                ViewHolder holder;

                ImageView imageView;

                if (convertView == null) {

                    holder = new ViewHolder();

                    imageView = new ImageView(getApplicationContext());

                    imageView.setPadding(3, 3, 3, 3);

                    convertView = imageView;

                    holder.imageView = imageView;

                    convertView.setTag(holder);

                } else {

                    holder = (ViewHolder) convertView.getTag();
                }

                Picasso.with(getApplicationContext()).load(iconImages[position]).into(holder.imageView);
                holder.imageView.setScaleType(ScaleType.CENTER_INSIDE);
                //    holder.imageView.setLayoutParams(new Gallery.LayoutParams(200, 200));
                //  holder.imageView.setBackgroundResource(imageBackground);
                return holder.imageView;
            }
        });
        gridView.setNumColumns(2);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                // do something here

                makeMaskImage(imageview_id, maskImages[i], frameImages[i]);
                currentimg = i;
                builder.dismiss();
            }


        });

        // Set grid view to alertDialog


        builder = new AlertDialog.Builder(this).create();
        builder.setView(gridView);
        builder.show();


    }

    AlertDialog builder;

}

