package yourmod.effect;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.dice.gameplay.content.ent.Ent;
import com.tann.dice.gameplay.content.ent.Hero;
import com.tann.dice.gameplay.content.ent.type.HeroType;
import com.tann.dice.gameplay.content.ent.type.lib.HeroTypeLib;
import com.tann.dice.gameplay.content.item.Item;
import com.tann.dice.gameplay.fightLog.EntState;
import com.tann.dice.gameplay.fightLog.Snapshot;
import com.tann.dice.gameplay.fightLog.event.snapshot.DiscardEvent;
import com.tann.dice.gameplay.trigger.Collision;
import com.tann.dice.gameplay.trigger.personal.Personal;
import com.tann.dice.statics.Images;
import com.tann.dice.util.Colours;
import com.tann.dice.util.Pixl;

import java.util.Iterator;
import java.util.List;

public class LostAtEndOfFight extends Personal {
    final String itemName;

    public LostAtEndOfFight(String itemName) {
        this.itemName = itemName;
    }

    public String describeForSelfBuff() {
        return "Discarded after one fight";
    }

    public boolean showInEntPanelInternal() {
        return false;
    }

    public String getImageName() {
        return "permalose";
    }

    public Actor makePanelActorI(boolean big) {
        return (new Pixl()).image(Images.ui_cross, Colours.grey).pix();
    }

    @Override
    public void endOfLevel(EntState self, Snapshot snapshot) {
        List<Item> items = self.getEnt().getItems();

        for (Item item : items) {
            if (item.getName(false).contains(this.itemName)) {
                snapshot.addEvent(new DiscardEvent(item, "[purple]Discarded " + item.getName(true)));
                snapshot.getFightLog().getContext().getParty().discardItem(item);
            }
        }
    }
}
