package yourmod.effect;


import com.bord.dice.modthedice.lib.SpirePatch2;
import com.bord.dice.modthedice.lib.SpireReturn;
import com.tann.dice.gameplay.content.ent.die.side.EnSiBi;
import com.tann.dice.gameplay.content.ent.die.side.EntSide;
import com.tann.dice.gameplay.content.ent.die.side.EntSidesLib;
import com.tann.dice.gameplay.effect.eff.EffBill;
import com.tann.dice.gameplay.effect.eff.EffType;

public class EntSidesLibPatch {
    public static EntSide genericKill;
    public static EntSide genericBuff;
    public static EntSide genericTarget;

    static {
        genericKill = (new EnSiBi()).image("special/generic/kill").effect((new EffBill()).nothing()).noVal().generic();
        genericBuff = (new EnSiBi()).image("special/generic/buff").effect((new EffBill()).nothing()).noVal().generic();
        genericTarget = (new EnSiBi()).image("special/generic/target").effect((new EffBill()).nothing()).noVal().generic();
    }

    @SpirePatch2(clz = EntSidesLib.class, method = "getSide")
    public static class GetSide {
        public static SpireReturn<EntSide> Prefix(EffType type, boolean basicOnly) {
            if(type == EffType.Buff) {
                return SpireReturn.Return(genericBuff);
            }
            if(type == EffType.Kill) {
                return SpireReturn.Return(genericKill);
            }
            if(type == EffType.JustTarget) {
                return SpireReturn.Return(genericTarget);
            }

            return SpireReturn.Continue();
        }
    }
}
