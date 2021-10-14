package wtf.nucker.simplemenus.adventure;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * @author Nucker
 * @project KitPvpCore
 * @date 01/07/2021
 */
public class Menu {

    private final int rows;
    private final Component name;
    private final Inventory inventory;

    private boolean cancelClicks;
    private boolean closable;
    private boolean closing = false;

    protected ArrayList<Consumer<InventoryClickEvent>> listeners;
    protected Consumer<InventoryCloseEvent> closeEvent = inventoryCloseEvent -> {};

    /**
     * Create a menu
     * @param rows the amount of rows (rows * 9)
     * @param name the title of the menu
     * @see Menu#Menu(int, Component, boolean) to
     */
    public Menu(int rows, Component name) {
        this(rows, name, true);
    }

    /**
     * Create a menu (with the option
     * @param rows the amount of rows (rows * 9)
     * @param name the title of the menu
     * @param canceledClicks weather the menu should cancel click events (by default this is true)
     * @see Menu#Menu(int, Component) to create a menu without setting cancel clicks
     */
    public Menu(int rows, Component name, boolean canceledClicks) {
        this.listeners = new ArrayList<>();
        this.rows = rows;
        this.name = name;
        this.inventory = Bukkit.createInventory(null, rows * 9, name);
        this.cancelClicks = canceledClicks;
        this.closable = true;
    }

    /**
     * Opens the menu
     * @param p player
     * @deprecated Using this when a menu is already open duplicates their place in the `Open Menus` map. Use {@link Menu#switchMenu(Player)}
     */
    public void open(Player p) {
        p.openInventory(this.inventory);
        MenuManager.getInstance().getOpenMenus().put(p, this);
    }

    /**
     * Allows you to open a different menu without duplicating their state in map of open menus
     * (If the player is not already in a menu, it will just open normally)
     * @param p player
     */
    public void switchMenu(Player p) {
        if(MenuManager.getInstance().getOpenMenus().containsKey(p)) {
            p.openInventory(this.getInventory());
        }else {
            this.open(p);
        }
    }

    /**
     * Execute code when the inventory is closed (WONT BE CALLED IF Menu#isClosable IS FALSE AND IT IS BEING CLOSED BY THE PLAYER)
     * @param consumer lambda reference to execute code
     */
    public void onClose(Consumer<InventoryCloseEvent> consumer) {
        this.closeEvent = consumer;
    }

    /**
     * Will fill the menu with the default filler item
     * @see MenuSettings#getDefaultFillerItem()
     */
    public void fillMenu() {
        this.fillMenu(MenuManager.getInstance().getSettings().getDefaultFillerItem());
    }

    /**
     * Will fill the menu with specified material
     * @param material The specified material
     */
    public void fillMenu(Material material) {
        this.fillMenu(new ItemStack(material));
    }

    /**
     * Will set the contents of the inventory
     * @param items the array of items you are setting
     */
    public void setContents(ItemStack[] items) {
        this.inventory.setContents(items);
    }

    /**
     * Will fill the menu with specified item
     * @see Menu#fillMenu(Material) to fill it with just a custom material
     * @param item the item you are filling
     */
    public void fillMenu(ItemStack item) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null || inventory.getItem(i).getType().equals(Material.AIR)) {
                inventory.setItem(i, item);
            }
        }
    }

    /**
     * Will prevent a player from closing the inventory unless {@link Menu#close(Player)} is called
     */
    public void preventClose() {
        this.preventClose(true);
    }

    /**
     * Allows you to set weather
     * @param state the state you want to set it too
     */
    public void preventClose(boolean state) {
        this.closable = state;
    }

    /**
     * Will close the player's menu if it is open
     * **This ignores weather {@link Menu#preventClose()} is true or not
     * @param player the player you are trying to close
     */
    public void close(Player player) {
        if(player.getInventory().equals(this.getInventory())) {
            this.closing = true;
            player.closeInventory();
        }
    }

    /**
     * Add a listner to the InventoryClickEvent
     * @see InventoryClickEvent
     * @param event the code that runs when the event is triggered
     */
    public void addListener(Consumer<InventoryClickEvent> event) {
        this.listeners.add(event);
    }

    /**
     * Set item to specified index
     * @param index the index you want to set the item too
     * @param item the item you are setting
     */
    public void setItem(int index, ItemStack item) {
        this.inventory.setItem(index, item);
    }

    /**
     * Add item to the next free sport in the inventory
     * @param item the item you are adding
     */
    public void addItem(ItemStack item) {
        this.inventory.addItem(item);
    }

    /**
     * Set a button to a specifix index
     * @see Button
     * @param button the button you are setting
     * @param index the index the item is going in at
     */
    public void setButton(Button button, int index) {
        this.inventory.setItem(index, button.getItem());
    }

    /**
     * @return the raw bukkit inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * @return the rows specified in the constructor
     */
    public int getRows() {
        return rows;
    }

    /**
     * @return the title of the menu
     */
    public Component getName() {
        return name;
    }

    /**
     * Set clicks canceled or not
     * @param canceled weather or not to cancel clicks
     */
    public void setClicksCanceled(boolean canceled) {
        this.cancelClicks = canceled;
    }

    /**
     * @return weather the menu can natrually be closed
     * @see Menu#preventClose()
     */
    public boolean isClosable() {
        return closable;
    }

    /**
     * @return weather clicks are cancled or not
     * @see Menu#setClicksCanceled(boolean)
     */
    public boolean cancelsClicks() {
        return this.cancelClicks;
    }

    protected boolean isClosing() {
        return closing;
    }

    protected void setClosing(boolean state) {
        this.closing = state;
    }
}
