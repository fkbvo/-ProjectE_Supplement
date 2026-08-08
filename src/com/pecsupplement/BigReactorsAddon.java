package com.pecsupplement;

import com.tagnumelite.projecteintegration.api.recipe.ARecipeTypeMapper;
import com.tagnumelite.projecteintegration.api.recipe.nss.NSSInput;
import com.tagnumelite.projecteintegration.api.recipe.nss.NSSOutput;
import it.zerono.mods.extremereactors.gamecontent.Content;
import it.zerono.mods.extremereactors.gamecontent.multiblock.fluidizer.recipe.FluidizerFluidMixingRecipe;
import it.zerono.mods.extremereactors.gamecontent.multiblock.fluidizer.recipe.FluidizerSolidMixingRecipe;
import it.zerono.mods.extremereactors.gamecontent.multiblock.fluidizer.recipe.FluidizerSolidRecipe;
import it.zerono.mods.extremereactors.gamecontent.multiblock.reprocessor.recipe.ReprocessorRecipe;
import it.zerono.mods.zerocore.lib.recipe.ModRecipe;
import it.zerono.mods.zerocore.lib.recipe.ingredient.FluidStackRecipeIngredient;
import it.zerono.mods.zerocore.lib.recipe.ingredient.ItemStackRecipeIngredient;
import it.zerono.mods.zerocore.lib.recipe.result.FluidStackRecipeResult;
import moze_intel.projecte.api.mapper.recipe.RecipeTypeMapper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.fluids.FluidStack;

public final class BigReactorsAddon {
    public static final String MODID = "bigreactors";

    private BigReactorsAddon() {
    }

    public static String NAME(String name) {
        return "Extreme Reactors " + name + " Mapper";
    }

    @RecipeTypeMapper(requiredMods = BigReactorsAddon.MODID)
    public static class ReprocessorMapper extends ARecipeTypeMapper<ReprocessorRecipe> {
        @Override
        public String getName() {
            return BigReactorsAddon.NAME("Reprocessor");
        }

        @Override
        public boolean canHandle(RecipeType<?> type) {
            return type == Content.Recipes.REPROCESSOR_RECIPE_TYPE.get();
        }

        @Override
        public NSSInput getInput(ReprocessorRecipe recipe) {
            NSSInput.Builder builder = getInputBuilder();
            for (ItemStack stack : recipe.getIngredient1().getMatchingElements()) {
                builder.addItem(stack);
            }
            for (FluidStack stack : recipe.getIngredient2().getMatchingElements()) {
                builder.addFluid(stack);
            }
            return builder.build();
        }

        @Override
        public NSSOutput getOutput(ReprocessorRecipe recipe) {
            return getOutputBuilder().addItem(recipe.getResult().getResult()).build();
        }
    }

    @RecipeTypeMapper(requiredMods = BigReactorsAddon.MODID)
    public static class FluidizerMapper extends ARecipeTypeMapper<ModRecipe> {
        @Override
        public String getName() {
            return BigReactorsAddon.NAME("Fluidizer");
        }

        @Override
        public boolean canHandle(RecipeType<?> type) {
            return type == Content.Recipes.FLUIDIZER_RECIPE_TYPE.get();
        }

        @Override
        public NSSInput getInput(ModRecipe recipe) {
            NSSInput.Builder builder = getInputBuilder();
            if (recipe instanceof FluidizerSolidRecipe solid) {
                addItems(builder, solid.getIngredient());
            } else if (recipe instanceof FluidizerSolidMixingRecipe mixing) {
                addItems(builder, mixing.getIngredient1());
                addItems(builder, mixing.getIngredient2());
            } else if (recipe instanceof FluidizerFluidMixingRecipe mixing) {
                addFluids(builder, mixing.getIngredient1());
                addFluids(builder, mixing.getIngredient2());
            }
            return builder.build();
        }

        @Override
        public NSSOutput getOutput(ModRecipe recipe) {
            NSSOutput.Builder builder = getOutputBuilder();
            if (recipe instanceof FluidizerSolidRecipe solid) {
                addFluid(builder, solid.getResult());
            } else if (recipe instanceof FluidizerSolidMixingRecipe mixing) {
                addFluid(builder, mixing.getResult());
            } else if (recipe instanceof FluidizerFluidMixingRecipe mixing) {
                addFluid(builder, mixing.getResult());
            }
            return builder.build();
        }

        private static void addItems(NSSInput.Builder builder, ItemStackRecipeIngredient ingredient) {
            for (ItemStack stack : ingredient.getMatchingElements()) {
                builder.addItem(stack);
            }
        }

        private static void addFluids(NSSInput.Builder builder, FluidStackRecipeIngredient ingredient) {
            for (FluidStack stack : ingredient.getMatchingElements()) {
                builder.addFluid(stack);
            }
        }

        private static void addFluid(NSSOutput.Builder builder, FluidStackRecipeResult result) {
            builder.addFluid(result.getResult());
        }
    }
}
