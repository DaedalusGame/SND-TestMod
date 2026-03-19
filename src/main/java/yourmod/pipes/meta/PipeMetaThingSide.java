package yourmod.pipes.meta;

import com.tann.dice.gameplay.content.ent.EntSize;
import com.tann.dice.gameplay.content.ent.die.side.EnSiBi;
import com.tann.dice.gameplay.content.ent.die.side.EntSide;
import com.tann.dice.gameplay.content.ent.type.HeroCol;
import com.tann.dice.gameplay.content.ent.type.HeroType;
import com.tann.dice.gameplay.content.ent.type.lib.HeroTypeLib;
import com.tann.dice.gameplay.content.ent.type.lib.HeroTypeUtils;
import com.tann.dice.gameplay.content.gen.pipe.regex.PipeRegexNamed;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNPref;
import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.fightLog.EntSideState;
import com.tann.dice.util.Tann;
import yourmod.pipes.item.PipeItemChangeType;

public class PipeMetaThingSide extends PipeRegexNamed<MetaThing> {
    static PRNPart PREF = new PRNPref("sd");

    public PipeMetaThingSide(PRNPart... parts) {
        super(PREF, NAME, COLON, HERO);
    }

    @Override
    protected MetaThing internalMake(String[] groups) {
        String id = groups[0];
        String hero = groups[1];
        return makeInternal(id, hero);
    }

    private MetaThing makeInternal(String id, String str) {
        if (bad(str) || bad(id)) {
            return null;
        } else {
            HeroType ht = HeroTypeLib.byName(str);
            return makeInternal(id, ht);
        }
    }

    private MetaThingSide makeInternal(String id, HeroType ht) {
        EntSideState entSide = PipeItemChangeType.getLeftmostBlankDerived(ht);
        Eff eff = entSide.getCalculatedEffect();
        EntSide newSide = new EntSide(entSide.getCalculatedTexture(), eff, entSide.getOriginal().size, entSide.getHsl());

        return new MetaThingSide("" + PREF + id + COLON + ht.getName(false), id, newSide);
    }

    @Override
    public MetaThing example() {
        return makeInternal(Tann.randomString(4),HeroTypeUtils.random());
    }
}