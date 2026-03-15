package yourmod.pipes.mod;

import com.tann.dice.gameplay.content.gen.pipe.regex.PipeRegexNamed;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNPref;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNSuff;
import com.tann.dice.gameplay.modifier.Modifier;
import com.tann.dice.gameplay.modifier.ModifierLib;
import com.tann.dice.gameplay.trigger.global.Global;
import com.tann.dice.gameplay.trigger.global.GlobalAddFight;
import com.tann.dice.gameplay.trigger.global.linked.all.GlobalAllEntities;
import com.tann.dice.util.Tann;


public class PipeModAddFights extends PipeRegexNamed<Modifier> {

    static final PRNPart PREF = new PRNPref("addfights") {
    };

    public PipeModAddFights() {
        super(PREF, UP_TO_FOUR_DIGITS);
    }

    public boolean canGenerate(boolean wild) {
        return wild;
    }

    protected Modifier generateInternal(boolean wild) {
        return this.example();
    }

    @Override
    protected Modifier internalMake(String[] strings) {
        return makeInternal(Integer.parseInt(strings[0]));
    }

    private Modifier makeInternal(int amt){
        return new Modifier(-0.069F, PREF + String.valueOf(amt), new GlobalAddFight(amt));
    }

    @Override
    public Modifier example() {
        return makeInternal(Tann.randomInt(-101, 101));
    }
}