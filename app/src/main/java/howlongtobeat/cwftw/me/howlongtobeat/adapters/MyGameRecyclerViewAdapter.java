/*
 * Colin Willson & Matt Allen
 * Final Project, PROG3210
 * December 13, 2015
 *
 */

package howlongtobeat.cwftw.me.howlongtobeat.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import howlongtobeat.cwftw.me.howlongtobeat.DatabaseHelper;
import howlongtobeat.cwftw.me.howlongtobeat.R;
import howlongtobeat.cwftw.me.howlongtobeat.Utils;
import howlongtobeat.cwftw.me.howlongtobeat.dummy.DummyContent.DummyItem;
import howlongtobeat.cwftw.me.howlongtobeat.fragments.GameFragment.OnGameFragmentInteractionListener;
import howlongtobeat.cwftw.me.howlongtobeat.models.Game;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnGameFragmentInteractionListener}.
 */
public class MyGameRecyclerViewAdapter extends RecyclerView.Adapter<MyGameRecyclerViewAdapter.ViewHolder> {

    private final List<Game> mValues;
    private final OnGameFragmentInteractionListener mListener;
    private final boolean isDetails = true;

    public MyGameRecyclerViewAdapter(List<Game> items, OnGameFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    public void addItems(ArrayList<Game> games) {
        this.mValues.addAll(games);
    }

    public void setItems(ArrayList<Game> games) {
        this.mValues.clear();
        this.mValues.addAll(games);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if(isDetails)
        {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.game_detail, parent, false);
        }
        else
        {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.game_item, parent, false);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);

        // Asynchronously load image with Ion library
        Ion.with(holder.gameItemImg)
                // use a placeholder image
                .placeholder(R.mipmap.ic_launcher)
                // load the url
                .load(mValues.get(position).getImageUrl());

        holder.mainStoryItem.setText(Utils.formatData(mValues.get(position).getMainHours(), Utils.FormatTypes.HOURS, holder.gameItemImg.getContext()));
        holder.extraItem.setText(Utils.formatData(mValues.get(position).getMainExtraHours(), Utils.FormatTypes.HOURS, holder.gameItemImg.getContext()));
        holder.completionistItem.setText(Utils.formatData(mValues.get(position).getCompletionistHours(), Utils.FormatTypes.HOURS, holder.gameItemImg.getContext()));
        holder.combinedItem.setText(Utils.formatData(mValues.get(position).getCombinedHours(), Utils.FormatTypes.HOURS, holder.gameItemImg.getContext()));
        holder.mainStoryDetail.setText(Utils.formatData(mValues.get(position).getMainHours(), Utils.FormatTypes.HOURS, holder.gameItemImg.getContext()));
        holder.extraDetail.setText(Utils.formatData(mValues.get(position).getMainExtraHours(), Utils.FormatTypes.HOURS, holder.gameItemImg.getContext()));
        holder.completionistDetail.setText(Utils.formatData(mValues.get(position).getCompletionistHours(), Utils.FormatTypes.HOURS, holder.gameItemImg.getContext()));
        holder.combinedDetail.setText(Utils.formatData(mValues.get(position).getCombinedHours(), Utils.FormatTypes.HOURS, holder.gameItemImg.getContext()));
        holder.polledDetail.setText(String.valueOf(mValues.get(position).getPolled()));
        holder.ratedDetail.setText(Utils.formatData(mValues.get(position).getRatedPercent(), Utils.FormatTypes.PERCENT, holder.gameItemImg.getContext()));
        holder.backlogDetail.setText(String.valueOf(mValues.get(position).getBacklogCount()));
        holder.playingDetail.setText(String.valueOf(mValues.get(position).getPlaying()));
        holder.retiredDetail.setText(String.valueOf(mValues.get(position).getRetired()));

        boolean isFavorited = DatabaseHelper.getInstance(holder.gameItemImg.getContext()).selectGame(position) != null;
        if (isFavorited) {
            holder.favoritedImg.setImageResource(R.mipmap.full_star);
            holder.favoritedImgDetail.setImageResource(R.mipmap.full_star);
        } else {
            holder.favoritedImg.setImageResource(R.mipmap.empty_star);
            holder.favoritedImgDetail.setImageResource(R.mipmap.empty_star);
        }

        holder.favoritedImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("INFO", "Image Clicked");
                boolean isFavorited = DatabaseHelper.getInstance(holder.gameItemImg.getContext()).selectGame(position) != null;

                if (!isFavorited) {
                    holder.favoritedImg.setImageResource(R.mipmap.full_star);
                    holder.favoritedImgDetail.setImageResource(R.mipmap.full_star);

//                    Bitmap photo = ((Ion)holder.gameItemImg.getDrawable()).getBitmap();
//                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                    photo.compress(Bitmap.CompressFormat.PNG, 100, bos);
//                    byte[] bArray = bos.toByteArray();
//
//                    holder.mItem.setImageBytes(bArray);

                    DatabaseHelper.getInstance(holder.gameItemImg.getContext()).insertGame(holder.mItem);
                } else {
                    holder.favoritedImg.setImageResource(R.mipmap.empty_star);
                    holder.favoritedImgDetail.setImageResource(R.mipmap.empty_star);
                    DatabaseHelper.getInstance(holder.gameItemImg.getContext()).deleteGame(holder.mItem.getId());
                }
            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onGameFragmentInteraction(holder.mItem);
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
        public final ImageButton favoritedImgDetail;
        public final TextView mainStoryItem;
        public final TextView extraItem;
        public final TextView completionistItem;
        public final TextView combinedItem;
        public final TextView mainStoryDetail;
        public final TextView extraDetail;
        public final TextView completionistDetail;
        public final TextView combinedDetail;
        public final TextView polledDetail;
        public final TextView ratedDetail;
        public final TextView backlogDetail;
        public final TextView playingDetail;
        public final TextView retiredDetail;
        public Game mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            gameItemImg = (ImageView) view.findViewById(R.id.gameItemImg);
            favoritedImg = (ImageButton) view.findViewById(R.id.favoritedImg);
            favoritedImgDetail = (ImageButton) view.findViewById(R.id.favoritedImgDetail);
            mainStoryItem = (TextView) view.findViewById(R.id.mainStoryItem);
            extraItem = (TextView) view.findViewById(R.id.extraItem);
            completionistItem = (TextView) view.findViewById(R.id.completionistItem);
            combinedItem = (TextView) view.findViewById(R.id.combinedItem);
            mainStoryDetail = (TextView) view.findViewById(R.id.mainStoryDetail);
            extraDetail = (TextView) view.findViewById(R.id.extraDetail);
            completionistDetail = (TextView) view.findViewById(R.id.completionistDetail);
            combinedDetail = (TextView) view.findViewById(R.id.combinedDetail);
            polledDetail = (TextView) view.findViewById(R.id.polledDetail);
            ratedDetail = (TextView) view.findViewById(R.id.ratedDetail);
            backlogDetail = (TextView) view.findViewById(R.id.backlogDetail);
            playingDetail = (TextView) view.findViewById(R.id.playingDetail);
            retiredDetail = (TextView) view.findViewById(R.id.retiredDetail);
        }
    }
}
