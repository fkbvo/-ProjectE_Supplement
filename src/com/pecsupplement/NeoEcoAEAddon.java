package com.pecsupplement;

import cn.dancingsnow.neoecoae.all.NERecipeTypes;
import cn.dancingsnow.neoecoae.recipe.IntegratedWorkingStationRecipe;
import com.tagnumelite.projecteintegration.api.recipe.ARecipeTypeMapper;
import com.tagnumelite.projecteintegration.api.recipe.nss.NSSInput;
import com.tagnumelite.projecteintegration.api.recipe.nss.NSSOutput;
import moze_intel.projecte.api.mapper.recipe.RecipeTypeMapper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

public final class NeoEcoAEAddon {
    public static final String MODID = "neoecoae";

    private NeoEcoAEAddon() {
    }

    public static String NAME(String name) {
        return "Neo Eco AE " + name + " Mapper";
    }

    @RecipeTypeMapper(requiredMods = NeoEcoAEAddon.MODID)
    public static class IntegratedWorkingStationMapper extends ARecipeTypeMapper<IntegratedWorkingStationRecipe> {
        @Override
        public String getName() {
            return NeoEcoAEAddon.NAME("Integrated Working Station");
        }

        @Override
        public boolean canHandle(RecipeType<?> type) {
            return type == NERecipeTypes.INTEGRATED_WORKING_STATION.value();
        }

        @Override
        public NSSInput getInput(IntegratedWorkingStationRecipe recipe) {
            NSSInput.Builder builder = getInputBuilder();
            for (SizedIngredient ing : recipe.inputItems()) {
                builder.addSizedIngredient(ing);
            }
            return builder.build();
        }

        @Override
        public NSSOutput getOutput(IntegratedWorkingStationRecipe recipe) {
            ItemStack out = recipe.itemOutput();
            if (out.isEmpty()) {
                return null;
            }
            return getOutputBuilder().addItem(out).build();
        }
    }
}
