package appx.craft.emoji.uc;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import appx.craft.emoji.R;
import appx.craft.emoji.bean.ContentBean;

// TODO: Auto-generated Javadoc
/**
 * The Class AdapterMessageList.
 */
public class AdapterMessageList extends BaseAdapter{
	
	/** The tag. */
	public String TAG = AdapterMessageList.class.getCanonicalName();
	
	private int[] rowColors = new int[] { Color.parseColor("#5c5c5c"), Color.parseColor("#adadad") };  
	
	/** The content mess array list. */
	ArrayList<ContentBean> contentMessArrayList;
	
	/** The m context. */
	Context mContext;
	
	/** The m layout inflater. */
	LayoutInflater mLayoutInflater;
	
	/**
	 * Instantiates a new adapter message list.
	 *
	 * @param context the context
	 */
	public AdapterMessageList(Context context){
		mContext = context;
		contentMessArrayList = new ArrayList<ContentBean>();
		mLayoutInflater = LayoutInflater.from(mContext);
	}
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return contentMessArrayList.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return contentMessArrayList.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	/**
	 * Adds the item array list.
	 *
	 * @param mArrayListBean the m array list bean
	 */
	public void addItemArrayList(ArrayList<ContentBean> mArrayListBean){
		if(mArrayListBean == null)
		return;
		if(!contentMessArrayList.isEmpty()){
			contentMessArrayList.clear();
		}
		for(ContentBean mBeanofContent : mArrayListBean){
			contentMessArrayList.add(mBeanofContent);
		}
		notifyDataSetChanged();
	}
	
	/**
	 * Removes the all iteams.
	 */
	public void removeAllIteams(){
		
		if(contentMessArrayList != null && contentMessArrayList.size() > 0)
		{
			contentMessArrayList.clear();
			notifyDataSetChanged();
			
		}
	}
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder mCatchView;
		
		if(convertView == null){
			convertView = mLayoutInflater.inflate(R.layout.item_message_content,null,false);
			mCatchView = new ViewHolder();
			mCatchView.mCustomTextView = (EmojiTextView)convertView.findViewById(R.id.textView_message_content);
			
			convertView.setTag(mCatchView);
			
		}else{
			
			mCatchView = (ViewHolder)convertView.getTag();
		}
		
		int colorPos = position % rowColors.length;
		mCatchView.mCustomTextView.setEmojiText(contentMessArrayList.get(position).getContent());
		mCatchView.mCustomTextView.setBackgroundColor(rowColors[colorPos]);
		
		return convertView;
	}
	
	/**
	 * The Class ViewHolder.
	 */
	static class ViewHolder {
		
		/** The m custom text view. */
		EmojiTextView mCustomTextView;
	}
}
