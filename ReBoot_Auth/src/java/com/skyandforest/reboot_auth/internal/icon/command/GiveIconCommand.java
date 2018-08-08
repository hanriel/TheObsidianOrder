package com.skyandforest.reboot_auth.internal.icon.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.skyandforest.reboot_auth.exception.FormatException;
import com.skyandforest.reboot_auth.internal.icon.IconCommand;
import com.skyandforest.reboot_auth.util.ItemStackReader;

public class GiveIconCommand extends IconCommand {
	
	private ItemStack itemToGive;
	private String errorMessage;
	
	public GiveIconCommand(String command) {
		super(command);
		
		try {
			ItemStackReader reader = new ItemStackReader(command, true);
			itemToGive = reader.createStack();
			
		} catch (FormatException e) {
			errorMessage = ChatColor.RED + "Invalid item to give: " + e.getMessage();
		}
	}

	@Override
	public void execute(Player player) {
		if (errorMessage != null) {
			player.sendMessage(errorMessage);
			return;
		}
		
		player.getInventory().addItem(itemToGive.clone());
	}

}
