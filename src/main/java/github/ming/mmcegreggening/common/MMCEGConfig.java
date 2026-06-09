package github.ming.mmcegreggening.common;

import github.ming.mmcegreggening.ModularMachineryGreggening;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = ModularMachineryGreggening.MODID)
@Config.LangKey("modularmachinerygreggening.config.title")
public class MMCEGConfig {

    @Config.Comment("Hatches capture periodic snapshots of the game state and process them asynchronously. The snapshot update frequency is adjusted dynamically. This sets the minimum interval between updates (in ms). Default: 500")
    public static int minIntervalMs = 500;

    @Config.Comment("Hatches capture periodic snapshots of the game state and process them asynchronously. The snapshot update frequency is adjusted dynamically. This sets the maximum interval between updates (in ms). Default: 30_000")
    public static int maxIntervalMs = 30_000;

    @Mod.EventBusSubscriber(modid = ModularMachineryGreggening.MODID)
    public static class EventHandler {
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(ModularMachineryGreggening.MODID)) {
                ConfigManager.sync(ModularMachineryGreggening.MODID, Config.Type.INSTANCE);
            }
        }
    }

}
