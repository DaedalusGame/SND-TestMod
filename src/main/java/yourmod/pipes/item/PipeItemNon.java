package yourmod.pipes.item;

import basemod.util.ReflectionUtils;
import com.tann.dice.gameplay.content.gen.pipe.regex.PipeRegexNamed;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNPref;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNSuff;
import com.tann.dice.gameplay.content.item.ItBill;
import com.tann.dice.gameplay.content.item.Item;
import com.tann.dice.gameplay.content.item.ItemLib;
import com.tann.dice.gameplay.effect.Buff;
import com.tann.dice.gameplay.effect.eff.conditionalBonus.conditionalRequirement.ConditionalRequirement;
import com.tann.dice.gameplay.effect.eff.conditionalBonus.conditionalRequirement.NotRequirement;
import com.tann.dice.gameplay.trigger.personal.Personal;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.AffectSides;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.condition.AffectSideCondition;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.condition.NotCondition;
import com.tann.dice.gameplay.trigger.personal.linked.stateCondition.PersonalConditionLink;
import yourmod.effect.PersonalConditionLinkEx;

import java.util.ArrayList;
import java.util.List;

public class PipeItemNon
        extends PipeRegexNamed<Item> {
    public static final PRNPart PREF = new PRNPref("non");

    public PipeItemNon() {
        super(PREF, ITEM);
    }

    @Override
    protected Item internalMake(String[] groups) {
        String itemS = groups[0];
        return makeInternal(ItemLib.byName(itemS));
    }

    private List<AffectSideCondition> non(List<AffectSideCondition> in) {
        ArrayList<AffectSideCondition> out = new ArrayList<>();

        for (AffectSideCondition cond : in) {
            out.add(new NotCondition(cond));
        }

        return out;
    }

    private PersonalConditionLink non(PersonalConditionLink in) {
        ConditionalRequirement req = ReflectionUtils.readField(ConditionalRequirement.class, PersonalConditionLink.class, in, "req");
        Personal linked = ReflectionUtils.readField(Personal.class, PersonalConditionLink.class, in, "linked");

        return new PersonalConditionLinkEx(new NotRequirement(req), linked);
    }

    private Item makeInternal(Item item) {
        if (item == null || item.isMissingno()) {
            return null;
        }
        Personal personal = item.getSinglePersonalOrNull();

        if(personal instanceof AffectSides) {
            AffectSides p = new AffectSides(non(((AffectSides) personal).getConditions()), ((AffectSides) personal).getEffects());
            return new ItBill(0, PREF + item.getName(false), "special/combined").prs(p).bItem();
        }
        if(personal instanceof PersonalConditionLink) {
            return new ItBill(0, PREF + item.getName(false), "special/combined").prs(non((PersonalConditionLink) personal)).bItem();
        }

        return null;
    }

    @Override
    public Item example() {
        return makeInternal(ItemLib.byName("Door"));
    }

    @Override
    public boolean showHigher() {
        return true;
    }
}