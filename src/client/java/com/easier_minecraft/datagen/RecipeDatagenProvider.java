package com.easier_minecraft.datagen;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.easier_minecraft.EasierMinecraft;
import com.easier_minecraft.register.ItemRegister;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.BlastingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.util.Identifier;

public class RecipeDatagenProvider extends FabricRecipeProvider {
    private static final List<ItemConvertible> headsConvertibleToSkeletonSkull = List.of(
            Items.PLAYER_HEAD,
            Items.CREEPER_HEAD,
            Items.ZOMBIE_HEAD,
            Items.PIGLIN_HEAD);

    public RecipeDatagenProvider(FabricDataOutput output, CompletableFuture<WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public String getName() {
        return "Recipe datagen provider";
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.COMBAT, ItemRegister.EXPLOSION_BOW)
                .input(Items.BOW)
                .input(Items.TNT)
                .criterion("has_item", RecipeDatagenProvider.conditionsFromItem(Items.BOW))
                .offerTo(exporter, Identifier.of(EasierMinecraft.MOD_ID, "explosion_bow"));
        ShapelessRecipeJsonBuilder.create(RecipeCategory.COMBAT, ItemRegister.TELEPORT_BOW)
                .input(Items.BOW)
                .input(Items.ENDER_PEARL)
                .criterion("has_item", RecipeDatagenProvider.conditionsFromItem(Items.BOW))
                .offerTo(exporter, Identifier.of(EasierMinecraft.MOD_ID, "teleport_bow"));
        ShapelessRecipeJsonBuilder.create(RecipeCategory.COMBAT, ItemRegister.FIREWORK_ROCKET_BOW)
                .input(Items.BOW)
                .input(Items.FIREWORK_ROCKET)
                .criterion("has_item", RecipeDatagenProvider.conditionsFromItem(Items.BOW))
                .offerTo(exporter, Identifier.of(EasierMinecraft.MOD_ID, "firework_rocket_bow"));
        ShapelessRecipeJsonBuilder.create(RecipeCategory.COMBAT, ItemRegister.VILLAGER_BOW)
                .input(Items.BOW)
                .input(Items.EMERALD)
                .criterion("has_item", RecipeDatagenProvider.conditionsFromItem(Items.BOW))
                .offerTo(exporter, Identifier.of(EasierMinecraft.MOD_ID, "villager_bow"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ItemRegister.DIAMOND_BOW)
                .pattern("WWD")
                .pattern("W S")
                .pattern("DSS")
                .input('W', Ingredient.ofItems(Items.STICK))
                .input('D', Ingredient.ofItems(Items.DIAMOND))
                .input('S', Ingredient.ofItems(Items.STRING))
                .criterion("has_item", RecipeDatagenProvider.conditionsFromItem(Items.DIAMOND))
                .offerTo(exporter, Identifier.of(EasierMinecraft.MOD_ID, "diamond_bow"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ItemRegister.EMERALD_APPLE)
                .pattern("EEE")
                .pattern("EAE")
                .pattern("EEE")
                .input('E', Ingredient.ofItems(Items.EMERALD_BLOCK))
                .input('A', Ingredient.ofItems(Items.ENCHANTED_GOLDEN_APPLE))
                .criterion("has_item", RecipeDatagenProvider.conditionsFromItem(Items.EMERALD_BLOCK))
                .offerTo(exporter, Identifier.of(EasierMinecraft.MOD_ID, "emerald_apple"));
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Items.TOTEM_OF_UNDYING)
                .pattern("EDE")
                .pattern("DGD")
                .pattern(" D ")
                .input('E', Ingredient.ofItems(Items.EMERALD))
                .input('G', Ingredient.ofItems(Items.GOLD_BLOCK))
                .input('D', Ingredient.ofItems(Items.DIAMOND))
                .criterion("has_item", RecipeDatagenProvider.conditionsFromItem(Items.EMERALD))
                .offerTo(exporter, Identifier.of(EasierMinecraft.MOD_ID, "totem_of_undying"));
        CookingRecipeJsonBuilder
                .create(Ingredient.ofItems(Items.RAW_COPPER_BLOCK), RecipeCategory.BUILDING_BLOCKS,
                        Items.COPPER_BLOCK, 6.3F, 100, RecipeSerializer.BLASTING,
                        BlastingRecipe::new)
                .group(null)
                .criterion("has_item", RecipeDatagenProvider.conditionsFromItem(Items.RAW_COPPER_BLOCK))
                .offerTo(exporter, getItemPath(Items.COPPER_BLOCK) + "_from_blasting_"
                        + getItemPath(Items.RAW_COPPER_BLOCK));
        CookingRecipeJsonBuilder
                .create(Ingredient.ofItems(Items.RAW_IRON_BLOCK), RecipeCategory.BUILDING_BLOCKS,
                        Items.IRON_BLOCK, 6.3F, 100, RecipeSerializer.BLASTING,
                        BlastingRecipe::new)
                .group(null)
                .criterion("has_item", RecipeDatagenProvider.conditionsFromItem(Items.RAW_IRON_BLOCK))
                .offerTo(exporter, getItemPath(Items.IRON_BLOCK) + "_from_blasting_"
                        + getItemPath(Items.RAW_IRON_BLOCK));
        CookingRecipeJsonBuilder
                .create(Ingredient.ofItems(Items.RAW_GOLD_BLOCK), RecipeCategory.BUILDING_BLOCKS,
                        Items.GOLD_BLOCK, 9.0F, 100, RecipeSerializer.BLASTING,
                        BlastingRecipe::new)
                .group(null)
                .criterion("has_item", RecipeDatagenProvider.conditionsFromItem(Items.RAW_GOLD_BLOCK))
                .offerTo(exporter, getItemPath(Items.GOLD_BLOCK) + "_from_blasting_"
                        + getItemPath(Items.RAW_GOLD_BLOCK));
        CookingRecipeJsonBuilder
                .create(Ingredient.ofItems(Items.RAW_COPPER_BLOCK), RecipeCategory.BUILDING_BLOCKS,
                        Items.COPPER_BLOCK, 6.3F, 200, RecipeSerializer.SMELTING,
                        SmeltingRecipe::new)
                .group(null)
                .criterion("has_item", RecipeDatagenProvider.conditionsFromItem(Items.RAW_COPPER_BLOCK))
                .offerTo(exporter, getItemPath(Items.COPPER_BLOCK) + "_from_smelting_"
                        + getItemPath(Items.RAW_COPPER_BLOCK));
        CookingRecipeJsonBuilder
                .create(Ingredient.ofItems(Items.RAW_IRON_BLOCK), RecipeCategory.BUILDING_BLOCKS,
                        Items.IRON_BLOCK, 6.3F, 200, RecipeSerializer.SMELTING,
                        SmeltingRecipe::new)
                .group(null)
                .criterion("has_item", RecipeDatagenProvider.conditionsFromItem(Items.RAW_IRON_BLOCK))
                .offerTo(exporter, getItemPath(Items.IRON_BLOCK) + "_from_smelting_"
                        + getItemPath(Items.RAW_IRON_BLOCK));
        CookingRecipeJsonBuilder
                .create(Ingredient.ofItems(Items.RAW_GOLD_BLOCK), RecipeCategory.BUILDING_BLOCKS,
                        Items.GOLD_BLOCK, 9.0F, 200, RecipeSerializer.SMELTING,
                        SmeltingRecipe::new)
                .group(null)
                .criterion("has_item", RecipeDatagenProvider.conditionsFromItem(Items.RAW_GOLD_BLOCK))
                .offerTo(exporter, getItemPath(Items.GOLD_BLOCK) + "_from_smelting_"
                        + getItemPath(Items.RAW_GOLD_BLOCK));
        offerSmelting(exporter, headsConvertibleToSkeletonSkull, RecipeCategory.MISC, Items.SKELETON_SKULL, 0.7F, 200,
                null);
        CookingRecipeJsonBuilder
                .create(Ingredient.ofItems(Items.SKELETON_SKULL), RecipeCategory.MISC,
                        Items.WITHER_SKELETON_SKULL, 0.7F, 200, RecipeSerializer.SMELTING,
                        SmeltingRecipe::new)
                .group(null)
                .criterion("has_item", RecipeDatagenProvider.conditionsFromItem(Items.SKELETON_SKULL))
                .offerTo(exporter, getItemPath(Items.WITHER_SKELETON_SKULL) + "_from_smelting_"
                        + getItemPath(Items.SKELETON_SKULL));
    }

}