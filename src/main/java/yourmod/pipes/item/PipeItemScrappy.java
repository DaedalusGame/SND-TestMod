package yourmod.pipes.item;

import com.tann.dice.gameplay.content.gen.pipe.Pipe;
import com.tann.dice.gameplay.content.gen.pipe.item.PipeItemCombined;
import com.tann.dice.gameplay.content.gen.pipe.regex.PipeRegexNamed;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNPref;
import com.tann.dice.gameplay.content.item.Item;
import com.tann.dice.gameplay.content.item.ItemLib;

public class PipeItemScrappy extends Pipe<Item> {
    @Override
    public boolean canGenerate(boolean wild) {
        return true;
    }

    @Override
    protected Item generateInternal(boolean wild) {
        Item pt = ItemLib.random();
        int tier = pt.getTier();
        int newTier = Math.round((float)pt.getTier() * 0.6f - Math.signum(tier) * 1.4f);
        return ItemLib.byName("("+pt.getName()+"#scrap).tier."+newTier+".n.Scrappy "+pt.getName(true));
    }

    @Override
    protected Item make(String s) {
        return null;
    }

    @Override
    protected boolean nameValid(String s) {
        return false;
    }

    @Override
    public boolean isHiddenAPI() {
        return true;
    }

    @Override
    public Item example() {
        return null;
    }
}
