package github.ming.mmcegreggening;

import github.ming.mmcegreggening.common.hatch.appeng.itembus.AdvancedMEItemInputBus;
import github.ming.mmcegreggening.common.hatch.appeng.itembus.ContainerAdvancedMEItemInputBus;
import github.ming.mmcegreggening.common.registry.ModularMachineryGreggeningBlocks;
import github.ming.mmcegreggening.common.registry.internal.EventHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import javax.annotation.Nullable;

/**
 * CommonProxy handles shared mod-side logic for the server and client.
 */
public class CommonProxy implements IGuiHandler {
    public static CreativeTabs creativeTabModularMachineryGreggening;

    private static boolean preInitCalled = false;

    public void preInit(FMLPreInitializationEvent event) {
        // Prevent duplicate calls
        if (preInitCalled) return;
        preInitCalled = true;

        ModularMachineryGreggening.logger.info("CommonProxy: Running preInit");

        MinecraftForge.EVENT_BUS.register(new EventHandler());

        NetworkRegistry.INSTANCE.registerGuiHandler(ModularMachineryGreggening.MODID, this);

        ModularMachineryGreggeningBlocks.initialise();
    }

    public void init() {
    }

    public void registerItemModel(net.minecraft.item.Item item) {}

    public void registerBlockModel(net.minecraft.block.Block block) {}

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        GuiType type = CommonProxy.GuiType.values()[MathHelper.clamp(ID, 0, CommonProxy.GuiType.values().length - 1)];
        Class<? extends TileEntity> required = type.requiredTileEntity;
        TileEntity present = null;
        if (required != null) {
            TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
            if (te == null || !required.isAssignableFrom(te.getClass())) {
                return null;
            }

            present = te;
        }
        if (type == GuiType.ADVANCED_ME_INPUT_BUS) {
            return new ContainerAdvancedMEItemInputBus((AdvancedMEItemInputBus) present, player);
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }


    public enum GuiType {

        ADVANCED_ME_INPUT_BUS(AdvancedMEItemInputBus.class);

        public final Class<? extends TileEntity> requiredTileEntity;

        GuiType(@Nullable Class<? extends TileEntity> requiredTileEntity) {
            this.requiredTileEntity = requiredTileEntity;
        }
    }
}