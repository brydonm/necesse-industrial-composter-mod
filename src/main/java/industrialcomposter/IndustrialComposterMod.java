package industrialcomposter;

import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.registries.ObjectRegistry;
import necesse.engine.registries.RecipeTechRegistry;
import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;
import industrialcomposter.objects.IndustrialComposterObject;

@ModEntry
public class IndustrialComposterMod {

	public void init() {
		System.out.println("Industrial Composter Mod loaded!");
		
		ObjectRegistry.registerObject("industrialcomposter", new IndustrialComposterObject(), 20.0F, true);
	}

	public void initResources() {
		// Resources including localization files are automatically loaded by Necesse
	}

	public void postInit() {
		System.out.println("Industrial Composter Mod initialization complete!");
		
		Recipes.registerModRecipe(new Recipe(
			"industrialcomposter",
			1,
			RecipeTechRegistry.WORKSTATION,
			new Ingredient[]{
				new Ingredient("compostbin", 4),
				new Ingredient("ironbar", 10),
				new Ingredient("stone", 20)
			}
		).showAfter("compostbin"));
	}
}

