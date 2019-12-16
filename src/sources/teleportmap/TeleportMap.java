package teleportmap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class TeleportMap extends JavaPlugin {

    @Override
    public void onDisable() {
        getLogger().info("Stopping TeleportMap Plugin");
    }

    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerListener(), this);

        getLogger().info("Loaded TeleportMap Plugin Successfully");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (commandLabel.equalsIgnoreCase("teleportmap")) {
            sender.sendMessage(ChatColor.GREEN + "[TM INFO] Place a map into a item frame and with shift + right click to teleport");
            return true;
        }
        return false;
    }
}
