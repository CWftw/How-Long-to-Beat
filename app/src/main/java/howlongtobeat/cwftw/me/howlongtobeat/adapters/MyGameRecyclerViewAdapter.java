/*
 * Colin Willson & Matt Allen
 * Final Project, PROG3210
 * December 13, 2015
 *
 */

package howlongtobeat.cwftw.me.howlongtobeat.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import howlongtobeat.cwftw.me.howlongtobeat.R;
import howlongtobeat.cwftw.me.howlongtobeat.dummy.DummyContent.DummyItem;
import howlongtobeat.cwftw.me.howlongtobeat.fragments.GameFragment.OnGameFragmentInteractionListener;
import howlongtobeat.cwftw.me.howlongtobeat.models.Game;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnGameFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyGameRecyclerViewAdapter extends RecyclerView.Adapter<MyGameRecyclerViewAdapter.ViewHolder> {

    private final List<Game> mValues;
    private final OnGameFragmentInteractionListener mListener;

    public MyGameRecyclerViewAdapter(List<Game> items, OnGameFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    public void addIems(ArrayList<Game> games) {
        this.mValues.addAll(games);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

//        holder.mIdView.setText(mValues.get(position).id);
//        holder.mContentView.setText(mValues.get(position).content);

        // Asynchronously load image with Ion library
        Ion.with(holder.gameItemImg)
                // use a placeholder image
                .placeholder(R.mipmap.ic_launcher)
                // load the url
                .load(mValues.get(position).getImageUrl());

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
        public final TextView mainStoryItem;
        public final TextView extraItem;
        public final TextView completionistItem;
        public final TextView combinedItem;
        public Game mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            gameItemImg = (ImageView) view.findViewById(R.id.gameItemImg);
            mainStoryItem = (TextView) view.findViewById(R.id.mainStoryItem);
            extraItem = (TextView) view.findViewById(R.id.extraItem);
            completionistItem = (TextView) view.findViewById(R.id.completionistItem);
            combinedItem = (TextView) view.findViewById(R.id.combinedItem);
        }
    }
}
