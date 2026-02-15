package personal.uploader;

import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * <b>Uploader</b>
 * 
 * The thread that will upload files periodically
 * 
 * @author Andrew Wang
 * @version 2.0
 * @since 1.0
 */
public class Uploader extends Thread implements Runnable{
	
	/**
	 * The targeted packet
	 */
	private Packet p;
	
	/**
	 * <b>Uploader</b>
	 * 
	 * Creates an Uploader Object
	 */
	public Uploader(Packet p) {
		this.p = p;
		setPriority(NORM_PRIORITY);
		setName(p.getTarget().getAbsolutePath() + "---" + p.getLoc().getAbsolutePath());
		setDaemon(false);
	}
	
	/**
	 * Runs the Thread
	 */
	@Override
	public synchronized void run() {
		while(true) {
			try {
				//Copying
				if(p.getTarget().isFile())
					FileUtils.copyFileToDirectory(p.getTarget(), p.getLoc());
				else
					FileUtils.copyDirectoryToDirectory(p.getTarget(), p.getLoc());
				if(Main.setting.isDebug()) 
					Main.info("Successfully copied file from \"" + p.getTarget().getAbsolutePath() + "\" to \"" + p.getLoc().getAbsolutePath() + "\n");
				//Pausing for cooldown
				wait(Main.setting.timeout());
			}catch(IOException e) {
				Main.error(e.getMessage());
			}catch(InterruptedException e) {
				Main.error("An internal error has occurred. Press <enter> to close.\n", true);
			}
		}
	}
	
	/**
	 * The Modified equal() Method
	 */
	@Override
	public boolean equals(Object obj) {
		//If it is nothing, it is not equal
		if(obj == null)
			return false;
		//If it is not even a Uploader, it is not equal
		if(!(obj instanceof Uploader))
			return false;
		//Convert to Uploader
		Uploader u = (Uploader) obj;
		//Match Names
		if(!u.getName().equals(this.getName()))
			return false;
		//If all passed, it is a match!
		return true;
	}
}
