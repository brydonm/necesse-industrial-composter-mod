package autosummon;

import necesse.engine.modLoader.annotations.ModEntry;

@ModEntry
public class AutoSummonMod {

    public void init() {
        System.out.println("Auto Summon Mod loaded!");
        System.out.println("Automatically summons using the rightmost staff in your hotbar.");
        
        // Initialize the control
        AutoSummonConfig.initControl();
    }

    public void initResources() {
        // No resources needed for this mod
    }

    public void postInit() {
        // No additional initialization needed
        System.out.println("Auto Summon Mod initialization complete!");
    }
}