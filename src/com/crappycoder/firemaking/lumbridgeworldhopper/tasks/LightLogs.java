package com.crappycoder.firemaking.lumbridgeworldhopper.tasks;

import com.crappycoder.firemaking.lumbridgeworldhopper.data.FiremakingConstants;
import com.crappycoder.firemaking.lumbridgeworldhopper.helpers.LogsToLight;
import org.rspeer.runetek.adapter.scene.Pickable;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;

import java.util.ArrayList;

public class LightLogs extends Task {

    private static final LogsToLight logsToLight = LogsToLight.getInstance();
    Player local;

    /**
     * Validates if task should run
     * @return
     */
    @Override
    public boolean validate() {
        return Boolean.FALSE.equals(logsToLight.getLogsToLightArray().isEmpty())
                && Inventory.contains(FiremakingConstants.TINDERBOX);
    }

    @Override
    public int execute() {
        local = Players.getLocal();
        lightLogs(logsToLight.getLogsToLightArray(), local);
        return Random.nextInt(1000,2000);
    }

    /**
     * Lights logs in arraylist.
     * @param logsToLight
     * @param local
     */
    private void lightLogs(ArrayList<Pickable> logsToLight, Player local) {
        for (Pickable log : logsToLight) {
            log.interact("Light");
            Time.sleep(500);
            Time.sleepUntil(() -> !local.isAnimating() && !local.isMoving(), 250, 10000
        );
        }
    }
}
