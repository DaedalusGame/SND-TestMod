package yourmod.buffs;

import com.tann.dice.gameplay.effect.Buff;
import com.tann.dice.gameplay.trigger.personal.Personal;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.AffectSides;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.AffectSideEffect;
import yourmod.ReflectionUtils;

import java.util.ArrayList;
import java.util.List;

public class SpecificSideBuff extends Buff {
    public List<AffectSideEffect> effects;

    public SpecificSideBuff(int turns, AffectSides inner) {
        super(turns, inner);
        effects = new ArrayList<>(inner.getEffects());
    }

    @Override
    public Buff copy() {
        SpecificSideBuff b = new SpecificSideBuff(this.turns, (AffectSides) this.personal);
        boolean skipFirstTick = ReflectionUtils.readField(boolean.class, Buff.class, this, "skipFirstTick");

        if (skipFirstTick) {
            b.skipFirstTick();
        }

        return b;
    }
}
