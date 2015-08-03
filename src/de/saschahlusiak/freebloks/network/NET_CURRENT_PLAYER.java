package de.saschahlusiak.freebloks.network;

import java.io.ByteArrayOutputStream;

public class NET_CURRENT_PLAYER extends NET_HEADER {
	public int player; /* int 8 */

	public NET_CURRENT_PLAYER() {
		super(Network.MSG_CURRENT_PLAYER, 1);
	}

	public NET_CURRENT_PLAYER(NET_HEADER from) throws ProtocolException {
		super(from);
		player = buffer[0];
		
		if (player < -1 || player > 3)
			throw new ProtocolException("invalid player: " + player);
	}

	@Override
	void prepare(ByteArrayOutputStream bos) {
		super.prepare(bos);
		bos.write(player);
	}
}
