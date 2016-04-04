package com.anujpatel.restaurantfinder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class RestaurantDetailView extends FragmentActivity {

    ImageLoaderConfiguration imgLoaderConfiguration;
    ImageLoader imageLoader;
    DisplayImageOptions options;
    GoogleMap mGoogleMap;

    private Restaurant mRestaurant = new Restaurant();
    TextView tv_business_name;
    RatingBar rb_restarant_rating;
    TextView tv_review_count;
    TextView tv_restaurant_add;
    TextView tv_restaurant_phone;
    TextView tv_restaurant_snippet;
    ImageView iv_restaurant_image;
    ImageView ib_app_icon_back;

    MapFragment mapFragment = (MapFragment) getFragmentManager()
            .findFragmentById(R.id.map);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_detail_view);

    Intent intent = getIntent();

     mRestaurant = intent.getExtras().getParcelable(MainActivity.RESTAURANTDETAILKEY);
        Log.d("Detail View B Name", mRestaurant.getBusinessName());


     tv_business_name = (TextView) findViewById(R.id.tv_restaurant_name);
        rb_restarant_rating = (RatingBar) findViewById(R.id.rb_restaurant);
        tv_restaurant_add = (TextView) findViewById(R.id.tv_restaurant_add);
        tv_review_count = (TextView) findViewById(R.id.tv_review_count);
        tv_restaurant_phone = (TextView) findViewById(R.id.tv_restaurant_phone);
        tv_restaurant_snippet = (TextView) findViewById(R.id.tv_restaurant_snippet);
        iv_restaurant_image = (ImageView) findViewById(R.id.iv_restaurant_image);
        ib_app_icon_back = (ImageView) findViewById(R.id.ib_app_icon_back);


        ib_app_icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_menu_camera)
                .showImageForEmptyUri(R.drawable.ic_menu_camera)
                .showImageOnFail(R.drawable.ic_menu_camera)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new RoundedBitmapDisplayer(20))
                .build();
        imgLoaderConfiguration = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(imgLoaderConfiguration);



        mGoogleMap = ((MapFragment) RestaurantDetailView.this.getFragmentManager().findFragmentById(R.id.map)).getMap();
        mGoogleMap.getUiSettings().setAllGesturesEnabled(false);

        setData();

    }

    private void setData(){
        tv_business_name.setText(mRestaurant.getBusinessName());
        rb_restarant_rating.setRating(mRestaurant.getRating());
        tv_restaurant_add.setText("Address \n" + mRestaurant.getBusinessAddress());
        tv_review_count.setText(mRestaurant.getCountOfReviews() + " Reviews");
        tv_restaurant_phone.setText("Phone \n" + mRestaurant.getPhoneNumber());
        tv_restaurant_snippet.setText("Snippet \n" + mRestaurant.getSnippet());



        ImageLoader.getInstance().displayImage(mRestaurant.getImageLink(), iv_restaurant_image, options);
        LatLng mLatLng = null;

        try{
            mLatLng = new LatLng(Double.valueOf(mRestaurant.getStaticMapAddresslat()),Double.valueOf(mRestaurant.getStaticMapAddresslong()));
            Marker marker = mGoogleMap.addMarker(new MarkerOptions().position(mLatLng).title("" + mRestaurant.getBusinessName()).snippet(mRestaurant.getBusinessAddress()));
            // Move the camera instantly to hamburg with a zoom of 15.
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 15));

            // Zoom in, animating the camera.
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);
        } catch (Exception e){
            e.printStackTrace();
        }

    }





}
