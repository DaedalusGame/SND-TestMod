package yourmod.pipes.meta;

import com.tann.dice.gameplay.content.ent.type.HeroCol;
import com.tann.dice.gameplay.content.ent.type.HeroType;
import com.tann.dice.gameplay.content.ent.type.lib.HeroTypeLib;
import com.tann.dice.gameplay.content.gen.pipe.regex.PipeRegexNamed;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNPref;
import com.tann.dice.util.Tann;

public class PipeMetaThingLevelUpHero extends PipeRegexNamed<MetaThing> {
    static PRNPart PREF = new PRNPref("levelup");

    public PipeMetaThingLevelUpHero(PRNPart... parts) {
        super(PREF, HEROCOL);
    }

    @Override
    protected MetaThing internalMake(String[] groups) {
        String heroCol = groups[0];
        return makeInternal(heroCol);
    }

    private MetaThing makeInternal(HeroCol col) {
        if (col == null) {
            return null;
        } else {
            return new MetaThingLevelUpHero(""+PREF+col.shortName(), col);
        }
    }

    private MetaThing makeInternal(String heroCol) {
        HeroCol col = HeroCol.byName(heroCol);

        return makeInternal(col);
    }

    @Override
    public MetaThing example() {
        return makeInternal(Tann.random(HeroCol.values()));
    }
}
