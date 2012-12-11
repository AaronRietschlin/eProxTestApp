package com.eproximiti.testingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.widget.FrameLayout;

import com.eproximiti.testingapp.GamesFragment.OnGamesItemClickedListener;
import com.eproximiti.testingapp.model.Game;

/**
 * This is the main activity that is used. It has different roles based the
 * device.
 * <ul>
 * <li>Phones (single pane) devices - It houses just the {@link GamesFragment}.
 * When clicking on a game, a new activity is launched.</li>
 * <li>Tablet (double pane) devices - It houses two tablets,
 * {@link GamesFragment} and {@link GameDetailFragment}. GameDetailFragment is
 * not shown at first. When clicking on a game, that game is loaded in the other
 * screen.</li>
 * </ul>
 */
public class MainActivity extends FragmentActivity implements
		OnGamesItemClickedListener {

	private boolean isTwoPane;
	private FrameLayout detailContainer;
	private FragmentManager fm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		detailContainer = (FrameLayout) findViewById(R.id.game_fragment);
		// Since we are using two different layouts depending on the layout,
		// this is how we programmatically determine if we should use a two pane
		// layout. The R.id.game_fragment is only visible in the layout-sw600dp
		// res folder.
		if (detailContainer != null) {
			isTwoPane = true;
		} else {
			isTwoPane = false;
		}

		fm = getSupportFragmentManager();
	}
	/**
	 * Callback from the {@link GamesFragment}.
	 */
	@Override
	public void itemClicked(Game game) {
		GameDetailFragment frag = GameDetailFragment.newInstance(game);
		if (isTwoPane) {
			// Since there is two panes, simply show the fragment in the second
			// pane
			FragmentTransaction ft = fm.beginTransaction();
			ft.replace(R.id.game_fragment, frag);
			ft.commit();
		} else {
			// Let a new activity show the fragment.
			Intent intent = new Intent(this, GameActivity.class);
			intent.putExtra(GameDetailFragment.EXTRA_GAME, game);
			startActivity(intent);
		}
	}
}
