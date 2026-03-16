package yourmod;

import com.bord.dice.modthedice.lib.SpirePatch2;
import com.tann.dice.Main;

@SpirePatch2(clz = Main.class, method = "setupJson")
public class MainLoadPatch {
    public static void Postfix() {
        Mod.loadMeta();
    }
}
