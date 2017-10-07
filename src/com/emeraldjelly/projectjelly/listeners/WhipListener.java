package com.NickC1211.korra.Whip;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;

public class WhipListener implements Listener {
	
	@EventHandler
	public void onLeftClick(PlayerAnimationEvent event){
		if (event.isCancelled()){
			return;
		}
		new Whip(event.getPlayer());
	}
  else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Whip")){
  new Whip(player);
}
