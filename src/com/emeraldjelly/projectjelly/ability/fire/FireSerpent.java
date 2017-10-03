package com.emeraldjelly.projectjelly.ability.fire;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.FireAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;

import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class FireSerpent extends FireAbility implements AddonAbility {
	private Player p;
	private Location loc;
	private Vector dir;
	private long startTime;
	private boolean Charged;
	private boolean notified;
	private Location start;
	private int particleAmount;
	private float BallSize;
	static FileConfiguration cm = ConfigManager.getConfig();
	private double damage = cm.getDouble("ProjectJelly.Fire.FireSerpent.Damage");
	private long cooldown = cm.getLong("ProjectJelly.Fire.FireSerpent.Cooldown");
	private long chargetime = cm.getLong("ProjectJelly.Fire.FireSerpent.ChargeTime");
	private int range = cm.getInt("ProjectJelly.Fire.FireSerpent.Range");
	private long duration = cm.getLong("ProjectJelly.Fire.FireSerpent.Duration");

	public FireSerpent(Player player) {
		super(player);
		this.particleAmount = 0;
		this.BallSize = 2.0F;
		this.p = player;

		this.loc = GeneralMethods.getTargetedLocation(this.p, 6);
		this.start = GeneralMethods.getTargetedLocation(this.p, 6);
		this.dir = GeneralMethods.getTargetedLocation(this.p, 6).getDirection();
		this.startTime = System.currentTimeMillis();
		this.Charged = false;
		this.notified = false;

		start();
	}

	public long getCooldown() {
		return this.cooldown;
	}

	public Location getLocation() {
		return this.loc;
	}

	public String getName() {
		return "FireSerpent";
	}

	public boolean isHarmlessAbility() {
		return false;
	}

	public boolean isSneakAbility() {
		return true;
	}

	public String getInstructions() {
		return "Hold Shift until charged, then release";
	}

	public void progress() {
		if (bPlayer.isOnCooldown(this)) {
			return;
		}
		if (!this.Charged) {
			if ((this.p.isSneaking()) && (!this.Charged)) {
				this.loc = GeneralMethods.getTargetedLocation(this.p, 6);
				this.start = GeneralMethods.getTargetedLocation(this.p, 6);
				if (this.particleAmount <= 100) {
					this.particleAmount += 1;
				}
				if (this.BallSize >= 0.1F) {
					this.BallSize = ((float) (this.BallSize - 0.02D));
				}
				if (new Random().nextInt(7) == 0) {
					playFirebendingSound(this.loc);
				}
				ParticleEffect.SMOKE_NORMAL.display(this.loc, this.BallSize, this.BallSize, this.BallSize, 0.0F,
						this.particleAmount);
			}
			if ((this.p.isSneaking()) && (System.currentTimeMillis() > this.startTime + this.chargetime)) {
				this.particleAmount = 200;
				this.BallSize = 0.2F;
				if (new Random().nextInt(7) == 0) {
					playFirebendingSound(this.loc);
				}
					ParticleEffect.FLAME.display(this.loc, this.BallSize + 0.05F, this.BallSize + 0.05F,
							this.BallSize + 0.05F, 0.05F, this.particleAmount);
				if (!this.notified) {
					playCombustionSound(this.loc);
					this.p.sendMessage(
							ChatColor.RED + "Your inner fire is ready to be unleashed into a powerful fire Serpent!");
					this.notified = true;
				}
			}
			if (!this.p.isSneaking()) {
				if (System.currentTimeMillis() > this.startTime + this.chargetime) {
					this.loc = this.loc.add(this.dir);
					this.start = GeneralMethods.getTargetedLocation(this.p, 6);
					this.dir = GeneralMethods.getTargetedLocation(this.p, 6).getDirection();

					this.Charged = true;
					boolean ds = false;
					ds = true;
					if ((ds) && (this.Charged)) {
						this.loc.getWorld().playSound(this.loc, Sound.ENTITY_CREEPER_PRIMED, 2.0F, 0.1F);
						ds = false;
					}
				} else {
					remove();
				}
			}
		} else {
			if (!this.player.isSneaking()) {
				this.dir = GeneralMethods.getTargetedLocation(this.p, 6).getDirection();
				this.loc.add(this.dir);
			} else {
				returnToSender();
			}
			this.loc.add(this.dir);
			if (System.currentTimeMillis() - getStartTime() > this.duration) {
				remove();
				bPlayer.addCooldown(this);
				return;
			}
			if (new Random().nextInt(7) == 0) {
				playFirebendingSound(this.loc);
			}
		
				ParticleEffect.FLAME.display(this.loc, this.BallSize + 0.05F, this.BallSize + 0.05F,
						this.BallSize + 0.05F, 0.05F, this.particleAmount);
				ParticleEffect.SMOKE_NORMAL.display(this.loc, this.BallSize + 0.1F, this.BallSize + 0.1F,
						this.BallSize + 0.1F, 0.005F, this.particleAmount);
			if ((this.p.isDead()) || (!this.p.isOnline())) {
				return;
			}
			if (GeneralMethods.isSolid(this.loc.getBlock())) {
				doExplosion();
				boolean ds = false;
				bPlayer.addCooldown(this);
				ds = true;
				if (ds) {
					this.loc.getWorld().playSound(this.loc, Sound.ENTITY_CREEPER_DEATH, 2.0F, 0.1F);
					ds = false;
				}
				remove();
				return;
			}
			if (this.start.distance(this.loc) > this.range) {
				this.p.sendMessage(ChatColor.RED + "Your Serpent has strayed too far. You have lost control.");
				remove();
				return;
			}
			if (this.loc.getBlock().isLiquid()) {
				doExplosion();
				remove();
				return;
			}
			for (Entity e : GeneralMethods.getEntitiesAroundPoint(this.loc, 2.5D)) {
				if (((e instanceof LivingEntity)) && (e.getEntityId() != this.p.getEntityId())) {
					DamageHandler.damageEntity(e, this.damage, this);
					e.setFireTicks(3);
					bPlayer.addCooldown(this);
				}
			}
		}
	}

	public void doExplosion() {
		for (Entity e : GeneralMethods.getEntitiesAroundPoint(this.loc, 10.0D)) {
			if (((e instanceof LivingEntity)) && (e.getEntityId() != this.p.getEntityId())) {
				if (new Random().nextInt(7) == 0) {
					playFirebendingSound(this.loc);
				}
					ParticleEffect.FLAME.display(e.getLocation(), 0.2F, 0.2F, 0.2F, 1.0F, 600);
				}
			}
		}
	private void returnToSender() {
		Location loc = this.player.getEyeLocation();
		Vector dV = loc.getDirection().normalize();
		loc.add(new Vector(dV.getX() * 6.0D, dV.getY() * 6.0D, dV.getZ() * 6.0D));

		Vector vector = loc.toVector().subtract(this.loc.toVector());
		this.dir = loc.setDirection(vector).getDirection().normalize();
	}

	public String getAuthor() {
		return "EmeraldJelly";
	}

	public String getVersion() {
		return "v1.0.0";
	}

	public String getDescription() {
		return getName() + " " + getVersion() + " Developed by " + getAuthor()
				+ ": \nAn ancient fire-bending technique, requiring sharp focus and a keen eye, allowing you to summon and control a mystical, powerful flying Fire Serpent";
	}

	public void load() {
	}

	public void stop() {
	}
}
