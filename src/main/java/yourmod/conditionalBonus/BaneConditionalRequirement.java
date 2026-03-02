package yourmod.conditionalBonus;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.dice.gameplay.content.ent.Ent;
import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.effect.eff.conditionalBonus.conditionalRequirement.ConditionalRequirement;
import com.tann.dice.gameplay.fightLog.EntState;
import com.tann.dice.gameplay.fightLog.Snapshot;
import com.tann.dice.util.Pixl;

public class BaneConditionalRequirement implements ConditionalRequirement {
    boolean isTarget;
    String name;

    public BaneConditionalRequirement(String name, boolean isTarget) {
        this.name = name;
        this.isTarget = isTarget;
    }

    @Override
    public boolean isValid(Snapshot snapshot, EntState source, EntState target, Eff eff) {
        Ent ent;

        if(isTarget) {
            if(target == null) return false;
            ent = target.getEnt();
        } else {
            ent = source.getEnt();
        }

        if(ent == null) return false;
        String name = ent.getName(true, false);
        if(name == null) return false;

        return name.toLowerCase().contains(this.name);
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
        return name;
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