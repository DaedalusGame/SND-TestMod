package yourmod.pipes;

import com.bord.dice.modthedice.lib.SpirePatch2;
import com.tann.dice.gameplay.content.ent.EntSize;
import com.tann.dice.gameplay.content.ent.die.side.blob.ESB;
import com.tann.dice.gameplay.content.ent.type.HeroCol;
import com.tann.dice.gameplay.content.ent.type.HeroType;
import com.tann.dice.gameplay.content.ent.type.MonsterType;
import com.tann.dice.gameplay.content.ent.type.bill.HTBill;
import com.tann.dice.gameplay.content.ent.type.bill.MTBill;
import com.tann.dice.gameplay.content.ent.type.lib.HeroTypeLib;
import com.tann.dice.gameplay.content.ent.type.lib.MonsterTypeLib;
import com.tann.dice.gameplay.content.gen.pipe.entity.monster.PipeMonsterGenerated;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import yourmod.Mod;
import yourmod.init.PipeMods;
import yourmod.pipes.meta.PipeMetaThing;

import java.util.List;
import java.util.Random;

@SpirePatch2(clz = PipeMonsterGenerated.class, method = "randomMonster")
public class PipeMonsterGeneratedPatch {
    public static List<MonsterType> injectMetaMonsterTypes(List<MonsterType> monTypes, EntSize size) {
        boolean checkSize = size != null;
        if(size == null) size = EntSize.reg;

        if(Mod.metaStorage.hasPin(PipeMetaThing.clearMonGenPool)){
            monTypes.clear();
            monTypes.add(new MTBill(size).name("noname").sides(ESB.blank,ESB.blank,ESB.blank,ESB.blank,ESB.blank,ESB.blank).bEntType());
        }

        for (String meta : Mod.metaStorage.getPins()) {
            MonsterType monType = MonsterTypeLib.safeByName(meta);
            if(!monType.isMissingno() && (checkSize && monType.size == size)) {
                monTypes.add(monType);
            }
        }

        return monTypes;
    }

    public static ExprEditor Instrument()
    {
        return new ExprEditor() {
            @Override
            public void edit(MethodCall m) throws CannotCompileException {
                if(m.getMethodName().equals("getMasterCopy") || m.getMethodName().equals("getWithSize")) {
                    m.replace("{" +
                            "$_ = yourmod.pipes.PipeMonsterGeneratedPatch.injectMetaMonsterTypes($proceed($$), size);" +
                            "}");
                }
            }
        };
    }
}
