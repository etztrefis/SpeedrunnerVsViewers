package me.trefis.speedrunnervsviewers;
import me.trefis.speedrunnervsviewers.context.Roles;

import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;


public class PlayerData {
    private final Map<Player, PlayerDetails> players = new HashMap<>();

    public List<Player> getPlayersByRole(Roles role){
        return players.entrySet().stream()
                .filter(e -> e.getValue().role == role)
                .map(Map.Entry :: getKey)
                .collect(Collectors.toList());
    }

    public Roles getRole(Player player){
        return Optional.ofNullable(players.get(player))
                .map(PlayerDetails :: getRole)
                .orElse(null);
    }

    public void reset(Player player){
        players.remove(player);
    }

    public void setRole(Player player, Roles role){
        PlayerDetails details = players.getOrDefault(player, new PlayerDetails());
        details.setRole(role);
        players.putIfAbsent(player, details);
    }

    private static class PlayerDetails{
        private Roles role;

        public Roles getRole(){
            return role;
        }
        public void setRole(Roles role){
            this.role = role;
        }
    }
}
