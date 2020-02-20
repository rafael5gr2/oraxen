package io.th0rgal.oraxen.recipes.listeners;

import io.th0rgal.oraxen.OraxenPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.Map;

public class RecipesEventsManager implements Listener {

    private static RecipesEventsManager instance;
    private Map<Recipe, String> permissionsPerRecipe;

    public static RecipesEventsManager get() {
        if (instance == null) {
            instance = new RecipesEventsManager();
            Bukkit.getPluginManager().registerEvents(instance, OraxenPlugin.get());
        }
        return instance;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onCrafted(PrepareItemCraftEvent event) {
        Recipe recipe = event.getRecipe();
        Player player = (Player) event.getView().getPlayer();
        if (hasPermissions(player, recipe))
            return;
        event.getInventory().setResult(new ItemStack(Material.AIR));

    }

    public void addRecipe(Recipe recipe, String permission) {
        permissionsPerRecipe.put(recipe, permission);
    }

    private boolean hasPermissions(Player player, Recipe recipe) {
        return (!permissionsPerRecipe.containsKey(recipe) || player.hasPermission(permissionsPerRecipe.get(recipe)));
    }


}