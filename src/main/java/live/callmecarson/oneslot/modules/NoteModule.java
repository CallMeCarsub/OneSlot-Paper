package live.callmecarson.oneslot.modules;

import me.lucko.helper.Commands;
import me.lucko.helper.Events;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import me.lucko.helper.utils.Players;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NoteModule implements TerminableModule {
    private Map<UUID, String> lastUsername = new HashMap<>();
    private Map<UUID, String> notes = new HashMap<>();

    @Override
    public void setup(@NotNull TerminableConsumer consumer) {
        Events.subscribe(PlayerJoinEvent.class, EventPriority.MONITOR)
                .handler(e -> {
                    this.showNote(e.getPlayer());
                }).bindWith(consumer);

        Commands.create()
                .assertPlayer()
                .handler(cmd -> {
                    String note = String.join(" ", cmd.args());
                    if(note.replace(" ","").length() == 0){
                        this.showNote(cmd.sender());
                        return;
                    }
                    if(note.equalsIgnoreCase("clear") || note.equalsIgnoreCase("reset")){
                        notes.remove(cmd.sender().getUniqueId());
                        cmd.reply("&aNote was cleared!");
                        return;
                    }
                    notes.put(cmd.sender().getUniqueId(), note);
                    lastUsername.put(cmd.sender().getUniqueId(), cmd.sender().getName());
                    cmd.reply("&aNote set successfully! The next player will see your note when they login.",note,"&7You can do &e/note clear &7to clear your note.");
                }).registerAndBind(consumer, "note","notes");

    }

    private void showNote(Player player){
        if(notes.containsKey(player.getUniqueId())){
            Players.msg(player,
                    "&dNote from &b" + lastUsername.get(player.getUniqueId()),
                    "&f  " + notes.get(player.getUniqueId()),
                    "&7Type &e/note clear &7to clear, or &e/note <message> &7to set a new note.");
        }else{
            Players.msg(player, "&7There is no current note. Use &e/note <message> &7to set one.");
        }
    }
}
