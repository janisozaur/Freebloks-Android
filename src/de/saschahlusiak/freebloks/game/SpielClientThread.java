package de.saschahlusiak.freebloks.game;

import java.io.IOException;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import de.saschahlusiak.freebloks.controller.SpielClient;


class SpielClientThread extends Thread {
	static final String tag = SpielClientThread.class.getSimpleName();
	SpielClient client;
	boolean godown;
	SendThread sendThread;

	static private class SendThread extends Thread {
		Looper looper;
		Handler handler;
		public void run()  {
			Looper.prepare();
			handler = new Handler();
			looper = Looper.myLooper();
			if (!interrupted())
				Looper.loop();
			Log.d("SendThread", "going down");
		}
	}

	SpielClientThread(SpielClient spiel) {
		this.client = spiel;
	}

	private synchronized boolean getGoDown() {
		return godown;
	}

	public synchronized void goDown() {
		godown = true;
	}

	Exception error = null;

	public Exception getError() {
		return error;
	}

	@Override
	public void run() {
		godown = false;
		sendThread = new SendThread();
		sendThread.start();
		/* wait short time for sendThread to come up, so that
		 * looper is initialized and can be quit, if following loop
		 * is finished to fast.
		 */
		try {
			sleep(200);
		} catch (InterruptedException e) {
			/* nothing */
		}

		try {
			do {
				client.poll();
				if (getGoDown()) {
					return;
				}
			} while (client.isConnected());
		}
		catch (IOException e) {
			e.printStackTrace();
			synchronized(client) {
				error = e;
			}
			return;
		}
		finally {
			client.disconnect();
			if (sendThread.looper != null)
				sendThread.looper.quit();
			else
				sendThread.interrupt();
			Log.i(tag, "disconnected, thread going down");
		}

	}

	public void post(Runnable r) {
		if (sendThread == null)
			return;

		sendThread.handler.post(r);
	}
}
