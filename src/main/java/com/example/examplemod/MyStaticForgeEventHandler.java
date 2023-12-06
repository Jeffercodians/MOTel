package com.example.examplemod;

import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraftforge.event.entity.living.LivingBreatheEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.slf4j.Logger;

public class MyStaticForgeEventHandler {

    private static final Logger LOGGER = LogUtils.getLogger();
    @SubscribeEvent
    public static void arrowNocked(LivingEvent event) {
        if (event.getEntity() instanceof Bee) {
            if (((Bee) event.getEntity()).isWithinRestriction()) {
                LOGGER.info("\uD83D\uDC1D House Party! " + event.toString());
            } else {
                LOGGER.info("\uD83D\uDC1D Event " + event.toString());
            }
        } else {
            return;
        }

    }
}