package de.zemox.weapons;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.addRecipe(getRecipe());
        KnockbackWeapon.init();
        HealerWeapon.init();
        SlamWeapon.init();
        ExplodingPickaxe.init();
        GrapplerBow.init();
        this.getCommand("knockbackWeapon").setExecutor(new Commands());
        this.getCommand("healerWeapon").setExecutor(new Commands());
        this.getCommand("slamWeapon").setExecutor(new Commands());
        this.getCommand("explodingPickaxe").setExecutor(new Commands());
        this.getCommand("grapplerBow").setExecutor(new Commands());
        this.getCommand("openGui").setExecutor(new Commands());
        this.getServer().getPluginManager().registerEvents(new KnockbackWeapon(), this);
        this.getServer().getPluginManager().registerEvents(new HealerWeapon(), this);
        this.getServer().getPluginManager().registerEvents(new SlamWeapon(), this);
        this.getServer().getPluginManager().registerEvents(new ExplodingPickaxe(), this);
        this.getServer().getPluginManager().registerEvents(new SlayerQuest(), this);
        this.getServer().getPluginManager().registerEvents(new GrapplerBow(this), this);
        snowballTrailCreator();
    }


    private void snowballTrailCreator(){

        new BukkitRunnable(){

            @Override
            public void run(){
                if(Bukkit.getOnlinePlayers().size() == 0) return;

                for(Player player:Bukkit.getOnlinePlayers()){
                    World world = player.getWorld();

                    for(Entity snowballs : world.getEntitiesByClass(Snowball.class)){
                        if(!snowballs.isDead() && !snowballs.isOnGround()){
                            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.WHITE, 2);
                            player.spawnParticle(Particle.REDSTONE, snowballs.getLocation(), 10, dustOptions);
                        }
                    }
                }
            }
        }.runTaskTimer(this, 0, 0);
    }

    public ShapedRecipe getRecipe(){
        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName(ChatColor.DARK_PURPLE + "Epic Pickaxe");
        itemMeta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 10, true);
        itemMeta.addEnchant(Enchantment.DIG_SPEED, 9, true);

        item.setItemMeta(itemMeta);

        NamespacedKey key = new NamespacedKey(this, "epicpick");

        ShapedRecipe recipe = new ShapedRecipe(key, item);

        recipe.shape("EEE", " T ", " T ");

        recipe.setIngredient('T', Material.EMERALD_BLOCK);
        recipe.setIngredient('E', Material.NETHER_STAR);


        return recipe;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
