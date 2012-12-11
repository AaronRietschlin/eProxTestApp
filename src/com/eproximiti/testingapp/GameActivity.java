package com.eproximiti.testingapp;

import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import com.eproximiti.testingapp.model.Game;

/**
 * This Activity is used only on smaller devices. This houses
 * {@link GameDetailFragment} and is launched when the user clicks a game.
 */
public class GameActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_activity);

		Bundle extras = getIntent().getExtras();
		Game game = extras.getParcelable(GameDetailFragment.EXTRA_GAME);

		FragmentManager fm = getSupportFragmentManager();
		fm.beginTransaction()
				.add(R.id.fragment_container,
						GameDetailFragment.newInstance(game)).commit();

		if (canUseActionBar()) {
			// If the device has an actionbar, set the home as up enabled so
			// that we can move back to the games activity by clicking the "Up"
			// button.
			ActionBar ab = getActionBar();
			ab.setDisplayHomeAsUpEnabled(true);
			ab.setHomeButtonEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (canUseActionBar()) {
			// If the "Up" button is clicked, go back to the previous screen.
			if (item.getItemId() == android.R.id.home) {
				finish();
			}
		}
		return true;
	}

	/**
	 * Determins if this device is Honeycomb or greater (thus, can use the
	 * ActionBar).
	 */
	public boolean canUseActionBar() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}

}
