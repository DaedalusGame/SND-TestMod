package yourmod.pipes.mod;

import com.tann.dice.gameplay.content.gen.pipe.regex.PipeRegexNamed;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNPref;
import com.tann.dice.gameplay.effect.eff.keyword.KUtils;
import com.tann.dice.gameplay.effect.eff.keyword.Keyword;
import com.tann.dice.gameplay.effect.targetable.ability.Ability;
import com.tann.dice.gameplay.effect.targetable.ability.AbilityUtils;
import com.tann.dice.gameplay.effect.targetable.ability.spell.SpellUtils;
import com.tann.dice.gameplay.modifier.Modifier;
import com.tann.dice.util.Tann;
import com.tann.dice.util.bsRandom.RandomCheck;
import yourmod.effect.GlobalSpellKeywordEx;

public class PipeModSpellNameKeyword extends PipeRegexNamed<Modifier> {
    static final PRNPart PREF = new PRNPref("spellk");

    public PipeModSpellNameKeyword() {
        super(PREF, NAME, COLON, PipeRegexNamed.KEYWORD);
    }

    protected Modifier internalMake(String[] groups) {
        String name = groups[0];
        Keyword k = Keyword.byName(groups[1]);
        if (k != null) {
            return makeInternal(name, k);
        }
        return null;
    }

    private Modifier makeInternal(String name, Keyword keyword) {
        try {
            Ability ability = AbilityUtils.byName(name);
            float tier = -0.069f;
            if(ability != null) {
                tier = KUtils.getValue(ability.getBaseEffect());
                if(tier == 0) {
                    tier = -0.069f;
                }
            }
            return new Modifier(tier, PREF + keyword.name(), new GlobalSpellKeywordEx(name,keyword));
        } catch (Exception ex) {
            return null;
        }
    }

    public Modifier example() {
        Ability ability = AbilityUtils.random();
        Keyword k = RandomCheck.checkedRandom(() -> Tann.random(Keyword.values()), SpellUtils::allowAddingKeyword, Keyword.poison);
        return makeInternal(ability.getTitle(), k);
    }

    public float getRarity(boolean wild) {
        return 1.0F;
    }

    public boolean canGenerate(boolean wild) {
        return wild;
    }

    protected Modifier generateInternal(boolean wild) {
        return this.example();
    }

    public boolean isComplexAPI() {
        return true;
    }
}
