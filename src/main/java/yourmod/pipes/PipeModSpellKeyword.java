package yourmod.pipes;

import com.tann.dice.gameplay.content.gen.pipe.regex.PipeRegexNamed;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNPref;
import com.tann.dice.gameplay.effect.eff.keyword.Keyword;
import com.tann.dice.gameplay.modifier.Modifier;
import com.tann.dice.gameplay.trigger.global.spell.GlobalSpellKeyword;
import com.tann.dice.util.Tann;
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
            return new Modifier(0, PREF + keyword.name(), new GlobalSpellKeywordEx(keyword));
        } catch (Exception ex) {
            return null;
        }
    }

    public Modifier example() {
        int attempts = 5;
        for (int at = 0; at < attempts; ++at) {
            Modifier i = makeInternal(Tann.random(Keyword.values()));
            if (i == null || i.getTier() == 0) continue;
            return i;
        }
        return null;
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
