package me.trefis.speedrunnervsviewers;

import me.trefis.speedrunnervsviewers.commands.GrouplistCommand;
import me.trefis.speedrunnervsviewers.commands.SpeedrunnerCommand;
import me.trefis.speedrunnervsviewers.events.Events;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class PwgoodViewersHuntPlugin extends JavaPlugin {
    @Override
    public void onEnable(){
        TeamManager manager = new TeamManager(this);
        PlayerData playerData = new PlayerData();

        getServer().getPluginManager().registerEvents(new Events(playerData, manager, this), this);

        Optional.ofNullable(getCommand("speedrunner"))
                .ifPresent(c -> c.setExecutor(new SpeedrunnerCommand(this, manager, playerData)));
        Optional.ofNullable(getCommand("grouplist"))
                .ifPresent(c -> c.setExecutor(new GrouplistCommand(playerData)));

        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Worker(this, playerData), 1, 1);

        getLogger().info("Speedrunners vs viewers plugin started.");
    }

    @Override
    public void onDisable(){
        getLogger().info("Speedrunners vs viewers plugin stopped.");
    }
}
