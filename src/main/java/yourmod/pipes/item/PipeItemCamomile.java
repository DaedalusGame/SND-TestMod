package yourmod.pipes.item;

import com.tann.dice.gameplay.content.gen.pipe.regex.PipeRegexNamed;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNPref;
import com.tann.dice.gameplay.content.item.ItBill;
import com.tann.dice.gameplay.content.item.Item;
import com.tann.dice.gameplay.effect.eff.keyword.Keyword;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.AffectSides;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.RemoveKeyword;
import com.tann.dice.util.Tann;
import com.tann.dice.util.bsRandom.RandomCheck;

public class PipeItemCamomile
    extends PipeRegexNamed<Item> {
    public static final PRNPart PREF = new PRNPref("rk");

    public PipeItemCamomile() {
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
        return new ItBill(0, PREF + k.name().toLowerCase(), "camomile").prs(new AffectSides(new RemoveKeyword(k))).bItem();
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
        Keyword k = RandomCheck.checkedRandom(() -> (Keyword) Tann.random(Keyword.values()), keyword -> !keyword.abilityOnly(), Keyword.pain);

        return makeInternal(k);
    }

    @Override
    public boolean showHigher() {
        return true;
    }
}
