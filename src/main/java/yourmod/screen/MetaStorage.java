package yourmod.screen;

import basemod.ledger.ILedgerPageType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.dice.Main;
import com.tann.dice.screens.dungeon.panels.book.page.ledgerPage.LedgerPage;
import com.tann.dice.util.Pixl;
import com.tann.dice.util.saves.Prefs;
import com.tann.dice.util.ui.resolver.MetaResolver;
import yourmod.pipes.meta.MetaThing;
import yourmod.pipes.meta.MetaThingLib;

import java.util.ArrayList;
import java.util.List;

import static yourmod.Mod.MODID;

public class MetaStorage {
    private List<String> meta = new ArrayList<>();

    public boolean hasPin(String s) {
        for (String pin : meta) {
            if(pin.equalsIgnoreCase(s)) {
                return true;
            }
        }

        return false;
    }

    public List<String> getPins() {
        return this.meta;
    }

    public void setPins(List<String> pins) {
        this.meta = pins;
        this.save();
    }

    public void save() {
        MetaThingLib.clearCache();
        Prefs.setString(MODID+":meta", Main.getJson().toJson(this));
    }

    public void reset() {
        save();
    }
}
