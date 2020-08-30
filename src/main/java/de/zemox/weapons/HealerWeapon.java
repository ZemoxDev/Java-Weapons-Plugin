package de.zemox.weapons;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.Instant;
import java.util.*;

public class HealerWeapon implements Listener {

    public static ItemStack HealerItem;
    private final Map<UUID, Instant> cooldownMap = new HashMap<>();

    public static void init(){
        HealerWeapon();
    }

    private static void HealerWeapon() {
        HealerItem = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta itemMeta = HealerItem.getItemMeta();

        itemMeta.setDisplayName(ChatColor.DARK_PURPLE + "Healing Sword of Legends");
        List<String> lore = new ArrayList<>();
        lore.add("This sword can heal you!");
        itemMeta.setLore(lore);
        itemMeta.setUnbreakable(true);
        itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        HealerItem.setItemMeta(itemMeta);
    }

    public void SwordHeal(Player player){
        player.playSound(player.getLocation(), Sound.ENCHANT_THORNS_HIT, .3f, 3);
        player.spawnParticle(Particle.HEART, player.getLocation().add(0, 1.5, 0), 1);
        player.setHealth(20);

        cooldownMap.put(player.getUniqueId(), Instant.now());
    }

    @EventHandler
    public void SwordUse(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getItem() != null){
            if(event.getItem().getItemMeta().equals(HealerWeapon.HealerItem.getItemMeta())){
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR){
                    if(!cooldownMap.containsKey(player.getUniqueId())){
                        SwordHeal(player);
                        event.setCancelled(true);
                    }
                    else{
                        Instant lastCast = cooldownMap.get(player.getUniqueId());
                        Instant currentTime = Instant.now();

                        int cooldownTime = 30;

                        if (currentTime.isAfter(lastCast.plusSeconds(cooldownTime))) {
                            SwordHeal(player);
                            event.setCancelled(true);
                            return;
                        }

                        player.playSound(player.getLocation(),Sound.BLOCK_DISPENSER_FAIL,2,5);
                        player.sendMessage(ChatColor.DARK_RED + "Healing Cooldown...");
                    }
                }
            }
        }
    }
}
