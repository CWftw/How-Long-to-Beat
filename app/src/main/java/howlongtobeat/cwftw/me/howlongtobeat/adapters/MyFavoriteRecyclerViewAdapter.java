/*
 * Colin Willson & Matt Allen
 * Final Project, PROG3210
 * December 13, 2015
 *
 */

package howlongtobeat.cwftw.me.howlongtobeat.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import howlongtobeat.cwftw.me.howlongtobeat.DatabaseHelper;
import howlongtobeat.cwftw.me.howlongtobeat.R;
import howlongtobeat.cwftw.me.howlongtobeat.Utils;
import howlongtobeat.cwftw.me.howlongtobeat.fragments.FavoriteFragment.OnFavoriteFragmentInteractionListener;
import howlongtobeat.cwftw.me.howlongtobeat.models.Game;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnFavoriteFragmentInteractionListener}.
 */
public class MyFavoriteRecyclerViewAdapter extends RecyclerView.Adapter<MyFavoriteRecyclerViewAdapter.ViewHolder> {

    private final List<Game> mValues;
    private final OnFavoriteFragmentInteractionListener mListener;

    public MyFavoriteRecyclerViewAdapter(ArrayList<Game> items, OnFavoriteFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    public void setItems(ArrayList<Game> games) {
        this.mValues.clear();
        this.mValues.addAll(games);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_item, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);

        //        ImageView imgViewer = (ImageView) findViewById(R.id.chart_image);
        Bitmap bm = BitmapFactory.decodeByteArray(holder.mItem.getImageBytes(), 0, holder.mItem.getImageBytes().length);
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);

//        imgViewer.setMinimumHeight(dm.heightPixels);
//        imgViewer.setMinimumWidth(dm.widthPixels);
//        imgViewer.setImageBitmap(bm);

        holder.gameItemImg.setImageBitmap(bm);

        holder.mainStoryItem.setText(Utils.formatData(mValues.get(position).getMainHours(), Utils.FormatTypes.HOURS, holder.gameItemImg.getContext()));
        holder.extraItem.setText(Utils.formatData(mValues.get(position).getMainExtraHours(), Utils.FormatTypes.HOURS, holder.gameItemImg.getContext()));
        holder.completionistItem.setText(Utils.formatData(mValues.get(position).getCompletionistHours(), Utils.FormatTypes.HOURS, holder.gameItemImg.getContext()));
        holder.combinedItem.setText(Utils.formatData(mValues.get(position).getCombinedHours(), Utils.FormatTypes.HOURS, holder.gameItemImg.getContext()));

        boolean isFavorited = DatabaseHelper.getInstance(holder.gameItemImg.getContext()).selectGame(mValues.get(position).getId()) != null;
        if (isFavorited) {
            holder.favoritedImg.setImageResource(R.drawable.ic_toggle_star);
        } else {
            holder.favoritedImg.setImageResource(R.drawable.ic_toggle_star_outline);
        }

        if (position == mValues.size()-1) {
            holder.separator.setVisibility(View.INVISIBLE);
        } else {
            holder.separator.setVisibility(View.VISIBLE);
        }

        holder.favoritedImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("INFO", "Image Clicked");
                DatabaseHelper.getInstance(holder.gameItemImg.getContext()).deleteGame(holder.mItem.getId());
                mValues.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onFavoriteFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView gameItemImg;
        public final ImageButton favoritedImg;
        public final TextView mainStoryItem;
        public final TextView extraItem;
        public final TextView completionistItem;
        public final TextView combinedItem;
        public Game mItem;
        public View separator;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            gameItemImg = (ImageView) view.findViewById(R.id.gameItemImg);
            favoritedImg = (ImageButton) view.findViewById(R.id.favoritedImg);
            mainStoryItem = (TextView) view.findViewById(R.id.mainStoryItem);
            extraItem = (TextView) view.findViewById(R.id.extraItem);
            completionistItem = (TextView) view.findViewById(R.id.completionistItem);
            combinedItem = (TextView) view.findViewById(R.id.combinedItem);
            separator = view.findViewById(R.id.separator);
        }
    }
}
