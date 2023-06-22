package live.callmecarson.oneslot;

import live.callmecarson.oneslot.modules.*;
import lombok.Getter;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
import me.lucko.helper.plugin.ap.Plugin;

@Plugin(name="OneSlot",version="1.0-SNAPSHOT",hardDepends={"helper","ProtocolLib"}, softDepends={}, apiVersion = "1.17")
@Getter
public class OneSlot extends ExtendedJavaPlugin {
    @Getter private static OneSlot instance;

    @Override
    protected void enable() {
        instance = this;
        new MiscModule().bindModuleWith(this);
        new NoteModule().bindModuleWith(this);
    }

    @Override
    protected void disable() {

    }
}
