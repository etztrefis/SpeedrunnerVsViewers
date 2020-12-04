package me.trefis.speedrunnervsviewers.commands;

import me.trefis.speedrunnervsviewers.TeamManager;
import me.trefis.speedrunnervsviewers.context.Roles;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import me.trefis.speedrunnervsviewers.PlayerData;

public class SpeedrunnerCommand implements CommandExecutor {
    private final Plugin plugin;
    private final TeamManager teamManager;
    private final PlayerData playerData;

    public SpeedrunnerCommand(Plugin plugin,TeamManager teamManager, PlayerData playerData){
        this.plugin = plugin;
        this.teamManager = teamManager;
        this.playerData = playerData;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        try {
            Player player = plugin.getServer().getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage("Could not find player " + args[0]);
                return true;
            }
            if (args.length == 1) {
                    addSpeedrunner(player);
                    sender.sendMessage(ChatColor.BLUE + "Added player: " + player.getName() + " to speedrunners.");
                return true;
            } else if (args.length == 2 && args[1].equals("remove")) {
                removeSpeedrunner(player);
                sender.sendMessage(ChatColor.BLUE + "Removed player: " + player.getName() + " from speedrunners.");
                return true;
            }
        }catch (Exception error) {
            sender.sendMessage(ChatColor.RED + "Error: Usage of command is -  /speedrunner <nickname> [remove]");
        }
        return false;
    }

    private void addSpeedrunner(Player player){
        playerData.setRole(player, Roles.SPEEDRUNNER);
        teamManager.addPlayer(Roles.SPEEDRUNNER, player);
        player.getInventory().remove(Material.COMPASS);
    }

    private void removeSpeedrunner(Player player){
        playerData.reset(player);
        teamManager.removePlayer(Roles.SPEEDRUNNER, player);

        playerData.setRole(player, Roles.HUNTER);
        teamManager.addPlayer(Roles.HUNTER, player);
        player.getInventory().addItem(new ItemStack(Material.COMPASS));
    }
}
