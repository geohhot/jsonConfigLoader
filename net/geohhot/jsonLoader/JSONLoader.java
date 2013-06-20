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
* @see JSONObject
* @version 0.2
*/
public class JSONLoader {
	private static String sourceFileName;
	private static File sourceFile;
	private static PrintWriter fileOut = null;
	private static Scanner fileIn = null;
	private static JSONObject defaultConfigFile = new JSONObject("{}");
	private static int indentFactor = 4; // indents for writing in file
	private static JSONObject root = defaultConfigFile;
        
        /**
         * Just initializes JSONLoader without any configuration
         */
	public JSONLoader () {

	}
        
        /**
         * Makes JSONLoader ready for .loadFile
         * @param configFileName 
         */
	public JSONLoader (String configFileName) {
		this.sourceFileName = configFileName;
	}	
        
        /**
         * Makes JSONLoader ready for .loadFile with default configuration, if file is not found, it will create one
         * @param configFileName
         * @param defaultConfig 
         */
	public JSONLoader (String configFileName, JSONObject defaultConfig) {
		this.sourceFileName = configFileName;
		this.defaultConfigFile = defaultConfig;
		this.root = this.defaultConfigFile;
	}
        
        /**
         * Loads file and makes ready for reading/changing
         * @throws NullPointerException
         * @throws IOException
         * @throws JSONException 
         */
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
        
        /**
         * Load selected file
         * @param fileName
         * @throws NullPointerException
         * @throws IOException
         * @throws JSONException 
         */
	public void loadFile (String fileName) throws NullPointerException, IOException, JSONException {
		this.sourceFileName = fileName;
		loadFile();
	}
        
        /**
         * Load selected file with selected default configuration
         * @param fileName
         * @param defaultConfigFile
         * @throws NullPointerException
         * @throws IOException
         * @throws JSONException 
         */
	public void loadFile (String fileName, JSONObject defaultConfigFile)
		throws NullPointerException, IOException, JSONException {
		this.sourceFileName = fileName;
		this.defaultConfigFile = defaultConfigFile;
		this.root = this.defaultConfigFile;
		loadFile();
	}
        
        /**
         * Saves modified file
         * @throws NullPointerException
         * @throws IOException
         * @throws JSONException 
         */
	public void saveFile () throws NullPointerException, IOException, JSONException {
		this.sourceFile.delete();
		this.sourceFile = new File(this.sourceFileName);
		this.sourceFile.createNewFile();
		this.fileOut = new PrintWriter (this.sourceFile);
		this.fileOut.println(this.root.toString(indentFactor));
	}
        
        /**
         * Return root object of JSON configuration file
         * @return JSONObject
         */
	public JSONObject getRoot() {
		return this.root;
	}
        
        /**
         * Sets new root with modified info
         * @param newRoot
         */
	public void setRoot(JSONObject newRoot) {
		this.root = newRoot;
	}
        
        /**
         * Returns configuration file name
         * @return String
         */
	public String getConfigFileName() {
		return this.sourceFileName;
	}
        
        /**
         * Returns configuration File
         * @return File
         */
	public File getConfigFile() {
		return this.sourceFile;
	}
}