/*
 * Colin Willson & Matt Allen
 * Final Project, PROG3210
 * December 13, 2015
 *
 */

package howlongtobeat.cwftw.me.howlongtobeat.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import howlongtobeat.cwftw.me.howlongtobeat.DatabaseHelper;
import howlongtobeat.cwftw.me.howlongtobeat.EmptyRecyclerView;
import howlongtobeat.cwftw.me.howlongtobeat.R;
import howlongtobeat.cwftw.me.howlongtobeat.adapters.MyFavoriteRecyclerViewAdapter;
import howlongtobeat.cwftw.me.howlongtobeat.models.Game;

/**
 * A fragment representing a list of Items.
 */
public class FavoriteFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private SwipeRefreshLayout swipeContainer;
    private int mColumnCount = 2;
    private MyFavoriteRecyclerViewAdapter adapter;
    private boolean isViewShown = false;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FavoriteFragment() {
    }

    @SuppressWarnings("unused")
    public static FavoriteFragment newInstance(int columnCount) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        adapter = new MyFavoriteRecyclerViewAdapter(new ArrayList<Game>());
    }

    public void updateList() {
        ArrayList<Game> games = DatabaseHelper.getInstance(getContext()).selectGames("");
        adapter.setItems(games);
        adapter.notifyDataSetChanged();
        swipeContainer.setRefreshing(false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            Log.d("FavoriteFragment", "Fragment is visible.");
            if (getView() != null) {
                isViewShown = true;
                updateList();
            } else {
                isViewShown = false;
            }
        } else {
            Log.d("FavoriteFragment", "Fragment is not visible.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_favorite_list, container, false);
        View view = parent.findViewById(R.id.list);

        // Set the adapter
        if (view instanceof EmptyRecyclerView) {
            Context context = view.getContext();
            EmptyRecyclerView recyclerView = (EmptyRecyclerView) view;
            recyclerView.setEmptyView(parent.findViewById(R.id.empty_favorites));
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(adapter);

            swipeContainer = (SwipeRefreshLayout) parent.findViewById(R.id.swipeContainer);
            // Setup refresh listener which triggers new data loading
            swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    updateList();
                }
            });

            swipeContainer.setColorSchemeResources(R.color.colorPrimary);

            if (!isViewShown) {
                updateList();
            }
        }
        return parent;
    }
}
