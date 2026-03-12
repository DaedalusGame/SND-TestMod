package yourmod.init;

import basemod.pipes.*;
import com.tann.dice.gameplay.content.ent.EntSize;
import com.tann.dice.gameplay.content.ent.die.side.EnSiBi;
import com.tann.dice.gameplay.content.gen.pipe.item.PipeItem;
import com.tann.dice.gameplay.content.item.ItBill;
import com.tann.dice.gameplay.content.item.Item;
import com.tann.dice.gameplay.effect.eff.EffBill;
import com.tann.dice.gameplay.effect.eff.EffType;
import com.tann.dice.gameplay.effect.eff.keyword.Keyword;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.AffectSides;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.condition.HasKeyword;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.condition.SpecificSidesCondition;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.condition.SpecificSidesType;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.condition.TypeCondition;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.ReplaceWith;
import yourmod.effect.LostAtEndOfFight;
import yourmod.effect.SetToLowest;
import yourmod.effect.TogBuffTimeEx;
import yourmod.effect.TogSideBuff;
import yourmod.pipes.*;

import java.util.List;

public class PipeItems implements IInitializeItemPipes {
    @Override
    public void initialize() {
        PipeItem.pipes.add(new PipeItemCamomile());
        PipeItem.pipes.add(new PipeItemCondKeyword());
        PipeItem.pipes.add(new PipeItemDividePips());
        PipeItem.pipes.add(new PipeItemChangeType());
        PipeItem.pipes.add(new PipeItemBuffPriority());
        PipeItem.pipes.add(new PipeItemTextureCondition());
        PipeItem.pipes.add(new PipeItemStartOfCombat());
        PipeItem.pipes.add(new PipeItemOverheal());
        PipeItem.pipes.add(new PipeItemOnDeath());
        PipeItem.pipes.add(new PipeItemOnOtherDeath());
        PipeItem.pipes.add(new PipeItemAfterUse());
        PipeItem.pipes.add(new PipeItemAdjacentDeath());
        PipeItem.pipes.add(new PipeItemHasSideCondition());
        PipeItem.pipes.add(new PipeItemIndexedSides());
        PipeItem.pipes.add(new PipeItemExactPips());
        PipeItem.pipes.add(new PipeItemTogFrom());
        PipeItem.pipes.add(new PipeItemColorRestriction());
        //PipeItem.pipes.add(new PipeItemForge());
    }

    @Override
    public void modifyHiddenModifiers(List<Item> list) {
        list.add((new ItBill("togspec")).prs(new AffectSides(new TogSideBuff())).bItem());
        list.add((new ItBill("togtime2")).prs(new AffectSides(new TogBuffTimeEx())).bItem());
        list.add((new ItBill("lowball")).prs(new AffectSides(new SetToLowest())).bItem());
        list.add((new ItBill("scrap")).prs(new LostAtEndOfFight("scrap")).bItem());
    }
}
