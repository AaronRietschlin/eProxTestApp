package com.eproximiti.testingapp;

import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import com.eproximiti.testingapp.model.Game;

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
			ActionBar ab = getActionBar();
			ab.setDisplayHomeAsUpEnabled(true);
			ab.setHomeButtonEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (canUseActionBar()) {
			if (item.getItemId() == android.R.id.home) {
				finish();
			}
		}
		return true;
	}

	public boolean canUseActionBar() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}

}
