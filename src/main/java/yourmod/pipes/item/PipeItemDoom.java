package yourmod.pipes.item;

import basemod.util.ReflectionUtils;
import com.tann.dice.gameplay.content.ent.die.side.EnSiBi;
import com.tann.dice.gameplay.content.ent.die.side.EntSide;
import com.tann.dice.gameplay.content.ent.type.EntType;
import com.tann.dice.gameplay.content.ent.type.HeroType;
import com.tann.dice.gameplay.content.ent.type.lib.EntTypeUtils;
import com.tann.dice.gameplay.content.ent.type.lib.HeroTypeLib;
import com.tann.dice.gameplay.content.ent.type.lib.HeroTypeUtils;
import com.tann.dice.gameplay.content.gen.pipe.item.sideReally.PipeItemCast;
import com.tann.dice.gameplay.content.gen.pipe.regex.PipeRegexNamed;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNS;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNPref;
import com.tann.dice.gameplay.content.item.ItBill;
import com.tann.dice.gameplay.content.item.Item;
import com.tann.dice.gameplay.content.item.ItemLib;
import com.tann.dice.gameplay.effect.Buff;
import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.effect.eff.EffBill;
import com.tann.dice.gameplay.effect.eff.conditionalBonus.ConditionalBonus;
import com.tann.dice.gameplay.effect.eff.conditionalBonus.conditionalRequirement.ConditionalRequirement;
import com.tann.dice.gameplay.effect.eff.keyword.Keyword;
import com.tann.dice.gameplay.fightLog.EntSideState;
import com.tann.dice.gameplay.trigger.personal.Personal;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.AffectSides;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.condition.SpecificSidesType;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.AffectSideEffect;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.FlatBonus;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.ReplaceWith;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.weirdTextmod.BasicTextmodToggle;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.weirdTextmod.LeftTextmodToggle;
import com.tann.dice.gameplay.trigger.personal.item.AsIfHasItem;
import com.tann.dice.gameplay.trigger.personal.onHit.OnHitFromPipe;
import com.tann.dice.statics.ImageUtils;
import com.tann.dice.util.Tann;
import yourmod.buffs.Doom;
import yourmod.effect.PersonalConditionLinkEx;
import yourmod.effect.TogFrom;
import yourmod.init.PipeItems;
import yourmod.pipes.PRNTog;

import java.lang.reflect.Field;
import java.util.List;

public class PipeItemDoom extends PipeRegexNamed<Item> {
    public static final PRNPart PREF = new PRNPref("doomdata");

    public PipeItemDoom() {
        super(PREF, ENTITY);
    }

    protected Item internalMake(String[] groups) {
        String heroName = groups[0];
        if (heroName == null) {
            return null;
        } else {
            HeroType ht = HeroTypeLib.byName(heroName);
            return ht.isMissingno() ? null : this.makeInternal(ht);
        }
    }

    private Item makeInternal(HeroType ht) {
        Eff e = OnHitFromPipe.getLeftmostBlankDerived(ht);
        EntSide es = new EnSiBi().effect((new EffBill()).friendly().buff(new Buff(ht.hp, new Doom(e)))).noVal();
        try {
            Field field = EntSide.class.getDeclaredField("tr");
            field.setAccessible(true);
            field.set(es, ImageUtils.loadExt3d("extra/LemonReco/zhstatus8"));
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }

        return new ItBill(0, PREF + ht.getName(), "special/sticker").prs(new AffectSides(SpecificSidesType.Middle, new ReplaceWith(es))).bItem();
    }


    public Item example() {
        return this.makeInternal(HeroTypeUtils.random());
    }
    public boolean isComplexAPI() {
        return true;
    }
}
