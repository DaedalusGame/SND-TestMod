package yourmod.pipes.item;

import com.tann.dice.gameplay.content.ent.type.HeroType;
import com.tann.dice.gameplay.content.ent.type.lib.HeroTypeLib;
import com.tann.dice.gameplay.content.ent.type.lib.HeroTypeUtils;
import com.tann.dice.gameplay.content.gen.pipe.item.PipeItem;
import com.tann.dice.gameplay.content.gen.pipe.regex.PipeRegexNamed;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNPref;
import com.tann.dice.gameplay.content.item.ItBill;
import com.tann.dice.gameplay.content.item.Item;
import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.effect.eff.conditionalBonus.ConditionalBonus;
import com.tann.dice.gameplay.effect.eff.conditionalBonus.conditionalRequirement.ConditionalRequirement;
import com.tann.dice.gameplay.effect.eff.conditionalBonus.conditionalRequirement.EnumConditionalRequirement;
import com.tann.dice.gameplay.effect.eff.conditionalBonus.conditionalRequirement.TargetingRestriction;
import com.tann.dice.gameplay.effect.eff.keyword.Keyword;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.AffectSides;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.FlatBonus;
import com.tann.dice.gameplay.trigger.personal.onHit.OnHitFromPipe;
import yourmod.effect.EffAdjacentsOnDeath;
import yourmod.effect.PersonalConditionLinkEx;

import java.util.List;

public class PipeItemResData extends PipeRegexNamed<Item> {
    public static final PRNPart PREF = new PRNPref("resdata");

    public PipeItemResData() {
        super(PREF, HERO);
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

    public static boolean isValidCondition(ConditionalRequirement req) {
        if(req instanceof TargetingRestriction) {
            return false;
        }

        return true;
    }

    private Item makeInternal(HeroType ht) {
        Eff e = OnHitFromPipe.getLeftmostBlankDerived(ht);
        ConditionalRequirement picked = null;
        List<ConditionalRequirement> reqs = e.getRestrictions(true);
        List<Keyword> keywords = e.getKeywords();
        if(reqs.size() > 0) {
            for (ConditionalRequirement req : reqs) {
                if (isValidCondition(req)) {
                    picked = reqs.get(0);
                    break;
                }
            }

        }

        if(picked == null) {
            for (Keyword kw : keywords) {
                ConditionalBonus bonus = kw.getConditionalBonus();

                if(bonus == null) continue;

                if(bonus.requirement != null && bonus.requirement != EnumConditionalRequirement.Always && isValidCondition(bonus.requirement)) {
                    picked = bonus.requirement;
                    break;
                }
            }
        }

        if(picked != null) {
            return new ItBill(PREF + ht.getName()).prs(new PersonalConditionLinkEx(picked, new AffectSides(new FlatBonus(1)))).bItem();
        } else {
            return null;
        }
    }

    public Item example() {
        return this.makeInternal(HeroTypeUtils.random());
    }

    public boolean isComplexAPI() {
        return true;
    }
}
