package com.example.chickenring;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class FowlForgeRecipe implements Recipe<Inventory> {
    private final Identifier id;
    private final ItemStack output;
    private final DefaultedList<Ingredient> ingredients;
    private final long essenceCost;

    public FowlForgeRecipe(Identifier id, ItemStack output, DefaultedList<Ingredient> ingredients, long essenceCost) {
        this.id = id;
        this.output = output;
        this.ingredients = ingredients;
        this.essenceCost = essenceCost;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        if (inventory.size() < 10) return false; // Need at least 10 slots (0=fluid, 1-9=inputs)
        for (int i = 0; i < 9; i++) {
            if (!ingredients.get(i).test(inventory.getStack(i + 1))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        return output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 9; // 3x3 grid
    }

    @Override
    public ItemStack getOutput() {
        return output;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ChickenRingMod.FOWL_FORGE_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ChickenRingMod.FOWL_FORGE_RECIPE_TYPE;
    }

    public long getEssenceCost() {
        return essenceCost;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return ingredients;
    }
}