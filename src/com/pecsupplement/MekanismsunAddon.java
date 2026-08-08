package com.pecsupplement;

import com.hamburger0abcde.mekanismsun.common.recipes.MSRecipeType;
import com.tagnumelite.projecteintegration.api.recipe.ARecipeTypeMapper;
import com.tagnumelite.projecteintegration.api.recipe.nss.NSSInput;
import com.tagnumelite.projecteintegration.api.recipe.nss.NSSOutput;
import mekanism.api.recipes.ItemStackToItemStackRecipe;
import moze_intel.projecte.api.mapper.recipe.RecipeTypeMapper;
import net.minecraft.world.item.crafting.RecipeType;

public final class MekanismsunAddon {
    public static final String MODID = "mekanismsun";

    private MekanismsunAddon() {
    }

    public static String NAME(String name) {
        return "Mekanism Sun " + name + " Mapper";
    }

    @RecipeTypeMapper(requiredMods = MekanismsunAddon.MODID)
    public static class TransmutationMapper extends ARecipeTypeMapper<ItemStackToItemStackRecipe> {
        @Override
        public String getName() {
            return MekanismsunAddon.NAME("Transmutation");
        }

        @Override
        public boolean canHandle(RecipeType<?> type) {
            return type == MSRecipeType.TRANSMUTATION.get();
        }

        @Override
        public NSSInput getInput(ItemStackToItemStackRecipe recipe) {
            return getInputBuilder().addSizedIngredient(recipe.getInput().ingredient()).build();
        }

        @Override
        public NSSOutput getOutput(ItemStackToItemStackRecipe recipe) {
            return getOutputBuilder().addOutputs(recipe.getOutputDefinition()).build();
        }
    }
}
