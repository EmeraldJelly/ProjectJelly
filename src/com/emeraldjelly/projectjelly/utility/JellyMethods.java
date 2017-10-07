package com.emeraldjelly.projectjelly.utility;

import com.projectkorra.projectkorra.ProjectKorra;

public class JellyMethods {
	
	/*
	 * This class is to be used to store any useful methods.
	 * These can be called at anytime by any class, ability ect ect..
	 * You May add anything to this Class if you think it will be useful. 
	 * Please make sure not to break this class.
	 */
	
	public static boolean isSpigot() {
		return ProjectKorra.plugin.getServer().getVersion().toLowerCase().contains("spigot");
	}
	
	public static String getVersion() {
		return "v1.0.0";
	}
	

}
