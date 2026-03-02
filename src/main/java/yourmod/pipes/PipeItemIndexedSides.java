package yourmod.pipes;

import com.tann.dice.gameplay.content.ent.die.side.EnSiBi;
import com.tann.dice.gameplay.content.ent.die.side.EntSide;
import com.tann.dice.gameplay.content.gen.pipe.regex.PipeRegexNamed;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNPref;
import com.tann.dice.gameplay.content.item.ItBill;
import com.tann.dice.gameplay.content.item.Item;
import com.tann.dice.gameplay.content.item.ItemLib;
import com.tann.dice.gameplay.trigger.personal.Personal;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.AffectSides;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.condition.SpecificSidesType;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.AffectByIndex;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.AffectSideEffect;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.ReplaceWith;
import yourmod.effect.PermanentPersonal;

import java.util.ArrayList;
import java.util.List;

public class PipeItemIndexedSides extends PipeRegexNamed<Item> {
    public static final PRNPart PREF = new PRNPref("indexsides");

    public PipeItemIndexedSides() {
        super(PREF, SIDE_POSITION, COLON, ITEM);
    }

    @Override
    protected Item internalMake(String[] groups) {
        SpecificSidesType type = SpecificSidesType.byName(groups[0]);
        return this.makeInternal(type,groups[1]);
    }

    private Item makeInternal(SpecificSidesType sidesType, String srcName) {
        Item src = ItemLib.byName(srcName);

        if (src.isMissingno()) {
            return null;
        } else {
            int tier = 0;
            List<AffectSideEffect> effectList = new ArrayList<>();

            for (Personal personal : src.getPersonals()) {
                if(personal instanceof AffectSides) {
                    AffectSides sides = (AffectSides) personal;
                    effectList.add(sides.getEffects().get(0));
                } else {
                    return null;
                }
            }

            return (new ItBill(tier, PREF + sidesType.getShortName() + ":" + src.getName(false), "special/sticker"))
                .prs(new AffectSides(sidesType, new AffectByIndex(effectList.toArray(new AffectSideEffect[0]))))
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