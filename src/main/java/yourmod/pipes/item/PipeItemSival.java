package yourmod.pipes.item;

import com.tann.dice.gameplay.content.gen.pipe.regex.PipeRegexNamed;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNPref;
import com.tann.dice.gameplay.content.item.ItBill;
import com.tann.dice.gameplay.content.item.Item;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.AffectSides;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.condition.SpecificSidesType;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.AffectSideEffect;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.weirdTextmod.Sidesc;
import com.tann.dice.util.Separators;
import com.tann.dice.util.Tann;
import yourmod.EffCalc;
import yourmod.effect.Sival;

public class PipeItemSival extends PipeRegexNamed<Item> {
    private static final PRNPart PREF = new PRNPref("sival");

    public PipeItemSival() {
        super(PREF, UP_TO_FOUR_DIGITS, COLON, UP_TO_FOUR_DIGITS);
    }

    protected Item internalMake(String[] groups) {
        return this.makeShortened(Integer.parseInt(groups[0]), Integer.parseInt(groups[1]));
    }

    protected Item makeShortened(int a, int b) {
        EffCalc effCalc = new EffCalc(a,b);
        return (new ItBill("" + PREF + a + COLON + b)).prs(new AffectSides(SpecificSidesType.All, new Sival(effCalc))).bItem();
    }

    public Item example() {
        return this.makeShortened(Tann.randomInt(0,999), Tann.randomInt(0,999));
    }

    public boolean isComplexAPI() {
        return true;
    }
}
