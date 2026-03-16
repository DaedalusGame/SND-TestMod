package yourmod.init;

import basemod.pipes.IInitializeModPipes;
import com.tann.dice.gameplay.content.gen.pipe.mod.PipeMod;
import com.tann.dice.gameplay.modifier.Modifier;
import yourmod.pipes.mod.*;

import java.util.List;

public class PipeMods implements IInitializeModPipes {
    @Override
    public void initialize() {
        PipeMod.pipes.add(new PipeModAllAll());
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
