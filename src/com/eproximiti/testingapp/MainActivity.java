package com.eproximiti.testingapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.widget.FrameLayout;

import com.eproximiti.testingapp.GamesFragment.OnGamesItemClickedListener;
import com.eproximiti.testingapp.model.Game;

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
		FragmentTransaction ft = fm.beginTransaction();
		if (isTwoPane) {
			if (fm.getBackStackEntryCount() == 0) {
				ft.add(R.id.game_fragment, frag);
			} else {
				ft.replace(R.id.game_fragment, frag);
			}
		} else {
			ft.add(frag, "gameDetail");
		}
		ft.commit();
	}

}
