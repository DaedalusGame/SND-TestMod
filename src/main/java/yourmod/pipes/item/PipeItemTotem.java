package yourmod.pipes.item;

import com.tann.dice.gameplay.content.gen.pipe.regex.PipeRegexNamed;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNPref;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNSuff;
import com.tann.dice.gameplay.content.item.ItBill;
import com.tann.dice.gameplay.content.item.Item;
import com.tann.dice.gameplay.content.item.ItemLib;
import com.tann.dice.gameplay.modifier.Modifier;
import com.tann.dice.gameplay.modifier.ModifierLib;
import com.tann.dice.gameplay.trigger.global.Global;
import com.tann.dice.gameplay.trigger.personal.Personal;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.AffectSides;
import com.tann.dice.gameplay.trigger.personal.linked.TriggerPersonalToGlobal;

public class PipeItemTotem extends PipeRegexNamed<Item> {
    public static final PRNPart PREF = new PRNPref("totem");

    public PipeItemTotem() {
        super(PREF, MODIFIER);
    }

    @Override
    protected Item internalMake(String[] groups) {
        String itemS = groups[0];
        return makeInternal(ModifierLib.byName(itemS));
    }

    private Item makeInternal(Modifier modifier) {
        if (modifier == null || modifier.isMissingno()) {
            return null;
        }
        Global global = modifier.getSingleGlobalOrNull();

        if(global != null) {
            return new ItBill(0, PREF + modifier.getName(false), "special/combined").prs(new TriggerPersonalToGlobal(global)).bItem();
        }

        return null;
    }

    @Override
    public Item example() {
        return makeInternal(ModifierLib.byName("Bonezone"));
    }

    @Override
    public boolean showHigher() {
        return true;
    }
}
