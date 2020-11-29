package me.trefis.speedrunnervsviewers;

import me.trefis.speedrunnervsviewers.context.Roles;
import org.apache.commons.lang.Validate;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
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

        }else {
            player.setCompassTarget(nearest.getLocation());
            PlayerInventory inventory = player.getInventory();
            if (inventory.getItemInMainHand().getType() == Material.COMPASS || inventory.getItemInOffHand().getType() == Material.COMPASS) {
                if (player.getWorld().getEnvironment() == World.Environment.NETHER || nearest.getWorld().getEnvironment() == World.Environment.NETHER
                        || player.getWorld().getEnvironment() == World.Environment.THE_END || nearest.getWorld().getEnvironment() == World.Environment.THE_END) {
                    drawDirection(player.getLocation(), nearest.getLocation(), 3);
                }
            }
        }
    }

    private void drawDirection(Location point1, Location point2, double space) {
        World world = point1.getWorld();
        Validate.isTrue(point2.getWorld().equals(world), "You have to be in same worlds!");
        Vector p1 = point1.toVector();
        Vector dir = point2.toVector().clone().subtract(p1).setY(0).normalize().multiply(space);
        Vector p = p1.add(dir);
        Particle.DustOptions dust = new Particle.DustOptions(Color.fromRGB(255, 255, 0), 1);
        world.spawnParticle(Particle.REDSTONE, p.getX(), p1.getY() + 1.25, p.getZ(), 0, 0, 0, 0, dust);

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
