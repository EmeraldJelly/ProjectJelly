package com.emeraldjelly.projectjelly.ability.fire;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.emeraldjelly.projectjelly.utility.JellyMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.FireAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;

public class Scorch extends FireAbility implements AddonAbility {
	
	private long cooldown;

	public Scorch(Player player) {
		super(player);
	}
	
	public void setFields() {
		this.cooldown = ConfigManager.getConfig().getLong(JellyMethods.firePath("Scorch", "Cooldown"));
	}

	@Override
	public long getCooldown() {
		return 0;
	}

	@Override
	public Location getLocation() {
		return null;
	}

	@Override
	public String getName() {
		return "Scorch";
	}
	
	public String getDescription() {
		JellyMethods.fireDesc("Offensive/Utility", "");
	}

	@Override
	public boolean isHarmlessAbility() {
		return false;
	}

	@Override
	public boolean isSneakAbility() {
		return false;
	}

	@Override
	public void progress() {

	}

	@Override
	public String getAuthor() {
		return null;
	}

	@Override
	public String getVersion() {
		return JellyMethods.getVersion();
	}

	@Override
	public void load() {
		
	}

	@Override
	public void stop() {
		
	}

}
