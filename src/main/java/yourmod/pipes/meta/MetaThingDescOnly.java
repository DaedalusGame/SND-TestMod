package yourmod.pipes.meta;

public class MetaThingDescOnly extends MetaThing {
    String desc;

    public MetaThingDescOnly(String name, String desc) {
        super(name);
        this.desc = desc;
    }

    @Override
    public String describe() {
        return desc;
    }
}
