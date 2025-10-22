package industrialcomposter.objectentity;

import necesse.entity.objectEntity.CompostBinObjectEntity;
import necesse.inventory.InventoryRange;
import necesse.level.maps.Level;

public class IndustrialComposterObjectEntity extends CompostBinObjectEntity {
    
    public IndustrialComposterObjectEntity(Level level, int x, int y) {
        super(level, x, y);
    }

    @Override
    public int getProcessTime() {
        return super.getProcessTime() / 4;
    }
    
    @Override
    public InventoryRange getInputInventoryRange() {
        return new InventoryRange(this.inventory, 0, 3);
    }
}

