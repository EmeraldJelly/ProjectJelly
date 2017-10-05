package com.emeraldjelly.projectjelly.ability.water;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.WaterAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.BlockSource;
import com.projectkorra.projectkorra.util.ClickType;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;
import com.projectkorra.projectkorra.util.TempBlock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class GeyserRush extends WaterAbility implements AddonAbility {
	private long cooldown;
	private double range;
	private double damage;
	private TempBlock tempBlock;
	private Location location;
	private Block sourceBlock;
	private Vector direction;

	public GeyserRush(Player player) {
		super(player);
		if (!this.bPlayer.canBend(this)) {
			return;
		}
		setFields();
		start();
	}

	public void setFields() {
		this.cooldown = ConfigManager.getConfig().getLong("ProjectJelly.Water.GeyserRush.Cooldown");
		this.range = ConfigManager.getConfig().getDouble("ProjectJelly.Water.GeyserRush.Range");
		this.damage = ConfigManager.getConfig().getDouble("ProjectJelly.Water.GeyserRush.Damage");

		this.sourceBlock = BlockSource.getWaterSourceBlock(this.player, this.range, ClickType.LEFT_CLICK, true, true,
				true);
		this.location = this.sourceBlock.getLocation().add(0.0D, 1.5D, 0.0D).clone();
	}

	public void progress() {
		if (!this.player.isSneaking()) {
			TempBlock.startReversion();
			remove();
			return;
		}
		if (!this.bPlayer.canBendIgnoreCooldowns(this)) {
			remove();
			return;
		}
		if (this.sourceBlock.getLocation().distance(this.location) > this.range) {
			remove();
			return;
		}
		if (this.location.getBlock().getType().isSolid()) {
			remove();
			return;
		}
		this.bPlayer.addCooldown(this);
		start();

		Location currentLoc = this.location.clone();
		this.direction = this.player.getLocation().getDirection();
		this.location.add(this.direction);
		canAutoSource();
		this.tempBlock = new TempBlock(currentLoc.getBlock(), Material.STATIONARY_WATER, (byte) 8);
		ParticleEffect.WATER_DROP.display(this.location, 0.5F, 0.5F, 0.5F, 0.0F, 5);
		ParticleEffect.CLOUD.display(this.location, 0.5F, 0.5F, 0.5F, 0, 5
		playWaterbendingSound(this.location);
		lcation.getWorld().playSound.ENTITY_CREEPER_HURT, 1, 1);
		this.tempBlock.setRevertTime(2500L);
		for (Entity entity : GeneralMethods.getEntitiesAroundPoint(this.location, 2.0D)) {
			if (((entity instanceof Entity)) && (entity.getEntityId() != this.player.getEntityId())) {
				Location location = this.player.getEyeLocation();
				Vector vector = location.getDirection();
				entity.setVelocity(vector.normalize().multiply(0.9F));
				DamageHandler.damageEntity(entity, this.damage, this);
			}
		}
	}

	public long getCooldown() {
		return this.cooldown;
	}

	public Location getLocation() {
		return this.location;
	}

	public String getName() {
		return "GeyserRush";
	}

	public String getDescription() {
		return "TBD";
	}

	public String getInstructions() {
		return "Left Click at water, then Hold Shift to send the stream";
	}

	public boolean isHarmlessAbility() {
		return false;
	}

	public boolean isSneakAbility() {
		return true;
	}

	public String getAuthor() {
		return "NickC1211. Idea By EmeraldJelly";
	}

	public String getVersion() {
		return "v1.0.0";
	}

	public void load() {
	}

	public void stop() {
	}
}
