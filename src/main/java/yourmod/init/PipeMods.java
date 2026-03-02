package yourmod.init;

import basemod.pipes.IInitializeModPipes;
import com.tann.dice.gameplay.content.gen.pipe.mod.PipeMod;
import com.tann.dice.gameplay.modifier.Modifier;
import yourmod.pipes.PipeModSpellKeyword;

import java.util.List;

public class PipeMods implements IInitializeModPipes {
    @Override
    public void initialize() {
        PipeMod.pipes.add(new PipeModSpellKeyword());
    }

    @Override
    public void modifyHiddenModifiers(List<Modifier> list) {

    }
}
