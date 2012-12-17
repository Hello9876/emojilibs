package appx.craft.emoji.bean;

import appx.craft.emoji.util.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class UnicodeBean.
 */
public class UnicodeBean {
	
	/** The tag. */
	final String TAG = UnicodeBean.class.getCanonicalName();
	
	/** The str unicode. */
	String strUnicode;
	
	/** The end point. */
	int startPoint,endPoint;
	
	/**
	 * Gets the start point.
	 *
	 * @return the start point
	 */
	public int getStartPoint() {
		return startPoint;
	}
	
	/**
	 * Sets the start point.
	 *
	 * @param startPoint the new start point
	 */
	public void setStartPoint(int startPoint) {
		this.startPoint = startPoint;
		Log.println(TAG,""+startPoint);
	}
	
	/**
	 * Gets the end point.
	 *
	 * @return the end point
	 */
	public int getEndPoint() {
		return endPoint;
	}
	
	/**
	 * Sets the end point.
	 *
	 * @param endPoint the new end point
	 */
	public void setEndPoint(int endPoint) {
		this.endPoint = endPoint;
		Log.println(TAG,""+endPoint);
	}
	
	/**
	 * Gets the str unicode.
	 *
	 * @return the str unicode
	 */
	public String getStrUnicode() {
		return strUnicode;
	}
	
	/**
	 * Sets the str unicode.
	 *
	 * @param strUnicode the new str unicode
	 */
	public void setStrUnicode(String strUnicode) {
		this.strUnicode = strUnicode;
		Log.println(TAG,strUnicode);
	}
	
}
