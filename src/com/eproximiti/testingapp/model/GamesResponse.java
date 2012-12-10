package com.eproximiti.testingapp.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * This class is used to handle the format of the API response.
 */
public class GamesResponse {

	@SerializedName("success")
	public boolean success;
	@SerializedName("games")
	public List<Game> games;

}
