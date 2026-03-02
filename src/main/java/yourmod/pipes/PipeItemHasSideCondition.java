package yourmod.pipes;

import com.tann.dice.gameplay.content.ent.die.side.EnSiBi;
import com.tann.dice.gameplay.content.ent.die.side.EntSide;
import com.tann.dice.gameplay.content.gen.pipe.regex.PipeRegexNamed;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNPref;
import com.tann.dice.gameplay.content.item.ItBill;
import com.tann.dice.gameplay.content.item.Item;
import com.tann.dice.gameplay.content.item.ItemLib;
import com.tann.dice.gameplay.trigger.personal.Personal;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.AffectSides;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.condition.HasKeyword;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.condition.SpecificSidesType;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.FlatBonus;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.ReplaceWith;
import com.tann.dice.gameplay.trigger.personal.linked.stateCondition.PersonalConditionLink;
import yourmod.conditionalBonus.HasSideConditionalRequirement;
import yourmod.conditionalBonus.PoisonConditionalRequirement;
import yourmod.effect.PermanentPersonal;
import yourmod.effect.PersonalConditionLinkEx;

public class PipeItemHasSideCondition extends PipeRegexNamed<Item> {
    public static final PRNPart PREF = new PRNPref("hassidecond");

    public PipeItemHasSideCondition() {
        super(PREF, ITEM);
    }

    @Override
    protected Item internalMake(String[] groups) {
        return this.makeInternal(groups[0]);
    }

    private Item makeInternal(String srcName) {
        Item src = ItemLib.byName(srcName);

        if (src.isMissingno()) {
            return null;
        } else {
            Personal personal = src.getSinglePersonalOrNull();
            if(personal instanceof AffectSides) {
                AffectSides affectSides = (AffectSides) personal;

                PersonalConditionLink link = new PersonalConditionLinkEx(new HasSideConditionalRequirement(affectSides.getConditions().get(0),false), new AffectSides(new FlatBonus(1)));

                return (new ItBill(0, PREF + src.getName(false), "special/sticker"))
                        .prs(link)
                        .bItem();
            }

            return null;
        }
    }

    @Override
    protected Item generateInternal(boolean wild) {
        /*int attempts = 5;
        for (int at = 0; at < attempts; ++at) {
            Item i = makeInternal(Tann.random(Keyword.values()));
            if (i == null || i.getTier() == 0) continue;
            return i;
        }*/
        return null;
    }

    @Override
    public boolean isHiddenAPI() {
        return true;
    }

    @Override
    public Item example() {
        return this.generateInternal(false);
    }

    @Override
    public boolean showHigher() {
        return true;
    }
}
