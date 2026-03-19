package yourmod;

import com.bord.dice.modthedice.lib.SpirePatch2;
import com.tann.dice.gameplay.context.DungeonContext;
import com.tann.dice.gameplay.modifier.Modifier;
import com.tann.dice.gameplay.modifier.ModifierLib;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.util.ArrayList;
import java.util.List;

@SpirePatch2(clz = DungeonContext.class, method = "onFirstInit")
public class DungeonContextPatch {
    /*ublic static DungeonContext Postfix(DungeonContext __instance) {
        //__result.addModifier(ModifierLib.byName("allitem.k.pain"));
        //return __result;
    }*/

    public static List<Modifier> changeModifiers(List<Modifier> modifiers) {
        modifiers = new ArrayList<>(modifiers);
        for (String meta : Mod.metaStorage.getPins()) {
            Modifier modifier = ModifierLib.safeByName(meta);
            if(modifier != null && !modifier.isMissingno()) {
                modifiers.add(modifier);
            }
        }

        return modifiers;
    }

    public static ExprEditor Instrument()
    {
        return new ExprEditor() {
            @Override
            public void edit(MethodCall m) throws CannotCompileException {
                if(m.getMethodName().equals("getStartingModifiers")) {
                    m.replace("{" +
                            "$_ = yourmod.DungeonContextPatch.changeModifiers($proceed($$));" +
                            "}");
                }
            }
        };
    }
}
