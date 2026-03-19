package yourmod;

import com.bord.dice.modthedice.lib.SpirePatch2;
import com.bord.dice.modthedice.lib.SpireReturn;
import com.tann.dice.gameplay.save.antiCheese.AnticheeseData;
import yourmod.init.PipeMods;
import yourmod.pipes.meta.PipeMetaThing;
import yourmod.screen.MetaStorage;

@SpirePatch2(clz = AnticheeseData.class, method = "canReroll")
public class AnticheeseDataPatch {
    public static SpireReturn<Boolean> Prefix() {
        if(Mod.metaStorage.hasPin(PipeMetaThing.rerollRuns)) {
            return SpireReturn.Return(true);
        }
        return SpireReturn.Continue();
    }
}
