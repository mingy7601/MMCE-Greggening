package github.ming.mmcegreggening;

import github.ming.mmcegreggening.common.Mods;
import github.ming.mmcegreggening.common.network.AdvancedMESettingsMessage;
import github.ming.mmcegreggening.common.network.AdvancedMERSyncMessage;
import github.ming.mmcegreggening.common.network.handler.AdvancedMESettingsMessageHandler;
import github.ming.mmcegreggening.common.network.handler.AdvancedMERSyncMessageHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = ModularMachineryGreggening.MODID,
        name = ModularMachineryGreggening.NAME,
        version = ModularMachineryGreggening.VERSION,
        dependencies = "required-after:forge@[14.21.0.2371,);"
                + "required-after:modularmachinery@[2.1.5,);"
        ,
        acceptedMinecraftVersions = "[1.12]",
        acceptableRemoteVersions = "[1.0.0]"
)
public class ModularMachineryGreggening {

    public static final String MODID = "modularmachinerygreggening";
    public static final String NAME = "Modular Machinery: Community Edition Greggening";
    public static final String VERSION = "1.0.0";
    public static final String CLIENT_PROXY = "github.ming.mmcegreggening.client.ClientProxy";
    public static final String COMMON_PROXY = "github.ming.mmcegreggening.CommonProxy";

    // Registries handlers

    @Mod.Instance(MODID)
    public static ModularMachineryGreggening instance;

    public static Logger logger;

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("mmaddons"); // MODID could not be used here as the max length of the channel name must be < 20

    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = COMMON_PROXY)
    public static CommonProxy proxy;

    public ModularMachineryGreggening() {
        System.out.println("Initializing ModularMachineryGreggening...");
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        event.getModMetadata().version = VERSION;
        logger = event.getModLog();

        proxy.preInit(event);
        MinecraftForge.EVENT_BUS.register(this);

        // Advanced ME Input Bus network messages
        if (Mods.APPLIEDENERGISTICS.isPresent()) {
            INSTANCE.registerMessage(AdvancedMESettingsMessageHandler.class, AdvancedMESettingsMessage.class, 2, Side.SERVER);
            INSTANCE.registerMessage(AdvancedMERSyncMessageHandler.class, AdvancedMERSyncMessage.class, 3, Side.SERVER);
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

    @Mod.EventHandler
    public void serverStart(FMLServerStartingEvent serverStartEvent) {
        ModularMachineryGreggening.logger.info("MMCEG: Server starting");
    }
}
