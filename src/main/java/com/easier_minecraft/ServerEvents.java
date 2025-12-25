package com.easier_minecraft;

import com.easier_minecraft.register.EnchantmentRegister;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.world.ServerWorld;

public class ServerEvents {

    public static void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            for (ServerWorld world : server.getWorlds()) {
                EnchantmentRegister.initEntry(world.getRegistryManager());
            }
        });
    }

}
