package com.eproximiti.testingapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.eproximiti.testingapp.model.Game;

/**
 * This fragment displays details about the {@link Game}: The name, description
 * and image.
 */
public class GameDetailFragment extends Fragment {

	private Game mGame;
	private TextView gameNameTv;
	private TextView gameDescripTv;
	private ImageView gameImageIv;
	private AQuery aq;

	public static final String EXTRA_GAME = "game";

	/**
	 * This is called when a game is clicked. Allows the Fragment and Activity
	 * to interact. The Activity must implement this for this to work.
	 */
	public interface OnGamesItemClickedListener {
		abstract void itemClicked(Game game);
	}

	/**
	 * Constructs an instance of this fragment with the given {@link Game} as
	 * the game to detail.
	 * 
	 * @param game
	 * @return
	 */
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

		aq = new AQuery(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.game_fragment, null);

		gameNameTv = (TextView) v.findViewById(R.id.game_detail_name);
		gameDescripTv = (TextView) v.findViewById(R.id.game_detail_description);
		gameImageIv = (ImageView) v.findViewById(R.id.game_detail_image);

		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setupUi();
	}

	/**
	 * Sets the {@link Game}'s data to the view widgets.
	 */
	private void setupUi() {
		gameNameTv.setText(mGame.name);
		gameDescripTv.setText(mGame.description);
		aq.id(gameImageIv).image(mGame.imageUrl, true, true);
	}

}
