package com.crappycoder.firemaking.lumbridgeworldhopper;

import com.crappycoder.firemaking.lumbridgeworldhopper.tasks.HopWorlds;
import com.crappycoder.firemaking.lumbridgeworldhopper.tasks.LightLogs;
import com.crappycoder.firemaking.lumbridgeworldhopper.tasks.ObtainTinderbox;
import com.crappycoder.helpers.ScriptTracker;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.ScriptCategory;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.Task;
import org.rspeer.script.task.TaskScript;

import java.awt.*;

import static java.awt.Color.GREEN;

@ScriptMeta(developer = "crappycoder",
        name = "Lumbridge Logs Lighter",
        desc = "Lights logs at Lumbridge and hops worlds (TaskList)",
        category = ScriptCategory.FIREMAKING,
        version = 0.1
)

public class LogsLighter extends TaskScript implements RenderListener {

    private static final Task[] TASKS = {
            new LightLogs(),
            new HopWorlds()
    };
    ScriptTracker fireMakingTracker;

    @Override
    public void onStart() {
        fireMakingTracker = new ScriptTracker(Skill.FIREMAKING);
        submit(new ObtainTinderbox());
        removeAll();
        submit(TASKS);
    }

    @Override
    public void notify(RenderEvent renderEvent) {
        Graphics g = renderEvent.getSource();
        Graphics2D g2 = (Graphics2D)g;
        int y = 45;
        int x = 10;

        g2.setColor(new Color(0, 0, 0, 0.40f));
        g2.fillRect(x - 5, y - 20, 170, 135);
        g2.setColor(GREEN);
        g2.drawRect(x - 6, y - 21, 171, 136);
        g2.drawString("\"Get banned\" Firemaker", x, y);
        g2.drawString(fireMakingTracker.getElapsedTime(), x, y+= 20);
        g2.drawString("Exp: " + fireMakingTracker.gainedXp(), x, y+= 20);
        g2.drawString("Exp/hr: " + fireMakingTracker.xpHour(), x, y+= 20);
        g2.drawString(fireMakingTracker.percentToLvl(), x, y+= 20);
        g2.drawString("Level: " + Skills.getVirtualLevel(Skill.FIREMAKING), x, y);
    }
}
