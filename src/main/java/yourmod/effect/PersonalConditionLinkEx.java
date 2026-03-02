package yourmod.effect;

import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.effect.eff.conditionalBonus.conditionalRequirement.ConditionalRequirement;
import com.tann.dice.gameplay.effect.eff.conditionalBonus.conditionalRequirement.GSCConditionalRequirement;
import com.tann.dice.gameplay.fightLog.EntState;
import com.tann.dice.gameplay.fightLog.Snapshot;
import com.tann.dice.gameplay.trigger.personal.Personal;
import com.tann.dice.gameplay.trigger.personal.linked.stateCondition.GenericStateCondition;
import com.tann.dice.gameplay.trigger.personal.linked.stateCondition.PersonalConditionLink;
import com.tann.dice.gameplay.trigger.personal.linked.stateCondition.StateConditionType;

import java.util.Arrays;
import java.util.List;

public class PersonalConditionLinkEx extends PersonalConditionLink {

    final ConditionalRequirement req;
    final Personal linked;
    List<Personal> pList;

    public PersonalConditionLinkEx(ConditionalRequirement req, Personal linked) {
        super(req, linked);

        this.req = req;
        this.linked = linked;
    }

    public PersonalConditionLinkEx(StateConditionType type, Personal linked) {
        this(new GenericStateCondition(type), linked);
    }

    public PersonalConditionLinkEx(GenericStateCondition gsc, Personal linked) {
        this((ConditionalRequirement)(new GSCConditionalRequirement(gsc)), linked);
    }

    boolean recCheck = false;

    @Override
    public List<Personal> getLinkedPersonals(Snapshot snapshot, EntState entState) {
        if(recCheck) {
            return null;
        }

        try {
            recCheck = true;

            if (!this.req.isValid(snapshot, entState, entState, (Eff) null)) {
                return null;
            } else {
                if (this.pList == null) {
                    this.pList = Arrays.asList(this.linked);
                }

                return this.pList;
            }
        } finally {
            recCheck = false;
        }
    }
}
