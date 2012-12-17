package appx.craft.emoji.bean;

import appx.craft.emoji.util.Log;


// TODO: Auto-generated Javadoc
/**
 * The Class ContentBean.
 */
public class ContentBean {
	
	/** The tag. */
	private String TAG = ContentBean.class.getCanonicalName();
	
	/** The title. */
	String content,title;

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the content.
	 *
	 * @param content the new content
	 */
	public void setContent(String content) {
		this.content = content;
		Log.debug(TAG,"[ content ] "+ content);
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
		Log.debug(TAG,"[ title ] "+ title);
	}
	
	
}
