package com.emeraldjelly.projectjelly.config;

import org.bukkit.configuration.file.FileConfiguration;

import com.emeraldjelly.projectjelly.Core;
import com.projectkorra.projectkorra.configuration.ConfigManager;

public class Configuration {

	public Configuration(Core plugin) {
		FileConfiguration c = plugin.getConfig();
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
		
		c.addDefault("ProjectJelly.Earth.Sinkhole.Cooldown", Double.valueOf( 5000D));
		c.addDefault("ProjectJelly.Earth.Sinkhole.Duration", Double.valueOf( 3500D));
		c.addDefault("ProjectJelly.Earth.Sinkhole.Range", Integer.valueOf( 25));

		ConfigManager.defaultConfig.save();
		plugin.saveConfig();
	}
}
