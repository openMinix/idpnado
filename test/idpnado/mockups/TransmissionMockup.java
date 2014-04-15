package idpnado.mockups;

import java.nio.ByteBuffer;

import idpnado.interfaces.Transmission;

public class TransmissionMockup implements Transmission {

	@Override
	public boolean open() {
		return true;
	}

	@Override
	public boolean writeMessage(ByteBuffer buffer) {
		
		return true;
	}

	@Override
	public boolean writeBytes(byte[] bytes) {
		return true;
	}

	@Override
	public boolean writeAck() {
		return true;
	}

	@Override
	public boolean writeInterest(int interest) {
		return true;
	}

	@Override
	public boolean writeString(String text) {
		return true;
	}

	@Override
	public ByteBuffer getMessage(int size) {
		// TODO Auto-generated method stub
		return ByteBuffer.allocate(size);
	}

	@Override
	public boolean getAck() {
		return true;
	}

	@Override
	public ByteBuffer getMessage() {
		return ByteBuffer.allocate(0);
	}

	@Override
	public byte[] getChunk() {
		return new byte[10];
	}

	@Override
	public void close() {

	}

}
