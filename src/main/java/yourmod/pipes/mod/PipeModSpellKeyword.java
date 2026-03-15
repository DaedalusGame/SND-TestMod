package yourmod.pipes.mod;

import com.tann.dice.gameplay.content.gen.pipe.regex.PipeRegexNamed;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNPref;
import com.tann.dice.gameplay.effect.eff.keyword.KUtils;
import com.tann.dice.gameplay.effect.eff.keyword.Keyword;
import com.tann.dice.gameplay.effect.targetable.ability.spell.SpellUtils;
import com.tann.dice.gameplay.modifier.Modifier;
import com.tann.dice.gameplay.trigger.global.spell.GlobalSpellKeyword;
import com.tann.dice.util.Tann;
import com.tann.dice.util.bsRandom.RandomCheck;
import yourmod.effect.GlobalSpellKeywordEx;

public class PipeModSpellKeyword extends PipeRegexNamed<Modifier> {
    static final PRNPart PREF = new PRNPref("spellk");

    public PipeModSpellKeyword() {
        super(PREF, PipeRegexNamed.KEYWORD);
    }

    protected Modifier internalMake(String[] groups) {
        Keyword k = Keyword.byName(groups[0]);
        if (k != null) {
            return makeInternal(k);
        }
        return null;
    }

    private Modifier makeInternal(Keyword keyword) {
        try {
            float tier = KUtils.getModTierAllHero(keyword);
            if(tier == 0) {
                tier = -0.069f;
            }
            return new Modifier(tier, PREF + keyword.name(), new GlobalSpellKeywordEx(keyword));
        } catch (Exception ex) {
            return null;
        }
    }

    public Modifier example() {
        Keyword k = RandomCheck.checkedRandom(() -> Tann.random(Keyword.values()), SpellUtils::allowAddingKeyword, Keyword.poison);
        return makeInternal(k);
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
