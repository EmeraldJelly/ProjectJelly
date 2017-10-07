package com.emeraldjelly.projectjelly.ability.water;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.PlantAbility;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.util.DamageHandler;
import com.projectkorra.projectkorra.util.ParticleEffect;
import com.projectkorra.projectkorra.util.TempBlock;

public class Sprout extends PlantAbility implements AddonAbility {
	
	private long cooldown;
	private double range;
	private double damage;
	private TempBlock tempBlock;
	private TempBlock vine;
	private TempBlock vine2;
	
	private Location location;
	private Location origin;
	private Vector direction;
	private Location target;
	private Location target2;

	public Sprout(Player player) {
		super(player);
		
		if (!bPlayer.canBend(this)){
			return;
		}
		
		if (player.getLocation().clone().add(0, -1, 0).getBlock().getType() == Material.GRASS){
			start();
		}else{
			return;
		}
		
		setFields();
		start();
	}
	
	public void setFields(){
		this.cooldown = ConfigManager.getConfig().getLong("ExtraAbilities.NickC1211.Sprout.Cooldown");
		this.range = ConfigManager.getConfig().getDouble("ExtraAbilities.NickC1211.Sprout.Range");
		this.damage = ConfigManager.getConfig().getDouble("ExtraAbilities.NickC1211.Sprout.Damage");
		
		this.origin = player.getLocation().clone().add(0, 0, 0);
		this.location = origin.clone();
		this.direction = player.getLocation().getDirection();
		this.direction.setY(0);
	}

	@Override
	public void progress() {
		if (!bPlayer.canBendIgnoreBindsCooldowns(this)){
			remove();
			return;
		}
		if (origin.distance(location) > range){
			remove();
			return;
		}
		if (player.isSneaking()){
			start();
		}else{
			remove();
			return;
		}
		if (location.getBlock().getType().isSolid()){
			remove();
			return;
		}
		if (location.clone().add(0, -1, 0).getBlock().getType() == Material.GRASS){
			start();
		}else{
			remove();
			return;
		}
		
		bPlayer.addCooldown(this);
		start();
		
		Location currentLoc = location.clone();
		direction = player.getLocation().getDirection();
		direction.setY(0);
		location.add(direction);
		tempBlock = new TempBlock(currentLoc.getBlock(), Material.DOUBLE_PLANT, (byte)2);
		ParticleEffect.VILLAGER_HAPPY.display(location, 0.5F, 0.5F, 0.5F, 0, 2);
		location.getWorld().playSound(location, Sound.BLOCK_GRASS_BREAK, 1, 1);
		tempBlock.setRevertTime(1100L);
		
		for (Entity entity : GeneralMethods.getEntitiesAroundPoint(location, 2)) {
			if (entity instanceof LivingEntity && entity.getEntityId() != player.getEntityId()) {
				target = entity.getLocation();
				target2 = entity.getLocation().add(0, 1, 0);
				vine = new TempBlock(target.getBlock(), Material.DOUBLE_PLANT, (byte)2);
				vine2 = new TempBlock(target2.getBlock(), Material.DOUBLE_PLANT, (byte)2);
				vine.setRevertTime(5000);
				vine2.setRevertTime(5000);
				DamageHandler.damageEntity(entity, damage, this);
				((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 3));
				return;
				
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
		return "Sprout";
	}
	
	@Override
	public String getDescription() {
		return "This ability allows skilled plantbenders to bring up the roots underground and shoot them at their opponent. This moves requires that you be standing on fertile ground (grass blocks). ";
	}
	
	@Override
	public String getInstructions() {
		return "Hold Shift and Left Click to shoot, and move cursor side to side to aim.";
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
