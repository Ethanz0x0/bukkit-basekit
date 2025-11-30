package io.github.ethanz0x0.basekit.utils.sound;

import com.cryptomorin.xseries.XSound;
import org.bukkit.entity.Player;

public class SoundData {

    private final XSound sound;
    private final float volume;
    private final float pitch;

    public SoundData(XSound sound, float volume, float pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public XSound getSound() {
        return sound;
    }

    public float getVolume() {
        return volume;
    }

    public float getPitch() {
        return pitch;
    }

    public void playFor(Player player) {
        sound.play(player, volume, pitch);
    }
}
