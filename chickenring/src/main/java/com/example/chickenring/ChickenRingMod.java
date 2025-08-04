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
    public static final Item CHICKEN_EGG_CARTON = new Item(new Item.Settings()
                .maxCount(16)
                    .group(ItemGroup.FOOD));
                
    public static final Block CHICKEN_EGG_CRATE = new ChickenEggCrateBlock(
            Block.Settings.copy(net.minecraft.block.Blocks.SAND));

    public static final Item BURNT_CHICKEN = new Item(new Item.Settings().group(ItemGroup.MISC));
    
    public static final Item CHICKEN_DUST = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item CALCIUM_DUST = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item CARBON_DUST = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item SMALL_PILE_IRON_DUST = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item DARK_CHICKEN_ESSENCE_CELL = new Item(new Item.Settings().group(ItemGroup.MISC).maxCount(16).fireproof());

    public static final Item ROTTING_CHICKEN = new Item(new Item.Settings()
            .group(ItemGroup.FOOD)
            .food(ModFoodComponents.ROTTING_CHICKEN_FOOD));
    
    public static final Item FROZEN_ROTTING_CHICKEN = new Item(new Item.Settings()
            .group(ItemGroup.FOOD)
                    .food(ModFoodComponents.FROZEN_ROTTING_CHICKEN_FOOD));
    public static final Item FROZEN_COOKED_CHICKEN = new Item(new Item.Settings()
            .group(ItemGroup.FOOD)
                    .food(ModFoodComponents.FROZEN_COOKED_CHICKEN_FOOD));
    public static final Item FROZEN_RAW_CHICKEN = new Item(new Item.Settings()
            .group(ItemGroup.FOOD)
                    .food(ModFoodComponents.FROZEN_RAW_CHICKEN_FOOD));


            
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
        
        // Register burnt chicken item
        Registry.register(Registry.ITEM,
                new Identifier(MOD_ID, "burnt_chicken"),
                        BURNT_CHICKEN);

        // Register chicken dust item
        Registry.register(Registry.ITEM,
                new Identifier(MOD_ID, "chicken_dust"),
                        CHICKEN_DUST);
                
        // Register calcium dust item
        Registry.register(Registry.ITEM,
                new Identifier(MOD_ID, "calcium_dust"),
                        CALCIUM_DUST);
                
        // Register burnt chicken item
        Registry.register(Registry.ITEM,
                new Identifier(MOD_ID, "carbon_dust"),
                        CARBON_DUST);

        // Register small pile iron dust item
        Registry.register(Registry.ITEM,
                new Identifier(MOD_ID, "small_pile_iron_dust"),
                        SMALL_PILE_IRON_DUST);
        
        // Register dark chicken essence cell item
        Registry.register(Registry.ITEM,
                new Identifier(MOD_ID, "dark_chicken_essence_cell"),
                DARK_CHICKEN_ESSENCE_CELL);


        Registry.register(Registry.ITEM,
                new Identifier(MOD_ID, "chicken_egg_carton"),
                        CHICKEN_EGG_CARTON);
        
        // Register raw chicken block + item
        Registry.register(Registry.BLOCK,
                new Identifier(MOD_ID, "chicken_egg_crate"),
                        CHICKEN_EGG_CRATE);
                
       Registry.register(Registry.ITEM,
                new Identifier(MOD_ID, "chicken_egg_crate"),
                new BlockItem(CHICKEN_EGG_CRATE,
                        new Item.Settings().group(ItemGroup.FOOD)));

        Registry.register(Registry.ITEM,
                new Identifier(MOD_ID, "rotting_chicken"),
                ROTTING_CHICKEN);

        Registry.register(Registry.ITEM,
                new Identifier(MOD_ID, "frozen_rotting_chicken"),
                FROZEN_ROTTING_CHICKEN);
        Registry.register(Registry.ITEM,
                new Identifier(MOD_ID, "frozen_raw_chicken"),
                        FROZEN_RAW_CHICKEN);
        Registry.register(Registry.ITEM,
                new Identifier(MOD_ID, "frozen_cooked_chicken"),
                FROZEN_COOKED_CHICKEN);


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
