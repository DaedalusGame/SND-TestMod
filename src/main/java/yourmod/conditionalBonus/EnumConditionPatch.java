package yourmod.conditionalBonus;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.bord.dice.modthedice.lib.SpirePatch2;
import com.bord.dice.modthedice.lib.SpireReturn;
import com.tann.dice.gameplay.effect.eff.conditionalBonus.conditionalRequirement.ConditionalRequirement;
import com.tann.dice.gameplay.effect.eff.conditionalBonus.conditionalRequirement.EnumConditionalRequirement;
import com.tann.dice.gameplay.effect.eff.conditionalBonus.conditionalRequirement.ORRequirement;
import com.tann.dice.gameplay.effect.eff.conditionalBonus.conditionalRequirement.XORRequirement;
import com.tann.dice.util.Pixl;

public class EnumConditionPatch {
    @SpirePatch2(clz = EnumConditionalRequirement.class, method = "getBasicString")
    public static class GetBasicString {
        public static SpireReturn<String> Prefix(EnumConditionalRequirement __instance) {
            switch (__instance) {
                case UnusedLastTurn: return SpireReturn.Return("unused last turn");
                case SixthDiceUsed: return SpireReturn.Return("5 dice used this turn");
                case NoOtherDice: return SpireReturn.Return("no dice used this turn");
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch2(clz = EnumConditionalRequirement.class, method = "getRestrictionActor")
    public static class GetRestrictionActor {
        public static SpireReturn<Actor> Prefix(EnumConditionalRequirement __instance) {
            switch (__instance) {
                case UnusedLastTurn: return SpireReturn.Return(new Pixl().text("[grey]zzz[cu]").pix());
                case SixthDiceUsed: return SpireReturn.Return(new Pixl().text("[orange]6[cu]").pix());
                case NoOtherDice: return SpireReturn.Return(new Pixl().text("[orange]0[cu]").pix());
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch2(clz = XORRequirement.class, method = "getRestrictionActor")
    public static class XORGetRestrictionActor {
        public static SpireReturn<Actor> Prefix(ConditionalRequirement ___a, ConditionalRequirement ___b) {
            return SpireReturn.Return(new Pixl(3).actor(___a.getRestrictionActor()).text("[pink]xor[cu]").actor(___b.getRestrictionActor()).pix());
        }
    }

    @SpirePatch2(clz = ORRequirement.class, method = "getRestrictionActor")
    public static class ORGetRestrictionActor {
        public static SpireReturn<Actor> Prefix(ConditionalRequirement ___a, ConditionalRequirement ___b) {
            return SpireReturn.Return(new Pixl(3).actor(___a.getRestrictionActor()).text("or").actor(___b.getRestrictionActor()).pix());
        }
    }
}
