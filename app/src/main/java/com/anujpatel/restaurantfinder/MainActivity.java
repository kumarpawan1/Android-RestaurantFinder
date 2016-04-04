package com.anujpatel.restaurantfinder;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.anujpatel.restaurantfinder.adapter.RestaurantListAdapter;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SearchView mSearchView;
    protected String mSearchKeyword = "",TAG="---MainActivity---";
    ListView lvRestaurant;
    Button btnSort;


    // JSON Node names
    public static final String RESTAURANTDETAILKEY = "restaurant_detail";
    private static final String TAG_BUSINESS = "businesses";
    private static final String TAG_NAME = "name";
    private static final String TAG_REVIEW_COUNT = "review_count";

    private static final String TAG_LOCATIONLAT = "latitude";
    private static final String TAG_LOCATIONLONG = "longitude";
    private static final String TAG_RATING = "rating";
    private static final String TAG_PHONENUMBER = "phone";
    private static final String TAG_SNIPPET = "snippet_text";
    private static final String TAG_LOCATION = "location";
    private static final String TAG_IMAGE = "image_url";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_DISPLAY_ADDRESS = "display_address";
    int PLACE_PICKER_REQUEST = 1;
    private boolean sortDistanceFlag = false;
    private double mLattitude = 0.00;
    private double mLongitude = 0.00;

    ArrayList<Restaurant> mRestaurantArrayList = new ArrayList<Restaurant>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient mClient;
    private RestaurantListAdapter mRestaurantListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRestaurantListAdapter = new RestaurantListAdapter(this, mRestaurantArrayList);
        lvRestaurant = (ListView) findViewById(R.id.restaurantLV);
        lvRestaurant.setAdapter(mRestaurantListAdapter);

        lvRestaurant.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             Intent i = new Intent(MainActivity.this, RestaurantDetailView.class);
                i.putExtra(RESTAURANTDETAILKEY, mRestaurantArrayList.get(position));
                startActivity(i);
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        btnSort = (Button) findViewById(R.id.btn_sort_toggle);
        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!sortDistanceFlag){
                    sortDistanceFlag = true;
                    btnSort.setText("SORT BY RELEVANCE");
                } else {
                    sortDistanceFlag = false;
                    btnSort.setText("SORT BY DISTANCE");
                }
                searchForYelp();
            }
        });

        mSearchView = (SearchView) findViewById(R.id.restaurantSearchView);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchKeyword = query;
                if(mSearchKeyword.length() > 0){
                    callPlacePicker();
                }
                //searchForYelp();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_search) {
            // Handle the camera action
        } else if (id == R.id.nav_favourites) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }





    public void searchForYelp() {
    //public void searchForYelp(View v) {
        DownloadWebPageTask task = new DownloadWebPageTask();
        task.execute();
    }

    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            try {
                String consumerKey = "Wpv8Cr_sVqPo-onG901qWA";
                String consumerSecret = "f90kSPqcK-49hWKFbVq_daFRh20";
                String token = "AABUW1u5mE09kL_67kxapBcoV5j5AzwL";
                String tokenSecret = "45CLNF_cphB4MCL1OcyqMaOCmes";
                YelpConnector yelp = new YelpConnector(consumerKey, consumerSecret, token, tokenSecret);

                if (!sortDistanceFlag) {
                    //response = yelp.search(mSearchKeyword, 30.361471, -87.164326, 20, 16093);
                    response = yelp.search(mSearchKeyword, mLattitude, mLongitude, 20, 16093);
                } else {
                    // On Sort by Distance Button Click
                    response = yelp.searchSortDistance(mSearchKeyword, mLattitude, mLongitude,1,20,16093);
                }



                Log.d(TAG,"The Search Keyword is "+ response);
                parseJsonObject(response);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }



        @Override
        protected void onPostExecute(String result) {
            String businessnames = "";
            for (Restaurant obj : mRestaurantArrayList) {
                businessnames = businessnames + obj.getBusinessName();
            }
            lvRestaurant.setAdapter(mRestaurantListAdapter);
            lvRestaurant.deferNotifyDataSetChanged();


        }
    }

    public void parseJsonObject(String response) {
        // Making a request to url and getting response
        String jsonStr = response;
        JSONArray restaurants = null;

        Log.d("Response: ", "> " + jsonStr);

        if (jsonStr != null) {
            mRestaurantArrayList.clear();
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                restaurants = jsonObj.getJSONArray(TAG_BUSINESS);

                // looping through All Contacts
                for (int i = 0; i < restaurants.length(); i++) {
                    JSONObject c = restaurants.getJSONObject(i);
                    String businessname = c.getString(TAG_NAME);
                    String snippet = c.getString(TAG_SNIPPET);
                    String reviewcount = c.getString(TAG_REVIEW_COUNT);
                    String rating = c.getString(TAG_RATING);
                    String phonenumber = c.getString(TAG_PHONENUMBER);
                    String imageUrl = c.getString(TAG_IMAGE);


                    // Phone node is JSON Object
                    JSONObject location = c.getJSONObject(TAG_LOCATION);
                    JSONObject location_coordinate = location.getJSONObject("coordinate");
                    String latitude = location_coordinate.getString(TAG_LOCATIONLAT);
                    String longitude = location_coordinate.getString(TAG_LOCATIONLONG);
                    String address = "";
                    JSONArray displayAdd = location.getJSONArray(TAG_DISPLAY_ADDRESS);
                    for(int k = 0; k < displayAdd.length(); k++){
                        if(k == displayAdd.length() - 1){
                            address += displayAdd.getString(k);
                        } else {
                            address += displayAdd.getString(k) + ", ";
                        }
                    }

                    Restaurant restaurant = new Restaurant();
                    restaurant.setBusinessName(businessname);
                    restaurant.setSnippet(snippet);
                    restaurant.setCountOfReviews(reviewcount);
                    try{
                        if(rating.length() > 0){
                            restaurant.setRating(Float.parseFloat(rating));
                        }
                    } catch(Exception pe){
                        pe.printStackTrace();
                    }

                    restaurant.setPhoneNumber(phonenumber);
                    restaurant.setStaticMapAddresslat(latitude);
                    restaurant.setStaticMapAddresslong(longitude);
                    restaurant.setBusinessAddress(address);
                    restaurant.setImageLink(imageUrl);

                    mRestaurantArrayList.add(restaurant);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
        }
    }


   private void callPlacePicker() {


       try {
           PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
           startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
       } catch(Exception e){
           e.printStackTrace();
       }

   }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                mLattitude = place.getLatLng().latitude;
                mLongitude = place.getLatLng().longitude;
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                searchForYelp();
            }
        }

    }
}
