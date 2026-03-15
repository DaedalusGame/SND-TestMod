package yourmod.pipes.mod;

import com.tann.dice.gameplay.content.gen.pipe.regex.PipeRegexNamed;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.PRNPart;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNPref;
import com.tann.dice.gameplay.content.gen.pipe.regex.prnPart.pos.PRNSuff;
import com.tann.dice.gameplay.modifier.Modifier;
import com.tann.dice.gameplay.modifier.ModifierLib;
import com.tann.dice.gameplay.trigger.global.Global;
import com.tann.dice.gameplay.trigger.global.linked.all.GlobalAllEntities;


public class PipeModToOrange extends PipeRegexNamed<Modifier> {

    static final PRNPart SUFF = new PRNSuff("nomtier");

    public PipeModToOrange() {
        super(MODIFIER, SUFF);
    }

    public boolean canGenerate(boolean wild) {
        return wild;
    }

    protected Modifier generateInternal(boolean wild) {
        return this.example();
    }

    @Override
    protected Modifier internalMake(String[] strings) {
        Modifier m = ModifierLib.byName(strings[0]);
        return makeInternal(m);
    }

    private Modifier makeInternal(Modifier src){
        return new Modifier(-0.069F, src.getName() + SUFF,src.getGlobals());
    }

    @Override
    public Modifier example() {
        return makeInternal(ModifierLib.random());
    }
}