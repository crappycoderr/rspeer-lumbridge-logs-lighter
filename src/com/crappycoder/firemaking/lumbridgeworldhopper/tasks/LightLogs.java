package com.crappycoder.firemaking.lumbridgeworldhopper.tasks;

import com.crappycoder.firemaking.lumbridgeworldhopper.data.FiremakingConstants;
import org.rspeer.runetek.adapter.scene.Pickable;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.query.results.PositionableQueryResults;
import org.rspeer.runetek.api.scene.Pickables;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.script.task.Task;

import java.util.ArrayList;
import java.util.Arrays;

public class LightLogs extends Task {

    private ArrayList<Pickable> logsToLight;
    Player local;

    /**
     * Validates if task should run
     * @return
     */
    @Override
    public boolean validate() {
        return Boolean.FALSE.equals(validLogs().isEmpty()) && Inventory.contains(FiremakingConstants.TINDERBOX);
    }

    @Override
    public int execute() {
        local = Players.getLocal();
        lightLogs(logsToLight, local);
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

    /**
     * Gets all valid logs in a distance of 6 to the player
     * @return Arraylist of valid logs
     */
    private ArrayList<Pickable> validLogs() {
        logsToLight = new ArrayList<>();
        PositionableQueryResults<Pickable> logs = Pickables.newQuery().names("Logs").filter(s -> s.distance() <= 6).results();
        for (Pickable log : logs) {
            if (!isBlocking(log.getPosition())) {
                logsToLight.add(log);
            }
        }
        return logsToLight;
    }

    /**
     * Checks if a dropped log is blocked by any Sceneobject
     * @param position Position to check on
     * @return Boolean if a Sceneobject is blocking or not.
     */
    private boolean isBlocking(Position position) {
        SceneObject scene = SceneObjects.getFirstAt(position);
        return (scene != null && Arrays.asList(FiremakingConstants.BLOCKING_SCENES).contains(scene.getName()));
    }
}
