package net.plaaasma.vortexmod.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.plaaasma.vortexmod.VortexMod;
import net.plaaasma.vortexmod.block.ModBlocks;
import net.plaaasma.vortexmod.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, VortexMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.THROTTLE_BLOCK.get())
                .add(ModBlocks.COORDINATE_BLOCK.get())
                .add(ModBlocks.KEYPAD_BLOCK.get())
                .add(ModBlocks.SIZE_MANIPULATOR_BLOCK.get())
                .add(ModBlocks.EQUALIZER_BLOCK.get())
                .add(ModBlocks.DOOR_BLOCK.get())
                .add(ModBlocks.SCANNER_BLOCK.get())
                .add(ModBlocks.GROUNDING_BLOCK.get())
                .add(ModBlocks.BIOMETRIC_BLOCK.get())
                .add(ModBlocks.TARDIS_SIGN_BLOCK.get());

        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.THROTTLE_BLOCK.get())
                .add(ModBlocks.COORDINATE_BLOCK.get())
                .add(ModBlocks.KEYPAD_BLOCK.get())
                .add(ModBlocks.EQUALIZER_BLOCK.get())
                .add(ModBlocks.DOOR_BLOCK.get())
                .add(ModBlocks.SCANNER_BLOCK.get())
                .add(ModBlocks.GROUNDING_BLOCK.get())
                .add(ModBlocks.BIOMETRIC_BLOCK.get())
                .add(ModBlocks.TARDIS_SIGN_BLOCK.get());

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.THROTTLE_BLOCK.get())
                .add(ModBlocks.COORDINATE_BLOCK.get())
                .add(ModBlocks.KEYPAD_BLOCK.get())
                .add(ModBlocks.SIZE_MANIPULATOR_BLOCK.get())
                .add(ModBlocks.EQUALIZER_BLOCK.get())
                .add(ModBlocks.DOOR_BLOCK.get())
                .add(ModBlocks.SCANNER_BLOCK.get())
                .add(ModBlocks.GROUNDING_BLOCK.get())
                .add(ModBlocks.BIOMETRIC_BLOCK.get())
                .add(ModBlocks.TARDIS_SIGN_BLOCK.get());
    }
}
