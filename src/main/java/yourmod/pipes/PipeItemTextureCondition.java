package yourmod.pipes;

import com.tann.dice.gameplay.content.ent.die.side.EnSiBi;
import com.tann.dice.gameplay.content.ent.die.side.EntSide;
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
import com.tann.dice.gameplay.trigger.personal.affectSideModular.AffectSides;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.condition.TextureCondition;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.ChangeType;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.FlatBonus;
import yourmod.effect.ChangeTypeEx;

import java.util.List;

public class PipeItemTextureCondition extends PipeRegexNamed<Item> {
    public static final PRNPart PREF = new PRNPref("texconddata");

    public PipeItemTextureCondition() {
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

    public static EntSideState getLeftmostBlankDerived(HeroType ht) {
        List<EntSideState> states = ht.makeEnt().getBlankState().getAllSideStates();
        return ((EntSideState)states.get(2));
    }

    private Item makeInternal(HeroType ht) {
        EntSideState entSide = getLeftmostBlankDerived(ht);
        EntSide original = entSide.getOriginal().withValue(-999);

        AffectSides affectSides = new AffectSides(new TextureCondition(original, "these"), new FlatBonus(1));
        return new ItBill(PREF + ht.getName()).prs(affectSides).bItem();
    }

    public Item example() {
        return this.makeInternal(HeroTypeUtils.random());
    }

    public boolean isComplexAPI() {
        return true;
    }
}
