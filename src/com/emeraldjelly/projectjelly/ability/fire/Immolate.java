package com.emeraldjelly.projectjelly.ability.fire;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.CombustionAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Immolate extends CombustionAbility implements AddonAbility {
	private Location pulse;
	private Location origin;
	private Vector direct;
	private long cooldown;
	private double range;
	private int speed;
	private int played = 0;
	private double damage;
	private double sacrifice;
	private int fireTicks;
	public float radius = 2.0F;
	public float grow = 0.05F;
	public double radials = 0.19634954084936207D;
	public int circles = 3;
	public int helixes = 4;
	protected int step = 0;

	public Immolate(Player player) {
		super(player);
		if (!this.bPlayer.canBend(this)) {
			return;
		}
		this.direct = player.getLocation().getDirection();
		this.pulse = player.getLocation().clone().add(0.0D, 1.0D, 0.0D);
		this.origin = player.getLocation().clone().add(0.0D, 1.0D, 0.0D);

		this.cooldown = ConfigManager.getConfig().getLong("ProjectJelly.Fire.Immolate.Cooldown");
		this.damage = ConfigManager.getConfig().getDouble("ProjectJelly.Fire.Immolate.Damage");
		this.range = ConfigManager.getConfig().getDouble("ProjectJelly.Fire.Immolate.Range");
		this.speed = 1;
		this.sacrifice = ConfigManager.getConfig().getDouble("ProjectJelly.Fire.Immolate.Sacrifice");
		this.fireTicks = ConfigManager.getConfig().getInt("ProjectJelly.Fire.Immolate.FireTicks");

		start();
	}

	public long getCooldown() {
		return this.cooldown;
	}

	public String getDescription() {
		return "Left click to sacrifice your energy into your inner fire and unleash a great blast of power!";
	}

	public Location getLocation() {
		return null;
	}

	public String getName() {
		return "Immolate";
	}

	public boolean isHarmlessAbility() {
		return false;
	}

	public boolean isSneakAbility() {
		return false;
	}

	public void load() {

	}

	public void stop() {
		
	}

	public String getAuthor() {
		return "EmeraldJelly";
	}

	public String getVersion() {
		return "v1.0.1";
	}

	public void progress() {
		if (this.pulse.getBlock().isLiquid()) {
			remove();
			return;
		}
		if ((GeneralMethods.isSolid(this.pulse.getBlock())) || (isWater(this.pulse.getBlock()))
				|| (isLava(this.pulse.getBlock()))) {
			remove();
			this.bPlayer.addCooldown(this);
			return;
		}
		if (!this.bPlayer.canBendIgnoreBinds(this)) {
			remove();
			return;
		}
		if (this.origin.distance(this.pulse) > this.range) {
			remove();
			this.bPlayer.addCooldown(this);
			return;
		}
		this.pulse.add(this.direct).multiply(this.speed);
		while (this.played <= 2) {
			playCombustionSound(this.origin);
			DamageHandler.damageEntity(this.player, this.sacrifice, this);
			sacrificeEffect();
			this.player.setFireTicks(20);
			this.played += 1;
		}
		callEffect();
		for (Entity entity : GeneralMethods.getEntitiesAroundPoint(this.pulse, 4.0D)) {
			if (((entity instanceof LivingEntity)) && (entity.getUniqueId() != this.player.getUniqueId())) {
				DamageHandler.damageEntity(entity, this.damage, this);
				entity.setFireTicks((int) (this.fireTicks * 20.0D));
				ParticleEffect.FLAME.display(entity.getLocation(), 0.2F, 0.2F, 0.2F, 1.0F, 600);
				remove();
				this.bPlayer.addCooldown(this);
				return;
			}
		}
	}

	public void sacrificeEffect() {
		Location location = this.player.getLocation().add(0.0D, 1.0D, 0.0D);
		for (int x = 0; x < this.circles; x++) {
			for (int i = 0; i < this.helixes; i++) {
				double angle = this.step * this.radials + 6.283185307179586D * i / this.helixes;
				Vector v = new Vector(Math.cos(angle) * this.radius, this.step * this.grow,
						Math.sin(angle) * this.radius);
				rotateAroundAxisX(v, (location.getPitch() + 90.0F) * 0.017453292F);
				rotateAroundAxisY(v, -location.getYaw() * 0.017453292F);

				location.add(v);
				ParticleEffect.FLAME.display(location, 0.0F, 0.0F, 0.0F, 0.1F, 100);
				location.subtract(v);
			}
			this.step += 1;
		}
	}

	public static final Vector rotateAroundAxisY(Vector v, double angle) {
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		double x = v.getX() * cos + v.getZ() * sin;
		double z = v.getX() * -sin + v.getZ() * cos;
		return v.setX(x).setZ(z);
	}

	public static final Vector rotateAroundAxisX(Vector v, double angle) {
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		double y = v.getY() * cos - v.getZ() * sin;
		double z = v.getY() * sin + v.getZ() * cos;
		return v.setY(y).setZ(z);
	}

	public void callEffect() {
		playFirebendingParticles(this.pulse, 200, 0.3F, 0.3F, 0.3F);
		if (new Random().nextInt(7) == 0) {
			playFirebendingSound(this.pulse);
		}
	}
}
