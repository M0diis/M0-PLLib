package me.m0dii.pllib.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Random;

public class InventoryUtils {
    private static final Random random = new Random();

    /**
     * Converts the player inventory to a Base64 encoded string.
     *
     * @param playerInventory to turn into an array of strings.
     * @return string with serialized Inventory
     */
    public static String playerInventoryToBase64(PlayerInventory playerInventory) throws IllegalStateException {
        // This contains contents, armor and offhand (contents are indexes 0 - 35, armor 36 - 39, offhand - 40)
        return itemStackArrayToBase64(playerInventory.getContents());
    }

    /**
     *
     * A method to serialize an {@link ItemStack} array to Base64 String.
     *
     * @param items to turn into a Base64 String.
     * @return Base64 string of the items.
     */
    public static String itemStackArrayToBase64(ItemStack[] items) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeInt(items.length);

            for (ItemStack item : items) {
                if (item != null) {
                    dataOutput.writeObject(item.serializeAsBytes());
                } else {
                    dataOutput.writeObject(null);
                }
            }

            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Gets an array of ItemStacks from Base64 string.
     *
     * @param data Base64 string to convert to ItemStack array.
     * @return ItemStack array created from the Base64 string.
     */
    public static ItemStack[] itemStackArrayFromBase64(String data) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];

            for (int Index = 0; Index < items.length; Index++) {
                byte[] stack = (byte[]) dataInput.readObject();

                if (stack != null) {
                    items[Index] = ItemStack.deserializeBytes(stack);
                } else {
                    items[Index] = null;
                }
            }

            dataInput.close();

            return items;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new ItemStack[0];
    }

    public static boolean hasEnchant(ItemStack item, Enchantment enchant) {
        ItemMeta itemMeta = item.getItemMeta();

        return itemMeta != null && itemMeta.getEnchants().containsKey(enchant);
    }

    public static int getEnchantLevel(ItemStack item, Enchantment enchant) {
        if(item == null) {
            return 0;
        }

        return item.getEnchantmentLevel(enchant);
    }

    public static void applyDurability(ItemStack item) {
        if(!(item.getItemMeta() instanceof Damageable damageable)) {
            return;
        }

        int unbreakingLevel = getEnchantLevel(item, Enchantment.DURABILITY);

        int chance = (100) / (1 + unbreakingLevel);

        int res = random.nextInt(100 - 1) + 1;

        if (res < chance) {
            damageable.setDamage(damageable.getDamage() + 1);
        }

        item.setItemMeta(damageable);

        if (damageable.getDamage() >= item.getType().getMaxDurability()) {
            item.setType(Material.AIR);
        }
    }

    public static boolean isEmpty(Inventory inv) {
        return Arrays.stream(inv.getContents()).noneMatch(i -> i != null && i.getType() != Material.AIR);
    }

    public static boolean hasItem(Inventory inv, ItemStack item) {
        return Arrays.stream(inv.getContents()).anyMatch(i -> i != null && i.isSimilar(item));
    }

    public static ItemStack createItem(Material m, String name) {
        final ItemStack item = new ItemStack(m);

        final ItemMeta itemMeta = item.getItemMeta();

        if (itemMeta != null) {
            itemMeta.displayName(TextUtils.colorize(name));
        }

        item.setItemMeta(itemMeta);

        return item;
    }
}