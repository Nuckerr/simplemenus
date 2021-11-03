package wtf.nucker.simplemenus.spigot;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * @author Nucker
 * @project KitPvpCore
 * @date 01/07/2021
 */
public abstract class PaginatedMenu extends Menu {

    private final int spaces;
    private List<ItemStack> items;
    private int totalPages;
    private int page = 0;

    /**
     * Create a paginated menu
     * @param rows the amount of rows in the menu (9 * rows)
     * @param title the title of the menu
     * @param items list of items that should be paginated
     * @param spaces the amount of empty space in the menu
     */
    public PaginatedMenu(int rows, String title, List<ItemStack> items, int spaces) {
        super(rows, title);
        this.spaces = spaces;
        this.items = items;
        this.totalPages = (int) Math.ceil((double) items.size() / spaces);
    }

    /**
     * Will open the menu to a specific page for the player
     * @param player the player who is having the menu opened
     * @param page the page you are opening it too
     */
    public void openPage(Player player, int page) {
        if(page <= 0) {
            player.sendMessage(MenuManager.getInstance().getSettings().getNoLastPageMessage());
            return;
        }
        if(page > this.totalPages) {
            player.sendMessage(MenuManager.getInstance().getSettings().getNoNextPageMessage());
            return;
        }
        this.page = page;
        for (int i = 0; i < this.getInventory().getSize(); i++) {
            this.getInventory().setItem(i, null);
        }
        int upperIndex = page * this.spaces;
        int lowerIndex = upperIndex - spaces;
        this.addFiller(new Button(MenuManager.getInstance().getSettings().getPreviousPageItem()) {
            @Override
            public void onClick(InventoryClickEvent event) {
                PaginatedMenu.this.openPage(player, PaginatedMenu.this.page - 1);
            }
        }, new Button(MenuManager.getInstance().getSettings().getNextPageItem()) {
            @Override
            public void onClick(InventoryClickEvent event) {
                PaginatedMenu.this.openPage(player, PaginatedMenu.this.page +1);
            }
        }, new Button(MenuManager.getInstance().getSettings().getClosePageItem()) {
            @Override
            public void onClick(InventoryClickEvent event) {
                event.getWhoClicked().closeInventory();
            }
        }, MenuManager.getInstance().getSettings().getDefaultFillerItem());

        if(items.size() > spaces) {
            for (int i = lowerIndex; i < upperIndex; i++) {
                if(i > (items.size()-1)) return;
                this.setButton(new Button(items.get(i)) {
                    @Override
                    public void onClick(InventoryClickEvent event) {
                        PaginatedMenu.this.onClick(event);
                    }
                }, this.getInventory().firstEmpty());
            }
        }else {
            for (ItemStack item : items) {
                this.setButton(new Button(item) {
                    @Override
                    public void onClick(InventoryClickEvent event) {
                        PaginatedMenu.this.onClick(event);
                    }
                }, this.getInventory().firstEmpty());
            }
        }

        player.openInventory(this.getInventory());
        MenuManager.getInstance().getOpenMenus().put(player, this);
    }

    /**
     * An alternative method to updating the list of paginated items in the menu (as initially set in the constructor)
     * @param items the list of items you want to paginate
     */
    public void setItems(List<ItemStack> items) {
        this.items = items;
        this.totalPages = (int) Math.ceil((double) this.items.size() / this.spaces);
    }

    /**
     * Will open the paginated menu to the first page
     * @param p player the player you are opening the menu for
     * @see PaginatedMenu#openPage(Player, int) to open the menu to a specific page
     */
    @Override
    public void open(Player p) {
        this.openPage(p, 1);
    }

    @Override
    public void switchMenu(Player p) {
        MenuManager.getInstance().getOpenMenus().remove(p);
        this.open(p);
    }

    /**
     * The method called when a player clicks one of the paginated items
     * @see PaginatedMenu#getItems()
     * @param e the instance of the event
     * @see InventoryClickEvent
     */
    public abstract void onClick(InventoryClickEvent e);

    /**
     * Allows you to build your menu to how you want This is called after the paginated items have been added
     * @see PaginatedMenu#getSpaces() this is where you set the empty space without interfering with the pagianted items
     * @param previousPageButton the button for the previous page
     * @param nextPageButton the button for the next page
     * @param closeInvButton the button to close the inventory
     * @param filler the default filler item
     */
    public abstract void addFiller(Button previousPageButton, Button nextPageButton, Button closeInvButton, ItemStack filler);


    /**
     * @return the list of paginated items
     */
    public List<ItemStack> getItems() {
        return items;
    }

    /**
     * @return the amount of empty space in the menu (or to be taken up for the filler)
     * @see PaginatedMenu#addFiller(Button, Button, Button, ItemStack)
     */
    public int getSpaces() {
        return spaces;
    }
}
