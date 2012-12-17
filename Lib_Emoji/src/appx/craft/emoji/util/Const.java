package appx.craft.emoji.util;

import android.content.Context;
import android.os.Environment;


// TODO: Auto-generated Javadoc
/**
 * The Class Const.
 */
public class Const {
	
	/** The pref file. */
	public static String PREF_FILE = "Pref_Emoji";
	
	//APPLICATION CONSTANCE WILL GOES HERE
	/** The app home. */
	public static String APP_HOME = Environment.getExternalStorageDirectory().getPath() + "/Emoji_Gallery";
	
	/** The private data. */
	public static String PRIVATE_DATA = "/data/data/appx.craft.emoji/";
	
	
	/** The log dir. */
	public static String LOG_DIR = APP_HOME + "/log";
	
	/** The context. */
	public static Context CONTEXT = null;
	
	/** The Constant CONTENT. */
	public static final String CONTENT = "content";
	
	/** The Constant HOST. */
	public static final String HOST = "http://x1.appxcraft.com/AContent/";
	
	/** The Constant OPERATION_GET_CONTENT. */
	public static final String OPERATION_GET_CONTENT = HOST + "getContent";
	
	/** The Constant OPERATION_ADD_CONTENT. */
	public static final String OPERATION_ADD_CONTENT = HOST + "addContent";
	
	/** The file emoji icons list. */
	public static String FILE_EMOJI_ICONS_LIST = PRIVATE_DATA + "FILE_EMOJI_ICONS_LIST.sr";
	
	
	// ACTIVITY PREF,INTENT FLAG ETC GOES HERE 

	/** IF IT SET FLAG FALSE THEN IT WILL NOT DISPLAY IN DDMS AND TRASH LOG HTML WILL START MAINTAIN THE ERROR OF APPLICATION. */
	public static Boolean flag = true;
	

	
	
	
}

