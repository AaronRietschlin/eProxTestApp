package com.eproximiti.testingapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eproximiti.testingapp.model.Game;

public class GameDetailFragment extends Fragment {

	private LayoutInflater mInflater;
	private Game mGame;

	public static final String EXTRA_GAME = "game";

	public interface OnGamesItemClickedListener {
		abstract void itemClicked(Game game);
	}

	public static GameDetailFragment newInstance(Game game) {
		GameDetailFragment frag = new GameDetailFragment();
		Bundle args = new Bundle();
		args.putParcelable(EXTRA_GAME, game);
		frag.setArguments(args);
		return frag;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		if (args != null) {
			mGame = args.getParcelable(EXTRA_GAME);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.game_fragment, null);
		mInflater = inflater;
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		View view = getView();

	}

}
