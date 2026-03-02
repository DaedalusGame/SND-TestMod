package yourmod.pipes;

import com.tann.dice.gameplay.content.gen.pipe.regex.PipeRegexNamed;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNSuff;
import com.tann.dice.gameplay.content.item.ItBill;
import com.tann.dice.gameplay.content.item.Item;
import com.tann.dice.gameplay.content.item.ItemLib;
import com.tann.dice.gameplay.trigger.personal.Personal;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.AffectSides;

public class PipeItemBuffPriority
        extends PipeRegexNamed<Item> {
    public static final PRNPart PREF = new PRNSuff("buffprio");

    public PipeItemBuffPriority() {
        super(ITEM, PREF);
    }

    @Override
    protected Item internalMake(String[] groups) {
        String itemS = groups[0];
        return makeInternal(ItemLib.byName(itemS));
    }

    private Item makeInternal(Item item) {
        if (item == null || item.isMissingno()) {
            return null;
        }
        Personal personal = item.getSinglePersonalOrNull();

        if(personal instanceof AffectSides)
        {
            AffectSides p = new AffectSides(((AffectSides) personal).getConditions(), ((AffectSides) personal).getEffects());
            p.buffPriority();
            return new ItBill(0, item.getName(false) + PREF, "special/combined").prs(p).bItem();
        }

        return null;
    }

    @Override
    public Item example() {
        return makeInternal(ItemLib.byName("Eye of Horus"));
    }

    @Override
    public boolean showHigher() {
        return true;
    }
}