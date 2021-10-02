package wtf.nucker.simplemenus.adventure;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * @author Nucker
 * @project KitPvpCore
 * @date 01/07/2021
 */
public abstract class Button {

    private final ItemStack item;
    private final UUID id;

    /**
     * Create a button
     * @param item the item the button should be
     */
    public Button(ItemStack item) {
        this.id = UUID.randomUUID();
        NBTItem nbt = new NBTItem(item);
        nbt.setString("button", id.toString());
        item = nbt.getItem();
        this.item = item;

        MenuManager.getInstance().registerButton(this);
    }

    /**
     * The method called when the button is clicked
     * @param event Gives you access to the raw click event
     */
    public abstract void onClick(InventoryClickEvent event);

    /**
     * @see Button#Button(ItemStack)
     * @return the item set in the constructor
     */
    public ItemStack getItem() {
        return item;
    }

    /**
     * @return the uuid set to identify the button
     */
    public UUID getId() {
        return id;
    }

    /**
     * Utility method to check if an itemstack is a button
     * @param item the item you are checking
     * @return true if it's a button
     */
    public static boolean isButton(ItemStack item) {
        if (item == null || item.getType().equals(Material.AIR)) return false;
        return new NBTItem(item).hasKey("button");
    }


    /**
     * Utility to find buttons via a method
     * @param id the id of the button
     * @return the button with that id (Or null if it doesn't exist)
     */
    public static Button getButtonById(UUID id) {
        return MenuManager.getInstance().getButtons().get(id);
    }
}
