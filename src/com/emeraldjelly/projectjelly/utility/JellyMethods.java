package com.emeraldjelly.projectjelly.utility;

import com.projectkorra.projectkorra.ProjectKorra;

public class JellyMethods {
	
	/*
	 * This class is to be used to store any useful methods.
	 * These can be called at any time by any class, ability ect ect..
	 * You May add anything to this Class if you think it will be useful. 
	 * Please make sure not to break this class. As All abilities will use this class.
	 */
	
	public static boolean isSpigot() {
		return ProjectKorra.plugin.getServer().getVersion().toLowerCase().contains("spigot");
	}
	
	public static String getVersion() {
		return "v1.0.0";
	}
	
	public static String firePath(String ability, String path) {
		return "ProjectJelly.Fire." + ability + "." + path;
	}
	
	public static String waterPath(String ability, String path) {
		return "ProjectJelly.Water." + ability + "." + path;
	}
	
	public static String earthPath(String ability, String path) {
		return "ProjectJelly.Earth." + ability + "." + path;
	}
	
	public static String airPath(String ability, String path) {
		return "ProjectJelly.Air." + ability + "." + path;
	}
	
	public static String chi(String ability, String path) {
		return "ProjectJelly.Chi." + ability + "." + path;
	}

}
