package me.trefis.speedrunnervsviewers;

import me.trefis.speedrunnervsviewers.context.Roles;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.Comparator;

public class Worker implements  Runnable {
    private final Plugin plugin;
    private final PlayerData playerData;

    public Worker(Plugin plugin, PlayerData playerData){
        this.plugin = plugin;
        this.playerData = playerData;
    }

    @Override
    public void run(){
        for(Player player : playerData.getPlayersByRole(Roles.HUNTER)){
            updateCompass(player);
        }
    }

    private void updateCompass(Player player) {
        Player nearest = getNearestSpeedrunner(player);
        if (nearest == null) {
            float angle = (float) (Math.random() * Math.PI * 2);
            float dx = (float) (Math.cos(angle) * 5);
            float dz = (float) (Math.sin(angle) * 5);
            player.setCompassTarget(player.getLocation().add(new Vector(dx, 0, dz)));

        } else {
            player.setCompassTarget(nearest.getLocation());
        }
    }

    private Player getNearestSpeedrunner(Player player) {
        Location playerLocation = player.getLocation();

        return plugin.getServer().getOnlinePlayers().stream()
                .filter(p -> !p.equals(player))
                .filter(p -> playerData.getRole(p) == Roles.SPEEDRUNNER)
                .filter(p -> p.getWorld().equals(player.getWorld()))
                .min(Comparator.comparing(p -> p.getLocation().distance(playerLocation)))
                .orElse(null);
    }
}
