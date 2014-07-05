package de.saschahlusiak.freebloks.game;

import android.app.backup.BackupManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import de.saschahlusiak.freebloks.controller.PlayerData;
import de.saschahlusiak.freebloks.database.HighscoreDB;

public class AddScoreTask extends AsyncTask<PlayerData,Void,Void> {
	int game_mode;
	Context context;

	public AddScoreTask(Context context, int game_mode) {
		this.game_mode = game_mode;
		this.context = context;
	}

	@Override
	protected Void doInBackground(PlayerData... data) {
		HighscoreDB db = new HighscoreDB(context);
		if (db.open()) {
			if (data != null)
				for (int i = 0; i < data.length; i++) if (data[i].is_local) {
					int flags = 0;
					if (data[i].is_perfect)
						flags |= HighscoreDB.FLAG_IS_PERFECT;

					db.addHighscore(
							game_mode,
							data[i].points,
							data[i].stones_left,
							data[i].player1,
							data[i].place,
							flags);
				}

			db.close();

			if (Build.VERSION.SDK_INT >= 8) {
				BackupManager backupManager = new BackupManager(context);
				backupManager.dataChanged();
			}
		}
		return null;
	}
}