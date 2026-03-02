package yourmod.conditionalBonus;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.effect.eff.conditionalBonus.conditionalRequirement.ConditionalRequirement;
import com.tann.dice.gameplay.fightLog.EntState;
import com.tann.dice.gameplay.fightLog.Snapshot;
import com.tann.dice.util.Pixl;

import java.util.List;

public class BottomConditionalRequirement implements ConditionalRequirement {
    @Override
    public boolean isValid(Snapshot snapshot, EntState sourceState, EntState targetState, Eff eff) {
        List<EntState> states = snapshot.getStates(targetState.isPlayer(), false);
        return states.indexOf(targetState) == states.size()-1;
    }

    @Override
    public boolean preCalculate() {
        return false;
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
        return "bottom";
    }

    @Override
    public Actor getRestrictionActor() {
        return (new Pixl()).text(this.getBasicString()).pix();
    }

    @Override
    public boolean isPlural() {
        return false;
    }
}
