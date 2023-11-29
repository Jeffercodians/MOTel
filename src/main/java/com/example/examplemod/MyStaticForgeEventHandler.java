package com.example.examplemod;

import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.slf4j.Logger;

public class MyStaticForgeEventHandler {

    private static final Logger LOGGER = LogUtils.getLogger();
    @SubscribeEvent
    public static void arrowNocked(LivingEvent event) {
        LOGGER.info("Event " + event.toString());
    }
}