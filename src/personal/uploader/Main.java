package personal.uploader;

import java.awt.Color;
import java.util.ArrayList;

import hsa.Console;

/**
 * <b>Main</b>
 * 
 * The Core file in the Uploader Program
 * 
 * @author Andrew Wang
 * @version 2.1
 * @since 1.0
 */
public class Main extends Thread{
	
	/**
	 * The setting file
	 */
	public static final Setting setting = new Setting();
	/**
	 * The data file
	 */
	public static final Data data = new Data();
	/**
	 * All Threads
	 */
	public static ArrayList<Uploader> up = new ArrayList<Uploader>();
	/**
	 * Console
	 */
	private static Console c;
	/**
	 * Main Thread
	 */
	private static Main m;
	
	/**
	 * <b>Main Method</b>
	 * 
	 * The starter method
	 * 
	 * @param args
	 * Is not needed
	 */
	public static void main(String[] args) {
		
		if(!setting.isHidden())
			c = new Console();
		info("Uploader 2.3\n");
		m = new Main();
		m.start();
	}
	
	/**
	 * Main Constructor
	 */
	public Main() {
		setName("Main");
		setPriority(MAX_PRIORITY);
	}
	
	/**
	 * The starter method
	 */
	@Override
	public synchronized void run() {
		try {
			while(true) {
				//Threads creation & initiation
				for(Packet p : data.getAllPackets()) {
					Uploader u = new Uploader(p);
					boolean isNew = true;
					for(Uploader thread : up) {
						if(thread.equals(u)) {
							isNew = false;
							break;
						}
					}
					if(isNew) {
						up.add(u);
						u.start();
					}
				}
				//Update configs
				data.parse();
				setting.parse();
				//Wait for next update
				wait(setting.updateTime());
			}
		} catch (InterruptedException e) {
			error("An internal error has occurred. Press <enter> to close.\n", true);
		}
	}
	
	/**
	 * Prints out info and give the option for closure
	 * 
	 * @param msg
	 * The message to display
	 */
	public static void info(String msg) {
		if(c != null) 
			if(!setting.isDebug())
				c.print("Info: " + msg);
			else
				c.print("Debug: " + msg);
	}
	
	/**
	 * Prints out info
	 * 
	 * @param msg
	 * The message to display
	 */
	public static void error(String msg) {
		error(msg, false);
	}
	
	/**
	 * Prints an error
	 * 
	 * @param msg
	 * The message to display
	 * @param close
	 * If the error is fatal
	 */
	public static void error(String msg, boolean close) {
		if(c == null)
			c = new Console("Uploader Error");
		c.setTextColor(Color.RED);
		c.print("Error: " + msg);
		c.setTextColor(Color.BLACK);
		if(close) {
			close();
		}
	}
	
	/**
	 * Closes the Program
	 */
	public static void close() {
		if(c == null)
			c = new Console("Uploader");
		c.readChar();
		System.exit(0);
	}
}
