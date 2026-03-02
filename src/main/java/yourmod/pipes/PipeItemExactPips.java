package yourmod.pipes;

import com.tann.dice.gameplay.content.gen.pipe.regex.PipeRegexNamed;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNPref;
import com.tann.dice.gameplay.content.item.ItBill;
import com.tann.dice.gameplay.content.item.Item;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.AffectSides;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.condition.ExactlyCondition;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.FlatBonus;
import yourmod.effect.DivideEffect;

public class PipeItemExactPips extends PipeRegexNamed<Item> {
    public static final PRNPart PREF = new PRNPref("exact");

    public PipeItemExactPips() {
        super(PREF, UP_TO_TWO_DIGITS);
    }

    @Override
    protected Item internalMake(String[] groups) {
        int pips = Integer.parseInt(groups[0]);
        return makeInternal(pips);
    }

    private Item makeInternal(int pips) {
        return new ItBill(0, PREF + Integer.toString(pips), "camomile").prs(new AffectSides(new ExactlyCondition(pips),new FlatBonus(1))).bItem();
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
    public Item example() {
        return this.generateInternal(false);
    }

    @Override
    public boolean showHigher() {
        return true;
    }
}
