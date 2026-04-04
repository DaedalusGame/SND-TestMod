package yourmod.conditionalBonus;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.effect.eff.conditionalBonus.conditionalRequirement.ConditionalRequirement;
import com.tann.dice.gameplay.fightLog.EntState;
import com.tann.dice.gameplay.fightLog.Snapshot;
import com.tann.dice.statics.ImageUtils;
import com.tann.dice.util.Pixl;
import yourmod.buffs.BuffUtil;

public class FuelEmptyConditionalRequirement implements ConditionalRequirement {
    boolean isTarget;

    public FuelEmptyConditionalRequirement(boolean isTarget) {
        this.isTarget = isTarget;
    }

    @Override
    public boolean isValid(Snapshot snapshot, EntState source, EntState target, Eff eff) {
        if(isTarget) {
            if(target == null) return false;
            return BuffUtil.getFuel(target) <= 0;
        } else {
            return BuffUtil.getFuel(source) <= 0;
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
        return "empty fuel";
    }

    @Override
    public Actor getRestrictionActor() {
        TextureRegion tr = ImageUtils.loadExt("trigger/equip-stuff/fuel_empty");
        return new Pixl().image(tr).pix();
    }

    @Override
    public boolean isPlural() {
        return false;
    }
}