package com.example.chickenring;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockEntities {
    public static BlockEntityType<FowlForgeBlockEntity> FOWL_FORGE;

    public static void registerAll() {
        FOWL_FORGE = Registry.register(
    Registry.BLOCK_ENTITY_TYPE,
    new Identifier(ChickenRingMod.MOD_ID, "fowl_forge"),
    FabricBlockEntityTypeBuilder
      .<FowlForgeBlockEntity>create(           // <-- explicit generic here
          FowlForgeBlockEntity::new,
          ChickenRingMod.FOWL_FORGE_BLOCK
      )
      .build(null)
);

    }
}
