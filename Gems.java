package com.gembooks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

/** Generated from gem-enchantments-table.html */
public final class Gems {
    public record Gem(int index, String enchantment, String name, String desc, int color) {
        public int displayColor() {
            int r = (color >> 16) & 255, g = (color >> 8) & 255, b = color & 255;
            if (0.299 * r + 0.587 * g + 0.114 * b < 110) {
                r += (255 - r) * 55 / 100; g += (255 - g) * 55 / 100; b += (255 - b) * 55 / 100;
            }
            return (r << 16) | (g << 8) | b;
        }
    }

    public static final List<Gem> ALL = List.of(
        new Gem(0, "protection", "Diamond", "Reduces most types of damage.", 0xBFE8F5),
        new Gem(1, "fire_protection", "Ruby", "Reduces fire damage and burn time.", 0xD4142F),
        new Gem(2, "blast_protection", "Carnelian", "Reduces explosion damage and knockback.", 0xC2491F),
        new Gem(3, "projectile_protection", "Hematite", "Reduces damage from arrows and other projectiles.", 0x6A6E78),
        new Gem(4, "thorns", "Tourmaline", "Damages attackers when they hit you.", 0x1F8A5B),
        new Gem(5, "unbreaking", "Sapphire", "Gives items a chance not to lose durability.", 0x1B45B8),
        new Gem(6, "mending", "Emerald", "Repairs the item using XP orbs.", 0x1FAE5E),
        new Gem(7, "binding_curse", "Amber", "Armor cannot be removed once worn.", 0xE0901A),
        new Gem(8, "vanishing_curse", "Moonstone", "Item disappears when you die.", 0xC9D3EA),
        new Gem(9, "respiration", "Aquamarine", "Extends breathing time underwater.", 0x6FD3D6),
        new Gem(10, "aqua_affinity", "Blue Chalcedony", "Removes the underwater mining speed penalty.", 0x8FB8E0),
        new Gem(11, "swift_sneak", "Smoky Quartz", "Lets you move faster while sneaking.", 0x6B5A4C),
        new Gem(12, "feather_falling", "Opal", "Reduces fall damage.", 0xD8E6EF),
        new Gem(13, "depth_strider", "Turquoise", "Increases movement speed underwater.", 0x2BC0B4),
        new Gem(14, "frost_walker", "Selenite", "Freezes water under your feet into ice.", 0xEEF4F7),
        new Gem(15, "soul_speed", "Amethyst", "Increases speed on soul sand and soul soil.", 0x8E4FD0),
        new Gem(16, "sharpness", "Moissanite", "Increases melee damage to all mobs.", 0xE4F0FF),
        new Gem(17, "smite", "Peridot", "Increases damage to undead mobs.", 0x9BC92B),
        new Gem(18, "bane_of_arthropods", "Tiger's Eye", "Increases damage to spiders and silverfish, and slows them.", 0xB0731F),
        new Gem(19, "knockback", "Lapis Lazuli", "Pushes enemies further back when hit.", 0x26429B),
        new Gem(20, "fire_aspect", "Fire Opal", "Sets the target on fire.", 0xFF6A1A),
        new Gem(21, "looting", "Golden Beryl", "Increases mob loot drops.", 0xE8C33A),
        new Gem(22, "sweeping_edge", "Kyanite", "Increases sweep attack damage (swords).", 0x3B78D6),
        new Gem(23, "efficiency", "Topaz", "Increases mining speed.", 0xF0A53A),
        new Gem(24, "silk_touch", "Rose Quartz", "Mines blocks in their original form (glass, ice, ores).", 0xF3B6C8),
        new Gem(25, "fortune", "Citrine", "Increases drops from ores and some blocks.", 0xF2B81C),
        new Gem(26, "power", "Red Spinel", "Increases arrow damage.", 0xC01A3C),
        new Gem(27, "punch", "Garnet", "Increases arrow knockback.", 0x8C1026),
        new Gem(28, "flame", "Sunstone", "Arrows set targets on fire.", 0xF08A2C),
        new Gem(29, "infinity", "Alexandrite", "Shoot without consuming arrows.", 0x2E8F86),
        new Gem(30, "multishot", "Labradorite", "Fires three arrows at once.", 0x3F7FA0),
        new Gem(31, "piercing", "Tanzanite", "Arrows pass through multiple entities.", 0x4A4FC4),
        new Gem(32, "quick_charge", "Zircon", "Reloads the crossbow faster.", 0x5EC0E8),
        new Gem(33, "loyalty", "Pearl", "Trident returns to you after being thrown.", 0xF1EBE3),
        new Gem(34, "impaling", "Onyx", "Extra damage to aquatic mobs.", 0x25232B),
        new Gem(35, "riptide", "Larimar", "Launches you forward in water or rain.", 0x4FB6D9),
        new Gem(36, "channeling", "Charoite", "Summons lightning on hit during a thunderstorm.", 0x7B3FA8),
        new Gem(37, "density", "Jasper", "Increases smash attack damage per block fallen.", 0xA8472D),
        new Gem(38, "breach", "Bloodstone", "Reduces the effectiveness of the target's armor.", 0x2F5A40),
        new Gem(39, "wind_burst", "Celestite", "Launches you upward after a smash attack.", 0xA9CDF0),
        new Gem(40, "luck_of_the_sea", "Jade", "Increases chance of treasure catches.", 0x4FAE7A),
        new Gem(41, "lure", "Rhodochrosite", "Makes fish bite faster.", 0xE0728F)
    );

    private static final Map<String, Gem> BY_ENCHANTMENT = new HashMap<>();
    static {
        for (Gem g : ALL) BY_ENCHANTMENT.put(g.enchantment(), g);
        BY_ENCHANTMENT.put("sweeping", BY_ENCHANTMENT.get("sweeping_edge"));
    }

    /** The gem of the first enchantment stored in an enchanted book, or null. */
    public static Gem of(ItemStack stack) {
        ItemEnchantments stored = stack.get(DataComponents.STORED_ENCHANTMENTS);
        if (stored == null || stored.isEmpty()) return null;
        for (Holder<Enchantment> holder : stored.keySet()) {
            Optional<ResourceKey<Enchantment>> key = holder.unwrapKey();
            if (key.isPresent()) {
                Gem g = BY_ENCHANTMENT.get(key.get().location().getPath());
                if (g != null) return g;
            }
        }
        return null;
    }

    private Gems() {}
}
