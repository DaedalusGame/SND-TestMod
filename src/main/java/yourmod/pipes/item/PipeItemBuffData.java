package yourmod.pipes.item;

import com.tann.dice.gameplay.content.ent.type.HeroType;
import com.tann.dice.gameplay.content.ent.type.lib.HeroTypeLib;
import com.tann.dice.gameplay.content.ent.type.lib.HeroTypeUtils;
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
import com.tann.dice.gameplay.trigger.personal.Personal;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.AffectSides;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.FlatBonus;
import com.tann.dice.gameplay.trigger.personal.onHit.OnHitFromPipe;
import com.tann.dice.gameplay.trigger.personal.startBuffed.StartPetrified;
import com.tann.dice.gameplay.trigger.personal.startBuffed.StartPoisoned;
import com.tann.dice.gameplay.trigger.personal.startBuffed.StartRegenned;
import com.tann.dice.gameplay.trigger.personal.startBuffed.StartVulnerable;
import yourmod.effect.PersonalConditionLinkEx;
import yourmod.effect.buffs.StartFueled;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class PipeItemBuffData extends PipeRegexNamed<Item> {
    public interface IBuffTransformer {
        boolean isValid(Eff eff);

        Personal getPersonalFromSide(Eff eff);
    }

    static {
        PipeItemBuffData.transformers = new ArrayList<>();
        PipeItemBuffData.transformers.add(new IBuffTransformer() {
            @Override
            public boolean isValid(Eff eff) {
                return eff.hasKeyword(Keyword.poison);
            }

            @Override
            public Personal getPersonalFromSide(Eff eff) {
                return new StartPoisoned(eff.getValue());
            }
        });
        PipeItemBuffData.transformers.add(new IBuffTransformer() {
            @Override
            public boolean isValid(Eff eff) {
                return eff.hasKeyword(Keyword.regen);
            }

            @Override
            public Personal getPersonalFromSide(Eff eff) {
                return new StartRegenned(eff.getValue());
            }
        });
        PipeItemBuffData.transformers.add(new IBuffTransformer() {
            @Override
            public boolean isValid(Eff eff) {
                return eff.hasKeyword(Keyword.petrify);
            }

            @Override
            public Personal getPersonalFromSide(Eff eff) {
                return new StartPetrified(eff.getValue());
            }
        });
        PipeItemBuffData.transformers.add(new IBuffTransformer() {
            @Override
            public boolean isValid(Eff eff) {
                return eff.hasKeyword(Keyword.vulnerable);
            }

            @Override
            public Personal getPersonalFromSide(Eff eff) {
                return new StartVulnerable(eff.getValue());
            }
        });
        PipeItemBuffData.transformers.add(new IBuffTransformer() {
            @Override
            public boolean isValid(Eff eff) {
                return eff.hasKeyword(Keyword.valueOf("fuel"));
            }

            @Override
            public Personal getPersonalFromSide(Eff eff) {
                return new StartFueled(eff.getValue());
            }
        });
    }

    private static List<IBuffTransformer> transformers;

    public static final PRNPart PREF = new PRNPref("buffdata");

    public PipeItemBuffData() {
        super(PREF, HERO);
    }

    private static Personal getPersonalFromSide(Eff eff) {
        for (IBuffTransformer transformer : transformers) {
            if(transformer.isValid(eff)) {
                return transformer.getPersonalFromSide(eff);
            }
        }

        return null;
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
        Personal personal = getPersonalFromSide(e);

        if(personal != null) {
            return new ItBill(PREF + ht.getName()).prs(personal).bItem();
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
