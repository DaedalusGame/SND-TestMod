package yourmod.pipes.meta;

import com.badlogic.gdx.graphics.Color;
import com.tann.dice.gameplay.content.gen.pipe.Pipe;
import com.tann.dice.gameplay.content.gen.pipe.mod.PipeMod;
import com.tann.dice.gameplay.modifier.Modifier;
import com.tann.dice.gameplay.modifier.ModifierLib;
import com.tann.dice.util.Colours;
import com.tann.dice.util.ui.resolver.Resolver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class MetaThingResolver extends Resolver<MetaThing> {
    public MetaThingResolver() {
        super(new Comparator<MetaThing>() {
            public int compare(MetaThing o1, MetaThing o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    protected MetaThing byName(String text) {
        MetaThing m = MetaThingLib.byName(text);
        if (!m.isMissingno()) {
            return m;
        } else {
            MetaThing of = this.onFail(text);
            return of;
        }
    }

    protected MetaThing byCache(String text) {
        return PipeMetaThing.byCache(text);
    }

    protected List<MetaThing> search(String text) {
        Pipe.setupChecks();
        List<MetaThing> mods = MetaThingLib.search(text);
        Pipe.disableChecks();
        if (!mods.isEmpty()) {
            return mods;
        } else {
            Pipe.setupChecks();
            List<MetaThing> fs = this.failSearch(text);
            Pipe.disableChecks();
            return fs != null && !fs.isEmpty() ? fs : makeBlank();
        }
    }

    protected List<MetaThing> failSearch(String text) {
        return makeBlank();
    }

    protected static List<MetaThing> makeBlank() {
        return new ArrayList();
    }

    protected String getTypeName() {
        return "a metathing";
    }

    protected Color getCol() {
        return Colours.BLURPLE;
    }

    public MetaThing onFail(String failString) {
        return null;
    }
}
