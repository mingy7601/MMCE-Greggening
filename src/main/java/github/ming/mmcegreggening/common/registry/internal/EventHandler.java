package github.ming.mmcegreggening.common.registry.internal;

import github.ming.mmcegreggening.ModularMachineryGreggening;
import github.ming.mmcegreggening.common.registry.RegistryBlocks;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

// This class was adapted from a similar class in MMCE
public class EventHandler {
    @SubscribeEvent
    public void onBlockRegister(RegistryEvent.Register<Block> event) {
        informAboutRegistrationFor(event);

        RegistryBlocks.initialise();

        event.getRegistry().registerAll(RegistryBlocks.BLOCKS.toArray(new Block[0]));
    }

    private static void informAboutRegistrationFor(RegistryEvent.Register<?> event) {
        String registryName = event.getRegistry().getRegistrySuperType().getSimpleName();

        ModularMachineryGreggening.logger.info("Registering " + registryName + "...");
    }
}
