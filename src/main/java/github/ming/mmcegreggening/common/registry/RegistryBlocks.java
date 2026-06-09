package github.ming.mmcegreggening.common.registry;

import github.ming.mmcegreggening.ModularMachineryGreggening;
import github.ming.mmcegreggening.common.Mods;
import github.ming.mmcegreggening.common.hatch.appeng.itembus.BlockAdvancedMEItemInputBus;
import github.ming.mmcegreggening.common.hatch.appeng.itembus.AdvancedMEItemInputBus;
import github.kasuminova.mmce.common.block.appeng.BlockMEMachineComponent;
import hellfirepvp.modularmachinery.common.block.BlockCustomName;
import hellfirepvp.modularmachinery.common.block.BlockMachineComponent;
import hellfirepvp.modularmachinery.common.item.ItemBlockCustomName;
import hellfirepvp.modularmachinery.common.item.ItemBlockMEMachineComponent;
import hellfirepvp.modularmachinery.common.item.ItemBlockMachineComponent;
import hellfirepvp.modularmachinery.common.item.ItemBlockMachineComponentCustomName;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

// This class was adapted from a similar class in MMCE
public class RegistryBlocks {
    private static final List<Block> blockModelRegister = new ArrayList<>();

    public static final List<Block> BLOCKS = new ArrayList<>();

    public static void initialise() {
        registerBlocks();
        registerTileEntities();
        registerBlockModels();
    }

    private static void registerBlocks() {


        // Advanced ME Item Input Bus (AE2)
        if (Mods.APPLIEDENERGISTICS.isPresent()) {
            ModularMachineryGreggeningBlocks.blockAdvancedMEItemInputBus = prepareRegister(new BlockAdvancedMEItemInputBus());
            prepareItemBlockRegister(ModularMachineryGreggeningBlocks.blockAdvancedMEItemInputBus);
        }
    }

    private static void registerTileEntities() {

        // Advanced ME Item Input Bus (AE2)
        if (Mods.APPLIEDENERGISTICS.isPresent()) {
            registerTileEntity(AdvancedMEItemInputBus.class);
        }
    }

    /**
     * Builds the tile path given the class name. It removes the package name and concatenates the base class name with the nested, static class' name.
     * For example:
     *  main class: TileRadiationProvider
     *  nested class: Input
     *  canonical name: {packageName}.TileRadiationProvider$Input
     *  output: tileradiationproviderinput
     */
    private static String buildPathForClass(Class<? extends TileEntity> clazz) {
        return clazz.getCanonicalName().replace(clazz.getPackage().getName(), "").replace(".", "").toLowerCase();
    }

    private static void registerTileEntity(Class<? extends TileEntity> entityClass) {
        ModularMachineryGreggening.logger.info("Registering TileEntity: " + entityClass);
        GameRegistry.registerTileEntity(entityClass, new ResourceLocation(ModularMachineryGreggening.MODID, buildPathForClass(entityClass)));
    }

    private static void registerBlockModels() {
        for (Block block : blockModelRegister) {
            ModularMachineryGreggening.proxy.registerBlockModel(block); // If on client side, will call ClientProxy.registerBlockModel
        }
    }

    // Copied from the MMCE code, credits to the original authors
    private static void prepareItemBlockRegister(Block block) {
        if (block instanceof BlockMachineComponent) {
            if (block instanceof BlockMEMachineComponent) {
                prepareItemBlockRegister(new ItemBlockMEMachineComponent(block));
            } else if (block instanceof BlockCustomName) {
                prepareItemBlockRegister(new ItemBlockMachineComponentCustomName(block));
            } else {
                prepareItemBlockRegister(new ItemBlockMachineComponent(block));
            }
        } else {
            if (block instanceof BlockCustomName) {
                prepareItemBlockRegister(new ItemBlockCustomName(block));
            } else {
                prepareItemBlockRegister(new ItemBlock(block));
            }
        }
    }

    // Copied from the MMCE code, credits to the original authors
    private static <T extends ItemBlock> void prepareItemBlockRegister(T item) {
        String name = item.getBlock().getClass().getSimpleName().toLowerCase();
        item.setRegistryName(ModularMachineryGreggening.MODID, name).setTranslationKey(ModularMachineryGreggening.MODID + '.' + name);

        ForgeRegistries.ITEMS.register(item);

        // Queue the ItemBlock for client-side model registration
        ModularMachineryGreggening.proxy.registerItemModel(item);
    }

    // Copied from the MMCE code, credits to the original authors
    private static <T extends Block> T prepareRegister(T block) {
        String name = block.getClass().getSimpleName().toLowerCase();
        block.setRegistryName(ModularMachineryGreggening.MODID, name).setTranslationKey(ModularMachineryGreggening.MODID + '.' + name);
        BLOCKS.add(block);

        return prepareRegisterWithCustomName(block);
    }

    // Copied from the MMCE code, credits to the original authors
    private static <T extends Block> T prepareRegisterWithCustomName(T block) {
        blockModelRegister.add(block);
        return block;
    }

}
