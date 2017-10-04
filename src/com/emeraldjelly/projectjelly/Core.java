package com.emeraldjelly.projectjelly;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.AvatarAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;

import com.emeraldjelly.projectjelly.listeners.AbilityListener;

public class Core extends AvatarAbility implements AddonAbility {

	public Core(Player player) {
		super(player);
	}

	@Override
	public void load() {
		ProjectKorra.plugin.getServer().getPluginManager().registerEvents(new AbilityListener(), ProjectKorra.plugin);
		ProjectKorra.log.info("ProjectJelly Has Successfully Been Loaded.");
		
		FileConfiguration c = ConfigManager.getConfig();
		c.options().copyDefaults(true);

		c.addDefault("ProjectJelly.Fire.FireSerpent.Duration", 10000);
		c.addDefault("ProjectJelly.Fire.FireSerpent.Damage", 5);
		c.addDefault("ProjectJelly.Fire.FireSerpent.Cooldown", 5000);
		c.addDefault("ProjectJelly.Fire.FireSerpent.ChargeTime", 5000);
		c.addDefault("ProjectJelly.Fire.FireSerpent.Range", 60);

		c.addDefault("ProjectJelly.Fire.Immolate.Damage", Integer.valueOf(6));
		c.addDefault("ProjectJelly.Fire.Immolate.Range", Integer.valueOf(45));
		c.addDefault("ProjectJelly.Fire.Immolate.Cooldown", Integer.valueOf(3000));
		c.addDefault("ProjectJelly.Fire.Immolate.SacrficeDamage", Integer.valueOf(8));
		c.addDefault("ProjectJelly.Fire.Immolate.FireTicks", Integer.valueOf(5));

		c.addDefault("ProjectJelly.Air.Whirlwind.Cooldown", Long.valueOf(2500L));
		c.addDefault("ProjectJelly.Air.Whirlwind.ChargeTime", Long.valueOf(5000L));
		c.addDefault("ProjectJelly.Air.Whirlwind.Damage", Double.valueOf(5.0D));
		c.addDefault("ProjectJelly.Air.Whirlwind.KnockBack", Double.valueOf(1.5D));
		
		c.addDefault("ProjectJelly.Air.Snare.Duration", Long.valueOf(10000));
		c.addDefault("ProjectJelly.Air.Snare.Hits", Integer.valueOf(3));
		c.addDefault("ProjectJelly.Air.Snare.Range", Integer.valueOf(40));
		c.addDefault("ProjectJelly.Air.Snare.Cooldown", Long.valueOf(2500));
		

		ConfigManager.defaultConfig.save();
	}

	@Override
	public void stop() {
		
		super.remove();
		
	}
	
	@Override
	public boolean isHiddenAbility() {
		return true;
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
		return "Core";
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
		return null;
	}

}
