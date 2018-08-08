package com.skyandforest.reboot_shop;

import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;

public class VersionUtils {
	
	private static boolean setup;
	private static Method oldGetOnlinePlayersMethod;
	private static boolean useReflection;
	
	public static Collection<? extends Player> getOnlinePlayers() {
		try {
			
			if (!setup) {
				oldGetOnlinePlayersMethod = Bukkit.class.getDeclaredMethod("getOnlinePlayers");
				if (oldGetOnlinePlayersMethod.getReturnType() == Player[].class) {
					useReflection = true;
				}
				
				setup = true;
			}
		
			if (!useReflection) {
				return Bukkit.getOnlinePlayers();
			} else {
				Player[] playersArray = (Player[]) oldGetOnlinePlayersMethod.invoke(null);
				return ImmutableList.copyOf(playersArray);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}
}
