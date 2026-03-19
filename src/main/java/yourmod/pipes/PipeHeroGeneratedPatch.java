package yourmod.pipes;

import com.bord.dice.modthedice.lib.SpirePatch2;
import com.tann.dice.gameplay.content.ent.type.HeroCol;
import com.tann.dice.gameplay.content.gen.pipe.entity.hero.generate.PipeHeroGenerated;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import yourmod.pipes.meta.MetaThingLevelUpHero;
import yourmod.pipes.meta.MetaThingLib;

public class PipeHeroGeneratedPatch {
    public static boolean canLevelUpColor(HeroCol col) {
        for (Object o : MetaThingLib.getPinThings()) {
            if(o instanceof MetaThingLevelUpHero && ((MetaThingLevelUpHero) o).getHeroColor() == col) {
                return true;
            }
        }

        return false;
    }

    @SpirePatch2(clz = PipeHeroGenerated.class, method = "generate", paramtypez = {HeroCol.class, int.class, long.class})
    public static class Generate {
        public static ExprEditor Instrument()
        {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if(m.getMethodName().equals("isBasic")) {
                        m.replace("{" +
                                "$_ = $proceed($$) || yourmod.pipes.PipeHeroGeneratedPatch.canLevelUpColor(col);" +
                                "}");
                    }
                }
            };
        }
    }
}
