package com.emeraldjelly.projectjelly.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import com.emeraldjelly.projectjelly.ability.air.Snare;
import com.emeraldjelly.projectjelly.ability.fire.FireSerpent;
import com.emeraldjelly.projectjelly.ability.fire.Immolate;
import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.ability.CoreAbility;


public class AbilityListener implements Listener {

	@EventHandler
	public void onSwing(PlayerAnimationEvent event) {

		Player player = event.getPlayer();
		BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

		if (event.isCancelled() || bPlayer == null) {
			return;

		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase(null)) {
			return;

		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Immolate")) {
			new Immolate(player);
			
		} else if (CoreAbility.hasAbility(event.getPlayer(), Snare.class)) {
			Snare.dealDamage();
		}

	}

	@EventHandler
	public void onSneak(PlayerToggleSneakEvent event) {

		Player player = event.getPlayer();
		BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

		if (CoreAbility.hasAbility(player, FireSerpent.class)) {
			return;
		}

		if (event.isCancelled() || bPlayer == null) {
			return;

		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase(null)) {
			return;

		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("FireSerpent")) {
			new FireSerpent(player);	
		} else if (bPlayer.getBoundAbilityName().equalsIgnoreCase("Snare")) {
			new Snare(player);
		}

	}
}