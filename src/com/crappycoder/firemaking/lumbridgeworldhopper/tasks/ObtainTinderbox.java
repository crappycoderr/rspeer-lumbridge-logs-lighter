package com.crappycoder.firemaking.lumbridgeworldhopper.tasks;

import com.crappycoder.firemaking.lumbridgeworldhopper.data.FiremakingConstants;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class ObtainTinderbox extends Task {

    Player local;

    /**
     * Validates if task should run
     * @return Boolean if task should run
     */
    @Override
    public boolean validate() {
        return !Inventory.contains(FiremakingConstants.TINDERBOX);
    }

    /**
     * Checks if a player is at the bank, walks to it, and obtains a tinderbox.
     * @return random int to sleep (between 300/800).
     */
    @Override
    public int execute() {
        local = Players.getLocal();
        if (Boolean.FALSE.equals(atBank())) {
            walkToBank();
        }
        if (!obtainTinderbox()) {
            return -1;
        }
        return Random.nextInt(300, 800);
    }

    /**
     * Checks if player is in reach of bank
     * @return If player is in reach of bank location.
     */
    private Boolean atBank() {
        return FiremakingConstants.BANK_LOCATION.getPosition().distance(local) > 9;
    }

    /**
     * Walks to bank using walkToRandomized
     */
    private void walkToBank() {
        Log.fine("Walking to bank");
        if (Movement.walkToRandomized(FiremakingConstants.BANK_LOCATION.getPosition())) {
            Time.sleepUntil(() ->
                            FiremakingConstants.BANK_LOCATION.getPosition().distance(local) < 9,
                            Random.mid(1000, 2400)
            );
        }
    }

    /**
     * Checks if bank contains tinderbox and obtains it
     * @return True if banked properly, false if player doesn't have a tinderbox.
     */
    private boolean obtainTinderbox(){
        Log.fine("Banking tinderbox");
        if (Bank.isClosed()) {
            Bank.open();
            Time.sleepUntil(() -> Bank.isOpen(), 500);
        }

        if (Bank.isOpen()) {
            if (Inventory.getCount() > 1) {
                Bank.depositAllExcept(FiremakingConstants.TINDERBOX);
                Time.sleepUntil(() ->Inventory.getCount() >= 1, 1000);
            }

            if (!Inventory.contains(FiremakingConstants.TINDERBOX) && !Bank.contains(FiremakingConstants.TINDERBOX)) {
                Log.fine("Bank doesn't contain Tinderbox");
                return false;
            } else if (!Inventory.contains(FiremakingConstants.TINDERBOX) && Bank.contains(FiremakingConstants.TINDERBOX)) {
                Log.fine("Getting Tinderbox from bank");
                Bank.withdraw(FiremakingConstants.TINDERBOX, 1);
                Time.sleepUntil(() ->Inventory.contains(FiremakingConstants.TINDERBOX), 1000);
            }
        }
        Bank.close();
        return true;
    }
}
