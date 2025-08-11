package com.example.chickenring;
import com.example.chickenring.entity.ChonkenEntity;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class ChickenRingMod implements ModInitializer {
    public static final String MOD_ID = "hellfiremadeit";


    // Recipe Type
    public static final RecipeType<FowlForgeRecipe> FOWL_FORGE_RECIPE_TYPE =
        RecipeType.register(MOD_ID + ":fowl_forge");

    // Recipe Serializer
    public static final RecipeSerializer<FowlForgeRecipe> FOWL_FORGE_RECIPE_SERIALIZER =
        new FowlForgeRecipeSerializer();

    public static final SoundEvent FOWL_FORGE_AMBIENT0 = new SoundEvent(new Identifier(MOD_ID, "fowl_forge_ambient0"));
    public static final SoundEvent FOWL_FORGE_AMBIENT1 = new SoundEvent(new Identifier(MOD_ID, "fowl_forge_ambient1"));
    public static final SoundEvent FOWL_FORGE_AMBIENT2 = new SoundEvent(new Identifier(MOD_ID, "fowl_forge_ambient2"));



    public static final EntityType<ChonkenEntity> CHONKEN = Registry.register(
           Registry.ENTITY_TYPE,
           new Identifier(MOD_ID, "chonken"),
           FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChonkenEntity::new)
               .dimensions(EntityType.CHICKEN.getDimensions())
               .build()
       );
    
    // --------------------
    // Custom Items
    // --------------------
    public static final Item CHICKEN_RING = new Item(new Item.Settings()
        .maxCount(1)
        .fireproof()
        .group(ItemGroup.MISC)
    );
    public static final Item CHICKEN_EGG_CARTON = new Item(new Item.Settings()
        .maxCount(16)
        .group(ItemGroup.FOOD)
    );
    public static final Item BURNT_CHICKEN = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item CHICKEN_DUST = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item CALCIUM_DUST = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item CARBON_DUST = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item FRACTURED_DARK_ESSENCE_GEMSTONE = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item FLAWLESS_DARK_ESSENCE_GEMSTONE = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item CARBON_PLATE = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item DARK_CARBON_PLATE = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item RAW_CHICKEN_INGOT = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item COOKED_CHICKEN_INGOT = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item ROTTING_CHICKEN_INGOT = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item DARK_CHICKEN_ESSENCE_INGOT = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item SMALL_PILE_IRON_DUST = new Item(new Item.Settings().group(ItemGroup.MISC));
    public static final Item DARK_CHICKEN_ESSENCE_CELL = new Item(new Item.Settings()
        .group(ItemGroup.MISC)
        .maxCount(16)
        .fireproof()
    );

    public static final Item peckrONOMICON = new WrittenBookItem(new Item.Settings().maxCount(1).group(ItemGroup.MISC));
    public static final Item ROTTING_CHICKEN = new Item(new Item.Settings()
        .group(ItemGroup.FOOD)
        .food(ModFoodComponents.ROTTING_CHICKEN_FOOD)
    );
    public static final Item FROZEN_ROTTING_CHICKEN = new Item(new Item.Settings()
        .group(ItemGroup.FOOD)
        .food(ModFoodComponents.FROZEN_ROTTING_CHICKEN_FOOD)
    );
    public static final Item FROZEN_COOKED_CHICKEN = new Item(new Item.Settings()
        .group(ItemGroup.FOOD)
        .food(ModFoodComponents.FROZEN_COOKED_CHICKEN_FOOD)
    );
    public static final Item FROZEN_RAW_CHICKEN = new Item(new Item.Settings()
        .group(ItemGroup.FOOD)
        .food(ModFoodComponents.FROZEN_RAW_CHICKEN_FOOD)
    );

    // --------------------
    // Custom Blocks
    // --------------------
    public static final Block CHICKEN_EGG_CRATE = new ChickenEggCrateBlock(
        FabricBlockSettings.copyOf(Blocks.SAND)
    );
    public static final Block RAW_CHICKEN_BLOCK = new RawChickenBlock(
        FabricBlockSettings.copyOf(Blocks.WHITE_WOOL)
    );
    public static final Block COOKED_CHICKEN_BLOCK = new Block(
        FabricBlockSettings.copyOf(Blocks.ORANGE_WOOL)
    );
    public static final Block ROTTING_CHICKEN_BLOCK = new Block(
        FabricBlockSettings.copyOf(Blocks.BROWN_WOOL)
    );
    public static final Block FROZEN_RAW_CHICKEN_BLOCK = new FrozenRawChickenBlock(
        FabricBlockSettings.of(Material.ICE)
            .strength(0.5f)
            .ticksRandomly()
    );
    public static final Block FROZEN_COOKED_CHICKEN_BLOCK = new FrozenCookedChickenBlock(
        FabricBlockSettings.of(Material.ICE)
            .strength(0.5f)
            .ticksRandomly()
    );
    public static final Block FROZEN_ROTTING_CHICKEN_BLOCK = new FrozenRottingChickenBlock(
        FabricBlockSettings.of(Material.ICE)
            .strength(0.5f)
            .ticksRandomly()
    );


    
    // --------------------
    // Fluids + Bucket
    // --------------------
    public static FlowableFluid DARK_CHICKEN_ESSENCE_STILL;
    public static FlowableFluid DARK_CHICKEN_ESSENCE_FLOWING;
    public static Block DARK_CHICKEN_ESSENCE_BLOCK;
    public static Item DARK_CHICKEN_ESSENCE_BUCKET;

    // --------------------
    // Fowl Forge
    // --------------------
    public static Block FOWL_FORGE_BLOCK;
    public static BlockEntityType<FowlForgeBlockEntity> FOWL_FORGE_BLOCK_ENTITY;
    public static ScreenHandlerType<FowlForgeScreenHandler> FOWL_FORGE_SCREEN_HANDLER;

    @Override
    public void onInitialize() {

        System.out.println("ChickenRingMod: Initializing...");
        Registry.register(Registry.ITEM, new Identifier("hellfiremadeit", "peckronomicon"), peckrONOMICON);
        PlayerJoinHandler.register();
        // Register sound events
        Registry.register(Registry.SOUND_EVENT, new Identifier(MOD_ID, "fowl_forge_ambient0"), FOWL_FORGE_AMBIENT0);
        Registry.register(Registry.SOUND_EVENT, new Identifier(MOD_ID, "fowl_forge_ambient1"), FOWL_FORGE_AMBIENT1);
        Registry.register(Registry.SOUND_EVENT, new Identifier(MOD_ID, "fowl_forge_ambient2"), FOWL_FORGE_AMBIENT2);
        System.out.println("ChickenRingMod: Registered Fowl Forge ambient sound events");

        // Register Fowl Forge block + item
        FOWL_FORGE_BLOCK = Registry.register(
            Registry.BLOCK,
            new Identifier(MOD_ID, "fowl_forge"),
            new FowlForgeBlock(FabricBlockSettings.of(Material.METAL).strength(4.0f).requiresTool())
        );
        Registry.register(
            Registry.ITEM,
            new Identifier(MOD_ID, "fowl_forge"),
            new BlockItem(FOWL_FORGE_BLOCK, new Item.Settings().group(ItemGroup.MISC))
        );

        // Register block entity
        FOWL_FORGE_BLOCK_ENTITY = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            new Identifier(MOD_ID, "fowl_forge_block_entity"),
            FabricBlockEntityTypeBuilder.<FowlForgeBlockEntity>create(FowlForgeBlockEntity::new, FOWL_FORGE_BLOCK).build()
        );

        // Register screen handler
        @SuppressWarnings("deprecation")
        ScreenHandlerType<FowlForgeScreenHandler> localScreenHandler = ScreenHandlerRegistry.registerExtended(
            new Identifier(MOD_ID, "fowl_forge"),
            (syncId, inv, buf) -> {
                BlockPos pos = buf.readBlockPos();
                FowlForgeBlockEntity be = (FowlForgeBlockEntity) inv.player.getWorld().getBlockEntity(pos);
                if (be != null) {
                    System.out.println("ChickenRingMod: Creating screen handler for FowlForge at " + pos);
                    return new FowlForgeScreenHandler(syncId, inv, be, ScreenHandlerContext.create(inv.player.getWorld(), pos));
                }
                System.out.println("ChickenRingMod: Failed to create screen handler at " + pos + ", block entity is " + inv.player.getWorld().getBlockEntity(pos));
                return null;
            }
        );
        FOWL_FORGE_SCREEN_HANDLER = localScreenHandler;
                
        // Register recipe serializer
        Registry.register(Registry.RECIPE_SERIALIZER,
            new Identifier(MOD_ID, "fowl_forge"),
            FOWL_FORGE_RECIPE_SERIALIZER);
        
        FabricDefaultAttributeRegistry.register(CHONKEN, ChonkenEntity.createMobAttributes()
               .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
               .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D));
    
            // Register other blocks/items
        registerBlockWithItem("chicken_egg_crate", CHICKEN_EGG_CRATE, ItemGroup.FOOD);
        registerBlockWithItem("raw_chicken_block", RAW_CHICKEN_BLOCK, ItemGroup.FOOD);
        registerBlockWithItem("cooked_chicken_block", COOKED_CHICKEN_BLOCK, ItemGroup.FOOD);
        registerBlockWithItem("rotting_chicken_block", ROTTING_CHICKEN_BLOCK, ItemGroup.FOOD);
        registerBlockWithItem("frozen_raw_chicken_block", FROZEN_RAW_CHICKEN_BLOCK, ItemGroup.FOOD);
        registerBlockWithItem("frozen_cooked_chicken_block", FROZEN_COOKED_CHICKEN_BLOCK, ItemGroup.FOOD);
        registerBlockWithItem("frozen_rotting_chicken_block", FROZEN_ROTTING_CHICKEN_BLOCK, ItemGroup.FOOD);

        registerItem("chicken_ring", CHICKEN_RING);
        registerItem("chicken_egg_carton", CHICKEN_EGG_CARTON);
        registerItem("burnt_chicken", BURNT_CHICKEN);
        registerItem("chicken_dust", CHICKEN_DUST);
        registerItem("calcium_dust", CALCIUM_DUST);
        registerItem("carbon_dust", CARBON_DUST);
        registerItem("small_pile_iron_dust", SMALL_PILE_IRON_DUST);
        registerItem("dark_chicken_essence_cell", DARK_CHICKEN_ESSENCE_CELL);
        registerItem("fractured_dark_essence_gemstone", FRACTURED_DARK_ESSENCE_GEMSTONE);
        registerItem("flawless_dark_essence_gemstone", FLAWLESS_DARK_ESSENCE_GEMSTONE);
        registerItem("carbon_plate", CARBON_PLATE);
        registerItem("dark_carbon_plate", DARK_CARBON_PLATE);
        registerItem("raw_chicken_ingot", RAW_CHICKEN_INGOT);
        registerItem("cooked_chicken_ingot", COOKED_CHICKEN_INGOT);
        registerItem("rotting_chicken_ingot", ROTTING_CHICKEN_INGOT);
        registerItem("dark_chicken_essence_ingot", DARK_CHICKEN_ESSENCE_INGOT);
        registerItem("rotting_chicken", ROTTING_CHICKEN);
        registerItem("frozen_rotting_chicken", FROZEN_ROTTING_CHICKEN);
        registerItem("frozen_raw_chicken", FROZEN_RAW_CHICKEN);
        registerItem("frozen_cooked_chicken", FROZEN_COOKED_CHICKEN);
        // Fluids + Bucket
        DARK_CHICKEN_ESSENCE_STILL = Registry.register(
            Registry.FLUID,
            new Identifier(MOD_ID, "dark_chicken_essence_still"),
            new DarkChickenEssenceFluid.Still()
        );
        DARK_CHICKEN_ESSENCE_FLOWING = Registry.register(
            Registry.FLUID,
            new Identifier(MOD_ID, "dark_chicken_essence_flowing"),
            new DarkChickenEssenceFluid.Flowing()
        );
        DARK_CHICKEN_ESSENCE_BLOCK = Registry.register(
            Registry.BLOCK,
            new Identifier(MOD_ID, "dark_chicken_essence_block"),
            new FluidBlock(DARK_CHICKEN_ESSENCE_STILL, FabricBlockSettings.copyOf(Blocks.WATER)) {}
        );
        DARK_CHICKEN_ESSENCE_BUCKET = Registry.register(
            Registry.ITEM,
            new Identifier(MOD_ID, "dark_chicken_essence_bucket"),
            new BucketItem(
                DARK_CHICKEN_ESSENCE_STILL,
                new Item.Settings()
                    .recipeRemainder(Items.BUCKET)
                    .maxCount(1)
                    .group(ItemGroup.MISC)
            )
        );

        // Fluid rendering setup
        FluidRenderHandler fluidHandler = new SimpleFluidRenderHandler(
            new Identifier(MOD_ID, "block/dark_chicken_essence_still"),
            new Identifier(MOD_ID, "block/dark_chicken_essence_flow"),
            0xFF3E0066
        );
        FluidRenderHandlerRegistry.INSTANCE.register(
            DARK_CHICKEN_ESSENCE_STILL,
            DARK_CHICKEN_ESSENCE_FLOWING,
            fluidHandler
        );
        BlockRenderLayerMap.INSTANCE.putFluids(
            RenderLayer.getTranslucent(),
            DARK_CHICKEN_ESSENCE_STILL,
            DARK_CHICKEN_ESSENCE_FLOWING
        );

        // Additional registries
        ItemDecayCallback.register();
        ModBlockEntities.registerAll();
    }

    private static void registerItem(String name, Item item) {
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, name), item);
    }

    private static void registerBlockWithItem(String name, Block block, ItemGroup group) {
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, name), block);
        Registry.register(
            Registry.ITEM,
            new Identifier(MOD_ID, name),
            new BlockItem(block, new Item.Settings().group(group))
        );
    }
}