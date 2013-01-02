package appx.craft.emoji;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import appx.craft.emoji.bean.ImageBean;
import appx.craft.emoji.ui.MainActivity;
import appx.craft.emoji.util.Const;
import appx.craft.emoji.util.JSONParser;
import appx.craft.emoji.util.Log;
import appx.craft.emoji.util.Utility;


// TODO: Auto-generated Javadoc
/**
 * The Class ChooseActivity.
 */
public class ChooseActivity extends Activity {

	/** The tag. */
	final String TAG = ChooseActivity.class.getCanonicalName();
	
	/** The m beanof icons. */
	ArrayList<ImageBean> mBeanofIcons = new ArrayList<ImageBean>();
	
	 // JSON Node names
	/** The Constant TAG_EMOJI_CATEGORY_ID. */
 	static final String TAG_EMOJI_CATEGORY_ID = "catID";
	
	/** The Constant TAG_EMOJI_UNICODE. */
	static final String TAG_EMOJI_UNICODE = "unicode";
	
	/** The Constant TAG_ICONS_ARRAY. */
	static final String TAG_ICONS_ARRAY = "icons";
	
	static final String TAG_HEX_CODE = "hexcode"; 
	
	/** The m progress dialog. */
	ProgressDialog mProgressDialog;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.debug(TAG, "[ onCreate ] ");
		mProgressDialog = new ProgressDialog(ChooseActivity.this);
		mProgressDialog.setMessage("Please wait....");
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.setCancelable(false);
		if(!Utility.verifySerializableFile(Const.FILE_EMOJI_ICONS_LIST)){
			new RetrieveIconsList().execute();
		}else{
			startActivity(new Intent(ChooseActivity.this,MainActivity.class));
			finish();
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();
		Log.debug(TAG, "[ onStart ] ");
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		super.onStop();
		Log.debug(TAG, "[ onStop ] ");
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		Log.debug(TAG, "[ onPause ] ");
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		Log.debug(TAG, "[ onResume ] ");
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.debug(TAG, "[ onDestroy ] ");
	}
	
	
	/**
	 * The Class RetrieveIconsList.
	 */
	class RetrieveIconsList extends AsyncTask<String,Void, Integer>{
		
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog.show();
		}
		
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Integer doInBackground(String... params) {
			int errorNo = 0;
			
			// JSONArray
		    JSONArray mEmojiIconsJsonArray = null;
		     // Creating JSON Parser instance
	        JSONParser jParser = new JSONParser();
	        // getting JSON string from local file
	        JSONObject mJson = jParser.getJSONFromLocal(ChooseActivity.this,R.raw.emojis_json);
	        
	        try {
	        	
                // Getting Array of icons tag
	            mEmojiIconsJsonArray = mJson.getJSONArray(TAG_ICONS_ARRAY);
	            // create object of ImageBean
	            ImageBean mBeanofEmojiIcons;
	            // looping through All icons
	            for(int i = 0; i < mEmojiIconsJsonArray.length(); i++){
	            	
	            	mBeanofEmojiIcons = new ImageBean();
	                JSONObject mObjectMessage = mEmojiIconsJsonArray.getJSONObject(i);
	                
	            	// Storing each json item in variable
	                String mIconName = mObjectMessage.getString(TAG_EMOJI_UNICODE);
	                String mID = mObjectMessage.getString(TAG_EMOJI_CATEGORY_ID);
	                String mHexCode = mObjectMessage.getString(TAG_HEX_CODE);
	                
	                mBeanofEmojiIcons.setmTag(mIconName);
	                mBeanofEmojiIcons.setCategory_id(mID);
	                mBeanofEmojiIcons.setHex_code(mHexCode);
	                
	                //finally added inside array list 
	                mBeanofIcons.add(mBeanofEmojiIcons);
	            }
	            
	            Utility.writeObject(Const.FILE_EMOJI_ICONS_LIST,mBeanofIcons);
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	        
			return errorNo;
		}
		
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			mProgressDialog.dismiss();
			if(result == 0){
				startActivity(new Intent(ChooseActivity.this,MainActivity.class));
				finish();
			}
		}
		
	}
}
