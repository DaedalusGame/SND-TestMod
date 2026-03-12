package yourmod.pipes;

import com.tann.dice.gameplay.content.ent.type.EntType;
import com.tann.dice.gameplay.content.ent.type.lib.EntTypeUtils;
import com.tann.dice.gameplay.content.ent.type.lib.HeroTypeLib;
import com.tann.dice.gameplay.content.gen.pipe.regex.PipeRegexNamed;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNS;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNPref;
import com.tann.dice.gameplay.content.item.ItBill;
import com.tann.dice.gameplay.content.item.Item;
import com.tann.dice.gameplay.content.item.ItemLib;
import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.fightLog.EntSideState;
import com.tann.dice.gameplay.trigger.personal.Personal;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.AffectSides;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.AffectSideEffect;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.FlatBonus;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.weirdTextmod.BasicTextmodToggle;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.effect.weirdTextmod.LeftTextmodToggle;
import com.tann.dice.gameplay.trigger.personal.linked.stateCondition.PersonalConditionLink;
import yourmod.conditionalBonus.HasSideConditionalRequirement;
import yourmod.effect.PersonalConditionLinkEx;
import yourmod.effect.TogFrom;
import yourmod.effect.TogSideBuff;

import java.util.List;

public class PipeItemTogFrom extends PipeRegexNamed<Item> {
    public static final PRNPart PREF = new PRNS("d");
    public static final PRNPart TOG = new PRNTog();

    public PipeItemTogFrom() {
        super(PREF, TOG, COLON, ENTITY);
    }

    @Override
    protected Item internalMake(String[] groups) {
        return this.makeInternal(groups[0], groups[1]);
    }

    private AffectSideEffect getAffectSideEffectOrNull(AffectSides affectSides) {
        List<AffectSideEffect> effects = affectSides.getEffects();

        if(effects.size() == 1) {
            return effects.get(0);
        }

        return null;
    }

    private Item makeInternal(String tog, String srcName) {
        Item src = ItemLib.byName(tog);
        EntType ent = EntTypeUtils.byName(srcName);

        if (src.isMissingno() || ent.isMissingno()) {
            return null;
        }

        Personal personal = src.getSinglePersonalOrNull();
        if(personal instanceof AffectSides) {
            AffectSides affectSides = (AffectSides) personal;
            AffectSideEffect affectEff = getAffectSideEffectOrNull(affectSides);

            String name = "d"+tog+":"+srcName;

            if(affectEff instanceof BasicTextmodToggle) {
                return new ItBill(name).prs(new AffectSides(new TogFrom((BasicTextmodToggle) affectEff))).bItem();
            } else if(affectEff instanceof LeftTextmodToggle) {
                EntSideState entSide = PipeItemChangeType.getLeftmostBlankDerived(ent);
                Eff eff = entSide.getCalculatedEffect();
                return new ItBill(name).prs(new AffectSides(new TogFrom((LeftTextmodToggle) affectEff, eff))).bItem();
            }
        }

        return null;
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
    public boolean isHiddenAPI() {
        return true;
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
