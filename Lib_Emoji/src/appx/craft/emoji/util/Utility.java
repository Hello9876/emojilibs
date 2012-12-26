package appx.craft.emoji.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.net.ConnectivityManager;
import appx.craft.emoji.bean.ImageBean;


// TODO: Auto-generated Javadoc
/**
 * The Class Utility.
 */
public class Utility {
	
	/** The tag. */
	private static String TAG = Utility.class.getCanonicalName();
	
	/**
	 * Checks if is online.
	 *
	 * @return true, if is online
	 */
	public static boolean isOnline() {
	
		try {
			ConnectivityManager cm = (ConnectivityManager) Const.CONTEXT
					.getSystemService(Context.CONNECTIVITY_SERVICE);
	
			if (cm != null) {
				return cm.getActiveNetworkInfo().isConnectedOrConnecting();
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Read stream.
	 *
	 * @param is the is
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static String readStream(InputStream is) throws IOException {
		int ch = 0;
		String str = new String();

		while ((ch = is.read()) != -1) {
			str += (char) ch;
		}

		is.close();

		return str;
	}

	/**
	 * Veirfy log path.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void veirfyLogPath() throws IOException {
          File dir = new File(Const.LOG_DIR);

          if (!dir.exists()) {
                  dir.mkdirs();
          }

          dir = null;
	  }
	
	/**
	 * Verify log file.
	 *
	 * @return the file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static File verifyLogFile() throws IOException {
          File logFile = new File(Const.LOG_DIR + "/Log_"
                          + new SimpleDateFormat("yyyy_MM_dd").format(new Date())
                          + ".html");
          FileOutputStream fos = null;

          Utility.veirfyLogPath();

          if (!logFile.exists()) {
                  logFile.createNewFile();
                  
                  fos = new FileOutputStream(logFile);
                  
                  String str = "<TABLE style=\"width:100%;border=1px\" cellpadding=2 cellspacing=2 border=1><TR>"
                          + "<TD style=\"width:30%\"><B>Date n Time</B></TD>"
                          + "<TD style=\"width:20%\"><B>Title</B></TD>" 
                          + "<TD style=\"width:50%\"><B>Description</B></TD></TR>";
                  
                  fos.write(str.getBytes());
          }

          if (fos != null){
                  fos.close();
          }
          
          fos = null;
          
          return logFile;
  }
	
	/**
	 * Verify app home.
	 */
	public static void verifyAppHome(){
		
	     File dir = new File(Const.APP_HOME);

         if (!dir.exists()) {
                 dir.mkdirs();
         }

         dir = null;
	}
	
	/**
	 * Removes the file if exist.
	 *
	 * @param fileName the file name
	 * @return true, if successful
	 */
	public static boolean removeFileIfExist(String fileName){
		
		  File dir = new File(fileName);
		  if (!dir.exists()) {
			  return true;
          }else{
        	  return dir.delete();
          }
	}
	
	/**
	 * Verify serializable file.
	 *
	 * @param fileName the file name
	 * @return true, if successful
	 */
	public static boolean verifySerializableFile(String fileName){
		 File dir = new File(fileName);
		  if (!dir.exists()) {
			  return false;
         }else{
       	  return true;
         }
	}
	
	/**
	 * Write object.
	 *
	 * @param fileName the file name
	 * @param obj the obj
	 */
	public static void writeObject(final String fileName,final Object obj){
		
		new Thread(){
			
			public void run() {
				

				ObjectOutputStream out = null;
				try{
					
					  File file = new File(fileName);
					  if (!file.exists()) {
						  file.createNewFile();
			          }
					  file = null;
					  
					 /* Create a object from the ObjectOutputStream class */
					out = new ObjectOutputStream (new FileOutputStream(fileName));
					//writeObject method stores the state of the object
					out.writeObject(obj);
					// Flush object 
					out.flush();
					out.close();
					
				}catch (Exception e) {
					Log.error(TAG, e);
				}finally{
					if(out != null)
						out = null;
				}
				
			};
		}.start();
		
	}
	
	/**
	 * Read object.
	 *
	 * @param fileName the file name
	 * @return the array list
	 */
	@SuppressWarnings("unchecked")
	public synchronized static ArrayList<ImageBean> readObject(String fileName){
		
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		ArrayList<ImageBean> mArrayList = null;
		try{
		
			/* Creates a new Object of the FileInputStream class 
	        which file name as parameters.  This is to define the
	        file we are reading from */
			fis = new FileInputStream(fileName);
			/* Create a new Object of the ObjectInputStream class
            which has the variable as the parameters to the object we
            created above */
			ois = new ObjectInputStream(fis);
			/* We would then need to type cast and read file using
	        readObject Method */
			mArrayList = (ArrayList<ImageBean>)ois.readObject();
			//Close ObjectInputStream
			ois.close();
			fis.close();
			 
			return mArrayList;
			
		}catch (Exception e) {
			Log.error(TAG, e);
		}finally{
			if(fis != null || ois != null){
				fis = null;
				ois = null;
			}
				
		}
		return mArrayList;
	}
	
	
	/**
	 * Copy stream.
	 *
	 * @param is the is
	 * @param os the os
	 */
	public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }
	
	
	/**
	 * From Unicode string to hex value 
	 * @param inputChar
	 * @return
	 */
	public static String getHexValue(char inputChar) 
	{
	 //One character
	 String strOutput = "";
     strOutput = Integer.toHexString(inputChar).toUpperCase();
	 return strOutput;
	} 
	
	/**
	 * From hex value to Unicode string  
	 * @param hexValue
	 * @return
	 */
	public static String convertHexValueToString(String hexValue)
	 { 
	  char[] emojiChars =
	Character.toChars(Integer.parseInt(hexValue,16)); 
	   
	  String strResult = new String(emojiChars); 
	  return strResult;
	 } 
}
