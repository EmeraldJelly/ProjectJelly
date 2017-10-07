package com.emeraldjelly.projectjelly.ability.fire;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.emeraldjelly.projectjelly.utility.JellyMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.FireAbility;

public class Scorch extends FireAbility implements AddonAbility {

	public Scorch(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
	}

	@Override
	public long getCooldown() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Location getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isHarmlessAbility() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSneakAbility() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void progress() {
		// TODO Auto-generated method stub

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
