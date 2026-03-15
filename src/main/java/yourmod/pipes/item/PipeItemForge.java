package yourmod.pipes.item;

import com.tann.dice.gameplay.content.ent.die.side.EnSiBi;
import com.tann.dice.gameplay.content.ent.die.side.EntSide;
import com.tann.dice.gameplay.content.gen.pipe.item.sideReally.PipeItemCast;
import com.tann.dice.gameplay.content.gen.pipe.regex.PipeRegexNamed;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNPref;
import com.tann.dice.gameplay.content.item.ItBill;
import com.tann.dice.gameplay.content.item.Item;
import com.tann.dice.gameplay.content.item.ItemLib;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.AffectSides;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.condition.SpecificSidesType;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.AffectSideEffect;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.ReplaceWith;
import yourmod.effect.DivideEffect;
import yourmod.effect.PermanentPersonal;

public class PipeItemForge extends PipeRegexNamed<Item> {
    public static final PRNPart PREF = new PRNPref("forge");

    public PipeItemForge() {
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
            int tier = Math.round((float)src.getTier() * 0.3F);

            PermanentPersonal i = new PermanentPersonal(srcName);
            src = new ItBill("a").prs(src.getPersonals()).prs(i).bItem();

            EntSide es = (new EnSiBi()).sticker(src);

            return (new ItBill(tier, PREF + src.getName(false), "special/sticker"))
                .prs(new AffectSides(SpecificSidesType.Middle, new ReplaceWith(es)))
                .bItem();
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
    public Item example() {
        return this.generateInternal(false);
    }

    @Override
    public boolean showHigher() {
        return true;
    }
}