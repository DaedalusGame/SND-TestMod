package yourmod.buffs;

import com.tann.dice.gameplay.effect.Buff;
import com.tann.dice.gameplay.fightLog.EntState;
import com.tann.dice.gameplay.trigger.personal.merge.Poison;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class BuffUtil {
    static Field buffsField;

    static {
        try {
            buffsField = EntState.class.getDeclaredField("buffs");
            buffsField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static List<Buff> getBuffs(EntState state) {
        try {
            List<Buff> buffs = (List<Buff>)buffsField.get(state);
            return buffs;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public static int getFuel(EntState state) {
        List<Buff> buffs = getBuffs(state);
        int n = 0;

        for (Buff buff : buffs) {
            if(buff.personal instanceof Fuel) {
                n += ((Fuel) buff.personal).getValue();
            }
        }

        return n;
    }

    public static void addFuel(EntState state, int n) {
        state.addBuff(new Buff(new Fuel(n)));

        List<Buff> buffs = getBuffs(state);
        for(int i = buffs.size() - 1; i >= 0; --i) {
            Buff b = buffs.get(i);
            if(b.personal instanceof Fuel) {
                Fuel poison = (Fuel) b.personal;

                if(poison.getValue() == 0) {
                    buffs.remove(i);
                }
            }
        }
    }

    public static void addPoison(EntState state, int n) {
        state.addBuff(new Buff(new Poison(n)));

        List<Buff> buffs = getBuffs(state);
        for(int i = buffs.size() - 1; i >= 0; --i) {
            Buff b = buffs.get(i);
            if(b.personal instanceof Poison) {
                Poison poison = (Poison) b.personal;

                if(poison.getPoisonDamage() == 0) {
                    buffs.remove(i);
                }
            }
        }
    }
}
