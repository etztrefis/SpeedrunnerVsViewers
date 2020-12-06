package me.trefis.speedrunnervsviewers.events;

import me.trefis.speedrunnervsviewers.PlayerData;
import me.trefis.speedrunnervsviewers.TeamManager;
import me.trefis.speedrunnervsviewers.context.Roles;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class Events implements Listener {
    private final PlayerData playerData;
    private final TeamManager teamManager;
    private final Plugin plugin;

    public Events(PlayerData playerData, TeamManager teamManager, Plugin plugin) {
        this.playerData = playerData;
        this.teamManager = teamManager;
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoining(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        playerData.setRole(player, Roles.HUNTER);
        teamManager.addPlayer(Roles.HUNTER, player);
        ItemStack compass = new ItemStack(Material.COMPASS);
        if (!player.getInventory().contains(compass)) {
            player.getInventory().addItem(new ItemStack(Material.COMPASS));
        }

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        playerData.reset(player);
        teamManager.removePlayer(Roles.SPEEDRUNNER, player);
        teamManager.removePlayer(Roles.HUNTER, player);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Roles data = playerData.getRole(player);
        if (data == Roles.HUNTER) {
            player.getInventory().addItem(new ItemStack(Material.COMPASS));
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Roles data = playerData.getRole(event.getEntity());
        if(data == Roles.HUNTER){
            Bukkit.getBanList(BanList.Type.NAME).addBan(event.getEntity().getName(), ChatColor.GREEN + "Сможешь зайти в следующей игре!", null, "trefis");
            Bukkit.getScheduler().runTaskLater(this.plugin, new Runnable(){
                @Override
                public void run(){
                    Bukkit.getPlayer(event.getEntity().getName()).kickPlayer(ChatColor.GREEN + "Сможешь зайти в следующей игре!");
                }
            }, 20 * 3);
        }
    }

    @EventHandler
    public void onWorldLoad(ServerLoadEvent event){
        for(OfflinePlayer player : Bukkit.getBannedPlayers()){
            Bukkit.getBanList(BanList.Type.NAME).pardon(player.getName());
        }
    }
}
