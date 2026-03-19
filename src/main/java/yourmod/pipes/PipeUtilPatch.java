package yourmod.pipes;

import com.bord.dice.modthedice.lib.SpirePatch2;
import com.tann.dice.gameplay.content.gen.pipe.PipeUtils;
import yourmod.pipes.meta.PipeMetaThing;

@SpirePatch2(clz = PipeUtils.class, method = "init")
public class PipeUtilPatch {
    public static void Postfix() {
        PipeMetaThing.init();
    }
}
