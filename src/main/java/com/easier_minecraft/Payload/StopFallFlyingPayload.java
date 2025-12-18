package com.easier_minecraft.Payload;

import com.easier_minecraft.EasierMinecraft;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record StopFallFlyingPayload() implements CustomPayload {
    public static final Id<StopFallFlyingPayload> ID = new CustomPayload.Id<>(Identifier.of(EasierMinecraft.MOD_ID, "stop_fall_flying"));
    public static final PacketCodec<PacketByteBuf, StopFallFlyingPayload> CODEC = PacketCodec.unit(new StopFallFlyingPayload());

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
