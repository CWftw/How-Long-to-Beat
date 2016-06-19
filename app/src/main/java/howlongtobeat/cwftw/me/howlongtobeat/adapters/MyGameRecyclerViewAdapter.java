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
import android.widget.ProgressBar;
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

public class MyGameRecyclerViewAdapter extends RecyclerView.Adapter {

    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private final List<Game> mValues;
    private Context context;

    public MyGameRecyclerViewAdapter(List<Game> items, Context context) {
        mValues = items;
        this.context = context;
    }

    public void addItems(ArrayList<Game> games) {
        for (Game game : games) {
            if (!mValues.contains(game)) {
                mValues.add(game);
            }
        }
    }

    public void addItem(Game game) {
        this.mValues.add(game);
    }

    public void removeItem(int index) {
        this.mValues.remove(index);
    }

    public Game getItem(int index) {
        return this.mValues.get(index);
    }

    @Override
    public int getItemViewType(int position) {
        return mValues.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;

        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.game_item, parent, false);

            vh = new GameViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.layout_loading_item, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof GameViewHolder) {
            ((GameViewHolder) holder).mItem = mValues.get(position);

            // Asynchronously load image with Ion library
            Ion.with(((GameViewHolder) holder).gameItemImg)
                    // use a placeholder image
                    .placeholder(R.mipmap.ic_launcher)
                    // load the url
                    .load(mValues.get(position).getImageUrl());

            ((GameViewHolder) holder).mainStoryItem.setText(Utils.formatData(mValues.get(position).getMainHours(), Utils.FormatTypes.HOURS, ((GameViewHolder) holder).gameItemImg.getContext()));
            ((GameViewHolder) holder).extraItem.setText(Utils.formatData(mValues.get(position).getMainExtraHours(), Utils.FormatTypes.HOURS, ((GameViewHolder) holder).gameItemImg.getContext()));
            ((GameViewHolder) holder).completionistItem.setText(Utils.formatData(mValues.get(position).getCompletionistHours(), Utils.FormatTypes.HOURS, ((GameViewHolder) holder).gameItemImg.getContext()));
            ((GameViewHolder) holder).combinedItem.setText(Utils.formatData(mValues.get(position).getCombinedHours(), Utils.FormatTypes.HOURS, ((GameViewHolder) holder).gameItemImg.getContext()));
            ((GameViewHolder) holder).txtTitle.setText(mValues.get(position).getTitle());

            boolean isFavorited = DatabaseHelper.getInstance(((GameViewHolder) holder).gameItemImg.getContext()).selectGame(mValues.get(position).getId()) != null;
            if (isFavorited) {
                ((GameViewHolder) holder).favoritedImg.setImageResource(R.drawable.ic_toggle_star);
            } else {
                ((GameViewHolder) holder).favoritedImg.setImageResource(R.drawable.ic_toggle_star_outline);
            }

            if (position == mValues.size() - 1) {
                ((GameViewHolder) holder).separator.setVisibility(View.INVISIBLE);
            } else {
                ((GameViewHolder) holder).separator.setVisibility(View.VISIBLE);
            }

            ((GameViewHolder) holder).favoritedImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("INFO", "Image Clicked");
                    boolean isFavorited = DatabaseHelper.getInstance(((GameViewHolder) holder).gameItemImg.getContext()).selectGame(mValues.get(position).getId()) != null;

                    if (!isFavorited) {
                        ((GameViewHolder) holder).favoritedImg.setImageResource(R.drawable.ic_toggle_star);
                        new LoadImageFromURL().execute(((GameViewHolder) holder).mItem);
                    } else {
                        ((GameViewHolder) holder).favoritedImg.setImageResource(R.drawable.ic_toggle_star_outline);
                        DatabaseHelper.getInstance(((GameViewHolder) holder).gameItemImg.getContext()).deleteGame(((GameViewHolder) holder).mItem.getId());
                    }
                }
            });
        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
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

    public void clearData() {
        mValues.clear(); //clear list
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
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

    public class GameViewHolder extends RecyclerView.ViewHolder {
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

        public GameViewHolder(View view) {
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
}
