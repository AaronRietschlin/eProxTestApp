package com.eproximiti.testingapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.eproximiti.testingapp.model.API;
import com.eproximiti.testingapp.model.Game;
import com.eproximiti.testingapp.model.GamesResponse;
import com.google.gson.Gson;

public class GamesFragment extends ListFragment {

	private GamesAdapter mAdapter;
	private OnGamesItemClickedListener mActivity;
	private LayoutInflater mInflater;

	public interface OnGamesItemClickedListener {
		abstract void itemClicked(Game game);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = (OnGamesItemClickedListener) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mAdapter = new GamesAdapter(getActivity(), new ArrayList<Game>());
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
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// Retrieve the game item and set the TextViews with the data
			// retrieved
			Game game = items.get(position);
			holder.name.setText(game.name);
			holder.description.setText(game.description);

			return convertView;
		}

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

	private class GetGamesTask extends AsyncTask<Void, Void, List<Game>> {

		@Override
		protected List<Game> doInBackground(Void... params) {
			String response = API.getListOfGames();
			Gson gson = new Gson();
			GamesResponse gameResponse = gson.fromJson(response,
					GamesResponse.class);
			return gameResponse.games;
		}

		@Override
		protected void onPostExecute(List<Game> games) {
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
