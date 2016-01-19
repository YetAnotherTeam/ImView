package jat.imview.ui.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

import jat.imview.R;

public class ImageNewActivity extends BaseActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_SELECT_IMAGE = 1;
    private ImageView mContentImage;
    private EditText mCaption;
    private Uri mSelectedImageUri;
    private Bitmap mOldBitmap;
    private Bitmap mNewBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_new);

        mContentImage = (ImageView) findViewById(R.id.content_image);
        mCaption = (EditText) findViewById(R.id.caption);

        findViewById(R.id.select_image_button).setOnClickListener(this);
        findViewById(R.id.publish_button).setOnClickListener(this);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case REQUEST_CODE_SELECT_IMAGE:
                if (resultCode == RESULT_OK) {
                    mSelectedImageUri = imageReturnedIntent.getData();
                    try {
                        mOldBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mSelectedImageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mContentImage.setImageBitmap(mOldBitmap);
                }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_image_button:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, getString(R.string.choose_image)), REQUEST_CODE_SELECT_IMAGE);
                break;
            case R.id.publish_button:
                if (createNewBitmap()) {

                }
                break;
        }
    }

    private boolean createNewBitmap() {
        String captionString = mCaption.getText().toString();
        if (mSelectedImageUri != null && !captionString.equals("")) {
            mNewBitmap = processingBitmap(mSelectedImageUri, captionString);
            return true;
        } else {
            Toast.makeText(getApplicationContext(), R.string.load_image_and_put_caption, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private Bitmap processingBitmap(Uri source, String captionString) {
        Bitmap oldBitmap;
        Bitmap newBitmap = null;
        try {
            oldBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(source));

            Bitmap.Config config = oldBitmap.getConfig();
            if (config == null) {
                config = Bitmap.Config.ARGB_8888;
            }
            int captionHeight = mCaption.getHeight();
            int oldBitmapWidth = oldBitmap.getWidth();
            float zoom = ((float) mContentImage.getWidth()) / oldBitmapWidth;
            int oldBitmapHeight = oldBitmap.getHeight();
            newBitmap = Bitmap.createBitmap(oldBitmapWidth, oldBitmapHeight + captionHeight, config);
            Canvas newCanvas = new Canvas(newBitmap);

            newCanvas.drawBitmap(oldBitmap, 0, 0, null);

            Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
            paintText.setColor(Color.WHITE);
            paintText.setTextSize(mCaption.getTextSize() / zoom);
            paintText.setStyle(Paint.Style.FILL);

            Rect rectText = new Rect();
            String[] captionStringLines = captionString.split("\n");
            int captionsLineHeight = captionHeight / captionStringLines.length;
            int offset = 3;
            for (int i = 0; i < captionStringLines.length; ++i) {
                String captionStringLine = captionStringLines[i];
                paintText.getTextBounds(captionStringLine, 0, captionStringLine.length(), rectText);
                newCanvas.drawText(
                        captionStringLine,
                        (oldBitmapWidth - rectText.width()) / 2,
                        captionsLineHeight * i + offset + oldBitmapHeight + rectText.height(),
                        paintText
                );
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return newBitmap;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.image_new_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                if (createNewBitmap()) {
                    addImageToGallery(mNewBitmap, "");
                }
                break;
            default:
                return false;
        }
        return true;
    }

    public void addImageToGallery(Bitmap bitmap, String filePath) {

        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, filePath);

        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "ImView", null);//+ String.valueOf(new Date().getTime())
        Toast.makeText(getApplicationContext(), R.string.image_saved, Toast.LENGTH_SHORT).show();

    }
}
