package com.emeraldjelly.projectjelly.ability.air;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.AirAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;

public class Snare extends AirAbility implements AddonAbility {

	private Location loc;
	private long duration;
	private long cooldown;
	private int currPoint;
	private int range;
	private static Entity targetO;
	private Entity target;
	private static int hits = 0;
	private int maxHits;

	public Snare(Player player) {
		super(player);

		start();
		setFields();
	}

	public void setFields() {
		this.loc = player.getLocation();
		this.duration = ConfigManager.getConfig().getLong("ProjectJelly.Air.Snare.Duration");
		this.cooldown = ConfigManager.getConfig().getLong("ProjectJelly.Air.Snare.Cooldown");
		this.range = ConfigManager.getConfig().getInt("ProjectJelly.Air.Snare.Range");
		this.target = GeneralMethods.getTargetedEntity(player, this.range);
		targetO = GeneralMethods.getTargetedEntity(player, this.range);
		this.maxHits = ConfigManager.getConfig().getInt("ProjectJelly.Air.Snare.Hits");
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
		return "Snare";
	}

	public String getInstructions() {
		return "Hold shift to stop enemy in their tracks. Left Click to Deal Damage.";
	}

	public String getDescription() {
		return ChatColor.BOLD + "" + ChatColor.DARK_PURPLE + "Forbidden: " + ChatColor.GRAY
				+ "A Forbidden air ability. An air bender is able to stop someone in their tracks and deal constant damage to them.";
	}

	@Override
	public boolean isHarmlessAbility() {
		return false;
	}

	@Override
	public boolean isSneakAbility() {
		return true;
	}

	@Override
	public void progress() {
		if (!bPlayer.canBend(this)) {
			remove();
			return;
		}
		if (player.isSneaking()) {
			Vector vec = player.getLocation().getDirection().multiply(0);
			target.setVelocity(vec);
			powerRing(30, 0.75f, 2);
			
		}
		if (System.currentTimeMillis() - getStartTime() > this.duration) {
			remove();
			hits = 0;
			bPlayer.addCooldown(this);
			return;
		}
		if (hits >= maxHits) {
			remove();
			bPlayer.addCooldown(this);
			hits = 0;
			return;
		}
	}
	
	private void powerRing(int points, float size, int speed) {
		for (int i = 0; i < speed; i++) {
			this.currPoint += 360 / points;
			if (this.currPoint > 360) {
				this.currPoint = 0;
			}
			double angle = this.currPoint * Math.PI / 180.0D;
			double x = size * Math.cos(angle);
			double z = size * Math.sin(angle);
			Location loc = target.getLocation().add(x, 1.0D, z);
			ParticleEffect.CLOUD.display(loc, 0F, 0F, 0F, 0, 30);
		}
	}
	

	@Override
	public String getAuthor() {
		return "EmeraldJelly";
	}

	public static void dealDamage() {
		DamageHandler.damageEntity(targetO, 5, Snare.getAbility("Snare"));
		ParticleEffect.CLOUD.display(targetO.getLocation().add(0D, 2D, 0D), 0.5F, 0.5F, 0.5F, 1, 40);
		playAirbendingSound(targetO.getLocation());
		hits++;
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
