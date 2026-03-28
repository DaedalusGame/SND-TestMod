package yourmod;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bord.dice.modthedice.lib.SpirePatch2;
import com.tann.dice.gameplay.content.ent.type.EntType;
import com.tann.dice.gameplay.content.ent.type.blob.heroblobs.HeroTypeBlobBlue;
import com.tann.dice.gameplay.content.ent.type.lib.HeroTypeLib;
import com.tann.dice.screens.dungeon.panels.entPanel.EntPanelCombat;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;

import java.util.Objects;

/*@SpirePatch2(clz = EntPanelCombat.class, method = "draw")
public class EntPanelCombatPatch {
    public static TextureRegion getPortrait(EntType entType) {
        return HeroTypeLib.byName("mage").portrait;
    }

    public static ExprEditor Instrument() {
        return new ExprEditor() {
            @Override
            public void edit(FieldAccess f) throws CannotCompileException {
                if(f.isReader() && Objects.equals(f.getFieldName(), "portrait")) {
                    f.replace("{" +
                            "$_ = yourmod.EntPanelCombatPatch.getPortrait($0);" +
                            "}");
                }
            }
        };
    }
}*/
