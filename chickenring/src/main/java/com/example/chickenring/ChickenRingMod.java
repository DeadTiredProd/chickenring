package com.example.chickenring;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ChickenRingMod implements ModInitializer {
    public static final String MOD_ID = "hellfiremadeit";

    public static final Item CHICKEN_RING = new Item(new Item.Settings()
            .maxCount(1)
            .fireproof()
            .group(ItemGroup.MISC));

    public static final Item ROTTING_CHICKEN = new Item(new Item.Settings()
            .group(ItemGroup.FOOD)
            .food(ModFoodComponents.ROTTING_CHICKEN_FOOD));
    
    public static final Item FROZEN_ROTTING_CHICKEN = new Item(new Item.Settings()
            .group(ItemGroup.FOOD)
                    .food(ModFoodComponents.FROZEN_ROTTING_CHICKEN_FOOD));

            
    public static final Block RAW_CHICKEN_BLOCK = new RawChickenBlock(
            Block.Settings.copy(net.minecraft.block.Blocks.WHITE_WOOL));

    public static final Block COOKED_CHICKEN_BLOCK = new Block(
            Block.Settings.copy(net.minecraft.block.Blocks.ORANGE_WOOL));

    public static final Block ROTTING_CHICKEN_BLOCK = new Block(
            Block.Settings.copy(net.minecraft.block.Blocks.BROWN_WOOL));

    public static final Block FROZEN_RAW_CHICKEN_BLOCK = new FrozenRawChickenBlock(
            Block.Settings.of(Material.ICE)
                    .strength(0.5f)
                                    .ticksRandomly());

    public static final Block FROZEN_COOKED_CHICKEN_BLOCK = new FrozenCookedChickenBlock(
            Block.Settings.of(Material.ICE)
                    .strength(0.5f)
                                    .ticksRandomly());
                    
    public static final Block FROZEN_ROTTING_CHICKEN_BLOCK = new FrozenRottingChickenBlock(
            Block.Settings.of(Material.ICE)
                    .strength(0.5f)
                    .ticksRandomly());

    @Override
    public void onInitialize() {
        // Register items
        Registry.register(Registry.ITEM,
                new Identifier(MOD_ID, "chicken_ring"),
                CHICKEN_RING);
        Registry.register(Registry.ITEM,
                new Identifier(MOD_ID, "rotting_chicken"),
                ROTTING_CHICKEN);

        Registry.register(Registry.ITEM,
                new Identifier(MOD_ID, "frozen_rotting_chicken"),
                FROZEN_ROTTING_CHICKEN);

        // Register raw chicken block + item
        Registry.register(Registry.BLOCK,
                new Identifier(MOD_ID, "raw_chicken_block"),
                RAW_CHICKEN_BLOCK);
        Registry.register(Registry.ITEM,
                new Identifier(MOD_ID, "raw_chicken_block"),
                new BlockItem(RAW_CHICKEN_BLOCK,
                        new Item.Settings().group(ItemGroup.FOOD)));

        // Register cooked chicken block + item
        Registry.register(Registry.BLOCK,
                new Identifier(MOD_ID, "cooked_chicken_block"),
                COOKED_CHICKEN_BLOCK);
        Registry.register(Registry.ITEM,
                new Identifier(MOD_ID, "cooked_chicken_block"),
                new BlockItem(COOKED_CHICKEN_BLOCK,
                        new Item.Settings().group(ItemGroup.FOOD)));

        // Register rotting chicken block + item
        Registry.register(Registry.BLOCK,
                new Identifier(MOD_ID, "rotting_chicken_block"),
                ROTTING_CHICKEN_BLOCK);
        Registry.register(Registry.ITEM,
                new Identifier(MOD_ID, "rotting_chicken_block"),
                new BlockItem(ROTTING_CHICKEN_BLOCK,
                        new Item.Settings().group(ItemGroup.FOOD)));

        // Register frozen raw chicken block + item
        Registry.register(Registry.BLOCK,
                new Identifier(MOD_ID, "frozen_raw_chicken_block"),
                FROZEN_RAW_CHICKEN_BLOCK);
        Registry.register(Registry.ITEM,
                new Identifier(MOD_ID, "frozen_raw_chicken_block"),
                new BlockItem(FROZEN_RAW_CHICKEN_BLOCK,
                        new Item.Settings().group(ItemGroup.FOOD)));

        // Register frozen raw chicken block + item
        Registry.register(Registry.BLOCK,
                new Identifier(MOD_ID, "frozen_cooked_chicken_block"),
                FROZEN_COOKED_CHICKEN_BLOCK);
        Registry.register(Registry.ITEM,
                new Identifier(MOD_ID, "frozen_cooked_chicken_block"),
                new BlockItem(FROZEN_COOKED_CHICKEN_BLOCK,
                                        new Item.Settings().group(ItemGroup.FOOD)));
        
                        
         // Register frozen rotting chicken block + item
        Registry.register(Registry.BLOCK,
                new Identifier(MOD_ID, "frozen_rotting_chicken_block"),
                FROZEN_ROTTING_CHICKEN_BLOCK);
        Registry.register(Registry.ITEM,
                new Identifier(MOD_ID, "frozen_rotting_chicken_block"),
                new BlockItem(FROZEN_ROTTING_CHICKEN_BLOCK,
                        new Item.Settings().group(ItemGroup.FOOD)));



        // Start the item-decay callback (rosting on ground)
        ItemDecayCallback.register();
    }
}
