package yourmod;

import com.bord.dice.modthedice.lib.SpirePatch2;
import com.bord.dice.modthedice.lib.SpireRawPatch;
import com.tann.dice.gameplay.effect.Buff;
import com.tann.dice.gameplay.fightLog.EntState;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.AffectSides;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.condition.SpecificSidesCondition;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.condition.SpecificSidesType;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.Handler;
import yourmod.buffs.SpecificSideBuff;

import java.util.Arrays;

@SpirePatch2(
    clz = EntState.class,
    method = "addBuff",
    paramtypez = {Buff.class}
)
public class EntStatePatch {
    public static Buff exchangeBuff(EntState self, Buff buff) {
        if(buff instanceof SpecificSideBuff) {
            SpecificSideBuff specificSideBuff = (SpecificSideBuff) buff;

            SpecificSidesType[] indexToSideMap = new SpecificSidesType[]{
                    SpecificSidesType.Top,
                    SpecificSidesType.Bot,
                    SpecificSidesType.Left,
                    SpecificSidesType.Right,
                    SpecificSidesType.Middle,
                    SpecificSidesType.RightMost,
            };

            int currIndex = self.getCurrentSideState().getIndex();
            AffectSides newEffect = new AffectSides(
                    Arrays.asList(new SpecificSidesCondition(indexToSideMap[currIndex])),
                    specificSideBuff.effects
            );

            buff = new Buff(buff.turns, newEffect);
        }
        return buff;
    }

    @SpireRawPatch
    public static void Raw(CtBehavior ctMethodToPatch) throws CannotCompileException {
        ctMethodToPatch.insertBefore("$1 = yourmod.EntStatePatch.exchangeBuff($0, $1);");
    }
}
