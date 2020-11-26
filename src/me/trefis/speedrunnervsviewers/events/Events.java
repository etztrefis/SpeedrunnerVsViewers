package me.trefis.speedrunnervsviewers.events;

import me.trefis.speedrunnervsviewers.PlayerData;
import me.trefis.speedrunnervsviewers.TeamManager;
import me.trefis.speedrunnervsviewers.context.Roles;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class Events implements Listener {
    private final PlayerData playerData;
    private final TeamManager teamManager;

    public Events(PlayerData playerData, TeamManager teamManager){
        this.playerData = playerData;
        this.teamManager = teamManager;
    }

    @EventHandler
    public void onJoining(PlayerJoinEvent event){
        event.setJoinMessage(ChatColor.YELLOW + "You were automatically assigned to the hunters team.");
        Player player = event.getPlayer();
        playerData.setRole(player, Roles.HUNTER);
        teamManager.addPlayer(Roles.HUNTER, player);
        ItemStack compass = new ItemStack(Material.COMPASS);
        if(!player.getInventory().contains(compass)){
            player.getInventory().addItem(new ItemStack(Material.COMPASS));
        }

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        playerData.reset(player);
        teamManager.removePlayer(Roles.SPEEDRUNNER, player);
        teamManager.removePlayer(Roles.HUNTER, player);
    }
}
