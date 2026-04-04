package yourmod.init;

import basemod.effect.EffectRegistry;
import basemod.effect.IEffType;
import com.tann.dice.gameplay.content.ent.Ent;
import com.tann.dice.gameplay.effect.eff.Eff;
import com.tann.dice.gameplay.effect.eff.EffType;
import com.tann.dice.gameplay.effect.eff.conditionalBonus.conditionalRequirement.ConditionalRequirement;
import com.tann.dice.gameplay.effect.targetable.Targetable;
import com.tann.dice.gameplay.fightLog.EntState;
import com.tann.dice.gameplay.trigger.personal.linked.stateCondition.StateConditionType;
import com.tann.dice.util.lang.Words;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EffTypes {
    static Method heal;

    static {
        try {
            heal = EntState.class.getDeclaredMethod("heal", int.class);
            heal.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static void init() {
        EffectRegistry.register("Use", false, new IEffType() {
            @Override
            public void hit(EntState state, Eff eff, Ent source, Targetable targetable) {
                state.useDie();
            }

            @Override
            public String describe(EffType type, Eff eff) {
                return "use dice";
            }
        });
        /*EffectRegistry.register("Purify", false, new IEffType() {
            @Override
            public void hit(EntState state, Eff eff, Ent source, Targetable targetable) {
                int value = eff.getValue();
                if (eff.hasValue() && eff.getValue() != value) {
                    eff = eff.copy();
                    eff.setValue(value);
                }

                if(state.isPlayer()) {
                    try {
                        heal.invoke(state, value);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    state.damage(value, source, eff, targetable);
                }
            }

            @Override
            public String describe(EffType type, Eff source) {
                return to(source, "purification");
            }
        });
        EffectRegistry.register("Defile", false, new IEffType() {
            @Override
            public void hit(EntState state, Eff eff, Ent source, Targetable targetable) {
                int value = eff.getValue();
                if (eff.hasValue() && eff.getValue() != value) {
                    eff = eff.copy();
                    eff.setValue(value);
                }

                if(!state.isPlayer()) {
                    try {
                        heal.invoke(state, value);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    state.damage(value, source, eff, targetable);
                }
            }

            @Override
            public String describe(EffType type, Eff source) {
                return to(source, "defilement");
            }
        });*/
    }

    private static String descCondition(Eff source) {
        String s = "";

        for (ConditionalRequirement req : source.getRestrictions()) {

        }

        if(s.length() > 0) {
            s += " ";
        }

        return s;
    }

    private static String to(Eff source, String name) {
        String part = source.getValue() + " " + name;
        if (source.isFriendlyForce()) {
            switch (source.getTargetingType()) {
                case Single:
                    return part + " to " + descCondition(source) + Words.entName(source, false);
                case Group:
                    return part + " to all " + descCondition(source) + Words.entName(source, true);
                case Self:
                    return source.getValue() + " self " + name;
                default:
                    return "weird unknown friend "+name+" description: " + source.getTargetingType();
            }
        } else {
            switch (source.getTargetingType()) {
                case Single:
                    return part;
                case Group:
                    return part + " to all " + descCondition(source) + Words.entName(source, true);
                case Self:
                default:
                    return "ahh help "+name+" targetingType" + source.getTargetingType();
                case ALL:
                    return part + " to all " + descCondition(source) + "heroes and monsters";
                case Top:
                    return part + " to the top-most " + Words.entName(source, (Boolean)null);
                case Bot:
                    return part + " to the bottom-most " + Words.entName(source, (Boolean)null);
                case Mid:
                    return part + " to the middle " + Words.entName(source, (Boolean)null);
                case TopAndBot:
                    return part + " to the top and bottom " + Words.entName(source, true);
            }
        }
    }
}
