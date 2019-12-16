package teleportmap;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event){
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        if (entity instanceof ItemFrame) {
            final ItemFrame frame = (ItemFrame) entity;
            Rotation frameRotation = frame.getRotation().rotateCounterClockwise();
            frame.getItem();
            if (frame.getItem().getType() == Material.FILLED_MAP) {
                if (player.isSneaking()) {
                    frame.setRotation(frameRotation);
                    final MapMeta meta = (MapMeta)frame.getItem().getItemMeta();
                    assert meta != null;
                    final MapView mapView = Bukkit.getMap((short)meta.getMapId());
                    assert mapView != null;
                    try {
                        Location location = new Location(mapView.getWorld(), mapView.getCenterX(),
                                mapView.getWorld().getHighestBlockYAt(mapView.getCenterX(), mapView.getCenterZ()), mapView.getCenterZ());
                        player.teleport(location);
                        player.sendMessage(ChatColor.GREEN + "Teleported to " + location.getX() + ", " + location.getY() + ", " + location.getZ());
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        int xp = player.getTotalExperience();
        if (player.getKiller() != null) {
            if (player.getKiller() != player) {
                event.setDeathMessage("Something wrong had happened between " + player.getName() + " and " + player.getKiller().getName());
                player.setTotalExperience(xp);
            } else if (player.getKiller() == player) {
                event.setDeathMessage(player.getName() + " has a problem with himself");
            }
        }
        try {
            EntityDamageEvent.DamageCause cause = player.getLastDamageCause().getCause();
            if (cause == EntityDamageEvent.DamageCause.PROJECTILE) {
                player.chat("Qualcú té una tirita?");
                event.setDeathMessage("A 'Latin' has killed " + player.getName());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
