package com.emeraldjelly.projectjelly.ability.earth;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.EarthAbility;
import com.projectkorra.projectkorra.util.ParticleEffect;

public class Sinkhole extends EarthAbility implements AddonAbility {

	private Location loc;
	private long cooldown;
	private long duration;
	private int range;

	public Sinkhole(Player player) {
		super(player);

		start();
		setFields();
	}

	public void setFields() {
		FileConfiguration cm = getConfig();
		this.loc = player.getLocation();
		this.cooldown = cm.getInt("ProjectJelly.Earth.Sinkhole.Cooldown");
		this.duration = cm.getLong("ProjectJelly.Earth.Sinkhole.Duration");
		this.range = cm.getInt("ProjectJelly.Earth.Sinkhole.Range");
	}

	@Override
	public long getCooldown() {
		return cooldown;
	}

	@Override
	public Location getLocation() {
		return loc;
	}

	@Override
	public String getName() {
		return "Bunker";
	}

	@Override
	public boolean isHarmlessAbility() {
		return false;
	}

	@Override
	public boolean isSneakAbility() {
		return false;
	}

	public String getDescription() {
		return ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Utility:" + ChatColor.GREEN
				+ "A simple earth ability that sinks target users in a mini sinkhole.";
	}

	public String getInstructions() {
		return "Left Click";
	}

	@Override
	public void progress() {
		if (!bPlayer.canBend(this)) {
			remove();
			return;
		}
		if (player.getLocation().distanceSquared(loc) > range) {
			remove();
			bPlayer.addCooldown(this);
		}
		ParticleEffect.FALLING_DUST.display(loc, 0.5f, -0.5f, 0.5f, 1, 50);
		playEarthbendingSound(loc);
		for (Entity entity : GeneralMethods.getEntitiesAroundPoint(this.loc, 2.0D)) {
			if (((entity instanceof LivingEntity)) && (entity.getUniqueId() != this.player.getUniqueId())) {
				if (EarthAbility.isEarth(entity.getLocation().getBlock())) {
					entity.teleport(entity.getLocation().subtract(0, 1, 0));
					entity.setVelocity(entity.getVelocity().subtract(entity.getVelocity()));
					playEarthbendingSound(loc);
				}
				if (getStartTime() - System.currentTimeMillis() >= duration) {
					entity.teleport(entity.getLocation().add(0, 1, 0));
					playEarthbendingSound(loc);
					remove();
					bPlayer.addCooldown(this);
					return;
				}
			}
		}
	}

	@Override
	public String getAuthor() {
		return "EmeraldJelly";
	}

	@Override
	public String getVersion() {
		return "v1.0.0";
	}

	@Override
	public void load() {

	}

	@Override
	public void stop() {

	}

}