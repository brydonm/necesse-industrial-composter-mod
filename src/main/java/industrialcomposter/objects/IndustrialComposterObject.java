package industrialcomposter.objects;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import necesse.engine.gameLoop.tickManager.TickManager;
import necesse.engine.localization.Localization;
import necesse.engine.registries.ContainerRegistry;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.objectEntity.ObjectEntity;
import necesse.entity.objectEntity.ProcessingTechInventoryObjectEntity;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.LevelSortedDrawable;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.inventory.InventoryItem;
import necesse.inventory.InventoryRange;
import necesse.inventory.container.object.OEInventoryContainer;
import necesse.inventory.item.toolItem.ToolType;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;
import necesse.level.gameObject.GameObject;
import necesse.level.gameObject.ObjectDamagedTextureArray;
import necesse.level.maps.Level;
import necesse.level.maps.levelData.settlementData.SettlementWorkstationObject;
import necesse.level.maps.light.GameLight;
import industrialcomposter.objectentity.IndustrialComposterObjectEntity;

public class IndustrialComposterObject extends GameObject implements SettlementWorkstationObject {
    public ObjectDamagedTextureArray texture;

    public IndustrialComposterObject() {
        // 1x1 object but visually scaled to 2x size for industrial appearance
        super(new Rectangle(4, 6, 24, 20));
        this.setItemCategory(new String[]{"objects", "craftingstations"});
        this.setCraftingCategory(new String[]{"craftingstations"});
        this.mapColor = new Color(80, 60, 20);
        this.displayMapTooltip = true;
        this.toolType = ToolType.ALL;
        this.objectHealth = 100;
        this.isLightTransparent = true;
        this.hoverHitbox = new Rectangle(0, -16, 32, 48);
        this.replaceCategories.add("workstation");
        this.canReplaceCategories.add("workstation");
        this.canReplaceCategories.add("wall");
        this.canReplaceCategories.add("furniture");
    }

    public void loadTextures() {
        super.loadTextures();
        this.texture = ObjectDamagedTextureArray.loadAndApplyOverlay(this, "objects/industrialcomposter");
    }

    public void addDrawables(List<LevelSortedDrawable> list, OrderableDrawables tileList, Level level, int tileX, int tileY, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        GameLight light = level.getLightLevel(tileX, tileY);
        int rotation = level.getObjectRotation(tileX, tileY);
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        GameTexture texture = this.texture.getDamagedTexture(this, level, tileX, tileY);
        
        // Draw at normal size (same as standard compost bin)
        final TextureDrawOptions options = texture.initDraw()
            .sprite(rotation % texture.getWidth() / 32, 0, 32, texture.getHeight())
            .light(light)
            .pos(drawX, drawY - (texture.getHeight() - 32));
            
        list.add(new LevelSortedDrawable(this, tileX, tileY) {
            public int getSortY() {
                return 16;
            }

            public void draw(TickManager tickManager) {
                options.draw();
            }
        });
    }

    public void drawPreview(Level level, int tileX, int tileY, int rotation, float alpha, PlayerMob player, GameCamera camera) {
        int drawX = camera.getTileDrawX(tileX);
        int drawY = camera.getTileDrawY(tileY);
        GameTexture texture = this.texture.getDamagedTexture(0.0F);
        texture.initDraw()
            .sprite(rotation % texture.getWidth() / 32, 0, 32, texture.getHeight())
            .alpha(alpha)
            .draw(drawX, drawY - (texture.getHeight() - 32));
    }

    public String getInteractTip(Level level, int x, int y, PlayerMob perspective, boolean debug) {
        return Localization.translate("controls", "opentip");
    }

    public boolean canInteract(Level level, int x, int y, PlayerMob player) {
        return true;
    }

    public void interact(Level level, int x, int y, PlayerMob player) {
        super.interact(level, x, y, player);
        if (level.isServer()) {
            OEInventoryContainer.openAndSendContainer(ContainerRegistry.PROCESSING_INVENTORY_CONTAINER, player.getServerClient(), level, x, y);
        }
    }

    public ObjectEntity getNewObjectEntity(Level level, int x, int y) {
        return new IndustrialComposterObjectEntity(level, x, y);
    }

    public ProcessingTechInventoryObjectEntity getProcessingObjectEntity(Level level, int tileX, int tileY) {
        ObjectEntity objectEntity = level.entityManager.getObjectEntity(tileX, tileY);
        return objectEntity instanceof ProcessingTechInventoryObjectEntity ? (ProcessingTechInventoryObjectEntity)objectEntity : null;
    }

    public Stream<Recipe> streamSettlementRecipes(Level level, int tileX, int tileY) {
        ProcessingTechInventoryObjectEntity processingOE = this.getProcessingObjectEntity(level, tileX, tileY);
        return processingOE != null ? Recipes.streamRecipes(processingOE.techs) : Stream.empty();
    }

    public boolean isProcessingInventory(Level level, int tileX, int tileY) {
        return true;
    }

    public boolean canCurrentlyCraft(Level level, int tileX, int tileY, Recipe recipe) {
        ProcessingTechInventoryObjectEntity processingOE = this.getProcessingObjectEntity(level, tileX, tileY);
        if (processingOE != null) {
            return processingOE.getExpectedResults().crafts < 15;
        } else {
            return false;
        }
    }

    public int getMaxCraftsAtOnce(Level level, int tileX, int tileY, Recipe recipe) {
        return 10; // Allow more crafts at once since it's faster
    }

    public InventoryRange getProcessingInputRange(Level level, int tileX, int tileY) {
        ProcessingTechInventoryObjectEntity processingOE = this.getProcessingObjectEntity(level, tileX, tileY);
        return processingOE != null ? processingOE.getInputInventoryRange() : null;
    }

    public InventoryRange getProcessingOutputRange(Level level, int tileX, int tileY) {
        ProcessingTechInventoryObjectEntity processingOE = this.getProcessingObjectEntity(level, tileX, tileY);
        return processingOE != null ? processingOE.getOutputInventoryRange() : null;
    }

    public ArrayList<InventoryItem> getCurrentAndFutureProcessingOutputs(Level level, int tileX, int tileY) {
        ProcessingTechInventoryObjectEntity processingOE = this.getProcessingObjectEntity(level, tileX, tileY);
        return processingOE != null ? processingOE.getCurrentAndExpectedResults().items : new ArrayList();
    }

    public ListGameTooltips getItemTooltips(InventoryItem item, PlayerMob perspective) {
        ListGameTooltips tooltips = super.getItemTooltips(item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "industrialcompostertip"));
        return tooltips;
    }

    protected boolean shouldPlayInteractSound(Level level, int tileX, int tileY) {
        return true;
    }
}

