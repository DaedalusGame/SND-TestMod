package yourmod.init;

import basemod.pipes.IInitializeMonsterPipes;
import com.tann.dice.gameplay.content.ent.type.MonsterType;
import com.tann.dice.gameplay.content.gen.pipe.entity.monster.PipeMonster;
import yourmod.pipes.monster.PipeMonsterReplica;

import java.util.List;

public class PipeMonsters implements IInitializeMonsterPipes {
    @Override
    public void initialize() {
        PipeMonster.pipes.add(new PipeMonsterReplica());
    }

    @Override
    public void modifyHiddenModifiers(List<MonsterType> list) {

    }
}
