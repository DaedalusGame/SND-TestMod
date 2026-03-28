package yourmod.pipes.mod;

import com.tann.dice.gameplay.content.gen.pipe.regex.PipeRegexNamed;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNPref;
import com.tann.dice.gameplay.content.item.Item;
import com.tann.dice.gameplay.content.item.ItemLib;
import com.tann.dice.gameplay.modifier.Modifier;
import com.tann.dice.gameplay.trigger.global.linked.all.GlobalAllEntities;
import com.tann.dice.gameplay.trigger.personal.linked.MultiDifferentPersonal;

public class PipeModAllAll extends PipeRegexNamed<Modifier> {
    String str;
    Boolean type;

    public PipeModAllAll(String str, Boolean type) {
        super(new PRNPref("all"+str), ITEM);
        this.str = str;
        this.type = type;
    }

    protected Modifier internalMake(String[] groups) {
        String ms = groups[0];
        return bad(ms) ? null : makeItemAll(ItemLib.byName(ms), type, str);
    }

    public static Modifier makeItemAll(Item item, Boolean type, String str) {
        if (item.isMissingno()) {
            return null;
        } else {
            float calcTier = -0.069F;

            GlobalAllEntities allEntities = new GlobalAllEntities(type, new MultiDifferentPersonal(item.getPersonals()));

            return new Modifier(calcTier, "all"+ str + "." + item.getName(), allEntities);
        }
    }

    public Modifier example() {
        Item i = ItemLib.random();
        return makeItemAll(i, type, str);
    }

    public boolean showHigher() {
        return true;
    }

    public boolean isComplexAPI() {
        return true;
    }
}
