package yourmod.pipes;

import com.bord.dice.modthedice.lib.SpirePatch2;
import com.tann.dice.gameplay.content.ent.die.side.EntSidesLib;
import com.tann.dice.gameplay.content.ent.die.side.blob.ESB;
import com.tann.dice.gameplay.content.ent.type.HeroCol;
import com.tann.dice.gameplay.content.ent.type.HeroType;
import com.tann.dice.gameplay.content.ent.type.bill.HTBill;
import com.tann.dice.gameplay.content.ent.type.lib.HeroTypeLib;
import com.tann.dice.gameplay.content.ent.type.lib.HeroTypeUtils;
import com.tann.dice.gameplay.context.DungeonContext;
import com.tann.dice.gameplay.modifier.Modifier;
import com.tann.dice.gameplay.modifier.ModifierLib;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import yourmod.Mod;
import yourmod.init.PipeMods;
import yourmod.pipes.meta.PipeMetaThing;
import yourmod.screen.MetaStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class HeroTypeUtilsPatch {
    public static List<HeroType> injectMetaHeroTypes(List<HeroType> heroTypes, boolean forHeroGen) {
        if(forHeroGen && Mod.metaStorage.hasPin(PipeMetaThing.clearGenPool)){
            heroTypes.clear();
            for (HeroCol heroCol: HeroCol.basics()) {
                heroTypes.add(new HTBill(heroCol, 1).name("noname").sides(ESB.blank,ESB.blank,ESB.blank,ESB.blank,ESB.blank,ESB.blank).bEntType());
            }
        }

        for (String meta : Mod.metaStorage.getPins()) {
            HeroType heroType = HeroTypeLib.safeByName(meta);
            if(!heroType.isMissingno()) {
                heroTypes.add(heroType);
            }
        }

        return heroTypes;
    }

    @SpirePatch2(clz = HeroTypeUtils.class, method = "getFilteredTypes")
    public static class GetFilteredTypes {
        public static ExprEditor Instrument()
        {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if(m.getMethodName().equals("getMasterCopy")) {
                        m.replace("{" +
                                "$_ = yourmod.pipes.HeroTypeUtilsPatch.injectMetaHeroTypes($proceed($$), true);" +
                                "}");
                    }
                }
            };
        }
    }

    @SpirePatch2(clz = HeroTypeUtils.class, method = "random", paramtypez = {})
    public static class Random {


        public static ExprEditor Instrument()
        {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if(m.getMethodName().equals("getMasterCopy")) {
                        m.replace("{" +
                                "$_ = yourmod.pipes.HeroTypeUtilsPatch.injectMetaHeroTypes($proceed($$), false);" +
                                "}");
                    }
                }
            };
        }
    }

    @SpirePatch2(clz = HeroTypeUtils.class, method = "random", paramtypez = {java.util.Random.class})
    public static class Random2 {

        public static ExprEditor Instrument()
        {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if(m.getMethodName().equals("getMasterCopy")) {
                        m.replace("{" +
                                "$_ = yourmod.pipes.HeroTypeUtilsPatch.injectMetaHeroTypes($proceed($$), false);" +
                                "}");
                    }
                }
            };
        }
    }
}
