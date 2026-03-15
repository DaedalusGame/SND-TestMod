package yourmod.effect;

import com.tann.dice.gameplay.trigger.personal.Personal;

public class TrueName extends Personal implements ITrueName {
    final String to;

    public TrueName(String to) {
        this.to = to;
    }

    public boolean skipCalc() {
        return true;
    }

    public boolean skipTraitPanel() {
        return true;
    }

    @Override
    public String getTrueName() {
        return to;
    }
}
