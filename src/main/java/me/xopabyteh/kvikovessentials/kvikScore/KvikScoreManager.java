package me.xopabyteh.kvikovessentials.kvikScore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class KvikScoreManager implements Listener {
    private String objectiveName = "kvscore";
    private String objectiveDisplayName = "Kvíkov Score";
    private int kvikScoreInitialValue = 100;
    public void setKvikScoreInitialValue(int v) {
        kvikScoreInitialValue = v;
    }
    private double kvikScoreKilledTransferPercentage = 0.25d;
    public void setKvikScoreKilledTransferPercentage(double v) {
        kvikScoreKilledTransferPercentage = v;
    }
    private double kvikScoreSuicideTransferPercentage = 0.05d;
    public void setKvikScoreSuicideTransferPercentage(double v) {
        kvikScoreSuicideTransferPercentage = v;
    }
    private Objective kvikObjective;

    private KvikScoreManager(JavaPlugin plugin) {
        kvikObjective = SetupObjective();

        //Setup event handlers
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    public static KvikScoreManager Create(JavaPlugin plugin) {
        KvikScoreManager manager = new KvikScoreManager(plugin);
        return manager;
    }
    private Objective SetupObjective() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Objective existingObjective = scoreboard.getObjective(objectiveName);
        if(existingObjective != null)
            return existingObjective;

        Objective objective = scoreboard.registerNewObjective(objectiveName, "dummy", ChatColor.RED + objectiveDisplayName);
        objective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        return objective;
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        //Set initial value for kvikScore ONLY FOR FIRST TIME JOIN
        Player player = event.getPlayer();
        if(player.hasPlayedBefore())
            return;

        Score score = kvikObjective.getScore(player.getName());
        score.setScore(kvikScoreInitialValue);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();
        if(killer == null) {
            setScoreSuicide(victim);
            return;
        }

        setScoreKilled(victim, killer);
    }

    private void setScoreSuicide(Player victim) {
        Score victimScore = kvikObjective.getScore(victim.getName());

        int victimScoreVal = victimScore.getScore();
        int scoreTransfer = (int) Math.round(victimScoreVal * kvikScoreSuicideTransferPercentage);

        victimScore.setScore(victimScoreVal - scoreTransfer);
    }

    private void setScoreKilled(Player victim, Player killer) {
        Score victimScore = kvikObjective.getScore(victim.getName());
        Score killerScore = kvikObjective.getScore(killer.getName());

        int victimScoreVal = victimScore.getScore();
        int scoreTransfer = (int) Math.round(victimScoreVal * kvikScoreKilledTransferPercentage);

        victimScore.setScore(victimScoreVal - scoreTransfer);
        killerScore.setScore(killerScore.getScore() + scoreTransfer);
    }
}
