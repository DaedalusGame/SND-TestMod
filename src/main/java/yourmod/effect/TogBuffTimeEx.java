package yourmod.effect;

import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.effect.eff.EffBill;
import com.tann.dice.gameplay.effect.eff.EffType;
import com.tann.dice.gameplay.effect.eff.EffUtils;
import com.tann.dice.gameplay.effect.eff.conditionalBonus.conditionalRequirement.ConditionalRequirement;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.weirdTextmod.LeftTextmodToggle;

public class TogBuffTimeEx extends LeftTextmodToggle {
    public String describe() {
        return "take pips from left side as buff duration";
    }

    public Eff alterEff(Eff e, Eff left) {
        e = e.copy();
        if (e.getType() == EffType.Buff && e.getBuff() != null) {
            e.getBuff().turns = left.getValue();
        }
        return e;
    }
}