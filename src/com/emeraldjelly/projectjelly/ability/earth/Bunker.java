package com.emeraldjelly.projectjelly.ability.earth;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.EarthAbility;
import com.projectkorra.projectkorra.util.TempBlock;

public class Bunker extends EarthAbility implements AddonAbility {

	private Location loc;
	private Location feetLoc;
	private int cooldown;
	

	public Bunker(Player player) {
		super(player);

		start();
		setFields();
	}

	public void setFields() {
		FileConfiguration cm = getConfig();
		this.feetLoc = player.getLocation().subtract(0, 1, 0);
		this.loc = player.getLocation();
		this.cooldown = cm.getInt("ProjectJelly.Earth.Bunker.Cooldown");
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
		return true;
	}

	public String getDescription() {
		return ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Defensive:" + ChatColor.GREEN
				+ "A simple earth ability that makes user immune to all damage. While using this"
				+ " ability the earthbender may not move." + " THIS ABILITY WILL NOT WORK AT ALL WHILE WEARING ARMOR!!";
	}

	public String getInstructions() {
		return "Hold shift.";
	}

	@Override
	public void progress() {
		if (!bPlayer.canBend(this)) {
			remove();
			return;
		}
		if (!EarthAbility.isEarthbendable(player, feetLoc.getBlock())) {
			remove();
			return;
		}
		if (player.isSneaking()) {
			Block bL = feetLoc.add(1, 0, 0).getBlock();
			Block bL1 = feetLoc.add(0, 0, 1).getBlock();
			Block bL2 = feetLoc.add(1, 1, 0).getBlock();
			Block bL3 = feetLoc.add(0, 1, 1).getBlock();
			Block bL4 = feetLoc.add(1, 2, 0).getBlock();
			Block bL5 = feetLoc.add(0, 2, 1).getBlock();
			Block bL6 = feetLoc.add(0, 3, 0).getBlock();
			Block bL7 = feetLoc.subtract(1, 0, 0).getBlock();
			Block bL8 = feetLoc.subtract(0, 0, 1).getBlock();
			Block bL9 = feetLoc.subtract(1, 0, 0).add(0, 1, 0).getBlock();
			Block bL10 = feetLoc.subtract(0, 0, 1).add(0, 1, 0).getBlock();
			
			playEarthbendingSound(loc);
			new TempBlock(bL, Material.DIRT, (byte) 0);
			new TempBlock(bL1, Material.DIRT, (byte) 0);
			new TempBlock(bL2, Material.DIRT, (byte) 0);
			new TempBlock(bL3, Material.DIRT, (byte) 0);
			new TempBlock(bL4, Material.DIRT, (byte) 0);
			new TempBlock(bL5, Material.DIRT, (byte) 0);
			new TempBlock(bL6, Material.DIRT, (byte) 0);
			new TempBlock(bL7, Material.DIRT, (byte) 0);
			new TempBlock(bL8, Material.DIRT, (byte) 0);
			new TempBlock(bL9, Material.DIRT, (byte) 0);
			new TempBlock(bL10, Material.DIRT, (byte) 0);
		}
		TempBlock.removeAll();
		remove();
		bPlayer.addCooldown(this);
		return;
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
