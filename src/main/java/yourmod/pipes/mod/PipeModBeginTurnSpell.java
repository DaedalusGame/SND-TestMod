package yourmod.pipes.mod;

import com.tann.dice.gameplay.content.gen.pipe.regex.PipeRegexNamed;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNPref;
import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.effect.targetable.ability.Ability;
import com.tann.dice.gameplay.effect.targetable.ability.AbilityUtils;
import com.tann.dice.gameplay.modifier.Modifier;
import com.tann.dice.gameplay.modifier.ModifierLib;
import com.tann.dice.gameplay.trigger.global.chance.Rarity;
import com.tann.dice.gameplay.trigger.personal.affectSideModular.AffectSides;
import com.tann.dice.gameplay.trigger.personal.linked.stateCondition.StateConditionType;
import yourmod.effect.GlobalBeginTurnSpell;

public class PipeModBeginTurnSpell
        extends PipeRegexNamed<Modifier> {
    static final PRNPart PREF = new PRNPref("ba");

    public PipeModBeginTurnSpell() {
        super(PREF, ABILITY);
    }

    @Override
    public Modifier example() {
        Ability a = AbilityUtils.random();
        return this.make(a, a.getTitle());
    }

    @Override
    protected Modifier internalMake(String[] groups) {
        String tag = groups[0];
        return this.make(AbilityUtils.byName(tag), tag);
    }

    private Modifier make(Ability spell, String tag) {
        if (spell == null || !this.isSpellOk(spell)) {
            return null;
        }
        float likeHeroTier = AbilityUtils.likeFromHeroTier(spell);
        float blessTier = Float.isNaN(likeHeroTier) ? 0.0f : spell.getCostFactorInActual(Math.round(likeHeroTier)) * 2.2f;
        String title = PREF + tag;
        return new Modifier(blessTier, title, new GlobalBeginTurnSpell(spell)).rarity(Rarity.TENTH);
    }

    private boolean isSpellOk(Ability s) {
        boolean ok = true;
        Eff e = s.getBaseEffect();
        ok &= !e.hasRestriction(StateConditionType.Dying);
        ok &= !e.needsTarget();
        switch (e.getType()) {
            case Resurrect:
            case Recharge: {
                ok = false;
                break;
            }
            case Buff: {
                ok &= !(e.getBuff().personal instanceof AffectSides);
            }
        }
        return ok;
    }

    /*@Override
    public boolean canGenerate(boolean wild) {
        return !wild;
    }

    @Override
    protected Modifier generateInternal(boolean wild) {
        Ability s = AbilityUtils.random();
        return ModifierLib.byName("et3-es-" + s.getTitle());
    }*/

    @Override
    public boolean isComplexAPI() {
        return true;
    }
}