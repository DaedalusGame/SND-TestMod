package yourmod.effect;

import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.effect.eff.EffBill;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.weirdTextmod.BasicTextmodToggle;
import com.tann.dice.util.Tann;
import yourmod.EffCalc;
import yourmod.SideValuePatch;

public class Sival extends BasicTextmodToggle {
    public EffCalc effCalc;

    public Sival(EffCalc effCalc) {
        this.effCalc = effCalc;
    }

    public String describe() {
        return "override calculated side value to "+ Tann.delta(effCalc.base)+" " + Tann.delta(effCalc.perPip) + "/pip";
    }

    public Eff alterEff(Eff e) {
        Eff copy = e.copy();
        SideValuePatch.EffField.set(copy, effCalc);
        return copy;
    }
}