package sonar.calculator.mod.integration.agricraft;

import com.InfinityRaider.AgriCraft.api.API;
import com.InfinityRaider.AgriCraft.api.APIBase;
import com.InfinityRaider.AgriCraft.api.v1.APIv1;
import net.minecraft.item.ItemStack;

public class AgriCraftAPIwrapper extends AgriCraftAPI {
    private APIv1 api;

    protected AgriCraftAPIwrapper() {
        super();
        APIBase api = API.getAPI(1);
        if (api instanceof APIv1) {
            this.api = (APIv1) api;
        }
    }

    public boolean isOk() {
        return api != null;
    }

    @Override
    public boolean isPlantingDisabled(ItemStack stack) {
        return isOk() && api.isNativePlantingDisabled(stack);
    }
}
