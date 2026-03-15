package yourmod.pipes.item;

import com.tann.dice.gameplay.content.gen.pipe.regex.PipeRegexNamed;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNPref;
import com.tann.dice.gameplay.content.item.ItBill;
import com.tann.dice.gameplay.content.item.Item;
import com.tann.dice.gameplay.trigger.personal.item.copyItem.CopyAlliedItems;
import com.tann.dice.util.Tann;

public class PipeItemCopyItemTiers extends PipeRegexNamed<Item> {
    public static final PRNPart PREF = new PRNPref("emerald");
    private static final PRNPart SEP2 = PipeRegexNamed.COLON;

    public PipeItemCopyItemTiers() {
        super(PREF, UP_TO_THREE_DIGITS_TIER, SEP2, UP_TO_THREE_DIGITS_TIER);
    }

    @Override
    protected Item internalMake(String[] groups) {
        int min = Integer.parseInt(groups[0]);
        int max;
        //if (groups[1] == null){
        //    max = min;
        //} else {
        max = Integer.parseInt(groups[1]);
        //}
        return makeInternal(min, max);
    }

    private Item makeInternal(int min, int max) {
        return new ItBill(-69, PREF + Integer.toString(min) + SEP2 + Integer.toString(max)).prs(new CopyAlliedItems(min, max)).bItem();
    }

    @Override
    protected Item generateInternal(boolean wild) {

        int attempts = 5;
        for (int at = 0; at < attempts; ++at) {
            int min = (int)Tann.random(-4, 5);
            Item i = makeInternal(min, min + (int)Tann.random(0, 10));
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