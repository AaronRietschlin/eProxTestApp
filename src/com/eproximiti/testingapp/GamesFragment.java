package com.eproximiti.testingapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.eproximiti.testingapp.model.API;
import com.eproximiti.testingapp.model.Game;
import com.eproximiti.testingapp.model.GamesResponse;
import com.google.gson.Gson;

/**
 * Shows a list of {@link Game}'s.
 */
public class GamesFragment extends ListFragment {

	private GamesAdapter mAdapter;
	private OnGamesItemClickedListener mActivity;
	private LayoutInflater mInflater;
	/**
	 * AQuery is a very helpful library that helps download and cache images
	 * (among many other things).
	 */
	private AQuery aq;

	public interface OnGamesItemClickedListener {
		abstract void itemClicked(Game game);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = (OnGamesItemClickedListener) activity;
		aq = new AQuery(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mAdapter = new GamesAdapter(getActivity(), new ArrayList<Game>());
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.list_fragment, null);
		mInflater = inflater;

		setListAdapter(mAdapter);

		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		new GetGamesTask().execute();
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				mActivity.itemClicked(mAdapter.getItem(position));
			}
		});
	}

	/**
	 * The {@link ArrayAdapter} that binds the list of {@link Game}'s to the
	 * ListView. This is how the OS knows how to display the items.
	 */
	private class GamesAdapter extends ArrayAdapter<Game> {

		private List<Game> items;

		/**
		 * This is a class that helps implement the ViewHolder pattern. This
		 * helps optimize the ListView by reducing the
		 * {@link View#findViewById(int)} (which can be expensive).
		 */
		class ViewHolder {
			ImageView image;
			TextView name;
			TextView description;
			ProgressBar progress;
		}

		public GamesAdapter(Context context, List<Game> objects) {
			super(context, 0, objects);
			items = objects;
		}

		@Override
		public int getCount() {
			return items == null ? 0 : items.size();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			// Android recycles views, thus we check if it's null. If it isn't,
			// it's being recycled.
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.game_row_item, null);
				// Set up the Viewholder object and initialize the widgets here.
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.game_item_image);
				holder.name = (TextView) convertView
						.findViewById(R.id.game_item_name);
				holder.description = (TextView) convertView
						.findViewById(R.id.game_item_descrip);
				holder.progress = (ProgressBar) convertView
						.findViewById(R.id.game_item_progress);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// Retrieve the game item and set the TextViews with the data
			// retrieved
			Game game = items.get(position);
			holder.name.setText(game.name);
			holder.description.setText(game.description);
			setImage(holder, game);

			return convertView;
		}

		/**
		 * Sets the rows image.
		 * 
		 * @param holder
		 * @param game
		 */
		private void setImage(final ViewHolder holder, Game game) {
			// Use AQuery to get/cache the image.
			aq.id(holder.image)
					.progress(holder.progress)
					.image(game.imageUrl, true, true)
					.image(game.imageUrl, true, true, 0, 0,
							new BitmapAjaxCallback() {
								@Override
								public void callback(String url, ImageView iv,
										Bitmap bm, AjaxStatus status) {
									// A callback for AQuery that allows you to
									// do something with the image. There was an
									// error where the ProgressBar wasn't
									// disappearing. Thus, we set that here if
									// it's visible.
									if (holder.progress.getVisibility() == View.VISIBLE)
										holder.progress
												.setVisibility(View.GONE);
									holder.image.setImageBitmap(bm);
								}
							});
		}

		/**
		 * Adds all of the given Items to the list binded to the adapter.
		 * 
		 * @param list
		 *            The list to add.
		 */
		public void addAll(List<Game> list) {
			if (items == null) {
				items = new ArrayList<Game>();
			} else {
				items.removeAll(items);
			}
			items.addAll(list);
			notifyDataSetChanged();
		}

	}

	/**
	 * An AsyncTask that retrieves the list of Games. You do not want to make
	 * network calls on the Main/UI thread, thus spawning a new thread is
	 * necessary. {@link AsyncTask}'s are a great way to do that.
	 */
	private class GetGamesTask extends AsyncTask<Void, Void, List<Game>> {

		@Override
		protected List<Game> doInBackground(Void... params) {
			String response = API.getListOfGames();
			// Gson is a JSON library for Java written by Google. We use this to
			// parse API web service responses
			Gson gson = new Gson();
			GamesResponse gameResponse = gson.fromJson(response,
					GamesResponse.class);
			if (gameResponse == null)
				return null;
			return gameResponse.games;
		}

		@Override
		protected void onPostExecute(List<Game> games) {
			// If the response is null, then something went wrong.
			if (games == null) {
				Toast.makeText(getActivity(),
						"There was an error getting the games.",
						Toast.LENGTH_SHORT).show();
				return;
			}
			mAdapter.addAll(games);
		}
	}

}
