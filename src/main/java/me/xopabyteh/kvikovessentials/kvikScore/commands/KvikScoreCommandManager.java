package me.xopabyteh.kvikovessentials.kvikScore.commands;

import me.xopabyteh.kvikovessentials.Kvikov_essentials;
import me.xopabyteh.kvikovessentials.common.commands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class KvikScoreCommandManager implements CommandExecutor {
    private final Kvikov_essentials context;
    private final ArrayList<SubCommand> subCommands;
    //Ad command impl here
    private void AddCommands() {
        subCommands.add(new ScoreTweakCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player))
            return false;

        System.out.println(label);
        Player player = (Player)sender;
        for (SubCommand subCommand : subCommands) {
            System.out.println(subCommand.getName());
            if(subCommand.getName().equalsIgnoreCase(label)) {
                try {
                    return subCommand.perform(player, args, context);
                } catch(Exception e) {
                    player.sendMessage(subCommand.getSyntax());
                    return false;
                }
            }
        }

        return false;
    }
    
    private void MapCommands() {
        for (SubCommand command : subCommands) {
            context.getCommand(command.getName()).setExecutor(this);
        }
    }
    private KvikScoreCommandManager(Kvikov_essentials context) {
        subCommands = new ArrayList<>();
        AddCommands();

        this.context = context;
        MapCommands();
    }

    public static KvikScoreCommandManager RegisterCommands(Kvikov_essentials context) {
        return new KvikScoreCommandManager(context);
    }
}
