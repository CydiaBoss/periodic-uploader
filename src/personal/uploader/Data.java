package personal.uploader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * <b>Data</b>
 * 
 * This class represents the uploads.txt file
 * 
 * @author Andrew Wang
 * @version 1.5
 * @since 2.0
 */
public class Data extends Config {

	/**
	 * The uploads.txt
	 */
	private File upload;
	/**
	 * The file reader
	 */
	private Scanner sc;
	/**
	 * The packets
	 */
	private ArrayList<Packet> packets = new ArrayList<Packet>();
	/**
	 * The file template
	 */
	private String temp = "//--------------------------------------------------------//\n"
						+ "//                      uploads.txt                       //\n"
						+ "//                                                        //\n"
						+ "// Welcome to this file. Here you will enter the files    //\n"
						+ "// you wish to upload periodically. See the format below. //\n"
						+ "//--------------------------------------------------------//\n"
						+ "//                        Format                          //\n"
						+ "//                                                        //\n"
						+ "//        <Targeted File's Pathname>?<Destination>        //\n"
						+ "//--------------------------------------------------------//\n";
	
	/**
	 * Creates the uploads.txt
	 * 
	 * Notice* Default file reset is now hard-coded
	 */
	public Data() {
		//Tries to find uploads.txt
		upload = new File("uploads.txt");
		//sc = new Scanner(upload);
		if(!upload.exists()) {
			try {
				write(upload, temp);
			} catch (IOException e) {
				Main.error("The creation of \"uploads.txt\" was interrupted. Press <enter> to close.\n", true);
			}
			Main.info("A new data file has been created. Fill it out, then relaunch the program.\n");
			Main.close();
		}
		parse();
	}
	
	/**
	 * Parses the file
	 */
	public void parse() {
		//Parses the file
		try{
			//Reopen
			sc = new Scanner(upload);
			//Reads the file
			String s = "";
			//Gets rid of comments
			while(sc.hasNextLine()) {
				String curLine = sc.nextLine();
				if(!curLine.startsWith("//") && !curLine.matches("( )*")) s += curLine + ";";
			}
			//Parser even further
			sc.close();
			for(String packet : s.split(";")) {
				int i = 0;
				File target = null, loc = null;
				for(String file : packet.split("\\?")) {
					if(i == 0) target = new File(file);
					else loc = new File(file);
					i++;
				}
				Packet p = new Packet(target, loc);
				packets.add(p);
			}
		}catch(NullPointerException e) {
			Main.error("It seems the upload.txt is empty or courrpted. Please fix it.\n", true);
		} catch (FileNotFoundException e) {
			Main.error("File \"uploads.txt\" wasn't found. Please restore.\n", true);
		}
	}
	
	/**
	 * Gets all the Packets
	 */
	public ArrayList<Packet> getAllPackets() {
		return packets;
	}
}
