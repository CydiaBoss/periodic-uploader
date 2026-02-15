package personal.uploader;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;

/**
 * <b>Config</b>
 * 
 * Parent class for data files
 * 
 * @author Andrew Wang
 * @version 2.0
 * @since 2.1
 */
public abstract class Config {
	
	/**
	 * Copies and Pastes files from one location to another
	 * 
	 * @param p
	 * The packet
	 * @throws IOException
	 * If the file isn't found
	 */
	protected static void copyPaste(Packet p) throws IOException {
		FileUtils.copyFileToDirectory(p.getTarget(), p.getLoc());
	}
	
	/**
	 * Writes on a file the old fashion way
	 * 
	 * @param temp
	 * The template
	 * @throws IOException 
	 */
	protected static void write(File f, String temp) throws IOException {
		FileUtils.write(f, temp, Charset.forName("US-ASCII"));
	}
	
	/**
	 * The parse method
	 */
	abstract public void parse();
}
