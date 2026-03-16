package yourmod.pipes.mod;

import com.tann.dice.gameplay.content.gen.pipe.regex.PipeRegexNamed;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNPref;
import com.tann.dice.gameplay.content.item.Item;
import com.tann.dice.gameplay.content.item.ItemLib;
import com.tann.dice.gameplay.effect.eff.keyword.Keyword;
import com.tann.dice.gameplay.modifier.Modifier;
import com.tann.dice.gameplay.trigger.global.Global;
import com.tann.dice.gameplay.trigger.global.linked.all.GlobalAllEntities;
import com.tann.dice.gameplay.trigger.personal.item.AsIfHasItem;
import com.tann.dice.gameplay.trigger.personal.linked.LinkedPersonal;
import com.tann.dice.gameplay.trigger.personal.linked.MultiDifferentPersonal;

public class PipeModAllAll extends PipeRegexNamed<Modifier> {
    private static PRNPart PREF = new PRNPref("allall");

    public PipeModAllAll() {
        super(PREF, ITEM);
    }

    protected Modifier internalMake(String[] groups) {
        String ms = groups[0];
        return bad(ms) ? null : makeItemAll(ItemLib.byName(ms));
    }

    public static Modifier makeItemAll(Item item) {
        if (item.isMissingno()) {
            return null;
        } else {
            float calcTier = -0.069F;

            GlobalAllEntities allEntities = new GlobalAllEntities(null, new MultiDifferentPersonal(item.getPersonals()));

            return new Modifier(calcTier, PREF + item.getName(), allEntities);
        }
    }

    public Modifier example() {
        Item i = ItemLib.random();
        return makeItemAll(i);
    }

    public boolean showHigher() {
        return true;
    }

    public boolean isComplexAPI() {
        return true;
    }
}
