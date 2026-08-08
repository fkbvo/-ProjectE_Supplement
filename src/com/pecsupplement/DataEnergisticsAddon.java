package com.pecsupplement;

import appeng.api.stacks.AEFluidKey;
import appeng.api.stacks.GenericStack;
import com.fish_dan_.data_energistics.recipe.DataChargerRecipe;
import com.fish_dan_.data_energistics.recipe.DataRipperReassemblerIngredient;
import com.fish_dan_.data_energistics.recipe.DataRipperReassemblerRecipe;
import com.fish_dan_.data_energistics.registry.ModRecipes;
import com.tagnumelite.projecteintegration.api.recipe.ARecipeTypeMapper;
import com.tagnumelite.projecteintegration.api.recipe.nss.NSSInput;
import com.tagnumelite.projecteintegration.api.recipe.nss.NSSOutput;
import moze_intel.projecte.api.mapper.recipe.RecipeTypeMapper;
import net.minecraft.world.item.crafting.RecipeType;

public final class DataEnergisticsAddon {
    public static final String MODID = "data_energistics";

    private DataEnergisticsAddon() {
    }

    public static String NAME(String name) {
        return "Data Energistics " + name + " Mapper";
    }

    @RecipeTypeMapper(requiredMods = DataEnergisticsAddon.MODID)
    public static class DataChargerMapper extends ARecipeTypeMapper<DataChargerRecipe> {
        @Override
        public String getName() {
            return DataEnergisticsAddon.NAME("Data Charger");
        }

        @Override
        public boolean canHandle(RecipeType<?> type) {
            return type == ModRecipes.DATA_CHARGER_TYPE.value();
        }

        @Override
        public NSSInput getInput(DataChargerRecipe recipe) {
            return getInputBuilder().addIngredient(1, recipe.getIngredient()).build();
        }

        @Override
        public NSSOutput getOutput(DataChargerRecipe recipe) {
            return getOutputBuilder().addItem(recipe.getResult()).build();
        }
    }

    @RecipeTypeMapper(requiredMods = DataEnergisticsAddon.MODID)
    public static class DataReassemblerMapper extends ARecipeTypeMapper<DataRipperReassemblerRecipe> {
        @Override
        public String getName() {
            return DataEnergisticsAddon.NAME("Data Reassembler");
        }

        @Override
        public boolean canHandle(RecipeType<?> type) {
            return type == ModRecipes.DATA_RIPPER_REASSEMBLER_TYPE.value();
        }

        @Override
        public NSSInput getInput(DataRipperReassemblerRecipe recipe) {
            NSSInput.Builder builder = getInputBuilder();
            for (DataRipperReassemblerIngredient ing : recipe.getItemInputs()) {
                builder.addIngredient(ing.count(), ing.ingredient());
            }
            for (GenericStack stack : recipe.getFluidInputs()) {
                if (stack.what() instanceof AEFluidKey key) {
                    builder.addFluid(key.toStack((int) stack.amount()));
                }
            }
            return builder.build();
        }

        @Override
        public NSSOutput getOutput(DataRipperReassemblerRecipe recipe) {
            return getOutputBuilder().addOutputs(recipe.getItemOutputs()).build();
        }
    }
}
