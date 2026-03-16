package yourmod.screen;

import basemod.ledger.ILedgerPageType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tann.dice.Main;
import com.tann.dice.screens.dungeon.panels.book.page.ledgerPage.LedgerPage;
import com.tann.dice.util.Pixl;
import com.tann.dice.util.saves.Prefs;

import java.util.ArrayList;
import java.util.List;

import static yourmod.Mod.MODID;

public class MetaStorage {
    private List<String> meta = new ArrayList<>();

    public List<String> getPins() {
        return this.meta;
    }

    public void setPins(List<String> pins) {
        this.meta = pins;
        this.save();
    }

    public void save() {
        Prefs.setString(MODID+":meta", Main.getJson().toJson(this));
    }

    public void reset() {
        save();
    }
}
