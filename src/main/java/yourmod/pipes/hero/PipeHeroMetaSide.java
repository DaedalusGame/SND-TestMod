package yourmod.pipes.hero;

import com.tann.dice.gameplay.content.ent.EntSize;
import com.tann.dice.gameplay.content.ent.die.side.EntSide;
import com.tann.dice.gameplay.content.ent.die.side.EntSidesLib;
import com.tann.dice.gameplay.content.ent.die.side.blob.ESB;
import com.tann.dice.gameplay.content.ent.type.EntType;
import com.tann.dice.gameplay.content.ent.type.HeroType;
import com.tann.dice.gameplay.content.ent.type.lib.HeroTypeLib;
import com.tann.dice.gameplay.content.ent.type.lib.HeroTypeUtils;
import com.tann.dice.gameplay.content.gen.pipe.entity.hero.side.PipeHeroSides;
import com.tann.dice.gameplay.content.gen.pipe.regex.PipeRegexNamed;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNMid;
import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.fightLog.EntSideState;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.condition.SpecificSidesType;
import com.tann.dice.util.Tann;
import yourmod.pipes.PRNMetaSide;
import yourmod.pipes.meta.MetaThingLib;
import yourmod.pipes.meta.MetaThingSide;

import java.util.List;
import java.util.Locale;

public class PipeHeroMetaSide extends PipeRegexNamed<HeroType> {
    static PRNPart MID = new PRNMid("metasd");
    static PRNPart SEP = prnS(":");
    static PRNMetaSide SIDE = new PRNMetaSide();

    public PipeHeroMetaSide() {
        super(HERO, MID, SIDE_POSITION, COLON, SIDE);
    }

    @Override
    protected HeroType internalMake(String[] groups) {
        HeroType ht = HeroTypeLib.byName(groups[0]);
        SpecificSidesType sst = SpecificSidesType.byName(groups[1]);
        String datum = groups[2];
        return this.make(ht, sst, datum);
    }

    @Override
    public HeroType example() {
        return null;
    }

    private EntSide getSide(String key) {
        for (Object o : MetaThingLib.getPinThings()) {
            if (!(o instanceof MetaThingSide)) {
                continue;
            }
            MetaThingSide metaThingSide = (MetaThingSide) o;
            if(metaThingSide.id.equalsIgnoreCase(key)) {
                return metaThingSide.entSide;
            }
        }

        return ESB.blankBug;
    }

    private HeroType make(HeroType src, SpecificSidesType sst, String datum) {
        if (src.isMissingno()) {
            return null;
        } else {
            String[] parts = datum.split("-");
            int val = 0;
            if (parts.length == 2) {
                if (!Tann.isInt(parts[1])) {
                    return null;
                }

                val = Integer.parseInt(parts[1]);
            }

            EntSide es = getSide(parts[0]).withValue(val);
            if (es == null) {
                return null;
            }

            List<EntSideState> srcStates = src.makeEnt().getBlankState().getAllSideStates();
            EntSide[] newSides = new EntSide[6];

            for(int i = 0; i < 6; ++i) {
                EntSideState existing = srcStates.get(i);
                Eff ecf = existing.getCalculatedEffect().innatifyKeywords();
                if (Tann.contains(sst.sideIndices, i)) {
                    newSides[i] = es;
                } else {
                    newSides[i] = new EntSide(existing.getCalculatedTexture(), ecf, EntSize.reg, existing.getHsl());
                }
            }

            EntType.realToNice(newSides);
            String realHeroName = src.getName(false) + MID + sst.getShortName() + SEP + datum;
            return HeroTypeUtils.copy(src).sides(newSides).name(realHeroName).bEntType();
        }
    }
}
