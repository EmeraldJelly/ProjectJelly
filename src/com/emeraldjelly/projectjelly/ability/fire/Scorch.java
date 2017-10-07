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
		return cooldown;
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
		return JellyMethods.fireDesc("Offensive/Utility", "A charge up advanced firebending move. This move allows the firebender to charge up to 3 different power levels.\r\n" + 
				"\r\n" + 
				"Power Level 1: The Weakest charge level. Can only deal a average fireblast to an enemy. (Just a simple Click doesnt require any charging by shifting)\r\n" + 
				"Power Level 2: A strong charge level, however not the strongest. This allows the fire bender to create a small but concentrated blast that does pretty significant damage. (Requires a 5 Second Charge)\r\n" + 
				"\r\n" + 
				"Power Level 3: A overwhelmingly powerful charge level. This does not enable the firebender to fire a \"Blast\" however this does make it so the fire bender can move at incredibly fast moving speeds. While in this move the firebender damages Everything within a 3 block radius of them. The firebender may also FLY (using thrust from fire) during this mode but be warned, not only does this take 10 seconds to charge but it also puts a huge strain on the firebender not allowing them to bend for 10 seconds after using this ability.");
	}
	
	public String getInstructions() {
		return "Left click to use power level 1, Hold Shift to continue charging to different power levels."; 
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
