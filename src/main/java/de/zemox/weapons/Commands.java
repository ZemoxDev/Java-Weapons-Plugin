package de.zemox.weapons;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Player only command");
            return true;
        }
        Player player = (Player) sender;
        if (command.getName().equalsIgnoreCase("knockbackWeapon")) {
            player.getInventory().addItem(KnockbackWeapon.KnockbackItem);
        }
        if (command.getName().equalsIgnoreCase("healerWeapon")) {
            player.getInventory().addItem(HealerWeapon.HealerItem);
        }
        if(command.getName().equalsIgnoreCase("slamWeapon")){
            player.getInventory().addItem(SlamWeapon.SlamItem);
        }
        if(command.getName().equalsIgnoreCase("explodingPickaxe")){
            player.getInventory().addItem(ExplodingPickaxe.ExplodingItem);
        }
        if(command.getName().equalsIgnoreCase("grapplerBow")){
            player.getInventory().addItem(GrapplerBow.GrapplerItem);
        }

        if(command.getName().equalsIgnoreCase("openGui")) {
            Inventory gui = Bukkit.createInventory(player, 9, ChatColor.AQUA + "SlayerQuests");

            ItemStack spider = new ItemStack(Material.COBWEB);
            ItemStack zombie = new ItemStack(Material.POISONOUS_POTATO);
            ItemStack skeleton = new ItemStack(Material.BONE);
            ItemStack cancel = new ItemStack(Material.BARRIER);

            ItemMeta spider_meta = spider.getItemMeta();
            spider_meta.setDisplayName(ChatColor.DARK_GREEN + "Spider Slayer");
            ArrayList<String> spider_lore = new ArrayList<>();
            spider_lore.add(ChatColor.GREEN + "This will activate a quest to kill 40 spiders!");
            spider_meta.setLore(spider_lore);
            spider.setItemMeta(spider_meta);

            ItemMeta zombie_meta = zombie.getItemMeta();
            zombie_meta.setDisplayName(ChatColor.DARK_RED + "Zombie Slayer");
            ArrayList<String> zombie_lore = new ArrayList<>();
            zombie_lore.add(ChatColor.RED + "This will activate the quest to kill 40 zombies!");
            zombie_meta.setLore(zombie_lore);
            zombie.setItemMeta(zombie_meta);

            ItemMeta skeleton_meta = skeleton.getItemMeta();
            skeleton_meta.setDisplayName(ChatColor.DARK_PURPLE + "Skeleton Slayer");
            ArrayList<String> skeleton_lore = new ArrayList<>();
            skeleton_lore.add(ChatColor.LIGHT_PURPLE + "This will activate the quest to kill 40 skeletons!");
            skeleton_meta.setLore(skeleton_lore);
            skeleton.setItemMeta(skeleton_meta);

            ItemMeta cancel_meta = cancel.getItemMeta();
            cancel_meta.setDisplayName(ChatColor.RED + "Cancel your Slayer Quest");
            ArrayList<String> cancel_lore = new ArrayList<>();
            cancel_lore.add(ChatColor.RED + "This will cancel your existing Slayer Quest!");
            cancel_meta.setLore(cancel_lore);
            cancel.setItemMeta(cancel_meta);

            ItemStack[] menu_items = {spider, zombie, skeleton, cancel};
            gui.setContents(menu_items);
            player.openInventory(gui);
        }

        return true;
    }
}
