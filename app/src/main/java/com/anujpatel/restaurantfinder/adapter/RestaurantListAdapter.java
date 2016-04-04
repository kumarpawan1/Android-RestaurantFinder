package com.anujpatel.restaurantfinder.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.anujpatel.restaurantfinder.R;
import com.anujpatel.restaurantfinder.Restaurant;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;

public class RestaurantListAdapter extends BaseAdapter{


    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<Restaurant> mAlRestaurant;
    ImageLoaderConfiguration imgLoaderConfiguration;
    ImageLoader imageLoader;
    DisplayImageOptions options;
    public RestaurantListAdapter(Context mContext,ArrayList<Restaurant> alRestaurant){
        this.mAlRestaurant = alRestaurant;
        this.mContext = mContext;
        mLayoutInflater = (LayoutInflater)mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
       imgLoaderConfiguration = new ImageLoaderConfiguration.Builder(mContext)
                .build();
        ImageLoader.getInstance().init(imgLoaderConfiguration);
//        imageLoader = new ImageLoader(mContext,mContext);
    }

    @Override
    public int getCount() {
        return mAlRestaurant.size();
    }

    @Override
    public Object getItem(int position) {
        return mAlRestaurant.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if(mAlRestaurant.size() > 1){
            return mAlRestaurant.size();
        }
        return 1;
    }

    public class Holder
    {
        TextView tv_name,tv_add;
        ImageView img;
        RatingBar mRatingBar;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)  {
        // TODO Auto-generated method stub
        Holder holder = null;
        View rowView = convertView;
        int type = getItemViewType(position);
        if (convertView == null) {
            holder = new Holder();
            rowView = mLayoutInflater.inflate(R.layout.row_list_view, null);
            holder.tv_name=(TextView) rowView.findViewById(R.id.tv_restaurant_name);
            holder.img=(ImageView) rowView.findViewById(R.id.iv_restaurant_image);
            holder.tv_add=(TextView) rowView.findViewById(R.id.tv_restaurant_add);
            holder.mRatingBar = (RatingBar) rowView.findViewById(R.id.rb_restaurant);
            rowView.setTag(holder);
        } else {
            holder = (Holder)convertView.getTag();
        }

        holder.tv_name.setText(mAlRestaurant.get(position).getBusinessName());
        holder.tv_add.setText(mAlRestaurant.get(position).getBusinessAddress());
        holder.mRatingBar.setRating(mAlRestaurant.get(position).getRating());
        ImageLoader.getInstance().displayImage(mAlRestaurant.get(position).getImageLink(), holder.img, options);
        return rowView;
    }
}
