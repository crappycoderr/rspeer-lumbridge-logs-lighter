package com.crappycoder.firemaking.lumbridgeworldhopper.tasks;

import com.crappycoder.firemaking.lumbridgeworldhopper.helpers.LogsToLight;
import com.crappycoder.firemaking.lumbridgeworldhopper.preferences.PlayerPreferences;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.WorldHopper;
import org.rspeer.script.task.Task;

public class HopWorlds extends Task {

    private static final LogsToLight logsToLight = LogsToLight.getInstance();
    private static final PlayerPreferences playerPreferences = PlayerPreferences.getInstance();

    /**
     * Validates if task should run
     * @return
     */
    @Override
    public boolean validate() {
        return logsToLight.isHopWorlds();
    }

    /**
     * Hops world either in P2P or F2P depeding on player Premium status.
     * @return
     */
    @Override
    public int execute() {
        if (playerPreferences.isPlayerIsMember()) {
            WorldHopper.randomHopInP2p();
        } else {
            WorldHopper.randomHopInF2p();
        }
        logsToLight.setHopWorlds(false);
        return Random.nextInt(1000,2000);
    }
}
