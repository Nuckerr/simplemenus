package wtf.nucker.simplemenus.adventure;

import de.tr7zw.changeme.nbtapi.NBTItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.logging.Level;

/**
 * @author Nucker
 * @project KitPvpCore
 * @date 01/07/2021
 */
public class MenuManager {

    private static MenuManager instance;

    private final Map<UUID, Button> buttons;
    private final Map<Player, Menu> openMenus;

    private MenuSettings settings;

    /**
     * Initiates the menu system
     * @param plugin your plugin instance/main class
     */
    public MenuManager(Plugin plugin, MenuSettings settings) {
        instance = this;

        try {
            //noinspection ConstantConditions
            if(Player.class.getMethod("sendMessage", Component.class) == null) {
                Bukkit.getLogger().log(Level.SEVERE, "Unable to load simple menus because you are not running on paper/a support paper fork");
                throw new IllegalStateException("Running on a unsupported plugin jar");
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        if(!(this.getSubVersion() >= 16)) {
            Bukkit.getLogger().log(Level.SEVERE, "Unable to load simple menus because you are using the adventure api on a unsupported version");
            throw new IllegalStateException("Running on unsupported version");
        }

        this.settings = settings;
        this.buttons = new HashMap<>();
        this.openMenus = new HashMap<>();
        plugin.getServer().getPluginManager().registerEvents(this.getListener(), plugin);
    }

    public MenuManager(Plugin plugin) {
        this(plugin, new MenuSettings());
    }


    private Listener getListener() {
        return new Listener() {

            @EventHandler
            public void onClick(InventoryClickEvent e) {
                if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) return;
                openMenus.forEach(((player, menu) -> {
                    if (e.getWhoClicked() == player && e.getInventory().equals(menu.getInventory())) {
                        menu.listeners.forEach(event -> event.accept(e));
                        e.setCancelled(menu.cancelsClicks());
                    }
                }));
                if (Button.isButton(e.getCurrentItem())) {
                    e.setCancelled(true);
                    Button.getButtonById(UUID.fromString(new NBTItem(e.getCurrentItem()).getString( "button").replace("\"", ""))).onClick(e);
                }
            }

            @EventHandler
            public void onClose(InventoryCloseEvent e) {
                if (e.getPlayer() instanceof Player) {
                    Player player = (Player) e.getPlayer();
                    List<Player> removedPlayers = new ArrayList<>();
                    openMenus.forEach(((p, menu) -> {
                        if (p == player && e.getInventory().equals(menu.getInventory())) {
                            if(!menu.isClosable()) {
                                if(menu.isClosing()) {
                                    menu.closeEvent.accept(e);
                                    removedPlayers.add(player);
                                }else {
                                    menu.open(player);
                                }
                                return;
                            }
                            menu.setClosing(true);
                            menu.closeEvent.accept(e);
                            removedPlayers.add(player);
                        }
                    }));
                    for (Player removedPlayer : removedPlayers) {
                        openMenus.remove(removedPlayer);
                    }
                }
            }
        };
    }

    protected static MenuManager getInstance() {
        return instance;
    }

    /**
     * Registers a button for use
     * @param button the button you are registering
     * @see Button
     */
    public void registerButton(Button button) {
        this.buttons.put(button.getId(), button);
    }

    /**
     * @return a map of registered buttons
     */
    public Map<UUID, Button> getButtons() {
        return buttons;
    }

    /**
     * @return a map of players with open menus
     */
    public Map<Player, Menu> getOpenMenus() {
        return openMenus;
    }

    /**
     * @return SimpleMenu's settings
     * @see MenuSettings
     */
    public MenuSettings getSettings() {
        return settings;
    }

    /**
     * Set the settings for simple menu
     * @param settings the settings you are setting
     */
    public void setSettings(MenuSettings settings) {
        this.settings = settings;
    }


    private int getSubVersion() {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").replace("v", "").split(",")[3];
        return Integer.parseInt(version.replace("1_", "").replaceAll("_R\\d", ""));
    }
}
