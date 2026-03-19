package yourmod.pipes.meta;

import com.tann.dice.gameplay.content.gen.pipe.item.PipeItem;
import com.tann.dice.gameplay.content.gen.pipe.mod.PipeMod;
import com.tann.dice.gameplay.content.item.Item;
import com.tann.dice.gameplay.modifier.Modifier;
import com.tann.dice.util.ui.resolver.MetaResolver;
import yourmod.Mod;
import yourmod.screen.MetaPageType;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class MetaThingLib {
    private static List<Object> cache;

    @Nonnull
    public static MetaThing byName(String name) {
        return PipeMetaThing.fetch(name);
    }

    public static MetaThing getMissingno() {
        return PipeMetaThing.getMissingno();
    }

    public static List<MetaThing> search(String search) {
        return new ArrayList<>();
    }

    public static void clearCache() {
        cache = null;
    }

    public static List<Object> getPinThings() {
        if(cache == null) {
            cache = new ArrayList<>();
            MetaResolver resolver = new MetaPageType.Resolver(){
                @Override
                public void resolve(Object o) {
                    cache.add(o);
                }
            };

            for (String pin : Mod.metaStorage.getPins()) {
                resolver.debugResolve(pin);
            }
        }

        return cache;
    }
}
