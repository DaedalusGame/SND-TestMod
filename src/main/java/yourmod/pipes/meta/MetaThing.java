package yourmod.pipes.meta;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.dice.gameplay.context.DungeonContext;
import com.tann.dice.gameplay.modifier.ModifierLib;
import com.tann.dice.gameplay.phase.levelEndPhase.rewardPhase.decisionPhase.choice.choosable.Choosable;
import com.tann.dice.gameplay.phase.levelEndPhase.rewardPhase.decisionPhase.choice.choosable.ChoosableType;
import com.tann.dice.gameplay.progress.chievo.unlock.Unlockable;
import com.tann.dice.gameplay.progress.stats.stat.Stat;
import com.tann.dice.screens.dungeon.panels.entPanel.choosablePanel.ModifierPanel;
import com.tann.dice.util.Colours;
import yourmod.screen.MetaThingPanel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MetaThing implements Choosable, Unlockable {
    String name;

    public MetaThing(String name) {
        this.name = name;
    }

    @Override
    public boolean isPositive() {
        return false;
    }

    @Override
    public Color getColour() {
        return Colours.BLURPLE;
    }

    @Override
    public String getSaveString() {
        return null;
    }

    @Override
    public ChoosableType getType() {
        return null;
    }

    @Override
    public void onChoose(DungeonContext dungeonContext, int i) {

    }

    @Override
    public void onReject(DungeonContext dungeonContext) {

    }

    @Override
    public Actor makeChoosableActor(boolean big, int i) {
        return new MetaThingPanel(this, big);
    }

    @Override
    public int getTier() {
        return 0;
    }

    @Override
    public float getModTier() {
        return 0;
    }

    @Override
    public String describe() {
        return "not impl";
    }

    @Override
    public float chance() {
        return 0;
    }

    @Override
    public String getTierString() {
        return "";
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean encountered(Map<String, Stat> map) {
        return true;
    }

    @Override
    public int getPicks(Map<String, Stat> map, boolean b) {
        return 0;
    }

    @Override
    public long getCollisionBits() {
        return 0;
    }

    public boolean isMissingno() {
        return this == MetaThingLib.getMissingno();
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public Actor makeUnlockActor(boolean big) {
        return new MetaThingPanel(this, big);
    }

    @Override
    public TextureRegion getAchievementIcon() {
        return null;
    }

    @Override
    public String getAchievementIconString() {
        return "X";
    }

    public List<Actor> makeMiddleActors() {
        return Collections.emptyList();
    }
}
