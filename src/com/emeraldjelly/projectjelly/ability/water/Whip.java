package com.emeraldjelly.projectjelly.ability.water;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.emeraldjelly.projectjelly.utility.JellyMethods;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.WaterAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;
import com.projectkorra.projectkorra.util.TempBlock;
import com.projectkorra.projectkorra.waterbending.util.WaterReturn;

public class Whip extends WaterAbility implements AddonAbility {
	
	private long cooldown;
	private double range;
	private double damage;
	private TempBlock tempBlock;
	
	private Location location;
	private Location origin;
	private Vector direction;
	
	public Whip(Player player) {
		super(player);
		
		if (!bPlayer.canBend(this)){
			return;
		}
		if (WaterReturn.hasWaterBottle(player)){
			start();
		}else{
			remove();
			return;
		}
		
		setFields();
		start();
	}
	
	public void setFields(){
		this.cooldown = ConfigManager.getConfig().getLong("ExtraAbilities.NickC1211.Whip.Cooldown");
		this.range = ConfigManager.getConfig().getDouble("ExtraAbilities.NickC1211.Whip.Range");
		this.damage = ConfigManager.getConfig().getDouble("ExtraAbilities.NickC1211.Whip.Damage");
		
		this.origin = player.getLocation().clone().add(0, 1, 0);
		this.location = origin.clone();
	}

	@Override
	public void progress() {
		if (!bPlayer.canBendIgnoreCooldowns(this)){
			remove();
			return;
		}
		if (origin.distance(location) > range){
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
		direction = player.getEyeLocation().getDirection();
		location.add(direction);
		tempBlock = new TempBlock(currentLoc.getBlock(), Material.STATIONARY_WATER, (byte)2);
		ParticleEffect.WATER_DROP.display(location, 0.5F, 0.5F, 0.5F, 0, 5);
		playWaterbendingSound(location);
		tempBlock.setRevertTime(1200L);
	
		for (Entity entity : GeneralMethods.getEntitiesAroundPoint(location, 2)) {
			if (entity instanceof Entity && entity.getEntityId() != player.getEntityId()) {
				Location location = player.getEyeLocation();
				Vector vector = location.getDirection();
				entity.setVelocity(vector.normalize().multiply(0.8F));
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
		return "Whip";
	}
	
	@Override
	public String getDescription() {
		return JellyMethods.waterDesc("Utility", "Send a whip of water at your opponent. You must have a bottle to use this ability.");
	}
	
	@Override
	public String getInstructions() {
		return "Hold Shift and Left Click to shoot.";
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
		return JellyMethods.getVersion();
	}

	@Override
	public void load() {
	}

	@Override
	public void stop() {
	
	}
}
