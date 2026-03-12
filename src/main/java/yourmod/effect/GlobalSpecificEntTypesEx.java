package yourmod.effect;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.dice.gameplay.content.ent.Ent;
import com.tann.dice.gameplay.content.ent.type.EntType;
import com.tann.dice.gameplay.content.ent.type.MonsterType;
import com.tann.dice.gameplay.fightLog.EntState;
import com.tann.dice.gameplay.trigger.global.linked.DipPanel;
import com.tann.dice.gameplay.trigger.global.linked.GlobalLinked;
import com.tann.dice.gameplay.trigger.global.linked.GlobalSpecificEntTypes;
import com.tann.dice.gameplay.trigger.personal.Personal;
import com.tann.dice.util.Colours;
import com.tann.dice.util.Pixl;
import com.tann.dice.util.Tann;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/*public class GlobalSpecificEntTypesEx extends GlobalLinked {
    final List<EntType> types;
    final Personal personal;

    public GlobalSpecificEntTypesEx(Personal personal, EntType... types) {
        super(personal);
        this.personal = personal;
        this.types = Arrays.asList(types);
    }

    public GlobalSpecificEntTypesEx(EntType type, Personal personal) {
        this(personal, type);
    }

    public String describeForSelfBuff() {
        return Tann.translatedCommaList(this.types) + ":[n]" + this.personal.describeForSelfBuff();
    }

    public Personal getLinkedPersonal(EntState entState) {
        Ent de = entState.getEnt();
        return this.types.contains(de.entType) ? this.personal : super.getLinkedPersonal(entState);
    }

    public Actor makePanelActorI(boolean big) {
        Pixl exception = new Pixl(0);
        Iterator var3 = this.types.iterator();

        while(var3.hasNext()) {
            EntType t = (EntType)var3.next();
            exception.image(t.portrait, t instanceof MonsterType).gap(1);
        }

        exception.cancelRowGap();
        return DipPanel.makeSidePanelGroup(big, exception.pix(), this.personal, Colours.red);
    }

    public long getCollisionBits(Boolean player) {
        return this.personal.getCollisionBits(false);
    }

    public GlobalLinked splice(Personal newCenter) {
        return new GlobalSpecificEntTypes(newCenter, (EntType[])this.types.toArray(new EntType[0]));
    }
}*/