package de.zemox.weapons;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.Instant;
import java.util.*;

public class ExplodingPickaxe implements Listener {
    public static ItemStack ExplodingItem;
    private final Map<UUID, Instant> cooldownMap = new HashMap<>();

    public static void init(){
        ExplodingPickaxe();
    }

    private static void ExplodingPickaxe() {
        ExplodingItem = new ItemStack(Material.STONE_PICKAXE);
        ItemMeta itemMeta = ExplodingItem.getItemMeta();

        itemMeta.setDisplayName(ChatColor.DARK_PURPLE + "Pickaxe of Explosions");
        List<String> lore = new ArrayList<>();
        lore.add("This Pickaxe will EXPLODE!");
        itemMeta.setLore(lore);
        itemMeta.setUnbreakable(true);
        itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        ExplodingItem.setItemMeta(itemMeta);
    }

    public void PickaxeExplode(Player player){
        Location location = player.getLocation();
        World world = player.getWorld();

        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        world.createExplosion(x, y, z, 30f, false, true);
        player.setHealth(20);

        cooldownMap.put(player.getUniqueId(), Instant.now());
    }

    @EventHandler
    public void PickaxeUse(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getItem() != null){
            if(event.getItem().getItemMeta().equals(ExplodingPickaxe.ExplodingItem.getItemMeta())){
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR){
                    if(!cooldownMap.containsKey(player.getUniqueId())){
                        PickaxeExplode(player);
                        event.setCancelled(true);
                    }
                    else{
                        Instant lastCast = cooldownMap.get(player.getUniqueId());
                        Instant currentTime = Instant.now();

                        int cooldownTime = 100;

                        if (currentTime.isAfter(lastCast.plusSeconds(cooldownTime))) {
                            PickaxeExplode(player);
                            event.setCancelled(true);
                            return;
                        }

                        player.playSound(player.getLocation(),Sound.BLOCK_DISPENSER_FAIL,1,3);
                        player.sendMessage(ChatColor.LIGHT_PURPLE + "Exploding Cooldown...");
                    }
                }
            }
        }
    }
}
