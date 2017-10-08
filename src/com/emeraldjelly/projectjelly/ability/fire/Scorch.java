package com.emeraldjelly.projectjelly.ability.fire;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.emeraldjelly.projectjelly.utility.JellyMethods;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.FireAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;

public class Scorch extends FireAbility implements AddonAbility {

	private long cooldown;
	private long cooldown1;
	private long cooldown2;
	private long cooldown3;
	private boolean ch1;
	private boolean ch2;
	private Location loc;
	private int duration;
	private long chT1;
	private long chT2;
	private int d1;
	private int d2;
	private int d3;
	private int r1;
	private int r2;
	private int currPoint;

	public Scorch(Player player) {
		super(player);

		start();
		setFields();
	}

	public void setFields() {
		this.cooldown1 = ConfigManager.getConfig().getLong(JellyMethods.configPath("fire", "Scorch", "Cooldown1"));
		this.cooldown2 = ConfigManager.getConfig().getLong(JellyMethods.configPath("fire", "Scorch", "Cooldown2"));
		this.cooldown3 = ConfigManager.getConfig().getLong(JellyMethods.configPath("fire", "Scorch", "Cooldown2"));
		this.d1 = ConfigManager.getConfig().getInt(JellyMethods.configPath("fire", "Scorch", "Damage1"));
		this.d2 = ConfigManager.getConfig().getInt(JellyMethods.configPath("fire", "Scorch", "Damage2"));
		this.d3 = ConfigManager.getConfig().getInt(JellyMethods.configPath("fire", "Scorch", "Damage3"));
		this.r1 = ConfigManager.getConfig().getInt(JellyMethods.configPath("fire", "Scorch", "Range1"));
		this.r2 = ConfigManager.getConfig().getInt(JellyMethods.configPath("fire", "Scorch", "Range2"));
		this.chT1 = ConfigManager.getConfig()
				.getInt(JellyMethods.configPath("fire", "Scorch", "PowerLevel2ChargeTime"));
		this.chT2 = ConfigManager.getConfig()
				.getInt(JellyMethods.configPath("fire", "Scorch", "PowerLevel3ChargeTime"));
		this.duration = ConfigManager.getConfig()
				.getInt(JellyMethods.configPath("fire", "Scorch", "DurationInSeconds"));
		this.loc = player.getLocation();
		ch1 = false;
		ch2 = false;

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
		return "Scorch";
	}

	public String getDescription() {
		return JellyMethods.abilityDescription(false, "Offensive/Utility", "fire",
				"A charge up advanced firebending move. This move allows the firebender to charge up to 3 different power levels.\r\n"
						+ "\r\n"
						+ "Power Level 1: The Weakest charge level. Can only deal a average fireblast to an enemy.\r\n"
						+ "Power Level 2: A strong charge level, however not the strongest. This allows the fire bender"
						+ " to create a small but concentrated blast that does pretty significant damage. (Requires a 5 Second Charge)\r\n"
						+ "\r\n"
						+ "Power Level 3: A overwhelmingly powerful charge level. This does not enable the firebender to fire a "
						+ "\"Blast\" however this does make it so the fire bender can move at incredibly fast moving speeds. "
						+ "While in this move the firebender damages Everything within a 3 block radius of them. The firebender"
						+ " may also FLY (using thrust from fire) during this mode but be warned, it also puts a huge strain on "
						+ "the firebender not allowing them to bend for 10 seconds after using this ability.");
	}

	public String getInstructions() {
		return "Tap Shift to use power level 1, Hold Shift to continue charging to different power levels.";
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
			if (System.currentTimeMillis() - getStartTime() > chT1) {
				ch1 = true;
				chargeRingOne(40, 0.75F, 2);
				if (GeneralMethods.isSolid(loc.getBlock())) {
					remove();
					player.sendMessage("Removed Due to IsSolid Issues");
					this.cooldown = cooldown2;
					bPlayer.addCooldown(this);
					return;
				}
				if (player.getLocation().distanceSquared(loc) > r2) {
					remove();
					this.cooldown = cooldown2;
					player.sendMessage("Removed Due to Range Issues");
					bPlayer.addCooldown(this);
					return;
				}
			}
			if (ch1) {
				if (!player.isSneaking()) {
					loc.add(player.getLocation().getDirection().multiply(1));
					ParticleEffect.FLAME.display(loc, 0.6F, 0.6F, 0.6F, 0, 40);
					for (Entity entity : GeneralMethods.getEntitiesAroundPoint(player.getLocation(), 3)) {
						if (entity instanceof LivingEntity && entity.getEntityId() != player.getEntityId()) {
							DamageHandler.damageEntity(entity, d2, this);
						}
					}
				}
			}
			if (System.currentTimeMillis() - getStartTime() > chT2) {
				ch1 = false;
				ch2 = true;
				chargeRingTwo(40, 1F, 2);
				chargeRingOne(40, 1.75F, 2);
			}
			if (ch2) {
					player.sendMessage("It Was Ran");
					player.setAllowFlight(true);
					player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, duration * 100, 3));
					if (!player.isSneaking()) {
						for (Entity entity : GeneralMethods.getEntitiesAroundPoint(player.getLocation(), 3)) {
							if (entity instanceof LivingEntity && entity.getEntityId() != player.getEntityId()) {
								DamageHandler.damageEntity(entity, d3, this);
								entity.setVelocity(new Vector(0.0D, 0.5D, 0.0D).add(GeneralMethods
										.getDirection(player.getLocation(), entity.getLocation()).multiply(2)));
								long startTime = System.currentTimeMillis();
								if (System.currentTimeMillis() - startTime > duration) {
									remove();
									player.sendMessage("DURATION");
									bPlayer.addCooldown(this);
									return;
								}
							}
						}
					}
			}
		}
	}

	@Override
	public String getAuthor() {
		return "EmeraldJelly";
	}

	private void chargeRingOne(int points, float size, int speed) {
		for (int i = 0; i < speed; i++) {
			this.currPoint += 360 / points;
			if (this.currPoint > 360) {
				this.currPoint = 0;
			}
			double angle = this.currPoint * Math.PI / 180.0D;
			double x = size * Math.cos(angle);
			double z = size * Math.sin(angle);
			Location pLoc = player.getLocation().add(x, 1.0D, z);
			ParticleEffect.SMOKE.display(pLoc, 0F, 0F, 0F, 0, 30);
		}
	}

	private void chargeRingTwo(int points, float size, int speed) {
		for (int i = 0; i < speed; i++) {
			this.currPoint += 360 / points;
			if (this.currPoint > 360) {
				this.currPoint = 0;
			}
			double angle = this.currPoint * Math.PI / 180.0D;
			double x = size * Math.sin(angle);
			double z = size * Math.cos(angle);
			Location pLoc = player.getLocation().add(x, 1.0D, z);
			ParticleEffect.FLAME.display(pLoc, 0F, 0F, 0F, 0, 30);
		}
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
