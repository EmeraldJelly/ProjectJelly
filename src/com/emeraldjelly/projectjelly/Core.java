package com.emeraldjelly.projectjelly;

import org.bukkit.plugin.java.JavaPlugin;

import com.projectkorra.projectkorra.ability.CoreAbility;

import com.emeraldjelly.projectjelly.config.Configuration;
import com.emeraldjelly.projectjelly.listeners.AbilityListener;

public class Core extends JavaPlugin {

	public static Core plugin;
	
	@Override
	public void onEnable() {
		plugin = this;
		
		new Configuration(this);
		
		getServer().getPluginManager().registerEvents(new AbilityListener(), this);
		
		CoreAbility.registerPluginAbilities(plugin, "com.emeraldjelly.projectjelly.ability");
	}
	
}
