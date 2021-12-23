package wtf.nucker.simplemenus.adventure;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import wtf.nucker.simplemenus.adventure.utils.ItemBuilder;

/**
 * @author Nucker
 * @project SimpleMenus
 * @date 26/09/2021
 */
public class MenuSettings {

    private Component noLastPageMessage, noNextPageMessage;

    private ItemStack defaultFillerItem;
    private ItemStack previousPageItem;
    private ItemStack nextPageItem;
    private ItemStack closePageItem;

    /**
     * Creates the default settings
     */
    public MenuSettings() {
        this.noLastPageMessage = Component.text("There is previous page").color(NamedTextColor.RED);
        this.noNextPageMessage = Component.text("There is no next page").color(NamedTextColor.RED);

        try {
            defaultFillerItem = new ItemBuilder().setType(Material.BLACK_STAINED_GLASS).setName("").build();
        }catch (NullPointerException e) {
            defaultFillerItem = new ItemBuilder().setType(Material.valueOf("STAINED_GLASS_PANE")).setData(DyeColor.BLACK.getDyeData()).setName("").build();
        }

        this.previousPageItem = new ItemBuilder().setType(Material.PAPER).setName("&aPrevious Page").setLore("&7Click to go to the previous page").build();
        this.nextPageItem = new ItemBuilder().setType(Material.PAPER).setName("&aNext Page").setLore("&7Click to go to the next page").build();
        this.closePageItem = new ItemBuilder().setType(Material.BARRIER).setName("&cClose inventory").setLore("&7Click to clos the inventory").build();
    }


    public Component getNoLastPageMessage() {
        return noLastPageMessage;
    }

    public Component getNoNextPageMessage() {
        return noNextPageMessage;
    }

    public void setNoLastPageMessage(Component noLastPageMessage) {
        this.noLastPageMessage = noLastPageMessage;
    }

    public void setNoNextPageMessage(Component noNextPageMessage) {
        this.noNextPageMessage = noNextPageMessage;
    }

    public ItemStack getDefaultFillerItem() {
        return defaultFillerItem;
    }

    public void setDefaultFillerItem(ItemStack defaultFillerItem) {
        this.defaultFillerItem = defaultFillerItem;
    }

    public ItemStack getNextPageItem() {
        return nextPageItem;
    }

    public ItemStack getPreviousPageItem() {
        return previousPageItem;
    }

    public void setNextPageItem(ItemStack nextPageItem) {
        this.nextPageItem = nextPageItem;
    }

    public void setPreviousPageItem(ItemStack previousPageItem) {
        this.previousPageItem = previousPageItem;
    }

    public ItemStack getClosePageItem() {
        return closePageItem;
    }

    public void setClosePageItem(ItemStack closePageItem) {
        this.closePageItem = closePageItem;
    }
}
