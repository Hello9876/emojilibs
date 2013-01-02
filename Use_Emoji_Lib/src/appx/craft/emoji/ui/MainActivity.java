package appx.craft.emoji.ui;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import appx.craft.emoji.R;
import appx.craft.emoji.bean.ContentBean;
import appx.craft.emoji.bean.ImageBean;
import appx.craft.emoji.uc.AdapterMessageList;
import appx.craft.emoji.uc.EmojiEditText;
import appx.craft.emoji.uc.EmojiTextView;
import appx.craft.emoji.uc.IconPopUpWindow;
import appx.craft.emoji.uc.OnHeadNavigationSelectedListener;
import appx.craft.emoji.util.Const;
import appx.craft.emoji.util.JSONParser;
import appx.craft.emoji.util.Log;
import appx.craft.emoji.util.Utility;

// TODO: Auto-generated Javadoc
/**
 * The Class MainActivity.
 */
public class MainActivity extends Activity implements OnHeadNavigationSelectedListener{
	
	/** The tag. */
	final String TAG = MainActivity.class.getCanonicalName(); 
	
	/** The emoji text view1. */
	EmojiTextView emojiTextView1;
	
	/** The emoji edit text. */
	EmojiEditText emojiEditText;
	
	/** The insert_button. */
	Button insert_button;
	
	/** The add_button. */
	Button add_button;
	
	/** The m pop window. */
	IconPopUpWindow mPopWindow;
	
	/** The panel_mainwindow. */
	RelativeLayout panel_mainwindow;
	
	/** The arr list image bean. */
	ArrayList<ImageBean> arrListImageBean;
	
	/** The list_message_box. */
	ListView list_message_box;
	
	/** The m message adapter. */
	AdapterMessageList mMessageAdapter;
	
	/** The m progress dialog. */
	ProgressDialog mProgressDialog;
	
	/** The m point. */
	Point mPoint;
	
	
	 // JSON Node names
    /** The Constant TAG_CONTENT_ARRAY. */
 	private static final String TAG_CONTENT_ARRAY = "Content";
    
    /** The Constant TAG_MESSAGE. */
    private static final String TAG_MESSAGE = "content";
    
    /** The Constant TAG_TITLE. */
    private static final String TAG_TITLE = "title";
    
    /** The Constant TAG_STATUS. */
    private static final String TAG_STATUS = "status";
    
    /** The Constant TAG_STATUS_MESSAGE. */
    private static final String TAG_STATUS_MESSAGE = "statusmsg";
    
    /** The m content array. */
    ArrayList<ContentBean> mContentArray = null;
    
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Const.CONTEXT = MainActivity.this;
        
        mProgressDialog = new ProgressDialog(MainActivity.this);
		mProgressDialog.setMessage("Please wait....");
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.setCancelable(false);
		
        arrListImageBean = new ArrayList<ImageBean>();
        mPopWindow = new IconPopUpWindow(MainActivity.this);
    	
        panel_mainwindow = (RelativeLayout)findViewById(R.id.panel_mainwindow);
        
        emojiEditText = (EmojiEditText)findViewById(R.id.emojiEditText);
        
        emojiEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					mMessageAdapter.addItemArrayList(mContentArray);
					list_message_box.post(new Runnable() {
						public void run() {
							list_message_box.setSelection(list_message_box.getCount() - 1);
						}
					});
				}
			}
		});
        emojiEditText.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				if(MotionEvent.ACTION_UP == event.getAction()){
				   new Handler().postDelayed(new Runnable() {
						
						@Override
						public void run() {
							
							mMessageAdapter.addItemArrayList(mContentArray);
							list_message_box.post(new Runnable() {
								public void run() {
									list_message_box.setSelection(list_message_box.getCount() - 1);
								}
							});
						}
					},1000);
				}
					
				
				return false;
			}
		});
        emojiEditText.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
	                int lineCount = ((EditText)v).getText().toString().split("\\n").length;
	                if (lineCount > 5) {
	                	return false;
	                }
	            }
				return false;
			}
		});
      
        
        insert_button = (Button)findViewById(R.id.insert_button);
        insert_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(mPopWindow != null){
					mPopWindow.showhideIconpopUp(insert_button,mPoint);
				}
				
			}
		});
        
        add_button = (Button)findViewById(R.id.add_button);
        add_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(Utility.isOnline()){
					
//					Toast.makeText(MainActivity.this,emojiEditText.getText().toString(), Toast.LENGTH_SHORT).show();
					if(emojiEditText.validEditText()){
							new AddContentData().execute(new String[]{emojiEditText.getEmojiText().toString().trim()});
					}else{
						Toast.makeText(MainActivity.this,"message box is empty", Toast.LENGTH_SHORT).show();
					}
					
				}else{
					Toast.makeText(MainActivity.this,"Please check internet connection !!!", Toast.LENGTH_SHORT).show();
				}
				
			
			}
		});
        
        list_message_box = (ListView)findViewById(R.id.list_message_box);
        list_message_box.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				if(MotionEvent.ACTION_UP == event.getAction()){
					
					if(mPopWindow != null){
						
						if(mPopWindow.isShowing()){
							mPopWindow.closePopUp();	
						}else{
							hideKeyBoard(emojiEditText);
						}
						
					}
				}
				
			
				return false;
			}
		});
        mMessageAdapter  = new AdapterMessageList(MainActivity.this);
        list_message_box.setAdapter(mMessageAdapter);
        
        if(Utility.isOnline()){
        	new RetrieveContent().execute();	
        }else{
    		Toast.makeText(MainActivity.this,"Please check internet connection !!!", Toast.LENGTH_SHORT).show();
        }
        
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume() {
    	super.onResume();
    }
    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if(mPopWindow.isShowing()){
			mPopWindow.closePopUp();
			return true;
		}else{
			return super.onKeyDown(keyCode, event);
		}
    	
    }
   
    /* (non-Javadoc)
     * @see android.app.Activity#onDestroy()
     */
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	if(mPopWindow != null){
    		mPopWindow.closePopUp();
		}
    }

	
	/**
	 * The Class RetrieveContent.
	 */
	class RetrieveContent extends AsyncTask<String,Void, Integer>{
		
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog.show();
			mContentArray = new ArrayList<ContentBean>();
		}
		
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Integer doInBackground(String... params) {
			int errorNo = 0;
			
			// contacts JSONArray
		    JSONArray mContentJsonArray = null;
		     // Creating JSON Parser instance
	        JSONParser jParser = new JSONParser();
	        // getting JSON string from URL
	        JSONObject mJson = jParser.getJSONFromUrl(Const.OPERATION_GET_CONTENT,null);
	        
	        try {
	            // Getting Array of contents
	            mContentJsonArray = mJson.getJSONArray(TAG_CONTENT_ARRAY);
	            ContentBean mBeanofContent;
	            // looping through All contents
	            for(int i = 0; i < mContentJsonArray.length(); i++){
	            	
	            	mBeanofContent = new ContentBean();
	            	JSONObject mObjectMessage = mContentJsonArray.getJSONObject(i);
	 
	            	// Storing each json item in variable
	                String mContent = mObjectMessage.getString(TAG_MESSAGE);
	                String mMessage = mObjectMessage.getString(TAG_TITLE);
	                
	                mBeanofContent.setContent(mContent);
	                mBeanofContent.setTitle(mMessage);
	                
	                //finally added inside array list 
	                mContentArray.add(mBeanofContent);
            }
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
				
				Log.debug(TAG,"ContentArray Size " + mContentArray.size());
				mMessageAdapter.addItemArrayList(mContentArray);

				list_message_box.post(new Runnable() {
					public void run() {
						list_message_box.setSelection(list_message_box.getCount() - 1);
					}
				});
			}
		}
		
	}
	
	/**
	 * The Class AddContentData.
	 */
	class AddContentData extends AsyncTask<String, Void, Integer>{
		
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog.show();
			emojiEditText.setText(null);
		}
		
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Integer doInBackground(String... params) {
			int errorNo = 0;
		
			 // Creating JSON Parser instance
	        JSONParser jParser = new JSONParser();
	        // getting JSON string from URL
	        JSONObject mJson = jParser.getJSONFromUrl(Const.OPERATION_ADD_CONTENT,params[0]);
	        
	        try {
	            // Getting Array of contents
	        	  String mStatus= mJson.getString(TAG_STATUS);
//	        	  String mMessage =mJson.getString(TAG_STATUS_MESSAGE);
	        	  
	        	  if(mStatus.equalsIgnoreCase("ok")){
	        		  return errorNo;
	        	  }else{
	        		  return errorNo;
	        	  }
	        	  
	        	  
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
			if(result ==0){
				 new RetrieveContent().execute();
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onWindowFocusChanged(boolean)
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {

	   int[] location = new int[2];
	   Button button = (Button) findViewById(R.id.insert_button);

	   // Get the x, y location and store it in the location[] array
	   // location[0] = x, location[1] = y.
	   button.getLocationOnScreen(location);

	   //Initialize the Point with x, and y positions
	   mPoint = new Point();
	   mPoint.x = location[0];
	   mPoint.y = location[1];
	}
	
	/**
	 * Hide key board.
	 *
	 * @param editTextTemp the edit text temp
	 */
	public void hideKeyBoard(EditText editTextTemp){
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		if(editTextTemp != null){
			imm.hideSoftInputFromWindow(editTextTemp.getWindowToken(), 0);
		}
	}

	@Override
	public void onButtonSelected(ImageBean objBean) {
		emojiEditText.setAddEmojiText(objBean);
		if(mPopWindow != null){
    		mPopWindow.closePopUp();
		}

	}
	

}
