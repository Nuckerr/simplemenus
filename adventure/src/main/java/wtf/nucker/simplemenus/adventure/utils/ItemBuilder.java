package wtf.nucker.simplemenus.adventure.utils;


import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Nucker
 * @project SkyCordHubCore
 * @date 22/09/2021
 */
public class ItemBuilder {

    private int amount = 1;
    private Material material = Material.GRASS;
    private Component name;
    private List<Component> lore;
    short data = -1;

    /**
     * Will update the material of the ite,
     * @param mat the material of your choice
     */
    public ItemBuilder setType(Material mat) {
        this.material = mat;
        return this;
    }

    /**
     * Wll set the display name of the item (it is translated)
     * @param name the name
     */
    public ItemBuilder setName(String name) {
        this.name = Component.text(name);
        return this;
    }

    public ItemBuilder setName(Component component) {
        this.name = component;
        return this;
    }

    public ItemBuilder setLore(List<Component> lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder setLore(Component... lore) {
        this.lore = Arrays.asList(lore);
        return this;
    }

    /**
     * Wll set the lore (it is translated)
     * @see ItemBuilder#setLore(List) to set a list of strings
     * @param lore the lore you are setting
     */
    public ItemBuilder setLore(String... lore) {
        List<Component> res = new ArrayList<>();
        Arrays.asList(lore).forEach(line -> {
            res.add(Component.text(line));
        });
        this.lore = res;
        return this;
    }

    /**
     * The amount of the item
     */
    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    /**
     * Set durability
     */
    public ItemBuilder setData(short data) {
        this.data = data;
        return this;
    }

    /**
     * Will build and return your bukkit item
     * @return the bukkit item
     */
    public ItemStack build() {
        ItemStack res = new ItemStack(material, amount);
        ItemMeta meta = res.getItemMeta();
        if(data != -1) res.setDurability(data);
        if(this.name != null) meta.displayName(this.name);
        if(this.lore != null) meta.lore(this.lore);
        res.setItemMeta(meta);
        return res;
    }

}
