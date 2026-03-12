package yourmod.pipes;

import com.tann.dice.gameplay.content.ent.die.side.EnSiBi;
import com.tann.dice.gameplay.content.ent.die.side.EntSide;
import com.tann.dice.gameplay.content.ent.type.EntType;
import com.tann.dice.gameplay.content.ent.type.HeroType;
import com.tann.dice.gameplay.content.ent.type.lib.HeroTypeLib;
import com.tann.dice.gameplay.content.ent.type.lib.HeroTypeUtils;
import com.tann.dice.gameplay.content.gen.pipe.regex.PipeRegexNamed;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNPref;
import com.tann.dice.gameplay.content.item.ItBill;
import com.tann.dice.gameplay.content.item.Item;
import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.fightLog.EntSideState;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.ChangeType;
import yourmod.effect.ChangeTypeEx;

import java.util.List;

public class PipeItemChangeType extends PipeRegexNamed<Item> {
    public static final PRNPart PREF = new PRNPref("chtypedata");

    public PipeItemChangeType() {
        super(new PRNPart[]{PREF, ONHIT});
    }

    protected Item internalMake(String[] groups) {
        String heroName = groups[0];
        if (heroName == null) {
            return null;
        } else {
            HeroType ht = HeroTypeLib.byName(heroName);
            return ht.isMissingno() ? null : this.makeInternal(ht);
        }
    }

    public static EntSideState getLeftmostBlankDerived(EntType ht) {
        List<EntSideState> states = ht.makeEnt().getBlankState().getAllSideStates();
        return ((EntSideState)states.get(2));
    }

    private Item makeInternal(HeroType ht) {
        EntSideState entSide = getLeftmostBlankDerived(ht);
        EntSide original = entSide.getOriginal();
        Eff eff = entSide.getCalculatedEffect();
        EnSiBi ensibi = new EnSiBi().effect(eff).size(entSide.getOriginal().size);

        int val = eff.getValue();

        Eff effForDesc = entSide.getCalculatedEffect().copy();
        if(effForDesc.getValue() != -999) effForDesc.setValue(69);

        String desc = effForDesc.describe().replace("69 ", "");
        ChangeType changeType = new ChangeTypeEx(ensibi, desc, val == -999 ? 1 : val, original);
        return new ItBill(PREF + ht.getName()).prs(changeType).bItem();
    }

    public Item example() {
        return this.makeInternal(HeroTypeUtils.random());
    }

    public boolean isComplexAPI() {
        return true;
    }
}