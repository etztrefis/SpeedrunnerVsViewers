package me.trefis.speedrunnervsviewers.commands;

import me.trefis.speedrunnervsviewers.PlayerData;
import me.trefis.speedrunnervsviewers.context.Roles;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class GrouplistCommand implements CommandExecutor {
    private final PlayerData playerData;

    public GrouplistCommand(PlayerData playerData){
        this.playerData = playerData;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        StringBuilder result = new StringBuilder("");
        String speedrunners = formatPlayerList(playerData.getPlayersByRole(Roles.SPEEDRUNNER));
        result.append(ChatColor.YELLOW + "Speedrunners:\n").append(speedrunners);

        result.append("\n");
        String hunters = formatPlayerList(playerData.getPlayersByRole(Roles.HUNTER));
        result.append(ChatColor.AQUA +  "\nHunters:\n").append(hunters);

        sender.sendMessage(result.toString());

        return true;
    }
    private String formatPlayerList(List<Player> players){
        return players.stream()
                .map(HumanEntity::getName)
                .collect(Collectors.joining(", "));
    }
}
