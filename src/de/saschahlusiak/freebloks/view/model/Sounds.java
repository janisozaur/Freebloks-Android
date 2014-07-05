package de.saschahlusiak.freebloks.view.model;

import de.saschahlusiak.freebloks.R;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class Sounds extends SoundPool {
	static float GLOBAL_VOLUME = 0.5f;

	boolean enabled;

	public int SOUND_CLICK1;
	public int SOUND_CLICK2;
	public int SOUND_CLICK3;
	public int SOUND_HINT;
	public int SOUND_UNDO;
	public int SOUND_PLAYER_OUT;
	public int SOUND_CHAT;

	public Sounds(Context context) {
		super(10, AudioManager.STREAM_MUSIC, 0);
		enabled = true;
		loadSounds(context);
	}

	void loadSounds(Context context) {
		SOUND_CLICK1 = load(context, R.raw.click1, 1);
		SOUND_CLICK2 = load(context, R.raw.click2, 1);
		SOUND_CLICK3 = load(context, R.raw.click3, 1);
		SOUND_HINT = load(context, R.raw.hint, 1);
		SOUND_UNDO = load(context, R.raw.drip1, 1);
		SOUND_PLAYER_OUT = load(context, R.raw.playerout, 1);
		SOUND_CHAT = load(context, R.raw.chat, 1);
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void toggle() {
		enabled = !enabled;
	}

	public boolean play(int id, float volume, float rate) {
		if (!enabled)
			return false;
		play(id, volume * GLOBAL_VOLUME, volume * GLOBAL_VOLUME, 1, 0, rate);
		return true;
	}
}
