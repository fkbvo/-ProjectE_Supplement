package com.pecsupplement;

import com.tagnumelite.projecteintegration.api.recipe.ARecipeTypeMapper;
import com.tagnumelite.projecteintegration.api.recipe.nss.NSSInput;
import com.tagnumelite.projecteintegration.api.recipe.nss.NSSOutput;
import mekanism.api.recipes.CombinerRecipe;
import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.api.recipes.MekanismRecipeTypes;
import mekanism.api.recipes.SawmillRecipe;
import moze_intel.projecte.api.mapper.recipe.RecipeTypeMapper;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;

public final class MekanismAddon {
    public static final String MODID = "mekanism";

    private MekanismAddon() {
    }

    public static String NAME(String name) {
        return "Mekanism " + name + " Mapper";
    }

    public abstract static class MekItemStackToItemStackMapper extends ARecipeTypeMapper<ItemStackToItemStackRecipe> {
        private final DeferredHolder<RecipeType<?>, RecipeType<ItemStackToItemStackRecipe>> recipeType;
        private final String name;

        protected MekItemStackToItemStackMapper(String name, DeferredHolder<RecipeType<?>, RecipeType<ItemStackToItemStackRecipe>> recipeType) {
            this.name = name;
            this.recipeType = recipeType;
        }

        @Override
        public String getName() {
            return MekanismAddon.NAME(name);
        }

        @Override
        public boolean canHandle(RecipeType<?> type) {
            return type == recipeType.value();
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

    @RecipeTypeMapper(requiredMods = MekanismAddon.MODID)
    public static class MekCrushingMapper extends MekItemStackToItemStackMapper {
        public MekCrushingMapper() {
            super("Crushing", MekanismRecipeTypes.TYPE_CRUSHING);
        }
    }

    @RecipeTypeMapper(requiredMods = MekanismAddon.MODID)
    public static class MekEnrichingMapper extends MekItemStackToItemStackMapper {
        public MekEnrichingMapper() {
            super("Enriching", MekanismRecipeTypes.TYPE_ENRICHING);
        }
    }

    @RecipeTypeMapper(requiredMods = MekanismAddon.MODID)
    public static class MekSmeltingMapper extends MekItemStackToItemStackMapper {
        public MekSmeltingMapper() {
            super("Smelting", MekanismRecipeTypes.TYPE_SMELTING);
        }
    }

    @RecipeTypeMapper(requiredMods = MekanismAddon.MODID)
    public static class MekCombiningMapper extends ARecipeTypeMapper<CombinerRecipe> {
        @Override
        public String getName() {
            return MekanismAddon.NAME("Combining");
        }

        @Override
        public boolean canHandle(RecipeType<?> type) {
            return type == MekanismRecipeTypes.TYPE_COMBINING.value();
        }

        @Override
        public NSSInput getInput(CombinerRecipe recipe) {
            return getInputBuilder()
                    .addSizedIngredient(recipe.getMainInput().ingredient())
                    .addSizedIngredient(recipe.getExtraInput().ingredient())
                    .build();
        }

        @Override
        public NSSOutput getOutput(CombinerRecipe recipe) {
            return getOutputBuilder().addOutputs(recipe.getOutputDefinition()).build();
        }
    }

    @RecipeTypeMapper(requiredMods = MekanismAddon.MODID)
    public static class MekSawingMapper extends ARecipeTypeMapper<SawmillRecipe> {
        @Override
        public String getName() {
            return MekanismAddon.NAME("Sawing");
        }

        @Override
        public boolean canHandle(RecipeType<?> type) {
            return type == MekanismRecipeTypes.TYPE_SAWING.value();
        }

        @Override
        public NSSInput getInput(SawmillRecipe recipe) {
            return getInputBuilder().addSizedIngredient(recipe.getInput().ingredient()).build();
        }

        @Override
        public NSSOutput getOutput(SawmillRecipe recipe) {
            return getOutputBuilder().addOutputs(recipe.getMainOutputDefinition()).build();
        }
    }
}
