package yourmod.pipes;

import com.tann.dice.gameplay.content.gen.pipe.regex.PipeRegexNamed;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNPref;
import com.tann.dice.gameplay.content.item.ItBill;
import com.tann.dice.gameplay.content.item.Item;
import com.tann.dice.gameplay.effect.eff.keyword.Keyword;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.AffectSides;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.condition.HasKeyword;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.FlatBonus;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.RemoveKeyword;
import com.tann.dice.util.Tann;

public class PipeItemCondKeyword extends PipeRegexNamed<Item> {
    public static final PRNPart PREF = new PRNPref("condk");

    public PipeItemCondKeyword() {
        super(PREF, KEYWORD);
    }

    @Override
    protected Item internalMake(String[] groups) {
        Keyword k = Keyword.byName(groups[0]);
        if (k != null) {
            return makeInternal(k);
        }
        return null;
    }

    private Item makeInternal(Keyword k) {
        if (k == null) {
            return null;
        }
        return new ItBill(0, PREF + k.name().toLowerCase(), "camomile").prs(new AffectSides(new HasKeyword(k),new FlatBonus(1))).bItem();
    }

    @Override
    protected Item generateInternal(boolean wild) {
        int attempts = 5;
        for (int at = 0; at < attempts; ++at) {
            Item i = makeInternal(Tann.random(Keyword.values()));
            if (i == null || i.getTier() == 0) continue;
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