package appx.craft.emoji.bean;

import java.io.Serializable;

import appx.craft.emoji.util.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class ImageBean.
 */
public class ImageBean implements Serializable{
	
	/** Unique serialVersionUID  for ImageBean. */
	private static final long serialVersionUID = 1L;

	/** The tag. */
	private String TAG = ImageBean.class.getCanonicalName();
	
	/** The icons_tag. */
	private String category_id,category,icons_tag;
	
	/**
	 * Gets the category_id.
	 *
	 * @return the category_id
	 */
	public String getCategory_id() {
		return category_id;
	}
	
	/**
	 * Sets the category_id.
	 *
	 * @param category_id the new category_id
	 */
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
		Log.debug(TAG,"[ category_id ] " + category_id);
	}
	
	/**
	 * Gets the m tag.
	 *
	 * @return the m tag
	 */
	public String getmTag() {
		return icons_tag;
	}
	
	/**
	 * Sets the m tag.
	 *
	 * @param mTag the new m tag
	 */
	public void setmTag(String mTag) {
		this.icons_tag = mTag;
		Log.debug(TAG,"[ TAG ] " + mTag);
	}
	
	/**
	 * Gets the caption.
	 *
	 * @return the caption
	 */
	public String getCaption() {
		return category;
	}
	
	/**
	 * Sets the caption.
	 *
	 * @param caption the new caption
	 */
	public void setCaption(String caption) {
		this.category = caption;
		Log.debug(TAG,"[ Caption ] " + caption);
	}
	
	
}
