package yourmod.keywords;

import basemod.keywords.IKeywordColorTag;
import com.badlogic.gdx.graphics.Color;
import com.tann.dice.gameplay.effect.eff.keyword.Keyword;
import com.tann.dice.util.ui.TextWriter;

public class KeywordColorTagSuffix implements IKeywordColorTag {
    String suffix;
    Color color;

    public KeywordColorTagSuffix(String suffix, Color color) {
        this.suffix = suffix;
        this.color = color;
    }

    @Override
    public String getColorTag(Keyword keyword) {
        String n = keyword.getName();
        if(n.toLowerCase().endsWith(suffix)) {
            n = TextWriter.getTag(keyword.getColour()) + n.substring(0, n.length() - suffix.length()).toLowerCase() + TextWriter.getTag(color) + suffix + "[cu][cu]";
        }

        return n;
    }
}
