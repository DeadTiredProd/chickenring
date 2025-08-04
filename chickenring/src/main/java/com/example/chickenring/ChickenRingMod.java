package com.example.chickenring;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ChickenRingMod implements ModInitializer {
    public static final String MOD_ID = "hellfiremadeit";

    // Items
    public static final Item CHICKEN_RING = new Item(new Item.Settings().maxCount(1).fireproof().group(ItemGroup.MISC));
    public static final Item CHICKEN_EGG_CARTON = new Item(new Item.Settings().maxCount(16).group(ItemGroup.FOOD));
    public static final Item BURNT_CHICKEN = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item CHICKEN_DUST = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item CALCIUM_DUST = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item CARBON_DUST = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item SMALL_PILE_IRON_DUST = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item DARK_CHICKEN_ESSENCE_CELL = new Item(new Item.Settings().group(ItemGroup.MISC).maxCount(16).fireproof());

    public static final Item ROTTING_CHICKEN = new Item(new Item.Settings().group(ItemGroup.FOOD).food(ModFoodComponents.ROTTING_CHICKEN_FOOD));
    public static final Item FROZEN_ROTTING_CHICKEN = new Item(new Item.Settings().group(ItemGroup.FOOD).food(ModFoodComponents.FROZEN_ROTTING_CHICKEN_FOOD));
    public static final Item FROZEN_COOKED_CHICKEN = new Item(new Item.Settings().group(ItemGroup.FOOD).food(ModFoodComponents.FROZEN_COOKED_CHICKEN_FOOD));
    public static final Item FROZEN_RAW_CHICKEN = new Item(new Item.Settings().group(ItemGroup.FOOD).food(ModFoodComponents.FROZEN_RAW_CHICKEN_FOOD));

    // Blocks
    public static final Block CHICKEN_EGG_CRATE = new ChickenEggCrateBlock(Block.Settings.copy(Blocks.SAND));
    public static final Block RAW_CHICKEN_BLOCK = new RawChickenBlock(Block.Settings.copy(Blocks.WHITE_WOOL));
    public static final Block COOKED_CHICKEN_BLOCK = new Block(Block.Settings.copy(Blocks.ORANGE_WOOL));
    public static final Block ROTTING_CHICKEN_BLOCK = new Block(Block.Settings.copy(Blocks.BROWN_WOOL));
    public static final Block FROZEN_RAW_CHICKEN_BLOCK = new FrozenRawChickenBlock(Block.Settings.of(Material.ICE).strength(0.5f).ticksRandomly());
    public static final Block FROZEN_COOKED_CHICKEN_BLOCK = new FrozenCookedChickenBlock(Block.Settings.of(Material.ICE).strength(0.5f).ticksRandomly());
    public static final Block FROZEN_ROTTING_CHICKEN_BLOCK = new FrozenRottingChickenBlock(Block.Settings.of(Material.ICE).strength(0.5f).ticksRandomly());

    // Fluid stuff
    public static FlowableFluid DARK_CHICKEN_ESSENCE_STILL;
    public static FlowableFluid DARK_CHICKEN_ESSENCE_FLOWING;
    public static Block DARK_CHICKEN_ESSENCE_BLOCK;
    public static Item DARK_CHICKEN_ESSENCE_BUCKET;

    // Fowl Forge block and block entity
    public static final Identifier FOWL_FORGE_ID = new Identifier(MOD_ID, "fowl_forge");
    public static Block FOWL_FORGE_BLOCK;
    public static BlockEntityType<FowlForgeBlockEntity> FOWL_FORGE_BLOCK_ENTITY;

    @Override
    public void onInitialize() {
        // Initialize your FOWL_FORGE_BLOCK here (missing in your snippet)
        FOWL_FORGE_BLOCK = new FowlForgeBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f));

        // Register blocks + block items
        registerBlockWithItem("chicken_egg_crate", CHICKEN_EGG_CRATE, ItemGroup.FOOD);
        registerBlockWithItem("raw_chicken_block", RAW_CHICKEN_BLOCK, ItemGroup.FOOD);
        registerBlockWithItem("cooked_chicken_block", COOKED_CHICKEN_BLOCK, ItemGroup.FOOD);
        registerBlockWithItem("rotting_chicken_block", ROTTING_CHICKEN_BLOCK, ItemGroup.FOOD);
        registerBlockWithItem("frozen_raw_chicken_block", FROZEN_RAW_CHICKEN_BLOCK, ItemGroup.FOOD);
        registerBlockWithItem("frozen_cooked_chicken_block", FROZEN_COOKED_CHICKEN_BLOCK, ItemGroup.FOOD);
        registerBlockWithItem("frozen_rotting_chicken_block", FROZEN_ROTTING_CHICKEN_BLOCK, ItemGroup.FOOD);
        registerBlockWithItem("fowl_forge", FOWL_FORGE_BLOCK, ItemGroup.MISC);

        // Register items only
        registerItem("chicken_ring", CHICKEN_RING);
        registerItem("chicken_egg_carton", CHICKEN_EGG_CARTON);
        registerItem("burnt_chicken", BURNT_CHICKEN);
        registerItem("chicken_dust", CHICKEN_DUST);
        registerItem("calcium_dust", CALCIUM_DUST);
        registerItem("carbon_dust", CARBON_DUST);
        registerItem("small_pile_iron_dust", SMALL_PILE_IRON_DUST);
        registerItem("dark_chicken_essence_cell", DARK_CHICKEN_ESSENCE_CELL);
        registerItem("rotting_chicken", ROTTING_CHICKEN);
        registerItem("frozen_rotting_chicken", FROZEN_ROTTING_CHICKEN);
        registerItem("frozen_raw_chicken", FROZEN_RAW_CHICKEN);
        registerItem("frozen_cooked_chicken", FROZEN_COOKED_CHICKEN);

        // Register fluids
        DARK_CHICKEN_ESSENCE_STILL = Registry.register(Registry.FLUID, new Identifier(MOD_ID, "dark_chicken_essence_still"), new DarkChickenEssenceFluid.Still());
        DARK_CHICKEN_ESSENCE_FLOWING = Registry.register(Registry.FLUID, new Identifier(MOD_ID, "dark_chicken_essence_flowing"), new DarkChickenEssenceFluid.Flowing());

        DARK_CHICKEN_ESSENCE_BLOCK = Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "dark_chicken_essence_block"),
                new FluidBlock(DARK_CHICKEN_ESSENCE_STILL, FabricBlockSettings.copyOf(Blocks.WATER)) {});

        DARK_CHICKEN_ESSENCE_BUCKET = Registry.register(Registry.ITEM, new Identifier(MOD_ID, "dark_chicken_essence_bucket"),
                new BucketItem(DARK_CHICKEN_ESSENCE_STILL, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(ItemGroup.MISC)));

        // Register block entity for Fowl Forge
        FOWL_FORGE_BLOCK_ENTITY = Registry.register(
                Registry.BLOCK_ENTITY_TYPE,
                new Identifier(MOD_ID, "fowl_forge_block_entity"),
                FabricBlockEntityTypeBuilder.<FowlForgeBlockEntity>create(FowlForgeBlockEntity::new, FOWL_FORGE_BLOCK).build(null)

        );

        // Register your block entities in ModBlockEntities if you have more there
        ModBlockEntities.registerAll();

        // ItemDecayCallback setup (your custom callback)
        ItemDecayCallback.register();

        // Fluid render handler & layer
        FluidRenderHandler fluidRenderHandler = new SimpleFluidRenderHandler(
                new Identifier(MOD_ID, "block/dark_chicken_essence_still"),
                new Identifier(MOD_ID, "block/dark_chicken_essence_flow"),
                0xFF3E0066 // Purple-ish tint ARGB
        );

        FluidRenderHandlerRegistry.INSTANCE.register(DARK_CHICKEN_ESSENCE_STILL, DARK_CHICKEN_ESSENCE_FLOWING, fluidRenderHandler);

        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), DARK_CHICKEN_ESSENCE_STILL, DARK_CHICKEN_ESSENCE_FLOWING);
    }

    private static void registerItem(String name, Item item) {
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, name), item);
    }

    private static void registerBlockWithItem(String name, Block block, ItemGroup group) {
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, name), block);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, name), new BlockItem(block, new Item.Settings().group(group)));
    }
}
