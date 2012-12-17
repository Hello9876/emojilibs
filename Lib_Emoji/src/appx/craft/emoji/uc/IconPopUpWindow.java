package appx.craft.emoji.uc;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;
import appx.craft.emoji.R;
import appx.craft.emoji.bean.ImageBean;
import appx.craft.emoji.util.Const;
import appx.craft.emoji.util.Log;
import appx.craft.emoji.util.Utility;

// TODO: Auto-generated Javadoc
/**
 * The Class IconPopUpWindow.
 */
public class IconPopUpWindow extends PopupWindow implements OnClickListener{
	
	/** The location. */
	int location[] = new int[2];
	
	/** The m context. */
	Context mContext;
	
	/** The gridview_iconlist. */
	GridView gridview_iconlist;
	
	/** The m adapter emoji. */
	AdapterEmojiIcons mAdapterEmoji;
	
	/** The arr list image bean. */
	ArrayList<ImageBean> arrListImageBean;
	
	/** The m activity. */
	Activity mActivity;
	
	/** The m callback. */
	OnHeadNavigationSelectedListener mCallback;
	
	/** The m handler obj. */
	EmojiHandler mHandlerObj = new EmojiHandler();
	
	/** The btn_car. */
	Button btn_smily,btn_flower,btn_bell,btn_car,btn_emoji_symbols;
	
	/** The Constant UPDATE_ADAPTER_EMOJI. */
	static final int UPDATE_ADAPTER_EMOJI = 0;
	
	/** The m content view. */
	View mContentView;
	
	/**
	 * Instantiates a new icon pop up window.
	 *
	 * @param mContext the m context
	 */
	public IconPopUpWindow(Context mContext) {
		super(mContext);
		this.mContext = mContext;
		mActivity = (Activity)mContext;
		mCallback = (OnHeadNavigationSelectedListener)mContext;
		
		LayoutInflater mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mContentView = mInflater.inflate(R.layout.icon_popup_window,null, false);
		
		arrListImageBean = new ArrayList<ImageBean>();
		mAdapterEmoji = new AdapterEmojiIcons((Activity)mContext);
		gridview_iconlist = (GridView) mContentView.findViewById(R.id.gridview_iconlist);	
		gridview_iconlist.setAdapter(mAdapterEmoji);
		
		btn_smily = (Button)mContentView.findViewById(R.id.btn_smily);
		btn_smily.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.emoji_smile_green));
		btn_smily.setOnClickListener(this);
		btn_flower = (Button)mContentView.findViewById(R.id.btn_flower);
		btn_flower.setOnClickListener(this);
		btn_bell = (Button)mContentView.findViewById(R.id.btn_bell);
		btn_bell.setOnClickListener(this);
		btn_car = (Button)mContentView.findViewById(R.id.btn_car);
		btn_car.setOnClickListener(this);
		btn_emoji_symbols = (Button)mContentView.findViewById(R.id.btn_emoji_symbols_green);
		btn_emoji_symbols.setOnClickListener(this);
		
		setContentView(mContentView);
		setWidth(WindowManager.LayoutParams.MATCH_PARENT);
		setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		setAnimationStyle(android.R.style.Widget_PopupWindow);
		
		new LoadArrayEmoji().execute();
		
	}
	
	/**
	 * Showhide iconpop up.
	 *
	 * @param anchor the anchor
	 * @param mPoint the m point
	 */
	public void showhideIconpopUp(View anchor,Point mPoint){
		if(!isShowing()){
			int OFFSET_X = 30;
			int OFFSET_Y = 30;
			mActivity.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					mAdapterEmoji.addItemArrayList(arrListImageBean,1);
				}
			});
			
			changebgofIcons(1);
			showAtLocation(anchor,Gravity.LEFT, mPoint.x + OFFSET_X,mPoint.y + OFFSET_Y);
			
		}else{
			
			dismiss();
		}
			
	}
	
	/**
	 * Show as drop icon pop up.
	 *
	 * @param anchor the anchor
	 */
	public void showAsDropIconPopUp(View anchor){
		showAsDropDown(anchor, 0, 0);
	}
	
	/**
	 * Close pop up.
	 */
	public void closePopUp(){
		if(isShowing()){
			dismiss();
		}
		
	}

	
	

	/**
	 * The Class LoadArrayEmoji.
	 */
	class LoadArrayEmoji extends AsyncTask<Void,Void,Integer>{

		/** The index. */
		int index = 0;
		
		/** The temp bean array. */
		ArrayList<ImageBean> tempBeanArray;
		
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			tempBeanArray = new ArrayList<ImageBean>();
			
		}
		
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Integer doInBackground(Void... params) {
			
			
	    	new Thread(new Runnable() {
	    		@Override
				public void run() {
	    			
	    			//generate three category icons
					ArrayList<ImageBean> readArrayList = Utility.readObject(Const.FILE_EMOJI_ICONS_LIST);
					for (int k = 0; k < readArrayList.size() ; k++) {
						tempBeanArray.add(readArrayList.get(k));
						
						if(index == 3){
							index = 0;
							synchronized(arrListImageBean)
							{
								
								for(ImageBean mTempBean : tempBeanArray){
									arrListImageBean.add(mTempBean);
								}
								tempBeanArray.clear();
								mHandlerObj.sendEmptyMessage(UPDATE_ADAPTER_EMOJI);
							}
							
						}
						index++;
					}
				    
				    
				}
			}).start();
			return null;
		}
		
		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			
		}
		
	}
	
	/**
	 * The Class EmojiHandler.
	 */
	class EmojiHandler extends Handler{

		/* (non-Javadoc)
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
				
			switch (msg.what) {
            case UPDATE_ADAPTER_EMOJI :
            	Log.println("[ ONE MESSAGE COMMING ");
            	//mAdapterEmoji.addItemArrayList(arrListImageBean);
            	break;
            default:
                super.handleMessage(msg);
			}
		}
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(final View v) {
		
		if(v instanceof Button){
			changebgofIcons(Integer.parseInt(v.getTag().toString()));
			mActivity.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					mAdapterEmoji.addItemArrayList(arrListImageBean,Integer.parseInt(v.getTag().toString()));
				}
			});
			
			new Handler().postDelayed(new Runnable() {
				
				@SuppressLint("NewApi")
				@Override
				public void run() {
					gridview_iconlist.smoothScrollToPositionFromTop(0,0);
					gridview_iconlist.invalidate();
				}
			},300);
			
		}
		
	}
	
	/**
	 * Changebgof icons.
	 *
	 * @param tagID the tag id
	 */
	@SuppressWarnings("deprecation")
	public void changebgofIcons(int tagID){
		
		if(tagID==1){
			btn_smily.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.emoji_smile_green));
			btn_flower.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.emoji_flower));
			btn_bell.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.emoji_bell));
			btn_car.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.emoji_car));
			btn_emoji_symbols.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.emoji_symbols));
		}else if(tagID == 2){
			btn_smily.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.emoji_smile));
			btn_flower.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.emoji_flower_green));
			btn_bell.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.emoji_bell));
			btn_car.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.emoji_car));
			btn_emoji_symbols.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.emoji_symbols));
		}else if(tagID == 3){
			btn_smily.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.emoji_smile));
			btn_flower.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.emoji_flower));
			btn_bell.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.emoji_bell_green));
			btn_car.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.emoji_car));
			btn_emoji_symbols.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.emoji_symbols));
		}else if(tagID == 4){
			btn_smily.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.emoji_smile));
			btn_flower.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.emoji_flower));
			btn_bell.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.emoji_bell));
			btn_car.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.emoji_car_green));
			btn_emoji_symbols.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.emoji_symbols));
		}else if(tagID == 5){
			btn_smily.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.emoji_smile));
			btn_flower.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.emoji_flower));
			btn_bell.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.emoji_bell));
			btn_car.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.emoji_car));
			btn_emoji_symbols.setBackgroundDrawable(mActivity.getResources().getDrawable(R.drawable.emoji_symbols_green));
		}
	}


	
}
