package yourmod.init;

import basemod.effect.EffectRegistry;
import basemod.effect.IEffType;
import com.tann.dice.gameplay.content.ent.Ent;
import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.effect.eff.EffType;
import com.tann.dice.gameplay.effect.targetable.Targetable;
import com.tann.dice.gameplay.fightLog.EntState;

public class EffTypes {
    public static void init() {
        EffectRegistry.register("Use", false, new IEffType() {
            @Override
            public void hit(EntState state, Eff eff, Ent source, Targetable targetable) {
                state.useDie();
            }

            @Override
            public String describe(EffType type, Eff eff) {
                return "use dice";
            }
        });
    }
}
