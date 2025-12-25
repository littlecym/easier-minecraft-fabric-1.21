package com.easier_minecraft;

import com.easier_minecraft.register.EnchantmentRegister;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

public class ClientEvents {

    public static void onInitializeClient() {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (client.world != null)
                EnchantmentRegister.initEntry(client.world.getRegistryManager());
        });
    }

}
