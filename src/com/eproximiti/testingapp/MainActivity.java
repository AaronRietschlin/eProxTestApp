package com.eproximiti.testingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;

import com.eproximiti.testingapp.GamesFragment.OnGamesItemClickedListener;
import com.eproximiti.testingapp.model.Game;

public class MainActivity extends FragmentActivity implements
		OnGamesItemClickedListener {

	private boolean isTwoPane;
	private FrameLayout detailContainer;
	private Fragment gamesFragment;
	private FragmentManager fm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		detailContainer = (FrameLayout) findViewById(R.id.game_fragment);
		if (detailContainer != null) {
			isTwoPane = true;
		} else {
			isTwoPane = false;
		}

		fm = getSupportFragmentManager();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	/**
	 * Callback from the {@link GamesFragment}.
	 */
	@Override
	public void itemClicked(Game game) {
		GameDetailFragment frag = GameDetailFragment.newInstance(game);
		if (isTwoPane) {
			FragmentTransaction ft = fm.beginTransaction();
			ft.replace(R.id.game_fragment, frag);
			ft.commit();
		} else {
			Intent intent = new Intent(this, GameActivity.class);
			intent.putExtra(GameDetailFragment.EXTRA_GAME, game);
			startActivity(intent);
		}
	}
}
