package com.gembooks;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

public class ClientEvents {
    @EventBusSubscriber(modid = GemBooks.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ModBus {
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            // item property "gembooks:gem" = (gem index + 1) / 64, read by the resource pack's enchanted_book model
            event.enqueueWork(() -> ItemProperties.register(
                    Items.ENCHANTED_BOOK,
                    ResourceLocation.fromNamespaceAndPath(GemBooks.MODID, "gem"),
                    (stack, level, entity, seed) -> {
                        Gems.Gem gem = Gems.of(stack);
                        return gem == null ? 0.0F : (gem.index() + 1) / 64.0F;
                    }));
        }
    }

    @EventBusSubscriber(modid = GemBooks.MODID, value = Dist.CLIENT)
    public static class GameBus {
        @SubscribeEvent
        public static void onTooltip(ItemTooltipEvent event) {
            ItemStack stack = event.getItemStack();
            if (!stack.is(Items.ENCHANTED_BOOK)) return;
            Gems.Gem gem = Gems.of(stack);
            if (gem == null) return;
            List<Component> tip = event.getToolTip();
            if (tip.isEmpty()) return;
            // gem name on top, what it does right below
            tip.set(0, Component.literal(gem.name()).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(gem.displayColor()))));
            tip.add(1, Component.literal(gem.desc()).withStyle(ChatFormatting.GRAY));
        }
    }
}
