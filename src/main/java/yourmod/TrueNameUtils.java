package yourmod;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.bord.dice.modthedice.lib.SpireField;
import com.bord.dice.modthedice.lib.SpirePatch;
import com.bord.dice.modthedice.lib.SpirePatch2;
import com.tann.dice.gameplay.content.ent.Ent;
import com.tann.dice.gameplay.content.ent.Hero;
import com.tann.dice.gameplay.content.ent.type.EntType;
import com.tann.dice.gameplay.content.ent.type.HeroCol;
import com.tann.dice.gameplay.content.ent.type.HeroType;
import com.tann.dice.gameplay.content.ent.type.MonsterType;
import com.tann.dice.gameplay.content.ent.type.bill.ETBill;
import com.tann.dice.gameplay.content.ent.type.bill.HTBill;
import com.tann.dice.gameplay.content.ent.type.bill.MTBill;
import com.tann.dice.gameplay.content.ent.type.lib.EntTypeUtils;
import com.tann.dice.gameplay.effect.Trait;
import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.fightLog.EntState;
import com.tann.dice.gameplay.trigger.personal.weird.DescribeOnly;
import com.tann.dice.gameplay.trigger.personal.weird.DescribeOnlyTrait;
import com.tann.dice.screens.dungeon.panels.Explanel.EntPanelInventory;
import com.tann.dice.screens.dungeon.panels.Explanel.Explanel;
import com.tann.dice.util.ui.TextWriter;
import yourmod.effect.ITrueName;

import java.util.List;
import java.util.Objects;

public class TrueNameUtils {
    @SpirePatch2(clz = EntType.class, method = SpirePatch.CLASS)
    public static class EntTypeField {
        public static SpireField<String> trueName = new SpireField<>(() -> null);

        public static void set(EntType entType, String trueName) {
            EntTypeField.trueName.set(entType, trueName);
        }

        public static String get(EntType entType) {
            return trueName.get(entType);
        }
    }

    @SpirePatch2(clz = ETBill.class, method = SpirePatch.CLASS)
    public static class ETBillField {
        public static SpireField<String> trueName = new SpireField<>(() -> null);

        public static void set(ETBill bill, String trueName) {
            ETBillField.trueName.set(bill, trueName);
        }

        public static String get(ETBill bill) {
            return trueName.get(bill);
        }
    }

    @SpirePatch2(clz = MTBill.class, method = "bEntType", paramtypez = {})
    public static class MTBillBuild {
        public static void Postfix(MonsterType __result, MTBill __instance) {
            EntTypeField.set(__result, ETBillField.get(__instance));
        }
    }

    @SpirePatch2(clz = HTBill.class, method = "bEntType", paramtypez = {})
    public static class HTBillBuild {
        public static void Postfix(HeroType __result, HTBill __instance) {
            EntTypeField.set(__result, ETBillField.get(__instance));
        }
    }

    @SpirePatch2(clz = EntTypeUtils.class, method = "finishInit")
    public static class EntTypeUtilsFinishInit {
        public static void Postfix(ETBill htb, EntType src) {
            ETBillField.set(htb, getTrueName(src));
        }
    }

    @SpirePatch2(clz = EntPanelInventory.class, method = "makeTraitActors")
    public static class EntPanelInventoryMakeTraitActors {
        public static List<Actor> Postfix(int width, Ent ent, List<Actor> __result) {
            String trueName = getTrueName(ent);
            String currName = ent.getName(true, false);
            if(!Objects.equals(currName, trueName)) {
                __result.add(new Explanel(new DescribeOnlyTrait(TextWriter.getTag(HeroCol.lime.col) + "" + trueName + "[cu]"), ent, false, (float) width));
            }

            return __result;
        }
    }

    public static void setTrueName(ETBill bill, String trueName) {
        ETBillField.set(bill, trueName);
    }

    public static void setTrueName(EntType type, String trueName) {
        EntTypeField.set(type, trueName);
    }

    public static String getTrueName(EntType type) {
        /*for (Trait trait : type.traits) {
            if(trait.personal instanceof ITrueName) {
                return ((ITrueName) trait.personal).getTrueName();
            }
        }*/

        String trueName = EntTypeField.get(type);
        if(trueName != null) {
            return trueName;
        }

        return type.getName(false);
    }

    public static String getTrueName(Ent ent) {
        return getTrueName(ent.entType);
    }
}
