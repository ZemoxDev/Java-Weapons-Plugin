package de.zemox.weapons;

import com.sun.media.jfxmedia.events.PlayerEvent;
import org.bukkit.*;
import org.bukkit.block.Skull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.time.Instant;
import java.util.*;

public class KnockbackWeapon implements Listener {

    public static ItemStack KnockbackItem;
    private final Map<UUID, Instant> cooldownMap = new HashMap<>();

    public static void init() {
        KnockbackWeapon();
    }

    private static void KnockbackWeapon() {
        KnockbackItem = new ItemStack(Material.STICK);
        ItemMeta itemMeta = KnockbackItem.getItemMeta();

        itemMeta.setDisplayName(ChatColor.BLUE + "Knockback Stick");
        List<String> lore = new ArrayList<>();
        lore.add("This stick can launch you!");
        itemMeta.setLore(lore);
        itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        KnockbackItem.setItemMeta(itemMeta);
    }

    private void Shoot(Player player) {
        if (!player.isSneaking()) {
            Vector direction = player.getLocation().getDirection();
            player.setVelocity(direction.add((new Vector(0, -.2, 0))).multiply(-2D));
        }
        player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, .5f, 1);
        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.GRAY, 2);
        player.spawnParticle(Particle.REDSTONE, player.getLocation().add(0, 1, 0), 10, .5, .5, .5, dustOptions);

        Snowball snowball = player.launchProjectile(Snowball.class, player.getLocation().getDirection().multiply(4D));
        cooldownMap.put(player.getUniqueId(), Instant.now());
    }


    @EventHandler
    public void knockbackShoot(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getItem() != null)
            if (event.getItem().getItemMeta().equals(KnockbackWeapon.KnockbackItem.getItemMeta())) {
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
                    if(!cooldownMap.containsKey(player.getUniqueId())){
                        Shoot(player);
                        event.setCancelled(true);
                    }
                    else{
                        Instant lastCast = cooldownMap.get(player.getUniqueId());
                        Instant currentTime = Instant.now();

                        int reloadTime = 3;

                        if (currentTime.isAfter(lastCast.plusSeconds(reloadTime))) {
                            Shoot(player);
                            event.setCancelled(true);
                            return;
                        }

                        player.playSound(player.getLocation(),Sound.BLOCK_DISPENSER_FAIL,1,1);
                        player.sendMessage(ChatColor.GRAY + "Shooting Cooldown...");
                    }
                }
            }
        }
}


