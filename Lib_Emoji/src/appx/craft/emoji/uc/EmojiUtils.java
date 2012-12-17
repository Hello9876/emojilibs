package appx.craft.emoji.uc;


// TODO: Auto-generated Javadoc
/**
 * The Class EmojiUtils.
 */
public class EmojiUtils {

	/**
	 * Gets the image encode name.
	 *
	 * @param imgUnicode the img unicode
	 * @return the image encode name
	 */
	public static String getImageEncodeName(String imgUnicode) {
		String mResult = imgUnicode.replace("[", "").replace("]", "").trim();
//		Log.debug("[UNICODE]", mResult);
		return mResult;
	}

	

}