package com.easier_minecraft;


import com.easier_minecraft.register.PredicateRegister;

import net.fabricmc.api.ClientModInitializer;

public class EasierMinecraftClient implements ClientModInitializer {
	

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		PredicateRegister.onInitializeClient();
	}
}