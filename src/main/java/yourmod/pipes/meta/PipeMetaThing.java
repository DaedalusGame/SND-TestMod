package yourmod.pipes.meta;

import basemod.pipes.PipeMasterHidden;
import com.tann.dice.gameplay.content.gen.pipe.Pipe;
import com.tann.dice.gameplay.content.gen.pipe.mod.PipeCache;
import com.tann.dice.gameplay.content.item.ItBill;
import com.tann.dice.gameplay.content.item.Item;
import com.tann.dice.gameplay.content.item.ItemLib;
import com.tann.dice.gameplay.effect.eff.EffBill;
import com.tann.dice.gameplay.modifier.Modifier;
import com.tann.dice.gameplay.trigger.global.GlobalDescribeOnly;
import com.tann.dice.gameplay.trigger.personal.OnOverheal;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class PipeMetaThing {
    private static MetaThing MISSINGNO_REAL;
    private static final String MISSINGNO_NAME = "err";

    public static String rerollRuns = "Reroll Runs";
    public static String clearGenPool = "Clear genpool";
    public static String clearMonGenPool = "Clear rmonpool";

    private static PipeCache<MetaThing> pmc;
    public static List<Pipe<MetaThing>> pipes;

    public static void init() {
        pipes = new ArrayList<>();
        pipes.add(pmc = new PipeCache<>());
        pipes.add(new PipeMasterHidden<>(getMetaThings()));
        pipes.add(new PipeMetaThingLevelUpHero());
        pipes.add(new PipeMetaThingSide());
        MISSINGNO_REAL = new MetaThing(MISSINGNO_NAME);
    }

    private static List<MetaThing> getMetaThings() {
        ArrayList<MetaThing> list = new ArrayList<>();
        list.add(new MetaThingDescOnly(rerollRuns, "Can always reroll runs"));
        list.add(new MetaThingDescOnly(clearGenPool,"Clear hero side generation pool"));
        list.add(new MetaThingDescOnly(clearMonGenPool, "Clear monster side generation pool"));
        return list;
    }

    public static MetaThing getMissingno() {
        return MISSINGNO_REAL;
    }

    @Nonnull
    public static MetaThing fetch(String name) {
        return !Pipe.DANGEROUS_NONMODIFIER_PIPE_CHARS.matcher(name).matches() ? getMissingno() : Pipe.checkPipes(pipes, name, pmc, getMissingno());
    }

    @Nonnull
    public static MetaThing cacheReturn(MetaThing i) {
        return MetaThingLib.byName(i.getName());
    }

    public static MetaThing byCache(String text) {
        return pmc.get(text);
    }

    public static void clearCache() {
        pmc.cc();
    }
}
