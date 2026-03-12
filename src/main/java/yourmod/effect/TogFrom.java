package yourmod.effect;

import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.fightLog.EntSideState;
import com.tann.dice.gameplay.fightLog.EntState;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.AffectSides;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.AffectSideEffect;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.weirdTextmod.BasicTextmodToggle;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.weirdTextmod.LeftTextmodToggle;

public class TogFrom extends AffectSideEffect {
    BasicTextmodToggle toggle;
    LeftTextmodToggle left;
    Eff orig;

    public TogFrom(BasicTextmodToggle toggle) {
        this.toggle = toggle;
    }

    public TogFrom(LeftTextmodToggle left, Eff orig) {
        this.left = left;
        this.orig = orig;
    }

    public boolean needsGraphic() {
        return false;
    }

    public String describe() {
        if(toggle != null) {
            return toggle.describe();
        } else if(left != null) {
            return left.describe().replace("left side", orig.describe(true, false));
        } else {
            return null;
        }
    }

    public final void affect(EntSideState sideState, EntState owner, int index, AffectSides sourceTrigger, int sourceIndex) {
        try {
            Eff curr = sideState.getCalculatedEffect();
            if(toggle != null) {
                sideState.setCalculatedEffect(toggle.alterEff(curr));
            } else if(left != null) {
                sideState.setCalculatedEffect(left.alterEff(curr, orig));
            }
        } catch (Exception e) {
            //NOOP
        }

    }
}
