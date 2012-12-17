package appx.craft.emoji.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

// TODO: Auto-generated Javadoc
/**
 * The Class JSONParser.
 */
public class JSONParser {
	
	/** The tag. */
	private String TAG = JSONParser.class.getCanonicalName();
	
	/** The is. */
	static InputStream is = null;
	
	/** The j obj. */
	static JSONObject jObj = null;
	
	/** The json. */
	static String json = "";

	// constructor
	/**
	 * Instantiates a new jSON parser.
	 */
	public JSONParser() {

	}

	/**
	 * Gets the jSON from url.
	 *
	 * @param url the url
	 * @param content the content
	 * @return the jSON from url
	 */
	public JSONObject getJSONFromUrl(String url,String content) {

		// Making HTTP request
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader( "Content-Type", "application/json" );
			
			
			JSONObject mJSONObject = new JSONObject();    
			if(content != null)
			mJSONObject.put(Const.CONTENT,content);
			
			Log.debug(TAG, mJSONObject.toString());
			
			StringEntity se = new StringEntity(mJSONObject.toString());
			se.setContentEncoding("UTF-8");
		    se.setContentType("application/json");
		    
		    httpPost.setEntity(se); 
			
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
		} catch (Exception e) {
			Log.debug("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			Log.debug("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		return jObj;

	}
	
	/**
	 * For Pare Local JSON File.
	 *
	 * @param mContext Activity Object
	 * @param RawfileName Name of Raw File like [  R.raw.emojis_json ]
	 * @return JSONObject
	 */
	public JSONObject getJSONFromLocal(Context mContext,int RawfileName){
		try {
			
        	//Load File
			BufferedReader jsonReader = new BufferedReader(new InputStreamReader(mContext.getResources().openRawResource(RawfileName)));
			StringBuilder jsonBuilder = new StringBuilder();
			for (String line = null; (line = jsonReader.readLine()) != null;) {
				jsonBuilder.append(line).append("\n");
			}
			json = jsonBuilder.toString();
			jsonReader.close();
			
			if(jObj == null){
				// try parse the string to a JSON object
				try {
					jObj = new JSONObject(json);
				} catch (JSONException e) {
					Log.debug("JSON Parser", "Error parsing data " + e.toString());
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}	
		return jObj;
	}
}
