/*
 * Colin Willson & Matt Allen
 * Final Project, PROG3210
 * December 13, 2015
 *
 */

package howlongtobeat.cwftw.me.howlongtobeat.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import howlongtobeat.cwftw.me.howlongtobeat.EmptyRecyclerView;
import howlongtobeat.cwftw.me.howlongtobeat.HLTBSearcher;
import howlongtobeat.cwftw.me.howlongtobeat.R;
import howlongtobeat.cwftw.me.howlongtobeat.ResultSet;
import howlongtobeat.cwftw.me.howlongtobeat.adapters.MyGameRecyclerViewAdapter;
import howlongtobeat.cwftw.me.howlongtobeat.models.Game;

/**
 * A fragment representing a list of Items.
 */
public class GameFragment extends Fragment
{
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 2;
    private MyGameRecyclerViewAdapter adapter;
    private ResultSet results;
    private HLTBSearcher searcher;
    private EmptyRecyclerView recyclerView;
    private boolean isLoading = false;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GameFragment() {
    }

    private class DownloadGames extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            try {
                isLoading = true;
                searcher.setPage(searcher.getPage() + 1);
                results = searcher.search();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            if (results != null) {
                adapter.addItems(results.getPage());
                adapter.notifyDataSetChanged();
                isLoading = false;

                if (searcher.getPage() == 1) {
                    recyclerView.scrollToPosition(0);
                }
            } else {
                Toast.makeText(getContext(), getResources().getString(R.string.nonet),Toast.LENGTH_LONG).show();
            }
        }
    }

    @SuppressWarnings("unused")
    public static GameFragment newInstance(int columnCount) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    public void search(String query) {
        adapter.clearData();
        adapter.notifyDataSetChanged();
        searcher.setQuery(query);
        new DownloadGames().execute();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        adapter = new MyGameRecyclerViewAdapter(new ArrayList<Game>(), getActivity());
        searcher = new HLTBSearcher();

        new DownloadGames().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_game_list, container, false);
        View view = parent.findViewById(R.id.list);

        // Set the adapter
        if (view instanceof EmptyRecyclerView) {
            Context context = view.getContext();
            recyclerView = (EmptyRecyclerView) view;
            recyclerView.setEmptyView(parent.findViewById(R.id.empty_games));
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(adapter);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                    if (!isLoading && searcher.getPage() <= results.getPages()) {
                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                                && firstVisibleItemPosition >= 0) {
                            new DownloadGames().execute();
                        }
                    }
                }
            });
        }
        return parent;
    }
}
