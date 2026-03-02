package yourmod.conditionalBonus;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.effect.eff.conditionalBonus.conditionalRequirement.ConditionalRequirement;
import com.tann.dice.gameplay.fightLog.EntState;
import com.tann.dice.gameplay.fightLog.Snapshot;

public class PostCalcConditionalRequirement implements ConditionalRequirement {
    private ConditionalRequirement inner;

    public PostCalcConditionalRequirement(ConditionalRequirement inner) {
        this.inner = inner;
    }

    @Override
    public boolean isValid(Snapshot snapshot, EntState entState, EntState entState1, Eff eff) {
        return inner.isValid(snapshot, entState, entState1, eff);
    }

    @Override
    public boolean preCalculate() {
        return false;
    }

    @Override
    public String getInvalidString(Eff eff) {
        return inner.getInvalidString(eff);
    }

    @Override
    public String describe(Eff eff) {
        return inner.describe(eff);
    }

    @Override
    public String getBasicString() {
        return inner.getBasicString();
    }

    @Override
    public Actor getRestrictionActor() {
        return inner.getRestrictionActor();
    }

    @Override
    public boolean isPlural() {
        return inner.isPlural();
    }
}
