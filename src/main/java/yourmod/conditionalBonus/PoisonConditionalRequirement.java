package yourmod.conditionalBonus;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.effect.eff.conditionalBonus.conditionalRequirement.ConditionalRequirement;
import com.tann.dice.gameplay.fightLog.EntState;
import com.tann.dice.gameplay.fightLog.Snapshot;
import com.tann.dice.util.Pixl;
import com.tann.dice.util.ui.HpGrid;

public class PoisonConditionalRequirement implements ConditionalRequirement {
    boolean isTarget;

    public PoisonConditionalRequirement(boolean isTarget) {
        this.isTarget = isTarget;
    }

    @Override
    public boolean isValid(Snapshot snapshot, EntState source, EntState target, Eff eff) {
        if(isTarget) {
            if(target == null) return false;
            return target.getBasePoisonPerTurn() > 0;
        } else {
            return source.getBasePoisonPerTurn() > 0;
        }
    }

    @Override
    public boolean preCalculate() {
        return !isTarget;
    }

    @Override
    public String getInvalidString(Eff eff) {
        return "invalid target?";
    }

    @Override
    public String describe(Eff eff) {
        return null;
    }

    @Override
    public String getBasicString() {
        return "poisoned";
    }

    @Override
    public Actor getRestrictionActor() {
        return HpGrid.make(3, 0, 3,3);
    }

    @Override
    public boolean isPlural() {
        return false;
    }
}

