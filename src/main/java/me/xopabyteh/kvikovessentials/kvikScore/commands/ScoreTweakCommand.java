package me.xopabyteh.kvikovessentials.kvikScore.commands;

import me.xopabyteh.kvikovessentials.Kvikov_essentials;
import me.xopabyteh.kvikovessentials.common.commands.SubCommand;
import org.bukkit.entity.Player;

public class ScoreTweakCommand extends SubCommand {
    @Override
    public String getName() {
        return "scoretweak";
    }

    @Override
    public String getDescription() {
        return "Tweak your KvikScore settings";
    }

    @Override
    public String getSyntax() {
        return "/scoretweak <init/suicide/killed> <value>";
    }

    @Override
    public boolean perform(Player player, String[] args, Kvikov_essentials context) {
        System.out.println("Got here!");
        if(args.length < 2) {
            player.sendMessage(getSyntax());
            return false;
        }

        String tweakPart = args[0];
        System.out.println(tweakPart);
        double value = Double.parseDouble(args[1]);
        System.out.println(value);
        switch (tweakPart) {
            case "init":
                context.ScoreManager.setKvikScoreInitialValue((int)value);
                break;
            case "suicide":
                context.ScoreManager.setKvikScoreSuicideTransferPercentage(value);
                break;
            case "killed":
                context.ScoreManager.setKvikScoreKilledTransferPercentage(value);
                break;
            default:
                player.sendMessage(getSyntax());
                return false;
        }

        player.sendMessage("Changed " + tweakPart + " to " + value);
        return true;
    }
}
