package yourmod.effect;

import com.bord.dice.modthedice.lib.SpirePatch2;
import com.tann.dice.gameplay.effect.Buff;
import yourmod.buffs.Doom;

public class BuffPatch {
    @SpirePatch2(clz = Buff.class, method = "getTurnsString")
    public static class GetTurnsString {
        public static String Postfix(Buff __instance, String __result) {
            if(__instance.personal instanceof Doom) {
                return __result.replace(" for", " in");
            }
            return __result;
        }
    }
}
