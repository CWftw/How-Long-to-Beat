/*
 * Colin Willson & Matt Allen
 * Final Project, PROG3210
 * December 13, 2015
 *
 */

package howlongtobeat.cwftw.me.gamelength.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import howlongtobeat.cwftw.me.gamelength.HLTBSearcher;
import howlongtobeat.cwftw.me.gamelength.R;
import howlongtobeat.cwftw.me.gamelength.ResultSet;
import howlongtobeat.cwftw.me.gamelength.adapters.MyGameRecyclerViewAdapter;
import howlongtobeat.cwftw.me.gamelength.models.Game;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnGameFragmentInteractionListener}
 * interface.
 */
public class GameFragment extends Fragment
{
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 2;
    private OnGameFragmentInteractionListener mListener;
    private MyGameRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private ResultSet results;
    private HLTBSearcher searcher;

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
                adapter.setItems(results.getPage());
                adapter.notifyDataSetChanged();
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
        searcher.setQuery(query);
        new DownloadGames().execute();
        recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        adapter = new MyGameRecyclerViewAdapter(new ArrayList<Game>(), mListener, getActivity());
        searcher = new HLTBSearcher();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            Log.d("MyFragment", "Fragment is visible.");
            new DownloadGames().execute();
        }
        else {
            Log.d("MyFragment", "Fragment is not visible.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(adapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnGameFragmentInteractionListener) {
            mListener = (OnGameFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFavoriteFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnGameFragmentInteractionListener
    {
        void onGameFragmentInteraction(Game item);
    }
}
