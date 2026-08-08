package com.pecsupplement;

import com.tagnumelite.projecteintegration.api.recipe.ARecipeTypeMapper;
import com.tagnumelite.projecteintegration.api.recipe.nss.NSSInput;
import com.tagnumelite.projecteintegration.api.recipe.nss.NSSOutput;
import io.github.lounode.ae2cs.common.init.AECSRecipeTypes;
import io.github.lounode.ae2cs.common.recipe.circuit_etcher.CircuitEtcherRecipe;
import io.github.lounode.ae2cs.common.recipe.crystal_aggregator.CrystalAggregatorRecipe;
import io.github.lounode.ae2cs.common.recipe.crystal_pulverizer.CrystalPulverizerRecipe;
import moze_intel.projecte.api.mapper.recipe.RecipeTypeMapper;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

public final class Ae2csAddon {
    public static final String MODID = "ae2cs";

    private Ae2csAddon() {
    }

    public static String NAME(String name) {
        return "AE2 CS " + name + " Mapper";
    }

    @RecipeTypeMapper(requiredMods = Ae2csAddon.MODID)
    public static class CrystalPulverizerMapper extends ARecipeTypeMapper<CrystalPulverizerRecipe> {
        @Override
        public String getName() {
            return Ae2csAddon.NAME("Crystal Pulverizer");
        }

        @Override
        public boolean canHandle(RecipeType<?> type) {
            return type == AECSRecipeTypes.CRYSTAL_PULVERIZER.get();
        }

        @Override
        public NSSInput getInput(CrystalPulverizerRecipe recipe) {
            return getInputBuilder().addSizedIngredient(recipe.input()).build();
        }

        @Override
        public NSSOutput getOutput(CrystalPulverizerRecipe recipe) {
            return getOutputBuilder().addItem(recipe.result()).build();
        }
    }

    @RecipeTypeMapper(requiredMods = Ae2csAddon.MODID)
    public static class CrystalAggregatorMapper extends ARecipeTypeMapper<CrystalAggregatorRecipe> {
        @Override
        public String getName() {
            return Ae2csAddon.NAME("Crystal Aggregator");
        }

        @Override
        public boolean canHandle(RecipeType<?> type) {
            return type == AECSRecipeTypes.CRYSTAL_AGGREGATOR.get();
        }

        @Override
        public NSSInput getInput(CrystalAggregatorRecipe recipe) {
            NSSInput.Builder builder = getInputBuilder();
            for (SizedIngredient ing : recipe.required()) {
                builder.addSizedIngredient(ing);
            }
            return builder.build();
        }

        @Override
        public NSSOutput getOutput(CrystalAggregatorRecipe recipe) {
            return getOutputBuilder().addItem(recipe.result()).build();
        }
    }

    @RecipeTypeMapper(requiredMods = Ae2csAddon.MODID)
    public static class CircuitEtcherMapper extends ARecipeTypeMapper<CircuitEtcherRecipe> {
        @Override
        public String getName() {
            return Ae2csAddon.NAME("Circuit Etcher");
        }

        @Override
        public boolean canHandle(RecipeType<?> type) {
            return type == AECSRecipeTypes.CIRCUIT_ETCHER.get();
        }

        @Override
        public NSSInput getInput(CircuitEtcherRecipe recipe) {
            NSSInput.Builder builder = getInputBuilder();
            for (SizedIngredient ing : recipe.required()) {
                builder.addSizedIngredient(ing);
            }
            return builder.build();
        }

        @Override
        public NSSOutput getOutput(CircuitEtcherRecipe recipe) {
            return getOutputBuilder().addItem(recipe.result()).build();
        }
    }
}
