package appx.craft.emoji.uc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.TextView;
import appx.craft.emoji.bean.UnicodeBean;
import appx.craft.emoji.util.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class EmojiTextView.
 */
public class EmojiTextView extends TextView {
	
	/** The context. */
	private Context context;
	
	/**
	 * Instantiates a new emoji text view.
	 *
	 * @param context the context
	 */
	public EmojiTextView(Context context) {
		super(context);
		this.context = context;
	}
	
	/**
	 * Instantiates a new emoji text view.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	public EmojiTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	/**
	 * Instantiates a new emoji text view.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 * @param defStyle the def style
	 */
	public EmojiTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}
	
	/**
	 * Sets the emoji text.
	 *
	 * @param text the new emoji text
	 */
	public void setEmojiText(String text) {
		
	    setText(getReplaceUnicodeImage_v2(text.toString()));
	}

	/** The emoji getter. */
	private ImageGetter emojiGetter = new ImageGetter()
	{
        public Drawable getDrawable(String source){
            int id = getResources().getIdentifier(source, "drawable", context.getPackageName());
            
            Drawable emoji = getResources().getDrawable(id);
            int w = (int)(emoji.getIntrinsicWidth() * 1.25);
            int h = (int)(emoji.getIntrinsicHeight() * 1.25);
            emoji.setBounds(0, 0, w, h);
            return emoji;
        }
    };
    
    /**
     * Gets the replace unicode image.
     *
     * @param imgName the img name
     * @return the replace unicode image
     */
    public CharSequence getReplaceUnicodeImage(String imgName) {
    	CharSequence result="";
		int i = 0, len = imgName.length();
		int index =0 ;
		Map<Integer,String>  mUnicodeName = new HashMap<Integer,String>();
		char c;
		StringBuffer sb = new StringBuffer(len);
		while (i < len) {
			c = imgName.charAt(i++);
			if (c == '\\') {
				if (i < len) {
					c = imgName.charAt(i++);
					if (c == 'u') {
						Log.println("[ MATCH UNICODE ] " + imgName.substring(i, i + 4));
						mUnicodeName.put(index,imgName.substring(i, i + 4));
						c =(char)'#';
						i += 4;
						index++;
					} 
				}
			}
			sb.append(c);
		}
		
		if(mUnicodeName != null && mUnicodeName.size()>0){
			
			for(int k=0;k<mUnicodeName.size();k++){
				Log.println("[ what do found ] " + mUnicodeName.get(k));
				//<img src=\"e022\"/>
				CharSequence spanned = Html.fromHtml("<img src=\""+mUnicodeName.get(k)+"\"/>", emojiGetter, null);
				result = sb.toString().replaceFirst("#",spanned.toString());
			}
		}else{
			result = sb.toString();
		}
		
		return result;
		
	}
    
    /**
     * Gets the replace unicode image_v2.
     *
     * @param imgName the img name
     * @return the replace unicode image_v2
     */
    public Spannable getReplaceUnicodeImage_v2(String imgName){

    	
    	int i = 0, len = imgName.length();
    	char c;
		UnicodeBean mBean;
		
        ArrayList<UnicodeBean> mUnicodeName = new ArrayList<UnicodeBean>();
        SpannableString mSpannable = new SpannableString(imgName);
        
		while (i < len) {
			c = imgName.charAt(i++);
			if (c == '\\') {
				if (i < len) {
					c = imgName.charAt(i++);
					if (c == 'u') {
						Log.println("[ MATCH UNICODE ] " + imgName.substring(i, i + 4));
						mBean = new UnicodeBean();
						mBean.setStartPoint(i-2);
						mBean.setEndPoint(i+4);
						mBean.setStrUnicode(imgName.substring(i, i + 4));
						mUnicodeName.add(mBean);		
						i += 4;
					} 
				}
			}
		}
		
		if(mUnicodeName != null && mUnicodeName.size()>0){
			
			for(int k=0;k<mUnicodeName.size();k++){
				
				Log.println("[ what found ] " + mUnicodeName.get(k).getStrUnicode());
				int id;
				if(mUnicodeName.get(k).getStrUnicode().equalsIgnoreCase("0402")){
					id = getResources().getIdentifier(EmojiUtils.getImageEncodeName("e001"), "drawable", context.getPackageName());	
				}else{
					id = getResources().getIdentifier(EmojiUtils.getImageEncodeName(mUnicodeName.get(k).getStrUnicode()), "drawable", context.getPackageName());
				}
				
				Drawable d = getResources().getDrawable(id); 
		        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight()); 
		        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE); 
		        mSpannable.setSpan(span,mUnicodeName.get(k).getStartPoint(),mUnicodeName.get(k).getEndPoint(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE); 
			}
		}
		
		return mSpannable;
    	
    	
    }
}