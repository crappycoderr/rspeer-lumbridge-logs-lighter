package com.crappycoder.firemaking.lumbridgeworldhopper.preferences;

import java.util.Objects;

public class PlayerPreferences {
    boolean guiClosed;
    boolean playerIsMember;

    private static PlayerPreferences instance;

    private PlayerPreferences() {
        guiClosed = false;
    }

    /**
     * Singleton class
     * @return PlayerPreferences instance
     */
    public static synchronized PlayerPreferences getInstance() {
        if (Objects.isNull(instance)) {
            instance = new PlayerPreferences();
        }
        return instance;
    }

    public boolean isGuiClosed() {
        return guiClosed;
    }

    public void setGuiClosed(boolean guiClosed) {
        this.guiClosed = guiClosed;
    }

    public boolean isPlayerIsMember() {
        return playerIsMember;
    }

    public void setPlayerIsMember(boolean playerIsMember) {
        this.playerIsMember = playerIsMember;
    }
}
