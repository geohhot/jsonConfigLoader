package net.geohhot.jsonLoader;

import org.json.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStream.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;
/**
* JSONLoader - helper library
* for loading JSON files as config files
* making changes, and saving them
* @author geohhot
* @version 0.1
*/
public class JSONLoader {
	private static String sourceFileName;
	private static File sourceFile;
	private static PrintWriter fileOut = null;
	private static Scanner fileIn = null;
	private static JSONObject defaultConfigFile = new JSONObject("{}");
	private static int indentFactor = 4; // indents for writing in file
	private static JSONObject root = defaultConfigFile;

	public JSONLoader () {

	}

	public JSONLoader (String configFileName) {
		this.sourceFileName = configFileName;
	}	

	public JSONLoader (String configFileName, JSONObject defaultConfig) {
		this.sourceFileName = configFileName;
		this.defaultConfigFile = defaultConfig;
		this.root = this.defaultConfigFile;
	}

	public void loadFile () throws NullPointerException, IOException, JSONException {
		File configFile = new File(sourceFileName);
		this.sourceFile = configFile;
		this.fileIn = new Scanner (configFile);
		this.fileOut = new PrintWriter (new BufferedWriter(new FileWriter(configFile, true)));

		if (! configFile.exists()) {
			// file doesn't exists
			// fill default config
			this.fileOut.println(defaultConfigFile.toString(indentFactor));
			return;
		} else {
			// file exists, read it!
			StringBuffer sb = new StringBuffer();
			while (this.fileIn.hasNextLine()) {
				String nl = this.fileIn.nextLine();
				sb.append (nl+"\n");
			}
			this.root = new JSONObject (sb.toString());
		}
	}
	public void loadFile (String fileName) throws NullPointerException, IOException, JSONException {
		this.sourceFileName = fileName;
		loadFile();
	}
	public void loadFile (String fileName, JSONObject defaultConfigFile)
		throws NullPointerException, IOException, JSONException {
		this.sourceFileName = fileName;
		this.defaultConfigFile = defaultConfigFile;
		this.root = this.defaultConfigFile;
		loadFile();
	}
	public void saveFile () throws NullPointerException, IOException, JSONException {
		this.sourceFile.delete();
		this.sourceFile = new File(this.sourceFileName);
		this.sourceFile.createNewFile();
		this.fileOut = new PrintWriter (this.sourceFile);
		this.fileOut.println(this.root.toString(indentFactor));
	}
	public JSONObject getRoot() {
		return this.root;
	}
	public void setRoot(JSONObject newRoot) {
		this.root = newRoot;
	}
	public String getConfigFileName() {
		return this.sourceFileName;
	}
	public File getConfigFile() {
		return this.sourceFile;
	}
}