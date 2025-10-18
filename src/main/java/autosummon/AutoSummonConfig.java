package autosummon;

/**
 * Configuration class for the auto summon mod.
 * Modify these values to adjust the summoning behavior.
 */
public class AutoSummonConfig {
    
    // Main configuration values
    public static final long SUMMON_CHECK_COOLDOWN = 250; // ms. How often to check for summoning (4 times/sec)
    
    // Runtime toggle state
    private static boolean isEnabled = true;
    private static boolean needsChatMessage = false;
    
    // Control for toggling auto-summon
    public static necesse.engine.input.Control TOGGLE_AUTO_SUMMON;
    
    /**
     * Check if the mod is enabled
     */
    public static boolean isEnabled() {
        return isEnabled;
    }
    
    /**
     * Toggle the mod on/off
     */
    public static void toggle() {
        isEnabled = !isEnabled;
    }
    
    /**
     * Set flag that chat message is needed
     */
    public static void setNeedsChatMessage(boolean needs) {
        needsChatMessage = needs;
    }
    
    /**
     * Check if chat message is needed and clear the flag
     */
    public static boolean checkAndClearNeedsChatMessage() {
        if (needsChatMessage) {
            needsChatMessage = false;
            return true;
        }
        return false;
    }
    
    /**
     * Initialize the control
     */
    public static void initControl() {
        TOGGLE_AUTO_SUMMON = necesse.engine.input.Control.addModControl(
            new AutoSummonControl() // F9 key
        );
    }
    
    /**
     * Get the summon check cooldown
     */
    public static long getSummonCheckCooldown() {
        return SUMMON_CHECK_COOLDOWN;
    }
}
