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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import howlongtobeat.cwftw.me.howlongtobeat.R;
import howlongtobeat.cwftw.me.howlongtobeat.dummy.DummyContent.DummyItem;
import howlongtobeat.cwftw.me.howlongtobeat.fragments.FavoriteFragment.OnFavoriteFragmentInteractionListener;
import howlongtobeat.cwftw.me.howlongtobeat.models.Game;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnFavoriteFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyFavoriteRecyclerViewAdapter extends RecyclerView.Adapter<MyFavoriteRecyclerViewAdapter.ViewHolder> {

    private final List<Game> mValues;
    private final OnFavoriteFragmentInteractionListener mListener;

    public MyFavoriteRecyclerViewAdapter(ArrayList<Game> items, OnFavoriteFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
//        holder.mItem = mValues.get(position);
//        holder.mIdView.setText(mValues.get(position).id);
//        holder.mContentView.setText(mValues.get(position).content);
//
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
//                    mListener.onFavoriteFragmentInteraction(holder.mItem);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
//        public final TextView mIdView;
//        public final TextView mContentView;
        public Game mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
//            mIdView = (TextView) view.findViewById(R.id.id);
//            mContentView = (TextView) view.findViewById(R.id.content);
        }
    }
}
