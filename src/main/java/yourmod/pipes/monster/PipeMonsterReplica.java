package yourmod.pipes.monster;

import com.tann.dice.gameplay.content.ent.Ent;
import com.tann.dice.gameplay.content.ent.EntSize;
import com.tann.dice.gameplay.content.ent.die.side.EntSide;
import com.tann.dice.gameplay.content.ent.type.EntType;
import com.tann.dice.gameplay.content.ent.type.HeroType;
import com.tann.dice.gameplay.content.ent.type.MonsterType;
import com.tann.dice.gameplay.content.ent.type.bill.HTBill;
import com.tann.dice.gameplay.content.ent.type.bill.MTBill;
import com.tann.dice.gameplay.content.ent.type.lib.EntTypeUtils;
import com.tann.dice.gameplay.content.ent.type.lib.HeroTypeLib;
import com.tann.dice.gameplay.content.ent.type.lib.HeroTypeUtils;
import com.tann.dice.gameplay.content.ent.type.lib.MonsterTypeLib;
import com.tann.dice.gameplay.content.gen.pipe.regex.PipeRegexNamed;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNPref;
import com.tann.dice.gameplay.effect.Trait;
import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.fightLog.EntSideState;
import com.tann.dice.gameplay.trigger.personal.RenameHero;
import yourmod.TrueNameUtils;

import java.util.List;

public class PipeMonsterReplica extends PipeRegexNamed<MonsterType> {
    public static final PRNPref PREF = new PRNPref("replica");

    public PipeMonsterReplica() {
        super(PREF, MONSTER);
    }

    protected MonsterType internalMake(String[] groups) {
        String heroName = groups[0];
        return this.make(MonsterTypeLib.byName(heroName));
    }

    private MonsterType make(MonsterType src) {
        if (src.isMissingno()) {
            return null;
        } else {
            String name = PREF + src.getName();
            MTBill htb = EntTypeUtils.copy(src).clearTraits().name(name).sides(sidesFromHero(src.makeEnt()));
            String disp = src.getName(true, false);
            TrueNameUtils.setTrueName(htb, disp);
            if (!disp.equals(src.getName())) {
                htb.trait(new RenameHero(disp));
            }

            return htb.bEntType();
        }
    }

    public static EntSide[] sidesFromHero(Ent h) {
        List<EntSideState> srcStates = h.getBlankState().getAllSideStates();
        EntSide[] newSides = new EntSide[6];

        for(int i = 0; i < 6; ++i) {
            EntSideState existing = srcStates.get(i);
            Eff ecf = existing.getCalculatedEffect();
            newSides[i] = new EntSide(existing.getCalculatedTexture(), ecf, h.getSize(), existing.getHsl());
        }

        EntType.realToNice(newSides);
        return newSides;
    }

    public MonsterType example() {
        return this.make(MonsterTypeLib.random());
    }

    public boolean isComplexAPI() {
        return true;
    }
}