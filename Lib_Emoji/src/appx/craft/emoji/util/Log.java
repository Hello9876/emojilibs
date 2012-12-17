package appx.craft.emoji.util;

// TODO: Auto-generated Javadoc
/**
 * The Class Log.
 */
public class Log {
    
   
   
	/**
	 * Println.
	 *
	 * @param mesg the mesg
	 */
	public static void println(String mesg) {
		if (Const.flag) {
			System.out.println(mesg);
		}
	}
	
	/**
	 * Println.
	 *
	 * @param title the title
	 * @param mesg the mesg
	 */
	public static void println(String title, String mesg) {
		if (Const.flag) {
			System.out.println(title + " :: " + mesg);
		}
	}
	
     /**
      * debug as blue color.
      *
      * @param tag the tag
      * @param message the message
      */
    public static void debug(String tag,String message){
            if(Const.flag){
            	android.util.Log.d(tag,message);
            }else{
            	TrashLog.debug(tag, message);
            }
            	
    }
    
    /**
     * debug as blue color.
     *
     * @param tag as String
     * @param message the message
     * @param throwable as Throwable
     */
    public static void debug(String tag,String message,Throwable throwable){
	    	if(Const.flag){
	        	android.util.Log.d(tag,message);
	        }else{
	        	TrashLog.debug(tag, message + throwable);
	        }
    }
    
    /**
     * verbose as black color.
     *
     * @param tag as String
     * @param message as String
     */
    public static void verbose(String tag,String message){
            if(Const.flag)
            android.util.Log.v(tag, message);
    }
    
    /**
     * verbose as black color.
     *
     * @param tag the tag
     * @param message the message
     * @param throwable the throwable
     */
    public static void verbose(String tag,String message,Throwable throwable){
            if(Const.flag)
            android.util.Log.v(tag, message,throwable);
    }

    /**
     * Error.
     *
     * @param tag as String
     * @param e as Exception
     */
    
    public static void error(String tag,Exception e){
            if(Const.flag)
            {
            	 	e.printStackTrace();
            	 	android.util.Log.e(tag,e.getMessage());
                   
            }else{
            	TrashLog.error(tag,e);
            }
            	
    }
    
    /**
     * Error.
     *
     * @param tag the tag
     * @param message the message
     */
    public static void error(String tag,String message){
    	
        if(Const.flag)
        {
        	android.util.Log.e(tag,message);
        }else{
        	TrashLog.error(tag,message);
        }
        	
    }

}

