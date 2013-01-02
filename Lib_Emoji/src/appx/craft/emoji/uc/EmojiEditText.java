package appx.craft.emoji.uc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import appx.craft.emoji.bean.ImageBean;

// TODO: Auto-generated Javadoc
/**
 * The Class EmojiEditText.
 */
public class EmojiEditText extends EditText implements OnEditorActionListener{
	
	/** The tag. */
	public final String TAG = EmojiEditText.class.getCanonicalName();
	
	/** The m context. */
	Context mContext;
	
	/** The icon tag. */
	String iconTag;
     
	ImageBean mBenofHex;
	
	/**
	 * Constructor of EmojiEditText version 1.0
	 *
	 * @param context the context
	 */
	public EmojiEditText(Context context) {
		super(context);
		mContext = context;
		addTextChangedListener(new EmojoTextWatcher());
	}
	
	/**
	 * Constructor of EmojiEditText version 1.1
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	
	public EmojiEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		addTextChangedListener(new EmojoTextWatcher());
	}

	/**
	 * Constructor of EmojiEditText version 1.2
	 *
	 * @param context the context
	 * @param attrs the attrs
	 * @param defStyle the def style
	 */
	public EmojiEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		addTextChangedListener(new EmojoTextWatcher());
	}
	
	/**
	 * work for append image icon over edit text.
	 *
	 * @param mIconTag the new adds the emoji text
	 */
	public void setAddEmojiText(ImageBean mIconTag){
		int selectionStart = getSelectionStart();
		int selectionEnd = getSelectionEnd();
		mBenofHex = mIconTag;
		StringBuilder mBuilder = new StringBuilder();
		mBuilder.append("[");
		mBuilder.append(mBenofHex.getmTag()); 
		mBuilder.append("]");
		iconTag = mBuilder.toString();
		String textToInsert = mBuilder.toString();
        getText().replace(Math.min(selectionStart, selectionEnd), Math.max(selectionStart, selectionEnd),textToInsert, 0, textToInsert.length());
	}
	
	/**
	 * Gets the emoji text.
	 *
	 * @return the emoji text
	 */
	public String getEmojiText(){
		String rexResult=null;
		try{
			rexResult = getText().toString();
			rexResult = rexResult.replace("[","\\u");
			rexResult = rexResult.replace("]","");
			return rexResult.toString();
//			return sendEncoding(rexResult).toString();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return rexResult;
	}
	
	/**
	 * Send encoding.
	 *
	 * @param imgName the img name
	 * @return the string builder
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public StringBuilder sendEncoding(String imgName)throws IOException{
		
			Charset utf8 = Charset.forName("UTF-8");
	    	int i = 0, len = imgName.length();
	    	char c;
			StringBuilder mSpannable = new StringBuilder(imgName);
	        
			while (i < len) {
				c = imgName.charAt(i++);
				if (c == '\\') {
					if (i < len) {
						c = imgName.charAt(i++);
						if (c == 'u') {
							String original1 = imgName.substring(i-2, i + 4);
					    	byte[] encoded = original1.getBytes();
					    	InputStream stream = new ByteArrayInputStream(encoded);
					    	// decode the data
					    	String decoded = decodeFromStream(stream, utf8);
					    	Log.d("[ DECODE ]",decoded.toString());
					    	mSpannable.replace(i,i+4,decoded);
					    	i += 4;
						} 
					}
				}
			}
			
			
	    	return mSpannable;
	    	
	    	
	    }
	
	  /**
  	 * Decode from stream.
  	 *
  	 * @param stream the stream
  	 * @param encoding the encoding
  	 * @return the string
  	 * @throws IOException Signals that an I/O exception has occurred.
  	 */
  	@SuppressLint("NewApi")
	private String decodeFromStream(InputStream stream, Charset encoding) throws IOException{
		  
		    StringBuilder builder = new StringBuilder();
		    byte[] buffer = new byte[4];
		    while (true) {
		      // read bytes into buffer
		      int r = stream.read(buffer);
		      if (r < 0) {
		        break;
		      }
		      // decode byte data into char data
		      String data = new String(buffer, 0, r, encoding);
		      builder.append(data);
		     
		      
		    }
		    return builder.toString();
	  }
	
	
	
	/**
	 * Valid edit text.
	 *
	 * @return true, if successful
	 */
	public boolean validEditText(){
		
		String rexResult = getText().toString();
		if(rexResult.length()>0 && rexResult != null && !rexResult.equalsIgnoreCase("")){
			return true;
		}else{
			return false;
		}
		
	}
	
	
	 /**
 	 * Work through the contents of the string, and replace any occurrences of [Encode] with the imageSpan.
 	 *
 	 * @param spannable the spannable
 	 */
    private void emotifySpannable(Spannable spannable) {
        int length = spannable.length();
        int position = 0;
        int tagStartPosition = 0;
        int tagLength = 0;
        StringBuilder buffer = new StringBuilder();
        boolean inTag = false;

        if(length <= 0)
            return;

        do {
            String c = spannable.subSequence(position, position + 1).toString();

            if (!inTag && c.equals("[")) {
                buffer = new StringBuilder();
                tagStartPosition = position;
//                Log.debug(TAG, "   Entering tag at " + tagStartPosition);

                inTag = true;
                tagLength = 0;
            }

            if (inTag) {
                buffer.append(c);
                tagLength ++;

                // Have we reached end of the tag?
                if (c.equals("]")) {
                    inTag = false;

                    String tag = buffer.toString();
                    int tagEnd = tagStartPosition + tagLength;

//                    Log.debug(TAG, "Tag: " + tag + ", started at: " + tagStartPosition + ", finished at " + tagEnd + ", length: " + tagLength);

                    if (tag.equals(iconTag)) {
                    	
                    	int id = getResources().getIdentifier(EmojiUtils.getImageEncodeName(mBenofHex.getmTag()), "drawable", mContext.getPackageName());
                    	Drawable iconDrawable = getResources().getDrawable(id);
                    	iconDrawable.setBounds(0, 0, iconDrawable.getIntrinsicWidth(), iconDrawable.getIntrinsicHeight());
                         
                        ImageSpan imageSpan = new ImageSpan(iconDrawable, ImageSpan.ALIGN_BASELINE);
                        spannable.setSpan(imageSpan, tagStartPosition, tagEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
            }

            position++;
        } while (position < length);
    }
	
    
    /**
     * <h1> Responsible for EmojoEditText change watcher </h1><br>
     * This help to replace image icon with encode while editable.
     */
	class EmojoTextWatcher implements TextWatcher{

		/* (non-Javadoc)
		 * @see android.text.TextWatcher#afterTextChanged(android.text.Editable)
		 */
		public void afterTextChanged(Editable medittable) {
			 emotifySpannable(medittable);
			 SpannableString spannable = new SpannableString(medittable.toString());
             emotifySpannable(spannable);
		}

		/* (non-Javadoc)
		 * @see android.text.TextWatcher#beforeTextChanged(java.lang.CharSequence, int, int, int)
		 */
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			
		}

		/* (non-Javadoc)
		 * @see android.text.TextWatcher#onTextChanged(java.lang.CharSequence, int, int, int)
		 */
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			  
		}
		
	}


	/* (non-Javadoc)
	 * @see android.widget.TextView.OnEditorActionListener#onEditorAction(android.widget.TextView, int, android.view.KeyEvent)
	 */
	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		Toast.makeText(mContext,"message box is empty", Toast.LENGTH_SHORT).show();
		return false;
	}



	
}


