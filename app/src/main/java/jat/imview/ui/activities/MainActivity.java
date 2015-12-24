package jat.imview.ui.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.HashMap;

import jat.imview.R;
import jat.imview.adapters.GalleryAdapter;
import jat.imview.service.SendServiceHelper;
import jat.imview.сontentProvider.DBHelper;
import jat.imview.сontentProvider.DBHelper_bulat;


public class MainActivity extends DrawerActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, View.OnClickListener {

    final String LOG_TAG = "myLogs";

    GalleryAdapter mImageSwipeAdapter;
    ViewPager mViewPager;
    LinearLayout mVoteUpButton;
    LinearLayout mCommentsButton;
    ImageButton mShareButton;

    DBHelper dbHelper;

    private SliderLayout mDemoSlider;

    private RecyclerView recyclerView;

    @Override
    public NavigationDrawerItem getCurrentNavDrawerItem() {
        return NavigationDrawerItem.FEATURED;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVoteUpButton = (LinearLayout) findViewById(R.id.vote_up_button);
        mVoteUpButton.setOnClickListener(this);
        mCommentsButton = (LinearLayout) findViewById(R.id.comments_button);
        mCommentsButton.setOnClickListener(this);
        mShareButton = (ImageButton) findViewById(R.id.share_button);
        mShareButton.setOnClickListener(this);
        SendServiceHelper.getInstance(this).requestImageList(true);
        mDemoSlider = (SliderLayout)findViewById(R.id.slider);

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Hannibal",R.drawable.hannibal);
        file_maps.put("Big Bang Theory",R.drawable.bigbang);
        file_maps.put("House of Cards",R.drawable.house);
        file_maps.put("Game of Thrones", R.drawable.game_of_thrones);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);

        dbHelper = new DBHelper(this);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    @Override
    public void onClick(View v) {
        // создаем объект для данных
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        switch (v.getId()) {
            case R.id.vote_up_button:
                Toast.makeText(this, "Лайк забро", Toast.LENGTH_SHORT).show();
                String isLike = "true";
                cv.put("isLike", isLike);
                long rowID = db.insert("mytable", null, cv);
                // подключаемся к БД
                Log.d(LOG_TAG, "row inserted, ID = " + rowID);

                break;
            case R.id.comments_button:
                Intent intent = new Intent(this, CommentsActivity.class);
                startActivity(intent);
                break;
            case R.id.share_button:
                Toast.makeText(this, "Share забро", Toast.LENGTH_SHORT).show();
                Cursor c = db.query("mytable", null, null, null, null, null, null);

                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                if (c.moveToFirst()) {
                    // определяем номера столбцов по имени в выборке
                    int idColIndex = c.getColumnIndex("id");
                    int nameColIndex = c.getColumnIndex("isLike");
                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        Log.d(LOG_TAG,
                                "ID = " + c.getInt(idColIndex) +
                            ", isLike = " + c.getString(nameColIndex)
                        );
                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                    } while (c.moveToNext());
                } else
                    Log.d(LOG_TAG, "0 rows");
                c.close();
                break;
        }
        dbHelper.close();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.featured_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}