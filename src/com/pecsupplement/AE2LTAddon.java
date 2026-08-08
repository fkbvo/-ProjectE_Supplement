package com.pecsupplement;

import com.moakiee.ae2lt.lightning.CountedIngredient;
import com.moakiee.ae2lt.lightning.LightningTransformRecipe;
import com.moakiee.ae2lt.machine.firmament.recipe.FirmamentConversionIngredient;
import com.moakiee.ae2lt.machine.firmament.recipe.FirmamentConversionRecipe;
import com.moakiee.ae2lt.machine.lightningassembly.recipe.LightningAssemblyRecipe;
import com.moakiee.ae2lt.machine.lightningchamber.recipe.LightningSimulationIngredient;
import com.moakiee.ae2lt.machine.lightningchamber.recipe.LightningSimulationRecipe;
import com.moakiee.ae2lt.machine.overloadfactory.recipe.OverloadProcessingIngredient;
import com.moakiee.ae2lt.machine.overloadfactory.recipe.OverloadProcessingRecipe;
import com.moakiee.ae2lt.registry.ModRecipeTypes;
import com.tagnumelite.projecteintegration.api.recipe.ARecipeTypeMapper;
import com.tagnumelite.projecteintegration.api.recipe.nss.NSSInput;
import com.tagnumelite.projecteintegration.api.recipe.nss.NSSOutput;
import moze_intel.projecte.api.mapper.recipe.RecipeTypeMapper;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.fluids.FluidStack;

public final class AE2LTAddon {
    public static final String MODID = "ae2lt";

    private AE2LTAddon() {
    }

    public static String NAME(String name) {
        return "AE2 Lightning Tech " + name + " Mapper";
    }

    @RecipeTypeMapper(requiredMods = AE2LTAddon.MODID)
    public static class FirmamentConversionMapper extends ARecipeTypeMapper<FirmamentConversionRecipe> {
        @Override
        public String getName() {
            return AE2LTAddon.NAME("Firmament Conversion");
        }

        @Override
        public boolean canHandle(RecipeType<?> type) {
            return type == ModRecipeTypes.FIRMAMENT_CONVERSION_TYPE.value();
        }

        @Override
        public NSSInput getInput(FirmamentConversionRecipe recipe) {
            NSSInput.Builder builder = getInputBuilder();
            for (FirmamentConversionIngredient ing : recipe.inputs()) {
                builder.addIngredient(ing.count(), ing.ingredient());
            }
            return builder.build();
        }

        @Override
        public NSSOutput getOutput(FirmamentConversionRecipe recipe) {
            return getOutputBuilder().addOutputs(recipe.getResultStacks()).build();
        }
    }

    @RecipeTypeMapper(requiredMods = AE2LTAddon.MODID)
    public static class LightningAssemblyMapper extends ARecipeTypeMapper<LightningAssemblyRecipe> {
        @Override
        public String getName() {
            return AE2LTAddon.NAME("Lightning Assembly");
        }

        @Override
        public boolean canHandle(RecipeType<?> type) {
            return type == ModRecipeTypes.LIGHTNING_ASSEMBLY_TYPE.value();
        }

        @Override
        public NSSInput getInput(LightningAssemblyRecipe recipe) {
            NSSInput.Builder builder = getInputBuilder();
            for (LightningSimulationIngredient ing : recipe.inputs()) {
                builder.addIngredient(ing.count(), ing.ingredient());
            }
            return builder.build();
        }

        @Override
        public NSSOutput getOutput(LightningAssemblyRecipe recipe) {
            return getOutputBuilder().addItem(recipe.getResultStack()).build();
        }
    }

    @RecipeTypeMapper(requiredMods = AE2LTAddon.MODID)
    public static class LightningSimulationMapper extends ARecipeTypeMapper<LightningSimulationRecipe> {
        @Override
        public String getName() {
            return AE2LTAddon.NAME("Lightning Simulation");
        }

        @Override
        public boolean canHandle(RecipeType<?> type) {
            return type == ModRecipeTypes.LIGHTNING_SIMULATION_TYPE.value();
        }

        @Override
        public NSSInput getInput(LightningSimulationRecipe recipe) {
            NSSInput.Builder builder = getInputBuilder();
            for (LightningSimulationIngredient ing : recipe.inputs()) {
                builder.addIngredient(ing.count(), ing.ingredient());
            }
            return builder.build();
        }

        @Override
        public NSSOutput getOutput(LightningSimulationRecipe recipe) {
            return getOutputBuilder().addItem(recipe.getResultStack()).build();
        }
    }

    @RecipeTypeMapper(requiredMods = AE2LTAddon.MODID)
    public static class OverloadProcessingMapper extends ARecipeTypeMapper<OverloadProcessingRecipe> {
        @Override
        public String getName() {
            return AE2LTAddon.NAME("Overload Processing");
        }

        @Override
        public boolean canHandle(RecipeType<?> type) {
            return type == ModRecipeTypes.OVERLOAD_PROCESSING_TYPE.value();
        }

        @Override
        public NSSInput getInput(OverloadProcessingRecipe recipe) {
            NSSInput.Builder builder = getInputBuilder();
            for (OverloadProcessingIngredient ing : recipe.itemInputs()) {
                builder.addIngredient(ing.count(), ing.ingredient());
            }
            FluidStack fluid = recipe.fluidInput();
            if (!fluid.isEmpty()) {
                builder.addFluid(fluid);
            }
            return builder.build();
        }

        @Override
        public NSSOutput getOutput(OverloadProcessingRecipe recipe) {
            NSSOutput.Builder builder = getOutputBuilder();
            builder.addOutputs(recipe.itemResults());
            FluidStack fluid = recipe.fluidResult();
            if (!fluid.isEmpty()) {
                builder.addFluid(fluid);
            }
            return builder.build();
        }
    }

    @RecipeTypeMapper(requiredMods = AE2LTAddon.MODID)
    public static class LightningTransformMapper extends ARecipeTypeMapper<LightningTransformRecipe> {
        @Override
        public String getName() {
            return AE2LTAddon.NAME("Lightning Transform");
        }

        @Override
        public boolean canHandle(RecipeType<?> type) {
            return type == ModRecipeTypes.LIGHTNING_TRANSFORM_TYPE.value();
        }

        @Override
        public NSSInput getInput(LightningTransformRecipe recipe) {
            NSSInput.Builder builder = getInputBuilder();
            for (CountedIngredient ing : recipe.inputs()) {
                builder.addIngredient(ing.count(), ing.ingredient());
            }
            return builder.build();
        }

        @Override
        public NSSOutput getOutput(LightningTransformRecipe recipe) {
            return getOutputBuilder().addItem(recipe.getResultItem(registryAccess)).build();
        }
    }
}
