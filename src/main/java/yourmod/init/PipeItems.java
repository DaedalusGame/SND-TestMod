package yourmod.init;

import basemod.pipes.*;
import com.tann.dice.gameplay.content.gen.pipe.item.PipeItem;
import com.tann.dice.gameplay.content.item.ItBill;
import com.tann.dice.gameplay.content.item.Item;
import com.tann.dice.gameplay.effect.eff.EffType;
import com.tann.dice.gameplay.effect.eff.keyword.Keyword;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.AffectSides;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.condition.HasKeyword;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.condition.PrimeCondition;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.condition.SpecificSidesType;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.condition.TypeCondition;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.*;
import yourmod.conditionalBonus.FuelEmptyConditionalRequirement;
import yourmod.effect.*;
import yourmod.pipes.item.*;

import java.util.ArrayList;
import java.util.List;

public class PipeItems implements IInitializeItemPipes {
    public static List<String> togs = new ArrayList<>();

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
        //PipeItem.pipes.add(new PipeItemHasSideCondition());
        PipeItem.pipes.add(new PipeItemIndexedSides());
        PipeItem.pipes.add(new PipeItemExactPips());
        PipeItem.pipes.add(new PipeItemTogFrom());
        PipeItem.pipes.add(new PipeItemColorRestriction());
        PipeItem.pipes.add(new PipeItemNon());
        PipeItem.pipes.add(new PipeItemScrappy());
        PipeItem.pipes.add(new PipeItemCopyItemTiers());
        PipeItem.pipes.add(new PipeItemAddKeywordColor());
        PipeItem.pipes.add(new PipeItemRemoveKeywordColor());
        //PipeItem.pipes.add(new PipeItemForge());
        PipeItem.pipes.add(new PipeItemTotem());
        PipeItem.pipes.add(new PipeItemSival());
        PipeItem.pipes.add(new PipeItemResData());
        PipeItem.pipes.add(new PipeItemDoom());
        PipeItem.pipes.add(new PipeItemBuffData());
    }

    @Override
    public void modifyHiddenModifiers(List<Item> list) {
        list.add((new ItBill("togspec")).prs(new AffectSides(new TogSideBuff())).bItem());
        list.add((new ItBill("togtime2")).prs(new AffectSides(new TogBuffTimeEx())).bItem());
        list.add((new ItBill("lowball")).prs(new AffectSides(new SetToLowest())).bItem());
        list.add((new ItBill("scrap")).prs(new LostAtEndOfFight("scrap")).bItem());

        list.add((new ItBill("left spear")).prs(new AffectSides(new ChangeToMyPosition(SpecificSidesType.Left))).bItem());
        list.add((new ItBill("top spear")).prs(new AffectSides(new ChangeToMyPosition(SpecificSidesType.Top))).bItem());
        list.add((new ItBill("bottom spear")).prs(new AffectSides(new ChangeToMyPosition(SpecificSidesType.Bot))).bItem());
        list.add((new ItBill("right spear")).prs(new AffectSides(new ChangeToMyPosition(SpecificSidesType.Right))).bItem());
        list.add((new ItBill("mid spear")).prs(new AffectSides(new ChangeToMyPosition(SpecificSidesType.Middle))).bItem());
        list.add((new ItBill("rightmost spear")).prs(new AffectSides(new ChangeToMyPosition(SpecificSidesType.RightMost))).bItem());

        list.add((new ItBill("kfuture")).prs(new AddKeyword(Keyword.future)).bItem());
        list.add((new ItBill("nprime")).prs(new PrimeCondition(), new NextPrime()).bItem());
        list.add((new ItBill("tkill")).prs(new TypeCondition(EffType.Kill), new FlatBonus(1)).bItem());
        list.add((new ItBill("tbuff")).prs(new TypeCondition(EffType.Buff), new FlatBonus(1)).bItem());
        list.add((new ItBill("ttarget")).prs(new TypeCondition(EffType.JustTarget), new FlatBonus(1)).bItem());

        //list.add((new ItBill("resfuelempty")).prs(new PersonalConditionLinkEx(new FuelEmptyConditionalRequirement(false), new AffectSides(new HasKeyword(Keyword.valueOf("fuel")),new AddKeyword(Keyword.singleUse), new RemoveKeyword(Keyword.valueOf("fuelcost"))))).bItem());
        list.add((new ItBill("resfuelempty")).prs(new PersonalConditionLinkEx(new FuelEmptyConditionalRequirement(false), new AffectSides(new FlatBonus(1)))).bItem());

        for (Item item : list) {
            String name = item.getName(false);
            if(name.startsWith("tog")) {
                togs.add(name);
            }
        }
    }
}
