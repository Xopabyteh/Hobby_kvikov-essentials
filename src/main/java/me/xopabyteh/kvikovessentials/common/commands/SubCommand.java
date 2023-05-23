package me.xopabyteh.kvikovessentials.common.commands;

import me.xopabyteh.kvikovessentials.Kvikov_essentials;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class SubCommand {
    public abstract String getName();
    public abstract String getDescription();
    public abstract String getSyntax();
    public abstract boolean perform(Player player, String[] args, Kvikov_essentials context);
}
