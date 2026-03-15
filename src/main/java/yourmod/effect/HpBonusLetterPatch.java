package yourmod.effect;

import com.bord.dice.modthedice.lib.SpirePatch2;
import com.bord.dice.modthedice.lib.SpireReturn;
import com.tann.dice.gameplay.content.gen.pipe.entity.hero.PipeHeroReplica;
import com.tann.dice.gameplay.fightLog.EntState;
import com.tann.dice.gameplay.trigger.personal.hp.HpBonusLetter;
import com.tann.dice.util.Tann;
import yourmod.TrueNameUtils;

@SpirePatch2(
        clz = HpBonusLetter.class,
        method = "getBonusMaxHp"
)
public class HpBonusLetterPatch  {
    public static SpireReturn<Integer> Prefix(HpBonusLetter __instance, int maxHp, EntState ent, char[] ___chars, int ___bonus)
    {
        int extra = 0;
        String nameToCheck = HpBonusLetter.transformName(TrueNameUtils.getTrueName(ent.getEnt()).toLowerCase());
        extra += Tann.countCharsInString(___chars, nameToCheck) * ___bonus;
        return SpireReturn.Return(extra);
    }
}
