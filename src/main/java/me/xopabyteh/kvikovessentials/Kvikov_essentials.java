package me.xopabyteh.kvikovessentials;

import me.xopabyteh.kvikovessentials.kvikScore.KvikScoreManager;
import me.xopabyteh.kvikovessentials.kvikScore.commands.KvikScoreCommandManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Kvikov_essentials extends JavaPlugin implements Listener {
    public KvikScoreManager ScoreManager;
    @Override
    public void onEnable() {
        //Initialize KvikScore
        ScoreManager = KvikScoreManager.Create(this);

        //Register commands
        KvikScoreCommandManager.RegisterCommands(this);
    }
}
