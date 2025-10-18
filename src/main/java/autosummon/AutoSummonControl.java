package autosummon;

import necesse.engine.input.Control;
import necesse.engine.input.InputEvent;
import necesse.engine.localization.message.GameMessage;
import necesse.engine.localization.message.StaticMessage;

/**
 * Custom control for toggling auto-summon that handles the toggle logic directly
 */
public class AutoSummonControl extends Control {
    
    public AutoSummonControl() {
        super(necesse.engine.input.InputID.KEY_F9, "toggleautosummon", new StaticMessage("Toggle Auto Summon"));
    }
    
    @Override
    public void activate(InputEvent event) {
        // Only handle key press events (not releases)
        if (event.state) {
            // Toggle the mod
            AutoSummonConfig.toggle();
            
            // Set a flag that the patch can check to send the chat message
            AutoSummonConfig.setNeedsChatMessage(true);
        }
        
        // Call the parent activate method to handle the normal control behavior
        super.activate(event);
    }
}
