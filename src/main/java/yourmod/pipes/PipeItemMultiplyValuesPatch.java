package yourmod.pipes;

import com.bord.dice.modthedice.lib.SpirePatch;
import com.bord.dice.modthedice.lib.SpirePatch2;
import com.tann.dice.gameplay.content.gen.pipe.item.PipeItemMultiplyValues;
import com.tann.dice.gameplay.content.gen.pipe.regex.PipeRegexNamed;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNMid;
import javassist.CannotCompileException;
import javassist.expr.ConstructorCall;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class PipeItemMultiplyValuesPatch {
    public static PRNPart[] replacePRN(PRNPart[] __existing) {
        return new PRNPart[] {
                PipeRegexNamed.ITEM,
                new PRNMid("m"),
                PipeRegexNamed.UP_TO_THREE_DIGITS
        };
    }

    @SpirePatch2(clz = PipeItemMultiplyValues.class, method = SpirePatch.CONSTRUCTOR)
    public static class Constructor {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(ConstructorCall c) throws CannotCompileException {
                    if(c.isSuper()) {
                        c.replace("{" +
                                "$_ = $proceed(yourmod.pipes.PipeItemMultiplyValuesPatch.replacePRN($$));" +
                                "}");
                    }
                }
            };
        }
    }
}
