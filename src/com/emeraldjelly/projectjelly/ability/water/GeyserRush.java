package com.emeraldjelly.projectjelly.ability.water;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.WaterAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.BlockSource;
import com.projectkorra.projectkorra.util.ClickType;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;
import com.projectkorra.projectkorra.util.TempBlock;

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
		
		if (!bPlayer.canBend(this)){
			return;
		}
		setFields();
		start();
	}
	
	public void setFields(){
		this.cooldown = ConfigManager.getConfig().getLong("ExtraAbilities.NickC1211.GeyserRush.Cooldown");
		this.range = ConfigManager.getConfig().getDouble("ExtraAbilities.NickC1211.GeyserRush.Range");
		this.damage = ConfigManager.getConfig().getDouble("ExtraAbilities.NickC1211.GeyserRush.Damage");
		
		this.sourceBlock = BlockSource.getWaterSourceBlock(player, range, ClickType.LEFT_CLICK, true, true, true);
		this.location = sourceBlock.getLocation().add(0, 1.5F, 0).clone();
	}

	@Override
	public void progress() {
		if (bPlayer.canBendIgnoreCooldowns(this)) {
			remove();
			return;
		}
		if (!player.isSneaking()){
			TempBlock.startReversion();;
			remove();
			return;
		}
		if (sourceBlock.getLocation().distance(location) > range){
			remove();
			return;
		}
		if (location.getBlock().getType().isSolid()){
			remove();
			return;
		}
		
		bPlayer.addCooldown(this);
		start();
		
		Location currentLoc = location.clone();
		direction = player.getLocation().getDirection();
		location.add(direction);
		tempBlock = new TempBlock(currentLoc.getBlock(), Material.STATIONARY_WATER, (byte)8);
		ParticleEffect.WATER_DROP.display(location, 0.5F, 0.5F, 0.5F, 0, 5);
		ParticleEffect.CLOUD.display(location, 0.5F, 0.5F, 0.5F, 0, 5);
		playWaterbendingSound(location);
		location.getWorld().playSound(location, Sound.ENTITY_CREEPER_HURT, 1, 1);
		tempBlock.setRevertTime(2500L);
	
		for (Entity entity : GeneralMethods.getEntitiesAroundPoint(location, 2)) {
			if (entity instanceof Entity && entity.getEntityId() != player.getEntityId()) {
				Location location = player.getEyeLocation();
				Vector vector = location.getDirection();
				entity.setVelocity(vector.normalize().multiply(0.9F));
				DamageHandler.damageEntity(entity, damage, this);
			}
		}
	}

	@Override
	public long getCooldown() {
		return cooldown;
	}
	@Override
	public Location getLocation() {
		return location;
	}
	
	@Override
	public String getName() {
		return "GeyserRush";
	}
	
	@Override
	public String getDescription() {
		return "Send a GeyserRush of water at your opponent. You must have a bottle to use this ability.";
	}
	
	@Override
	public String getInstructions() {
		return "Left Click at water, then Hold y";
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
	public String getAuthor() {
		return "NickC1211";
	}

	@Override
	public String getVersion() {
		return "v1.0";
	}

	@Override
	public void load() {
	}

	@Override
	public void stop() {
	
	}
}
