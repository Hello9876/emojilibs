package appx.craft.emoji.uc;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import appx.craft.emoji.R;
import appx.craft.emoji.bean.ImageBean;
import appx.craft.emoji.util.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class AdapterEmojiIcons.
 */
public class AdapterEmojiIcons extends BaseAdapter{
	
	/** The m inflater. */
	public LayoutInflater mInflater;
	
	/** The m activity. */
	public Activity mActivity;
	
	/** The m category_one. */
	public ArrayList<ImageBean> mCategory_one;
	
	/** The m callback. */
	OnHeadNavigationSelectedListener mCallback;
	
    /**
     * The Class ViewHolder.
     */
    public static class ViewHolder{
        
        /** The m image view. */
        public ImageView mImageView;
    }
    
	/**
	 * Instantiates a new adapter emoji icons.
	 *
	 * @param mActivity the m activity
	 */
	public AdapterEmojiIcons(Activity mActivity) {
		this.mActivity = mActivity;
		mCallback = (OnHeadNavigationSelectedListener)mActivity;
		mCategory_one = new ArrayList<ImageBean>();
	
		mInflater = (LayoutInflater)(this.mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
	}
	
	/**
	 * Adds the item array list.
	 *
	 * @param mArrayListBean the m array list bean
	 * @param index the index
	 */
	public void addItemArrayList(ArrayList<ImageBean> mArrayListBean, int index){
		if(mArrayListBean == null)
		return;
		if(!mCategory_one.isEmpty()){
			mCategory_one.clear();
		}
		for(ImageBean mBeanofImage : mArrayListBean){
			
			if(mBeanofImage.getCategory_id().equalsIgnoreCase(String.valueOf(index))){
				mCategory_one.add(mBeanofImage);
				notifyDataSetChanged();
			}
		}
		
		
		
	}
	
	/**
	 * Removes the all iteams.
	 */
	public void removeAllIteams(){
		if(mCategory_one != null && mCategory_one.size() > 0)
		{
			mCategory_one.clear();
			notifyDataSetChanged();
			
		}
	}
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	public int getCount() {
		return mCategory_one.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	public Object getItem(int position) {
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	public long getItemId(int position) {
		return position;
	}	
	 // create a new ImageView for each item referenced by the Adapter
	/* (non-Javadoc)
 	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
 	 */
 	@SuppressWarnings("deprecation")
	public View getView(final int position, View convertView, ViewGroup parent) {
		
			ViewHolder mHolder;
		 	View mView = convertView;
	        if (mView == null) {  // if it's not recycled, initialize some attributes
	        	mView = mInflater.inflate(R.layout.item_emoji_gallery_adapter, parent, false);
	        	mHolder = new ViewHolder();
	        	mHolder.mImageView = (ImageView)mView.findViewById(R.id.imagView_Photo);
	        	mView.setTag(mHolder);
	        } else {
	        	mHolder = (ViewHolder)mView.getTag();
	        }

	        ImageBean mBeanofImage = mCategory_one.get(position);
	        int id = mActivity.getResources().getIdentifier(mBeanofImage.getmTag(), "drawable", mActivity.getPackageName());
            Drawable emoji = mActivity.getResources().getDrawable(id);
            int w = (int)(emoji.getIntrinsicWidth() * 1.25);
            int h = (int)(emoji.getIntrinsicHeight() * 1.25);
            emoji.setBounds(0, 0, w, h);
            mHolder.mImageView.setBackgroundDrawable(emoji);
	        mHolder.mImageView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Log.println("Call Back");
					mCallback.onButtonSelected(mCategory_one.get(position).getmTag());
				}
			});
	   return mView;
	}

	
}
