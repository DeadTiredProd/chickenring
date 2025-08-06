package com.example.chickenring;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;

public class FowlForgeRecipeSerializer implements RecipeSerializer<FowlForgeRecipe> {
    @Override
    public FowlForgeRecipe read(Identifier id, JsonObject json) {
        ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result"));
        JsonArray ingredientsJson = JsonHelper.getArray(json, "ingredients");
        DefaultedList<Ingredient> ingredients = DefaultedList.ofSize(9, Ingredient.EMPTY);
        for (int i = 0; i < Math.min(ingredientsJson.size(), 9); i++) {
            ingredients.set(i, Ingredient.fromJson(ingredientsJson.get(i)));
        }
        long essenceCost = JsonHelper.getLong(json, "essence", 1000); // Default to 1000 mB
        return new FowlForgeRecipe(id, output, ingredients, essenceCost);
    }

    @Override
    public FowlForgeRecipe read(Identifier id, PacketByteBuf buf) {
        ItemStack output = buf.readItemStack();
        DefaultedList<Ingredient> ingredients = DefaultedList.ofSize(9, Ingredient.EMPTY);
        for (int i = 0; i < 9; i++) {
            ingredients.set(i, Ingredient.fromPacket(buf));
        }
        long essenceCost = buf.readLong();
        return new FowlForgeRecipe(id, output, ingredients, essenceCost);
    }

    @Override
    public void write(PacketByteBuf buf, FowlForgeRecipe recipe) {
        buf.writeItemStack(recipe.getOutput());
        for (Ingredient ingredient : recipe.getIngredients()) {
            ingredient.write(buf);
        }
        buf.writeLong(recipe.getEssenceCost());
    }
}