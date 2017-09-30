package com.emeraldjelly.projectjelly.ability.air;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.AirAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Whirlwind extends AirAbility implements AddonAbility {
	private double t = 0.7853981633974483D;
	Location loc;
	private boolean isCharged;
	private long chargeTime;
	private long cooldown;
	private double damage;
	private double knockback;

	public Whirlwind(Player player) {
		super(player);

		setFields();
		start();
	}

	public void setFields() {
		this.loc = this.player.getLocation().clone();
		this.damage = ConfigManager.getConfig().getDouble("ProjectJelly.Air.Whirlwind.Damage");
		this.knockback = ConfigManager.getConfig().getDouble("ProjectJelly.Air.Whirlwind.KnockBack");
		this.cooldown = ConfigManager.getConfig().getLong("ProjectJelly.Air.Whirlwind.Cooldown");
		this.chargeTime = ConfigManager.getConfig().getLong("ProjectJelly.Air.Whirlwind.ChargeTime");
	}

	public long getCooldown() {
		return this.cooldown;
	}

	public Location getLocation() {
		return null;
	}

	public String getName() {
		return "Whirlwind";
	}

	public boolean isHarmlessAbility() {
		return false;
	}

	public boolean isSneakAbility() {
		return true;
	}

	public void progress() {
		if (!this.bPlayer.canBendIgnoreCooldowns(this)) {
			remove();
			return;
		}
		if ((System.currentTimeMillis() > getStartTime() + this.chargeTime) && (!this.isCharged)) {
			this.isCharged = true;
		}
		if (!this.player.isSneaking()) {
			if (this.isCharged) {
				radialWave();
			} else {
				remove();
			}
		} else if (this.isCharged) {
			ParticleEffect.CLOUD.display(this.player.getLocation().add(0.0D, 0.0D, 0.0D), 0.4F, 0.4F, 0.4F, 0.0F, 75);
			this.loc = this.player.getLocation();
		}
	}

	public String getAuthor() {
		return "EmeraldJelly";
	}

	public void radialWave() {
		if (this.isCharged) {
			this.t += 0.3141592653589793D;
			for (double theta = 0.0D; theta <= 6.283185307179586D; theta += 0.04908738521234052D) {
				double x = this.t * Math.cos(theta);
				double y = 2.0D * Math.exp(-0.1D * this.t) * Math.sin(this.t) + 1.5D;
				double z = this.t * Math.sin(theta);
				this.loc.add(x, y, z);
				ParticleEffect.CLOUD.display(this.loc, 0.0F, 0.0F, 0.0F, 0.0F, 1);
				for (Entity entity : GeneralMethods.getEntitiesAroundPoint(this.loc, 3.0D)) {
					if (((entity instanceof LivingEntity)) && (entity.getUniqueId() != this.player.getUniqueId())) {
						DamageHandler.damageEntity(entity, this.damage, this);
						entity.setVelocity(new Vector(0.0D, 0.5D, 0.0D).add(
								GeneralMethods.getDirection(this.loc, entity.getLocation()).multiply(this.knockback)));
					}
				}
				this.loc.subtract(x, y, z);

				theta += 0.04908738521234052D;

				x = this.t * Math.cos(theta);
				y = 2.0D * Math.exp(-0.1D * this.t) * Math.sin(this.t) + 1.5D;
				z = this.t * Math.sin(theta);
				this.loc.add(x, y, z);
				ParticleEffect.CLOUD.display(this.loc, 0.0F, 0.0F, 0.0F, 0.0F, 1);
				if (new Random().nextInt(7) == 0) {
					playAirbendingSound(this.loc);
				}
				for (Entity entity : GeneralMethods.getEntitiesAroundPoint(this.loc, 3.0D)) {
					if (((entity instanceof LivingEntity)) && (entity.getUniqueId() != this.player.getUniqueId())) {
						DamageHandler.damageEntity(entity, this.damage, this);
						entity.setVelocity(new Vector(0.0D, 0.5D, 0.0D).add(
								GeneralMethods.getDirection(this.loc, entity.getLocation()).multiply(this.knockback)));
					}
				}
				this.loc.subtract(x, y, z);
			}
		}
		if (this.t > 20.0D) {
			remove();
			this.bPlayer.addCooldown(this);
			return;
		}
	}

	public String getVersion() {
		return "v1.0.0";
	}

	public String getDescription() {
		return "This is a very high level airbending move, requiring a lot of focus and breathing. You are able to create a wave of air knocking back your enemies while dealing damage. To do this, hold shift until you see air particles then release.";
	}

	public void load() {
	}

	@Override
	public void stop() {

	}
}
