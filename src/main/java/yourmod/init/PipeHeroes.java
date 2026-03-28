package yourmod.init;

import basemod.pipes.IInitializeHeroPipes;
import com.tann.dice.gameplay.content.ent.die.side.EnSiBi;
import com.tann.dice.gameplay.content.ent.type.HeroCol;
import com.tann.dice.gameplay.content.ent.type.HeroType;
import com.tann.dice.gameplay.content.ent.type.bill.HTBill;
import com.tann.dice.gameplay.content.gen.pipe.entity.hero.PipeHero;
import com.tann.dice.gameplay.effect.eff.EffBill;
import com.tann.dice.gameplay.effect.eff.EffType;
import com.tann.dice.gameplay.effect.eff.TargetingType;
import yourmod.pipes.hero.PipeHeroMetaSide;
import yourmod.pipes.hero.PipeHeroMetaSideMini;

import java.util.List;

public class PipeHeroes implements IInitializeHeroPipes {
    @Override
    public void initialize() {
        PipeHero.pipes.add(new PipeHeroMetaSide());
        PipeHero.pipes.add(new PipeHeroMetaSideMini());
    }

    @Override
    public void modifyHiddenModifiers(List<HeroType> list) {
        list.add(
            new HTBill(HeroCol.amber, 0)
                .name("DHUser")
                .sides(new EnSiBi().effect(new EffBill().targetType(TargetingType.Group).friendly().type(EffType.valueOf("Use"))).noVal())
                .bEntType()
        );
    }
}
