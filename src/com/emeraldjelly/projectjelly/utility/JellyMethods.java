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
		return "a1.0.0";
	}
	
	public static String configPath(String element, String ability, String path) {
		
		/* Simple new method. Just set element as whatever the element is (doesn't have to be lower/upper case. The method
		 * handles that). Then define the name of the ability (you WILL have to make it upper case). Then define the rest of
		 * the path if applicable.
		 * Example: Methods.configPath(fire, FireBlast, Cooldown.Cooldown2);
		 * Then it will create the path like: ProjectJelly.Fire.FireBlast.Cooldown.Cooldown2
		 */

		String base = "ProjectJelly.";
		String elementPath = null;
		String abilityPath = ability + ".";
		
		if (element.equalsIgnoreCase("fire")) {
			elementPath = ".Fire.";
		} else if (element.equalsIgnoreCase("water")) {
			elementPath = ".Water.";
		} else if (element.equalsIgnoreCase("earth")) {
			elementPath = ".Earth.";
		} else if (element.equalsIgnoreCase("air")) {
			elementPath = ".Air.";
		} else if (element.equalsIgnoreCase("chi")) {
			elementPath = ".Chi.";
		} else if (element.equalsIgnoreCase("avatar")) {
			elementPath = ".Avatar.";
		}
		return base + elementPath + abilityPath + path;
	}
	
	public static String abilityDescription(boolean isForbidden, String abilityType, String element, String description) {
		
		/* Another pretty simple method. This one will allow you to create descriptions with ease. Simple define whether or not
		 * the ability is forbidden, define what type of ability it's going to be, define what element it's for, then write out
		 * the description. No work other than that!
		 * Example: Methods.abilityDescription(false, "Offense", "fire", "Left-Click to shoot a blast!");
		 * Then it will create the descriptions like: ChatColor.DARK_RED + "Offense: " + ChatColor.RED + "Left-Click to shoot
		 * a blast!"
		 * 
		 * Example2: Methods.abilityDescription(true, "Offense", "fire", "Left-Click to shoot a blast!");
		 * Then it will create the descriptions like: ChatColor.DARK_PURPLE + "Forbidden: " + ChatColor.RED + "Left-Click to
		 * shoot a blast!"
		 * 
		 * NOTE: If the ability IS forbidden, then leave the abilityType as ""
		 */
		
		String forbidden;
		String type;
		String doColon;
		ChatColor bold = ChatColor.BOLD;
		ChatColor titleColor = null;
		ChatColor descriptionColor = null;
		
		if (isForbidden) {
			forbidden = ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Forbidden: ";
			type = "";
			doColon = "";
			titleColor = null;
		} else {
			forbidden = "";
			doColon = ": ";
			type = abilityType;
		}
		
		if (element.equalsIgnoreCase("fire")) {
			titleColor = ChatColor.DARK_RED;
			descriptionColor = ChatColor.RED;
		} else if (element.equalsIgnoreCase("water")) {
			titleColor = ChatColor.DARK_AQUA;
			descriptionColor = ChatColor.AQUA;
		} else if (element.equalsIgnoreCase("earth")) {
			titleColor = ChatColor.DARK_GREEN;
			descriptionColor = ChatColor.GREEN;
		} else if (element.equalsIgnoreCase("air")) {
			titleColor = ChatColor.DARK_GRAY;
			descriptionColor = ChatColor.GRAY;
		} else if (element.equalsIgnoreCase("chi")) {
			titleColor = ChatColor.GOLD;
			descriptionColor = ChatColor.YELLOW;
		} else if (element.equalsIgnoreCase("avatar")) {
			titleColor = ChatColor.BOLD;
			descriptionColor = ChatColor.DARK_PURPLE;
		} else {
			descriptionColor = ChatColor.BLUE;
		}
		
		return forbidden + titleColor + bold + type + doColon + descriptionColor + description;
		
	}

}
