package yourmod.pipes.item;

import com.tann.dice.gameplay.content.gen.pipe.regex.PipeRegexNamed;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNPref;
import com.tann.dice.gameplay.content.item.ItBill;
import com.tann.dice.gameplay.content.item.Item;
import com.tann.dice.gameplay.content.ent.type.HeroCol;
import com.tann.dice.gameplay.trigger.personal.Personal;
import com.tann.dice.gameplay.trigger.personal.hp.MaxHP;
import com.tann.dice.gameplay.trigger.personal.linked.stateCondition.ColLink;
import com.tann.dice.util.Tann;

public class PipeItemColorRestriction extends PipeRegexNamed<Item> {
    public static final PRNPart PREF = new PRNPref("colres");

    public PipeItemColorRestriction() {
        super(PREF, HEROCOL);
    }

    @Override
    protected Item internalMake(String[] groups) {
        HeroCol col = HeroCol.byName(groups[0]);
        return makeInternal(col);
    }

    private Item makeInternal(HeroCol col) {
        return new ItBill(-69, PREF + col.shortName()).prs((Personal)new ColLink(col, (Personal)new MaxHP(1))).bItem();
    }

    @Override
    protected Item generateInternal(boolean wild) {

        int attempts = 5;
        for (int at = 0; at < attempts; ++at) {
            Item i = makeInternal((HeroCol)Tann.random((Object[])HeroCol.values()));
            if (i == null) continue;
            return i;
        }

        return null;
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
