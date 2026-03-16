package yourmod.effect;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.dice.Main;
import com.tann.dice.gameplay.effect.targetable.ability.Ability;
import com.tann.dice.gameplay.fightLog.Snapshot;
import com.tann.dice.gameplay.trigger.global.eff.GlobalStartTurnEff;
import com.tann.dice.gameplay.trigger.global.linked.DipPanel;
import com.tann.dice.gameplay.trigger.global.scaffolding.turnRequirement.GlobalTurnRequirement;
import com.tann.dice.gameplay.trigger.global.scaffolding.turnRequirement.TurnRequirementAll;
import com.tann.dice.util.ImageActor;

public class GlobalBeginTurnSpell
        extends GlobalStartTurnEff {
    final Ability sp;

    public GlobalBeginTurnSpell(Ability sp) {
        super(sp.getBaseEffect());
        if (sp.getBaseEffect().needsTarget()) {
            throw new RuntimeException("invalid start of turn eff");
        }
        this.sp = sp;
    }

    @Override
    public String describeForSelfBuff() {
        return "[notranslate]" + Main.t("At the start of each turn") + ", " + this.descSpellEff();
    }

    private String descSpellEff() {
        return this.sp.getBaseEffect().describe(true);
    }

    @Override
    public void startOfTurnGeneral(Snapshot snapshot, int turn) {
        snapshot.target(null, this.sp, false);
    }

    @Override
    public Actor makePanelActorI(boolean big) {
        return DipPanel.makeSidePanelGroup(new TurnRequirementAll().makePanelActor(), new ImageActor(this.sp.getImage()), GlobalTurnRequirement.TURN_COL);
    }

    @Override
    public String describeForHourglass() {
        return this.descSpellEff();
    }
}