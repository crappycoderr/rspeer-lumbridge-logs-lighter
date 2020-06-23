package com.crappycoder.helpers;

import org.rspeer.runetek.api.commons.StopWatch;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;

public class ScriptTracker {

    private static StopWatch timer;
    private Experience experience;
    private long startTime;
    private long startXp;
    private int startLvl;
    private Skill trackedSkill;

    public ScriptTracker(Skill skill) {
        timer = StopWatch.start();
        trackedSkill = skill;
        experience = new Experience();
        startTime = System.currentTimeMillis();
        startXp = Skills.getExperience(trackedSkill);
        startLvl = Skills.getLevel(trackedSkill);
    }

    public final String getElapsedTime() {
        return timer.toElapsedString();
    }

    public final String xpHour() {
        final long upTime = System.currentTimeMillis() - startTime;
        double gainedXp = Skills.getExperience(trackedSkill) - startXp;
        return String.format("%.1f", (gainedXp * 3600000 / upTime) / 1000) + "K";
    }

    public final String xpToLvl() {
        return String.format("%.1f", ((double) Skills.getExperienceToNextLevel(trackedSkill) / 1000)) + "K";
    }

    public final String timeToLvl() {

        return String.format("%.1f", ((double) Skills.getExperienceToNextLevel(trackedSkill) / 1000)) + "K";
    }

    public final String gainedXp() {
        return String.format("%.1f", ((double) Skills.getExperience(trackedSkill) - startXp) / 1000) + "K";
    }

    public final int gainedLvl () {
        int currentSkillLevel = Skills.getLevel(trackedSkill);
        return currentSkillLevel - startLvl;
    }

    /**
     * Shows what % you are to next level in a skill
     * @return String containing: {% to next level}%/100%
     */
    public final String percentToLvl() {
        long currentXp = Skills.getExperience(trackedSkill);
        int currentSkillLvl = Skills.getLevel(trackedSkill);
        long xpNextLvl = Skills.getExperienceAt(currentSkillLvl);
        long xpAtLvl = Skills.getExperienceAt(currentSkillLvl + 1);
        return (((xpNextLvl - currentXp) * 100) / (xpNextLvl - xpAtLvl)) + "% / 100%";
    }

    public long getStartXp() {
        return startXp;
    }

    public int getStartLvl() {
        return startLvl;
    }
}
