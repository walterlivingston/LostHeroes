package com.greenone.lostheroes;

import com.greenone.lostheroes.common.IProxy;
import com.greenone.lostheroes.common.enums.Metal;
import com.greenone.lostheroes.common.items.LHItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(LostHeroes.MOD_ID)
public class LostHeroes
{
    public static final String MOD_ID = "lostheroes";
    public static final ItemGroup lh_group = new ItemGroup(12, LostHeroes.MOD_ID) {
        @Override
        public ItemStack makeIcon() {
        return new ItemStack(LHItems.ingots.get(Metal.CELESTIAL_BRONZE));
        }
    };
    // Directly reference a log4j logger.
    public static IProxy PROXY;
    public static final Logger LOGGER = LogManager.getLogger();

    public LostHeroes() {
        PROXY = DistExecutor.safeRunForDist(() -> SideProxy.Client::new, () -> SideProxy.Common::new);
    }
}
