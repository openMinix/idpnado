package idpnado.interfaces;

import java.nio.ByteBuffer;

public interface Transmission {

	public abstract boolean open();

	public abstract boolean writeMessage(ByteBuffer buffer);

	public abstract boolean writeBytes(byte[] bytes);

	public abstract boolean writeAck();

	public abstract boolean writeInterest(int interest);

	public abstract boolean writeString(String text);

	public abstract ByteBuffer getMessage(int size);

	public abstract boolean getAck();

	public abstract ByteBuffer getMessage();

	public abstract byte[] getChunk();

	public abstract void close();

}