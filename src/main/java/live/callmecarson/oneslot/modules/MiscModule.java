package live.callmecarson.oneslot.modules;

import com.destroystokyo.paper.event.entity.PhantomPreSpawnEvent;
import me.lucko.helper.Commands;
import me.lucko.helper.Events;
import me.lucko.helper.event.filter.EventHandlers;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import me.lucko.helper.utils.Players;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MiscModule implements TerminableModule {
    @Override
    public void setup(@NotNull TerminableConsumer consumer) {
        Events.subscribe(PlayerQuitEvent.class, EventPriority.MONITOR)
                .handler(e -> {
                    Bukkit.getWorlds().forEach(w -> {
                        w.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
                        w.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
                    });
                }).bindWith(consumer);

        Events.subscribe(PlayerJoinEvent.class, EventPriority.MONITOR)
                .handler(e -> {
                    Bukkit.getWorlds().forEach(w -> {
                        w.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
                        w.setGameRule(GameRule.DO_WEATHER_CYCLE, true);
                    });
                }).bindWith(consumer);
    }
}
