package personal.uploader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * <b>Setting</b>
 * 
 * This class represents the setting.txt file
 * 
 * @author Andrew Wang
 * @version 1.5
 * @since 2.0
 */
public class Setting extends Config{

	/**
	 * Represents the file
	 */
	private File file; 
	/**
	 * The file reader
	 */
	private Scanner sc;
	/**
	 * File template
	 */
	private String temp = "//--------------------------------------------------------//\n" +
						  "//                       setting.txt                      //\n" +
                          "//--------------------------------------------------------//\n" +
                          "\n" +
                          "debug=false\n" +
                          "timeout=60000\n" +
                          "hidden=true\n" +
                          "updateTime=1000";
	
	/*--- Settings ---*/
	
	/**
	 * Debug mode<br>
	 * <br>
	 * Default: false
	 */
	private boolean debug = false;
	/**
	 * Time until next upload<br>
	 * <br>
	 * Default: 60 seconds
	 */
	private long timeout = 60000;
	/**
	 * The console's visibility<br>
	 * <br>
	 * Default: true
	 */
	private boolean hidden = true;
	/**
	 * The program files' update intervals<br>
	 * <br>
	 * Default: 1 second<br>
	 * <br>
	 * <b>Special</b>: If debug=true, hidden=false 
	 */
	private long updateTime = 1000;
	
	/**
	 * Creates the setting.txt file 
	 * 
	 * Notice* Default file reset is now hard-coded
	 */
	public Setting(){
		//Setup file
		file = new File("setting.txt");
		//Finding/Creating File
		if(!file.exists()) {
			try {
				write(file, temp);
				Main.info("setting.txt was created.\n");
			} catch (IOException e) {
				Main.error("The creation of \"setting.txt\" was interrupted. Press <enter> to close\n", true);
			}
		}
	}
	
	/**
	 * Parses the file
	 */
	public void parse() {
		//Setup file reader
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			Main.error("The file \"setting.txt\" was not found.\n");
		}
		String setting = "";
		//Get settings
		while(sc.hasNextLine()) {
			String curLine = sc.nextLine();
			if(!curLine.startsWith("//") && !curLine.matches("( )*")) setting += curLine + "*";
		}
		sc.close();
		//Parse
		for(String s : setting.split("\\*")) {
			String[] set = s.split("=");
			try{
				if(set[0].equals("debug")) {
					debug = Boolean.parseBoolean(set[1]);
				}else if(set[0].equals("timeout")) {
					timeout = Long.parseLong(set[1]);
				}else if(set[0].equals("hidden")) {
					hidden = Boolean.parseBoolean(set[1]);
					if(debug)
						hidden = false;
				}else if(set[0].equals("updateTime")){
					updateTime = Long.parseLong(set[1]);
				}else{
					Main.error("The setting " + set[0] + " is invalid. Please fix.\n", true);
				}
			}catch(ArrayIndexOutOfBoundsException e) {
				Main.error("The setting " + set[0] + " is lacking an answer. Please fix.\n", true);
			}
		}
	}
	
	/**
	 * Checks if the program is in debug mode
	 * 
	 * @return
	 * If the debug mode is on
	 */
	public boolean isDebug() {
		return debug;
	}
	
	/**
	 * Gets the timeout required
	 * 
	 * @return
	 * The desired timeout
	 */
	public long timeout() {
		return timeout;
	}
	
	/**
	 * Checks  if the user wants to see the console
	 * 
	 * @return
	 * If the hidden mode is on
	 */
	public boolean isHidden() {
		return hidden;
	}
	
	/**
	 * Gets the desired update intervals
	 * 
	 * @return
	 * The desired intervals
	 */
	public long updateTime() {
		return updateTime;
	}
}
