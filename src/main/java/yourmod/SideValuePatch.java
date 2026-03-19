package yourmod;

import com.bord.dice.modthedice.lib.SpireField;
import com.bord.dice.modthedice.lib.SpirePatch;
import com.bord.dice.modthedice.lib.SpirePatch2;
import com.bord.dice.modthedice.lib.SpireReturn;
import com.tann.dice.gameplay.content.ent.die.side.EntSide;
import com.tann.dice.gameplay.content.ent.type.EntType;
import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.effect.eff.EffBill;

public class SideValuePatch {
    @SpirePatch2(clz = Eff.class, method = SpirePatch.CLASS)
    public static class EffField {
        public static SpireField<EffCalc> effCalc = new SpireField<>(() -> null);

        public static void set(Eff eff, EffCalc calc) {
            effCalc.set(eff, calc);
        }

        public static EffCalc get(Eff eff) {
            return effCalc.get(eff);
        }
    }

    @SpirePatch2(clz = EffBill.class, method = SpirePatch.CLASS)
    public static class EffBillField {
        public static SpireField<EffCalc> effCalc = new SpireField<>(() -> null);

        public static void set(EffBill eff, EffCalc calc) {
            effCalc.set(eff, calc);
        }

        public static EffCalc get(EffBill eff) {
            return effCalc.get(eff);
        }
    }

    @SpirePatch2(clz = Eff.class, method = "copy")
    public static class EffCopy {
        public static Eff Postfix(Eff __result, Eff __instance) {
            EffField.set(__result, EffField.get(__instance));
            return __result;
        }
    }

    @SpirePatch2(clz = EffBill.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {Eff.class})
    public static class EffBillConstructor {
        public static EffBill Postfix(Object __instance, Eff e) {
            EffBill effBill = (EffBill) __instance;
            EffBillField.set(effBill, EffField.get(e));
            return effBill;
        }
    }

    @SpirePatch2(clz = EffBill.class, method = "bEff")
    public static class EffBillBuild {
        public static Eff Postfix(Eff __result, EffBill __instance) {
            EffField.set(__result, EffBillField.get(__instance));
            return __result;
        }
    }

    @SpirePatch2(clz = EntSide.class, method = "checkForHackyOverride")
    public static class CheckForHackyOverride {
        public static SpireReturn<Float> Prefix(Eff e, int tier, EntType type, boolean player) {
            EffCalc effCalc = EffField.get(e);

            if(effCalc != null) {
                return SpireReturn.Return(effCalc.getValue(e.getValue()));
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch2(clz = EntSide.class, method = "getExtraFlatEffectTier")
    public static class GetExtraFlatEffectTier {
        public static SpireReturn<Float> Prefix(EntSide __instance, EntType type) {
            Eff eff = __instance.getBaseEffect();
            EffCalc effCalc = EffField.get(eff);

            if(effCalc != null) {
                return SpireReturn.Return(0.0f);
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch2(clz = EntSide.class, method = "getApproxTotalEffectTier")
    public static class GetApproxTotalEffectTier {
        public static SpireReturn<Float> Prefix(EntSide __instance, EntType type) {
            Eff eff = __instance.getBaseEffect();
            EffCalc effCalc = EffField.get(eff);

            if(effCalc != null) {
                return SpireReturn.Return(effCalc.getValue(eff.getValue()));
            }

            return SpireReturn.Continue();
        }
    }
}
