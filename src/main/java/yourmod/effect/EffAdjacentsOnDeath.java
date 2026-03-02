package yourmod.effect;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.dice.Main;
import com.tann.dice.gameplay.content.ent.Ent;
import com.tann.dice.gameplay.content.ent.type.EntType;
import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.effect.eff.EffBill;
import com.tann.dice.gameplay.effect.eff.VisualEffectType;
import com.tann.dice.gameplay.effect.targetable.SimpleTargetable;
import com.tann.dice.gameplay.fightLog.EntState;
import com.tann.dice.gameplay.fightLog.Snapshot;
import com.tann.dice.gameplay.trigger.Collision;
import com.tann.dice.gameplay.trigger.personal.Personal;
import com.tann.dice.util.Pixl;

import java.util.Iterator;
import java.util.List;

public class EffAdjacentsOnDeath extends Personal {
    public Eff eff;

    public EffAdjacentsOnDeath(Eff eff) {
        this.eff = eff;
    }

    public boolean showInEntPanelInternal() {
        return true;
    }

    public String getImageName() {
        return "explode";
    }

    public String describeForSelfBuff() {
        return "[notranslate]" + Main.t("On death, adjacent allies") + ": " + this.eff.describe();
    }

    public Actor makePanelActorI(boolean big) {
        Actor effImage = this.eff.getBasicImage();
        return (new Pixl()).image(this.getImage()).text("[notranslate][text]: ").actor(effImage).pix();
    }

    public void onDeath(EntState self, Snapshot snapshot) {
        List<? extends EntState> var3 = snapshot.getAdjacents(self, true, false, 1, 1);

        for (EntState es : var3) {
            es.hit(this.eff, self.getEnt());
        }
    }

    public long getCollisionBits(Boolean player) {
        return Collision.death(player);
    }
}