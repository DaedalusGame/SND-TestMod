package yourmod.init;

import basemod.pipes.IInitializeModPipes;
import com.tann.dice.gameplay.content.gen.pipe.mod.PipeMod;
import com.tann.dice.gameplay.modifier.Modifier;
import com.tann.dice.gameplay.trigger.global.Global;
import com.tann.dice.gameplay.trigger.global.GlobalDescribeOnly;
import yourmod.pipes.mod.*;

import java.util.List;

public class PipeMods implements IInitializeModPipes {

    @Override
    public void initialize() {
        PipeMod.pipes.add(new PipeModAllAll("all", null));
        PipeMod.pipes.add(new PipeModAllAll("p", true));
        PipeMod.pipes.add(new PipeModAllAll("e", false));
        PipeMod.pipes.add(new PipeModSpellKeyword());
        PipeMod.pipes.add(new PipeModSpellNameKeyword());
        PipeMod.pipes.add(new PipeModToOrange());
        PipeMod.pipes.add(new PipeModAddFights());
        PipeMod.pipes.add(new PipeModBeginTurnSpell());
    }

    @Override
    public void modifyHiddenModifiers(List<Modifier> list) {

    }
}
