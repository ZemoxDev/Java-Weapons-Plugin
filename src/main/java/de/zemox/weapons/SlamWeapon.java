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
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.time.Instant;
import java.util.*;

public class SlamWeapon implements Listener {

    public static ItemStack SlamItem;
    private final Map<UUID, Instant> cooldownMap = new HashMap<>();

    public static void init(){
        SlamWeapon();
    }

    private static void SlamWeapon() {
        SlamItem = new ItemStack(Material.STONE_SWORD);
        ItemMeta itemMeta = SlamItem.getItemMeta();

        itemMeta.setDisplayName(ChatColor.GREEN + "Ancient Sword of Earthquackes");
        List<String> lore = new ArrayList<>();
        lore.add("This ancient sword can launch you up in the air, as soon as you come down again there will be destruction!");
        itemMeta.setLore(lore);
        itemMeta.setUnbreakable(true);
        itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        SlamItem.setItemMeta(itemMeta);
    }

    public void SlamSlam(Player player){
        double alpha = 0;
        World world = player.getWorld();
        player.setVelocity(new Vector(0, 1.4, 0));
        alpha += Math.PI / 16;
        Location location = player.getLocation();
        Location firstLocation = location.clone().add( Math.cos(alpha), Math.sin(alpha) + 1, Math.sin(alpha));
        Location secondLocation = location.clone().add( Math.cos( alpha + Math.PI ), Math.sin( alpha ) + 1, Math.sin(alpha + Math.PI));
        Location thirdLocation = location.clone().add( Math.cos(alpha), Math.sin(alpha) + 1, Math.sin(alpha));
        Location fourthLocation = location.clone().add( Math.cos( alpha + Math.PI ), Math.sin( alpha ) + 1, Math.sin(alpha + Math.PI));
        player.spawnParticle( Particle.FLAME, firstLocation, 0, 0, 0, 0, 0 );
        player.spawnParticle( Particle.FLAME, secondLocation, 0, 0, 0, 0, 0 );
        player.spawnParticle( Particle.FLAME, thirdLocation, 0, 5, 0, 1, 0 );
        player.spawnParticle( Particle.FLAME, fourthLocation, 0, 5, 0, -1, 0 );
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        world.createExplosion(x, y, z, .1f, false, false);

        cooldownMap.put(player.getUniqueId(), Instant.now());
    }

    @EventHandler
    public void SlamUse(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getItem() != null) {
            if (event.getItem().getItemMeta().equals(SlamWeapon.SlamItem.getItemMeta())) {
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR){
                    if(!cooldownMap.containsKey(player.getUniqueId())){
                        SlamSlam(player);
                        event.setCancelled(true);
                    }
                    else{
                        Instant lastCast = cooldownMap.get(player.getUniqueId());
                        Instant currentTime = Instant.now();

                        int cooldownTime = 10;

                        if (currentTime.isAfter(lastCast.plusSeconds(cooldownTime))) {
                            SlamSlam(player);
                            event.setCancelled(true);
                            return;
                        }

                        player.playSound(player.getLocation(),Sound.BLOCK_DISPENSER_FAIL,3,0);
                        player.sendMessage(ChatColor.GREEN + "Slamming Cooldown...");
                    }
                }
            }
        }
    }
}
