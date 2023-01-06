package me.m0dii.pllib.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.Collections;

public class ItemStackBuilder extends ItemStack {
    public ItemStackBuilder(ItemStack item) {
        super(item);
    }

    public ItemStackBuilder(Material item, int amount) {
        super(item, amount);
    }

    public ItemStackBuilder(Material item) {
        super(item);
    }

    public ItemStackBuilder amount(int amount) {
        setAmount(amount);
        return this;
    }


    public ItemStackBuilder name(String name) {
        ItemMeta meta = this.hasItemMeta() ? this.getItemMeta() : Bukkit.getItemFactory().getItemMeta(this.getType());
        assert meta != null;
        meta.setDisplayName(name);
        setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder lore(String... lore) {
        ItemMeta meta = this.hasItemMeta() ? this.getItemMeta() : Bukkit.getItemFactory().getItemMeta(this.getType());
        assert meta != null;
        meta.setLore(Arrays.asList(lore));
        setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder addLore(String... lore) {
        ItemMeta meta = this.hasItemMeta() ? this.getItemMeta() : Bukkit.getItemFactory().getItemMeta(this.getType());
        if (meta.hasLore() && meta.getLore() != null) {
            meta.getLore().addAll(Arrays.asList(lore));
        } else {
            meta.setLore(Arrays.asList(lore));
        }
        setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder lore(int index, String lore) {
        ItemMeta meta = this.hasItemMeta() ? this.getItemMeta() : Bukkit.getItemFactory().getItemMeta(this.getType());
        if (meta.hasLore() && meta.getLore() != null && meta.getLore().size() > index) {
            meta.getLore().set(index, lore);
        } else {
            meta.setLore(Collections.singletonList(lore));
        }
        setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder addAttribute(Attribute attribute, AttributeModifier modifier) {
        ItemMeta meta = this.hasItemMeta() ? this.getItemMeta() : Bukkit.getItemFactory().getItemMeta(this.getType());
        meta.addAttributeModifier(attribute, modifier);
        setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder modelData(int modelData) {
        ItemMeta meta = this.hasItemMeta() ? this.getItemMeta() : Bukkit.getItemFactory().getItemMeta(this.getType());
        meta.setCustomModelData(modelData);
        setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder enchant(Enchantment enchantment, int level) {
        addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemStackBuilder enchant(Enchantment enchantment) {
        addUnsafeEnchantment(enchantment, 1);
        return this;
    }

    public ItemStackBuilder removeEnchant(Enchantment enchantment) {
        removeEnchantment(enchantment);
        return this;
    }

    public ItemStackBuilder clearEnchants() {
        getEnchantments().forEach((enchantment, integer) -> removeEnchantment(enchantment));
        return this;
    }

    public <T, Z> ItemStackBuilder addTag(NamespacedKey key, PersistentDataType<T, Z> type, Z data) {
        ItemMeta meta = this.hasItemMeta() ? this.getItemMeta() : Bukkit.getItemFactory().getItemMeta(this.getType());
        meta.getPersistentDataContainer().set(key, type, data);
        setItemMeta(meta);
        return this;
    }
}