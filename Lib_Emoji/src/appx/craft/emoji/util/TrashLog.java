package appx.craft.emoji.util;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class TrashLog.
 */
public class TrashLog {
	
    /*Logging and Console*/
    /** The do logging. */
    public static boolean DO_LOGGING = true;
    
    /** The do sop. */
    public static boolean DO_SOP = true;

    /**
     * Debug.
     *
     * @param title the title
     * @param mesg the mesg
     */
    public static void debug(String title, String mesg){
            File logFile = null;
            RandomAccessFile raf = null;

            try {

                    if (TrashLog.DO_LOGGING) {
                            logFile = Utility.verifyLogFile();

                            raf = new RandomAccessFile(logFile, "rw");

                            // seek to end of file
                            raf.seek(logFile.length());

							raf.writeUTF("<TR>" + "<TD>" + new Date() + "</TD>" + "<TD>"
									+ title + "</TD>" + "<TD>" + mesg + "</TD></TR>");
                  }

            } catch (Exception exception) {
                    System.out.println("Log :: Debug :: " + exception.getMessage());
                    exception.printStackTrace();
                    
            } finally {
                    if (raf != null) {
                            try{
                            raf.close();
                            }catch(Exception e){}
                    }
            }

            raf = null;
            logFile = null;
    }

    /**
     * Error.
     *
     * @param title the title
     * @param mesg the mesg
     */
    public static void error(String title, String mesg) {
            File logFile = null;
            RandomAccessFile raf = null;

            try {
                    if (TrashLog.DO_LOGGING) {
                            
                            logFile = Utility.verifyLogFile();

                            raf = new RandomAccessFile(logFile, "rw");

                            // seek to end of file
                            raf.seek(logFile.length());

                            raf
                            .writeUTF("<TR>"
                                            + "<TD style=\"color:#FF0000\">" + new Date() + "</TD>"
                                            + "<TD style=\"color:#FF0000\">" + title + "</TD>" 
                                            + "<TD style=\"color:#FF0000\">" + mesg + "</TD></TR>");
                    }
            } catch (Exception exception) {
            } finally {
                    if (raf != null) {
                            try{
                                    raf.close();
                                    }catch(Exception e){}
                    }
            }

            raf = null;
            logFile = null;
    }
    
    /**
     * Error.
     *
     * @param title the title
     * @param mException the m exception
     */
    public static void error(String title, Exception mException) {
            mException.printStackTrace();
            File logFile = null;
            RandomAccessFile raf = null;
            StackTraceElement[] mStackTraceElement;
            String str = new String();
            try {
                    if (TrashLog.DO_LOGGING) {
                            logFile = Utility.verifyLogFile();
                            
                            raf = new RandomAccessFile(logFile, "rw");

                            // seek to end of file
                            raf.seek(logFile.length());

                            mStackTraceElement = mException.getStackTrace();
                            
                            str = "<TR>"
                                            + "<TD style=\"color:#FF0000\">" + new Date() + "</TD>"
                                            + "<TD style=\"color:#FF0000\">"
                                            + title
                                            + "</TD><TD style=\"color:#FF0000\"><BR/>"
                                            + mException.toString() + "<BR/><BR/>"
                                            + mException.getMessage() + "<BR/><BR/>";
                            
                            for (int ele=0; ele<mStackTraceElement.length; ele++){
                                    str += mStackTraceElement[ele].getClassName() + "." + mStackTraceElement[ele].getMethodName() + " (" + mStackTraceElement[ele].getFileName() + " : " + mStackTraceElement[ele].getLineNumber() + ") <BR/>";
                            }

                            str += "</TD></TR>";
                            raf
                            .writeUTF(str);
                    }
            } catch (Exception exception) {
            } finally {
                    if (raf != null) {
                            try{
                                    raf.close();
                                    }catch(Exception e){}
                    }
            }

            raf = null;
            logFile = null;
            str = null;

    }

   /**
    * Html encode.
    *
    * @param str the str
    * @return the string
    */
   public static String htmlEncode(String str){
            return str.replaceAll(">", "&lt;").replaceAll("<", "&gt;").replaceAll("&", "&amp;").replaceAll("\"", "&quot;").replaceAll("'", "&#039;");
    }

}
