package de.zemox.weapons;

import com.sun.tracing.dtrace.StabilityLevel;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.*;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SlayerQuest implements Listener {

    private Main plugin;
    public int killCount = 0;
    public int spiderobj = 0;
    public int zombieobj = 0;
    public int skeletonobj = 0;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        createScoreBoard(event.getPlayer());
    }

    public void createScoreBoard(Player player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("MapScoreboard", "dummy", ChatColor.RED + " Welcome ");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score score = objective.getScore(" ");
        score.setScore(4);
        Score score1 = objective.getScore(ChatColor.GRAY + "Your Kills: " + ChatColor.RED + killCount + "/40");
        score1.setScore(3);
        Score score3 = objective.getScore(" ");
        score3.setScore(2);
        Score score4 = objective.getScore(ChatColor.GRAY + "Online Player: " + Bukkit.getOnlinePlayers().size());
        score4.setScore(1);

        player.setScoreboard(board);
    }


    @EventHandler
    public void onDeath(EntityDeathEvent event){
        if(event.getEntity() instanceof Spider) {
            Spider spider = (Spider) event.getEntity();
            if(spider.getKiller() != null) {
                if(spiderobj >= 1) {
                    Player player = spider.getKiller();
                    World world = player.getWorld();
                    Location location = player.getLocation();
                    int x = location.getBlockX();
                    int y = location.getBlockY();
                    int z = location.getBlockZ();
                    createScoreBoard(player);

                    if(spiderobj == 1) {
                        killCount += 1;
                        if(killCount == 10) {
                            player.sendMessage(ChatColor.DARK_GREEN + "Well done you killed 10 spiders 30 more to go!");
                        }
                        if(killCount == 20) {
                            player.sendMessage(ChatColor.DARK_GREEN + "Well done you killed 20 spiders 20 more to go!");
                        }
                        if(killCount == 30) {
                            player.sendMessage(ChatColor.DARK_GREEN + "Well done you killed 30 spiders 10 more to go!");
                        }
                        if(killCount == 41) {
                            player.sendMessage(ChatColor.DARK_GREEN + "Wow you did it you killed all 40 spiders, you now have to kill the broodmother to get your reward!");
                            world.createExplosion(x, y, z, 10f, false, false);

                            Spider spider1 = (Spider) location.getWorld().spawnEntity(location, EntityType.SPIDER);
                            spider1.setCustomName(ChatColor.DARK_GREEN + "The Broodmother");
                            spider1.setCustomNameVisible(true);
                            killCount = 0;
                            spiderobj = 0;
                            zombieobj = 0;
                            skeletonobj = 0;
                        }
                    }
                }
            }
        }
        if(event.getEntity() instanceof Zombie) {
            Zombie zombie = (Zombie) event.getEntity();
            if(zombie.getKiller() != null) {
                if(zombieobj >= 1) {
                    Player player = zombie.getKiller();
                    World world = player.getWorld();
                    Location location = player.getLocation();
                    int x = location.getBlockX();
                    int y = location.getBlockY();
                    int z = location.getBlockZ();
                    createScoreBoard(player);

                    if(zombieobj == 1) {
                        killCount += 1;
                        if(killCount == 10) {
                            player.sendMessage(ChatColor.DARK_RED + "Well done you killed 10 zombies 30 more to go!");
                        }
                        if(killCount == 20) {
                            player.sendMessage(ChatColor.DARK_RED + "Well done you killed 20 zombies 20 more to go!");
                        }
                        if(killCount == 30) {
                            player.sendMessage(ChatColor.DARK_RED + "Well done you killed 30 zombies 10 more to go!");
                        }
                        if(killCount == 41) {
                            player.sendMessage(ChatColor.DARK_RED + "Wow you did it you killed all 40 zombies, you now have to kill the Zombie King to get your reward!");
                            world.createExplosion(x, y, z, 10f, false, false);

                            Zombie zombie1 = (Zombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
                            zombie1.setCustomName(ChatColor.DARK_GREEN + "The Zombie King");
                            zombie1.setCustomNameVisible(true);
                            killCount = 0;
                            spiderobj = 0;
                            zombieobj = 0;
                            skeletonobj = 0;
                        }
                    }
                }
            }
        }
        if(event.getEntity() instanceof Skeleton) {
            Skeleton skeleton = (Skeleton) event.getEntity();
            if(skeleton.getKiller() != null) {
                if(skeletonobj >= 1) {
                    Player player = skeleton.getKiller();
                    World world = player.getWorld();
                    Location location = player.getLocation();
                    int x = location.getBlockX();
                    int y = location.getBlockY();
                    int z = location.getBlockZ();
                    createScoreBoard(player);

                    if(skeletonobj == 1) {
                        killCount += 1;
                        if(killCount == 10) {
                            player.sendMessage(ChatColor.DARK_PURPLE + "Well done you killed 10 skeletons 30 more to go!");
                        }
                        if(killCount == 20) {
                            player.sendMessage(ChatColor.DARK_PURPLE + "Well done you killed 20 skeletons 20 more to go!");
                        }
                        if(killCount == 30) {
                            player.sendMessage(ChatColor.DARK_PURPLE + "Well done you killed 30 skeletons 10 more to go!");
                        }
                        if(killCount == 41) {
                            player.sendMessage(ChatColor.DARK_PURPLE + "Wow you did it you killed all 40 zombies, you now have to kill Skeletor to get your reward!");
                            world.createExplosion(x, y, z, 10f, false, false);

                            Skeleton skeleton1 = (Skeleton) location.getWorld().spawnEntity(location, EntityType.SKELETON);
                            skeleton1.setCustomName(ChatColor.DARK_PURPLE + "Skeletor");
                            skeleton1.setCustomNameVisible(true);
                            killCount = 0;
                            spiderobj = 0;
                            zombieobj = 0;
                            skeletonobj = 0;
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void clickEvent(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        if(event.getView().getTitle().equalsIgnoreCase(ChatColor.AQUA + "SlayerQuests")) {

            switch (event.getCurrentItem().getType()) {
                case COBWEB:
                    player.closeInventory();
                    spiderobj += 1;
                    skeletonobj += 2;
                    zombieobj += 2;
                    if(spiderobj == 1) {
                        player.sendMessage(ChatColor.DARK_GREEN + "Your objective is it to kill 40 Spiders! Good Luck!");
                    }
                    if(spiderobj >= 2) {
                        player.sendMessage(ChatColor.RED + "You you have alreade started a Slayer Quest!");
                    }
                    break;
                case POISONOUS_POTATO:
                    player.closeInventory();
                    zombieobj += 1;
                    skeletonobj += 2;
                    spiderobj += 2;
                    if(zombieobj == 1) {
                        player.sendMessage(ChatColor.DARK_RED + "Your objective is it to kill 40 Zombies! Good Luck!");
                    }
                    if(zombieobj >= 2) {
                        player.sendMessage(ChatColor.RED + "You you have alreade started a Slayer Quest!");
                    }
                    break;
                case BONE:
                    player.closeInventory();
                    skeletonobj += 1;
                    zombieobj += 2;
                    spiderobj += 2;
                    if(skeletonobj == 1) {
                        player.sendMessage(ChatColor.DARK_PURPLE + "Your objective is it to kill 40 Skeletons! Good Luck!");
                    }
                    if(skeletonobj >= 2) {
                        player.sendMessage(ChatColor.RED + "You you have alreade started a Slayer Quest!");
                    }
                    break;
                case BARRIER:
                    player.closeInventory();
                    skeletonobj = 0;
                    zombieobj = 0;
                    spiderobj = 0;
                    player.sendMessage(ChatColor.RED + "You cancelled your existing Slayer Quest!");
                    break;
            }
            event.setCancelled(true);
        }
    }
}
