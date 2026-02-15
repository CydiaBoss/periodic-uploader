package personal.uploader;

import java.io.File;

/**
 * <b>Packet</b>
 * 
 * Represents a packet
 * 
 * @author Andrew Wang
 * @version 1.0
 * @since 1.0
 */
public class Packet {

	/**
	 * The targeted file/directory
	 */
	private File target;
	/**
	 * The location
	 */
	private File loc;
	
	/**
	 * <b>Packet</b>
	 *
	 * Creates a packet
	 * 
	 * @param target
	 * The targeted file/dir
	 * @param loc
	 * The targeted copy point for the target
	 */
	public Packet(File target, File loc) {
		target.setReadable(true);
		loc.setWritable(true);
		this.target = target;
		this.loc = loc;
	}

	/**
	 * <b>Get Target</b>
	 * 
	 * Gets the targeted file or directory
	 * 
	 * @return
	 * The target
	 */
	public File getTarget() {
		return target;
	}

	/**
	 * <b>Get Location</b>
	 * 
	 * Gets the target copy point for the target
	 * 
	 * @return
	 * The location
	 */
	public File getLoc() {
		return loc;
	}

}
