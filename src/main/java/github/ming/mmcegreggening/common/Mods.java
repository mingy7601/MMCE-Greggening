package github.ming.mmcegreggening.common;

import net.minecraftforge.fml.common.Loader;

public enum Mods {
    APPLIEDENERGISTICS("appliedenergistics2")
    ;

    // Used in annotations, which require compile-time constants. Yes, it's redundant. But it stil reduces boilerplate code
    public static final String APPLIEDENERGISTICS_ID = "appliedenergistics2";

    public final String modid;
    private final boolean loaded;

    Mods(String modName) {
        this.modid = modName;
        this.loaded = Loader.isModLoaded(this.modid);
    }

    public boolean isPresent() {
        return loaded;
    }
}