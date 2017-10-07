package com.emeraldjelly.projectjelly.utility;

import com.projectkorra.projectkorra.ProjectKorra;

import net.md_5.bungee.api.ChatColor;

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
	
	public static String chiPath(String ability, String path) {
		return "ProjectJelly.Chi." + ability + "." + path;
	}
	
	public static String fireDesc(String type, String desc) {
		return ChatColor.BOLD + "" + ChatColor.DARK_RED + type + ": " + ChatColor.RED + desc;
	}
	
	public static String fireDescForbidden(String desc) {
		return ChatColor.BOLD + "" + ChatColor.DARK_PURPLE + "Forbidden: " + "" + ChatColor.RED + desc;
	}
	
	public static String waterDesc(String type, String desc) {
		return ChatColor.BOLD + "" + ChatColor.DARK_AQUA + type + ": " + ChatColor.AQUA + desc;
	}
	
	public static String waterDescForbidden(String desc) {
		return ChatColor.BOLD + "" + ChatColor.DARK_PURPLE + "Forbidden: " + "" + ChatColor.AQUA + desc;
	}
	
	public static String earthDesc(String type, String desc) {
		return ChatColor.BOLD + "" + ChatColor.DARK_GREEN + type + ": " + ChatColor.GREEN + desc;
	}
	
	public static String earthDescForbidden(String desc) {
		return ChatColor.BOLD + "" + ChatColor.DARK_PURPLE + "Forbidden: " + "" + ChatColor.GREEN + desc;
	}
	
	public static String airDesc(String type, String desc) {
		return ChatColor.BOLD + "" + ChatColor.DARK_GRAY + type + ": " + ChatColor.GRAY + desc;
	}
	
	public static String airDescForbidden(String desc) {
		return ChatColor.BOLD + "" + ChatColor.DARK_PURPLE + "Forbidden: " + "" + ChatColor.GRAY + desc;
	}
	
	public static String chiDesc(String type, String desc) {
		return ChatColor.BOLD + "" + ChatColor.GOLD + type + ": " + ChatColor.YELLOW + desc;
	}
	
	public static String chiDescForbidden(String desc) {
		return ChatColor.BOLD + "" + ChatColor.DARK_PURPLE + "Forbidden: " + "" + ChatColor.YELLOW + desc;
	}

}
