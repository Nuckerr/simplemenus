package wtf.nucker.simplemenus.spigot;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import wtf.nucker.simplemenus.spigot.utils.ChatUtils;
import wtf.nucker.simplemenus.spigot.utils.ItemBuilder;

/**
 * @author Nucker
 * @project SimpleMenus
 * @date 26/09/2021
 */
public class MenuSettings {

    private String noLastPageMessage;
    private String noNextPageMessage;

    private ItemStack defaultFillerItem;
    private ItemStack previousPageItem;
    private ItemStack nextPageItem;
    private ItemStack closePageItem;

    /**
     * Creates the default settings
     */
    public MenuSettings() {
        this.noLastPageMessage = ChatUtils.translate("&cThere is previous page");
        this.noNextPageMessage = ChatUtils.translate("&cThere is no next page");

        try {
            defaultFillerItem = new ItemBuilder().setType(Material.BLACK_STAINED_GLASS).setName("").build();
        }catch (NullPointerException e) {
            defaultFillerItem = new ItemBuilder().setType(Material.valueOf("STAINED_GLASS_PANE")).setData(DyeColor.BLACK.getDyeData()).setName("").build();
        }

        this.previousPageItem = new ItemBuilder().setType(Material.PAPER).setName("&aPrevious Page").setLore("&7Click to go to the previous page").build();
        this.nextPageItem = new ItemBuilder().setType(Material.PAPER).setName("&aNext Page").setLore("&7Click to go to the next page").build();
        this.closePageItem = new ItemBuilder().setType(Material.BARRIER).setName("&cClose inventory").setLore("&7Click to clos the inventory").build();
    }


    public String getNoLastPageMessage() {
        return noLastPageMessage;
    }

    public String getNoNextPageMessage() {
        return noNextPageMessage;
    }

    public void setNoLastPageMessage(String noLastPageMessage) {
        this.noLastPageMessage = noLastPageMessage;
    }

    public void setNoNextPageMessage(String noNextPageMessage) {
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
