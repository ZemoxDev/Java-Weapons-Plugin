package de.zemox.weapons;

import com.sun.javafx.event.EventHandlerManager;
import org.apache.commons.lang.Validate;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class GrapplerBow implements Listener {
    public static ItemStack GrapplerItem;
    private Main plugin;

    public GrapplerBow(Main plugin){
        this.plugin = plugin;
    }

    public static void init(){
        GrapplerBow();
    }

    private static void GrapplerBow() {
        GrapplerItem = new ItemStack(Material.BOW);
        ItemMeta itemMeta = GrapplerItem.getItemMeta();

        itemMeta.setDisplayName(ChatColor.YELLOW + "GrapplingBow");
        List<String> lore = new ArrayList<>();
        lore.add("This Bow will Launch you towards where you shoot!");
        itemMeta.setLore(lore);
        itemMeta.setUnbreakable(true);
        itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        GrapplerItem.setItemMeta(itemMeta);
    }

    @EventHandler
    public void grappleEvent(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            Arrow arrow = (Arrow) event.getProjectile();
            arrow.setVelocity(arrow.getVelocity().multiply(2D));

            new BukkitRunnable() {
                @Override
                public void run() {
                    if(event.getBow().getItemMeta().equals(GrapplerBow.GrapplerItem.getItemMeta())){
                        if (arrow.getLocation().distance(player.getLocation()) > 30) {
                            arrow.remove();
                            this.cancel();
                        }

                        if (player.isSneaking()) {
                            player.setVelocity(player.getLocation().getDirection().multiply(2D));
                            arrow.remove();
                            this.cancel();
                        }

                        if (arrow.isOnGround() && !arrow.isDead()) {
                            player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 2);
                            player.spawnParticle(Particle.FIREWORKS_SPARK, player.getLocation(), 3);
                            Vector direction = arrow.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
                            player.setVelocity(direction.multiply(1.2D));
                            drawline(player.getLocation(), arrow.getLocation(), 2);
                            if (player.getLocation().distance(arrow.getLocation()) <= 3) {
                                this.cancel();
                                arrow.remove();
                            }
                        }
                    }
                }
            }.runTaskTimer(plugin, 0, 0);
        }
    }

    private void drawline(Location point1, Location point2, double space) {
        World world = point1.getWorld();
        Validate.isTrue(point2.getWorld().equals(world), "Particles can not be drawn in diffrent worlds!");

        double distance = point1.distance(point2);

        Vector p1 = point1.toVector();
        Vector p2 = point2.toVector();

        Vector vector = p2.clone().subtract(p1).normalize().multiply(space);

        double covered = 0;
    }


}
