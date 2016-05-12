/*
 * Colin Willson & Matt Allen
 * Final Project, PROG3210
 * December 13, 2015
 *
 */

package howlongtobeat.cwftw.me.howlongtobeat.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import howlongtobeat.cwftw.me.howlongtobeat.DatabaseHelper;
import howlongtobeat.cwftw.me.howlongtobeat.R;
import howlongtobeat.cwftw.me.howlongtobeat.Utils;
import howlongtobeat.cwftw.me.howlongtobeat.models.Game;

public class MyGameRecyclerViewAdapter extends RecyclerView.Adapter<MyGameRecyclerViewAdapter.ViewHolder> {

    private final List<Game> mValues;
    private Context context;

    public MyGameRecyclerViewAdapter(List<Game> items, Context context) {
        mValues = items;
        this.context = context;
    }

    public class LoadImageFromURL extends AsyncTask<Game, Void, Game> {

        @Override
        protected Game doInBackground(Game... params) {
            Game game = params[0];

            try {
                URL url = new URL(game.getImageUrl());
                InputStream is = url.openConnection().getInputStream();
                byte[] imageData = getBytes(is);
                game.setImageBytes(imageData);
                return game;

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Game result) {
            DatabaseHelper.getInstance(context).insertGame(result);
        }
    }

    public void addItems(ArrayList<Game> games) {
        this.mValues.addAll(games);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_item, parent, false);
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
        holder.txtTitle.setText(mValues.get(position).getTitle());

        boolean isFavorited = DatabaseHelper.getInstance(holder.gameItemImg.getContext()).selectGame(mValues.get(position).getId()) != null;
        if (isFavorited) {
            holder.favoritedImg.setImageResource(R.drawable.ic_toggle_star);
        } else {
            holder.favoritedImg.setImageResource(R.drawable.ic_toggle_star_outline);
        }

        if (position == mValues.size() - 1) {
            holder.separator.setVisibility(View.INVISIBLE);
        } else {
            holder.separator.setVisibility(View.VISIBLE);
        }

        holder.favoritedImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("INFO", "Image Clicked");
                boolean isFavorited = DatabaseHelper.getInstance(holder.gameItemImg.getContext()).selectGame(mValues.get(position).getId()) != null;

                if (!isFavorited) {
                    holder.favoritedImg.setImageResource(R.drawable.ic_toggle_star);
                    new LoadImageFromURL().execute(holder.mItem);
                } else {
                    holder.favoritedImg.setImageResource(R.drawable.ic_toggle_star_outline);
                    DatabaseHelper.getInstance(holder.gameItemImg.getContext()).deleteGame(holder.mItem.getId());
                }
            }
        });
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
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
        public final TextView txtTitle;
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
            txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        }
    }

    public void clearData() {
        mValues.clear(); //clear list
    }
}
