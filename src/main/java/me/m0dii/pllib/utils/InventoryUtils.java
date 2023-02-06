package me.m0dii.pllib.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class InventoryUtils {
    private static final Random random = new Random();

    private static final List<Material> CONTAINERS = List.of(
            Material.CHEST,
            Material.TRAPPED_CHEST,
            Material.DISPENSER,
            Material.DROPPER,
            Material.SHULKER_BOX
    );

    public static boolean isContainer(ItemStack item) {
        return CONTAINERS.contains(item.getType()) || item.getType().name().endsWith("_SHULKER_BOX");
    }

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

    public static void applyCustomHeadTexture(ItemStack item, String texture) {
        UUID hashAsId = new UUID(texture.hashCode(), texture.hashCode());
        Bukkit.getUnsafe().modifyItemStack(item, "{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + texture + "\"}]}}}");
    }

    public static ItemStack setTexture(ItemStack item, String texture) {
        ItemMeta meta = item.getItemMeta();

        if (meta instanceof SkullMeta) {
            if (Utils.isValidPlayerName(texture)) {
                ((SkullMeta) meta).setOwner(texture);
                return item;
            }

            applyCustomHeadTexture(item, texture);
        }

        item.setItemMeta(meta);

        return item;
    }
}