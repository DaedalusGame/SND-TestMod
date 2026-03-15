package yourmod.pipes;

import com.bord.dice.modthedice.lib.SpirePatch2;
import com.tann.dice.gameplay.content.ent.type.HeroType;
import com.tann.dice.gameplay.content.gen.pipe.entity.hero.PipeHeroReplica;
import com.tann.dice.gameplay.effect.Buff;
import com.tann.dice.gameplay.effect.Trait;
import com.tann.dice.gameplay.fightLog.EntState;
import yourmod.effect.TrueName;

@SpirePatch2(
        clz = PipeHeroReplica.class,
        method = "make"
)
public class PipeHeroReplicaPatch {
    public static HeroType Postfix(HeroType __result, HeroType src)
    {
        __result.traits.add(new Trait(new TrueName(src.getName(true, false))));
        return __result;
    }
}
