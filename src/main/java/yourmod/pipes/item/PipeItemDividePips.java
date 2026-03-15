package yourmod.pipes.item;

import com.badlogic.gdx.math.MathUtils;
import com.tann.dice.gameplay.content.gen.pipe.regex.PipeRegexNamed;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNPref;
import com.tann.dice.gameplay.content.item.ItBill;
import com.tann.dice.gameplay.content.item.Item;
import com.tann.dice.gameplay.effect.eff.keyword.Keyword;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.AffectSides;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.condition.HasKeyword;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.FlatBonus;
import com.tann.dice.util.Tann;
import yourmod.effect.DivideEffect;

public class PipeItemDividePips extends PipeRegexNamed<Item> {
    public static final PRNPart PREF = new PRNPref("divvy");

    public PipeItemDividePips() {
        super(PREF, UP_TO_TWO_DIGITS, COLON, UP_TO_TWO_DIGITS);
    }

    @Override
    protected Item internalMake(String[] groups) {
        int upper = Integer.parseInt(groups[0]);
        int lower = Integer.parseInt(groups[1]);
        return makeInternal(upper, lower);
    }

    private Item makeInternal(int upper, int lower) {
        if (lower <= 1) {
            return null;
        }
        return new ItBill(0, PREF + Integer.toString(upper) + COLON + Integer.toString(lower), "camomile").prs(new AffectSides(new DivideEffect(upper, lower))).bItem();
    }

    @Override
    protected Item generateInternal(boolean wild) {
        int attempts = 5;
        for (int at = 0; at < attempts; ++at) {
            Item i = makeInternal(Tann.randomInt(1,10), Tann.randomInt(1,10));
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
