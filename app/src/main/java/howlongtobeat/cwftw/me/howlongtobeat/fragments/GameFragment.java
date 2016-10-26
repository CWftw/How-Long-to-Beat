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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;

import howlongtobeat.cwftw.me.howlongtobeat.EmptyRecyclerView;
import howlongtobeat.cwftw.me.howlongtobeat.HLTBSearcher;
import howlongtobeat.cwftw.me.howlongtobeat.R;
import howlongtobeat.cwftw.me.howlongtobeat.ResultSet;
import howlongtobeat.cwftw.me.howlongtobeat.activities.MainActivity;
import howlongtobeat.cwftw.me.howlongtobeat.adapters.MyGameRecyclerViewAdapter;
import howlongtobeat.cwftw.me.howlongtobeat.models.Game;

/**
 * A fragment representing a list of Items.
 */
public class GameFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final int VISIBLE_THRESHOLD = 0;

    private SwipeRefreshLayout swipeContainer;
    private MyGameRecyclerViewAdapter adapter;
    private ResultSet results;
    private HLTBSearcher searcher;
    private EmptyRecyclerView recyclerView;

    private int mColumnCount = 2;
    private boolean isLoading = false;
    private boolean networkError = false;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GameFragment() {
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
        adapter.setCallback((MainActivity) getActivity());
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
                    int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                    // When scrolled to the end of a page, load next page
                    if (!isLoading && searcher.getPage() <= results.getPages()) {
                        if ((totalItemCount - visibleItemCount)
                                <= (firstVisibleItem + VISIBLE_THRESHOLD)) {
                            adapter.addItem(null);
                            adapter.notifyItemInserted(adapter.getItemCount() - 1);
                            new DownloadGames().execute();
                        }
                    }
                }
            });

            swipeContainer = (SwipeRefreshLayout) parent.findViewById(R.id.swipeContainer);
            // Setup refresh listener which triggers new data loading
            swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    search(searcher.getQuery());
                }
            });

            swipeContainer.setColorSchemeResources(R.color.colorPrimary);

        }
        return parent;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            Log.d("GameFragment", "Fragment is visible.");
            if (getView() != null) {
                adapter.notifyDataSetChanged();
            }
        } else {
            Log.d("GameFragment", "Fragment is not visible.");
        }
    }

    private void removeProgressbar() {
        if (adapter.getItemCount() > 0 && adapter.getItem(adapter.getItemCount() - 1) == null) {
            adapter.removeItem(adapter.getItemCount() - 1);
            adapter.notifyItemRemoved(adapter.getItemCount());
        }
    }

    private class DownloadGames extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            try {
                isLoading = true;
                searcher.setPage(searcher.getPage() + 1);
                results = searcher.search();
            } catch (IOException e) {
                networkError = true;
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
            if (results != null && !networkError) {
                // Remove progress indicator
                removeProgressbar();

                // Add next page, refresh list
                adapter.addItems(results.getPage());
                adapter.notifyDataSetChanged();

                // If on page 1, scroll to top
                if (searcher.getPage() == 1) {
                    recyclerView.scrollToPosition(0);
                }
            } else {
                searcher.setPage(searcher.getPage() - 1);
            }

            removeProgressbar();
            swipeContainer.setRefreshing(false);
            isLoading = false;
            networkError = false;
        }
    }
}
