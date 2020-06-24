package com.crappycoder.firemaking.lumbridgeworldhopper.helpers;

import com.crappycoder.firemaking.lumbridgeworldhopper.data.FiremakingConstants;
import org.rspeer.runetek.adapter.scene.Pickable;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.query.results.PositionableQueryResults;
import org.rspeer.runetek.api.scene.Pickables;
import org.rspeer.runetek.api.scene.SceneObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class LogsToLight {

    private static LogsToLight instance;
    private ArrayList<Pickable> logsToLightArray = new ArrayList<>();
    private boolean hopWorlds = false;

    private LogsToLight() { }

    public static synchronized LogsToLight getInstance() {
        if (Objects.isNull(instance)){
            instance = new LogsToLight();
        }
        return instance;
    }

    /**
     * Gets all valid logs in a distance of 6 to the player
     * @return Arraylist of valid logs
     */
    public ArrayList<Pickable> getLogsToLightArray() {
        // The array lookup was already performed and not emptied. Return the array.
        if (!logsToLightArray.isEmpty()) {
            return logsToLightArray;
        }
        // The array is empty, create new array and return the result.
        ArrayList<Pickable> logsToLight = new ArrayList<>();
        PositionableQueryResults<Pickable> logs = Pickables.newQuery().names("Logs").filter(s -> s.distance() <= 6).results();
        for (Pickable log : logs) {
            if (!isBlocking(log.getPosition())) {
                logsToLight.add(log);
            }
        }
        // Save array for performance.
        logsToLightArray = logsToLight;
        // If empty after function, we should hop worlds.
        hopWorlds = logsToLightArray.isEmpty();
        return logsToLight;
    }

    public void emtpyLogsArray() {
        logsToLightArray.clear();
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

    public boolean isHopWorlds() {
        return hopWorlds;
    }

    public void setHopWorlds(boolean hopWorlds) {
        this.hopWorlds = hopWorlds;
    }
}
