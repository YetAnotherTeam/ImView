package jat.imview.ui.activity;


import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class PreferenceActivity extends DrawerActivity {

    @Override
    public NavigationDrawerItem getCurrentNavDrawerItem() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(jat.imview.R.layout.activity_preference);

        //Toolbar toolbar = (Toolbar) findViewById(jat.imview.R.id.toolbar);
        //toolbar.setTitleTextColor(0xFFFFFFFF);

        //setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getFragmentManager().beginTransaction().replace(jat.imview.R.id.pref_fragment_container, new PrefFragment()).commit();
    }

    public static class PrefFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(jat.imview.R.xml.pref_general);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(jat.imview.R.menu.preference_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
