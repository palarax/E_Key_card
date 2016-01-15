package palarax.e_key_card;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import palarax.e_key_card.CardReader.nfcCard;

/**
 * @author Ilya Thai
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = MainActivity.class.getSimpleName(); //used for debugging

    private nfcCard NFC_card_fragment = new nfcCard();
    private MainFragment home_fragment = new MainFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //create a drawer layout menu
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MainFragment fragment = new MainFragment();
        transaction.replace(R.id.main_frag, fragment);
        transaction.commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //TODO: change navigation format to case method
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // TODO: create a main screen
            setTitle("HOME");
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_frag, home_fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if (id == R.id.nav_scan) {
            //scan ID and tech
            setTitle("SCAN");
            //Set the correct layout since WRITE/SCAN are both using nfcCard
            try {
                NFC_card_fragment.setViewLayout(R.layout.nfc_details_fragment);

            }catch (Exception e){
                Log.i(TAG,"Scan/Write error on the first go");
                NFC_card_fragment.setviewID(R.layout.nfc_details_fragment);
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_frag, NFC_card_fragment);
            transaction.commit();
        }
        else if (id == R.id.nav_write) {
            // TODO: finish write to NFC
            setTitle("WRITE");
            //Set the correct layout since WRITE/SCAN are both using nfcCard
            try {
                NFC_card_fragment.setViewLayout(R.layout.nfc_write_fragment);
            }catch (Exception e){
                Log.i(TAG,"Scan/Write error on the first go");
                NFC_card_fragment.setviewID(R.layout.nfc_write_fragment);
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_frag, NFC_card_fragment);
            transaction.commit();
        }
        else if (id == R.id.nav_card_emulate) {
            // TODO: emulate NFC card
            setTitle("CARD EMULATION");
        }
        else if (id == R.id.nav_manage_tags) {
            // TODO: manage what the NFC card does

            setTitle("MANAGE");
        }
        else if (id == R.id.nav_share) {
            setTitle("SHARE");
        }
        else if (id == R.id.nav_send) {
            setTitle("SEND");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Fragment that appears in the main screen
     */
    public static class MainFragment extends Fragment {

        public MainFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            getActivity().setTitle("HOME");
            return inflater.inflate(R.layout.content_main, container, false);
        }
    }
}
