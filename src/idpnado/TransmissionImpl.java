package idpnado;

import idpnado.interfaces.Transmission;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.log4j.Logger;

import common.Constants;

public class TransmissionImpl implements Transmission
{
	public SocketChannel channel;
	
	String IP = null;
	int port = -1;
	String myName = null;
	
	boolean isOpened = false;
	
	static Logger logger = Logger.getLogger(TransmissionImpl.class);
	public TransmissionImpl(SocketChannel channel)
	{
		this.channel = channel;
		
		isOpened = true;
	}
	
	public TransmissionImpl(String IP, int port, String myName)
	{
		this.IP = IP;
		this.port = port;
		this.myName = myName;
	}
	
	/* (non-Javadoc)
	 * @see idpnado.Transimission#open()
	 */
	@Override
	public boolean open()
	{
		logger.debug("Channel is opening");
		if(isOpened)
			return true;
		
		if(IP == null || port == -1)
			return false;
		
		try
		{
			channel = SocketChannel.open(new InetSocketAddress(IP, port));
			channel.configureBlocking(false);
		}
		catch(IOException e)
		{
			logger.error("Error on channel connection");
			
			return false;
		}
		
		return true;
	}
	
	/* (non-Javadoc)
	 * @see idpnado.Transimission#writeMessage(java.nio.ByteBuffer)
	 */
	@Override
	public boolean writeMessage(ByteBuffer buffer)
	{
		int written = 0; 
		
		logger.debug("Writing " + buffer.toString() );
		buffer.flip();
		int capacity = buffer.capacity();
		
		try
		{
			while(capacity > 0)
			{
				written = channel.write(buffer);
				if(written == -1)
					return false;
				
				capacity -= written;
			}
		}
		catch(IOException e)
		{
			logger.error("Error on writing " + buffer.toString());
			
		}
		
		return true;
	}
	
	/* (non-Javadoc)
	 * @see idpnado.Transimission#writeBytes(byte[])
	 */
	@Override
	public boolean writeBytes(byte[] bytes)
	{
		ByteBuffer buffer = ByteBuffer.allocate(bytes.length + 4);
		buffer.clear();
		
		buffer.putInt(bytes.length);
		buffer.put(bytes);
		
		return writeMessage(buffer);
	}
	
	/* (non-Javadoc)
	 * @see idpnado.Transimission#writeAck()
	 */
	@Override
	public boolean writeAck()
	{
		logger.debug("Writing ack");
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.putInt(Constants.ack);
		
		return writeMessage(buffer);
	}
	
	/* (non-Javadoc)
	 * @see idpnado.Transimission#writeInterest(int)
	 */
	@Override
	public boolean writeInterest(int interest)
	{
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.putInt(interest);
		
		return writeMessage(buffer);		
	}
	
	/* (non-Javadoc)
	 * @see idpnado.Transimission#writeString(java.lang.String)
	 */
	@Override
	public boolean writeString(String text)
	{
		byte[] bytes = text.getBytes();
		
		return writeBytes(bytes);
	}
	
	/* (non-Javadoc)
	 * @see idpnado.Transimission#getMessage(int)
	 */
	@Override
	public ByteBuffer getMessage(int size)
	{
		logger.debug("Receiving message of size " + size);
		ByteBuffer buffer = ByteBuffer.allocate(size);
		buffer.clear();
		int read = 0;
		
		try
		{
			int capacity = buffer.capacity();
			while(capacity > 0)
			{
				read = channel.read(buffer);
				if(read == -1)
					return null;
				
				capacity -= read;
			}
		}
		catch(IOException e)
		{
			
		}
		
		return buffer;
	}
	
	/* (non-Javadoc)
	 * @see idpnado.Transimission#getAck()
	 */
	@Override
	public boolean getAck()
	{
		ByteBuffer buffer = getMessage(4);
		if(buffer == null)
			return false;
		
		buffer.flip();
		if(buffer.getInt() == Constants.ack)
			return true;
		
		return false;
	}
	
	/* (non-Javadoc)
	 * @see idpnado.Transimission#getMessage()
	 */
	@Override
	public ByteBuffer getMessage()
	{
		logger.info("Getting message");
		ByteBuffer buffer = ByteBuffer.allocate(4);
		int read = 0;
		
		int capacity = buffer.capacity();
		try
		{
			while(capacity > 0)
			{
				read = channel.read(buffer);
				if(read == -1)
					return null;
				
				capacity -= read;
			}
			
			buffer.flip();
			capacity = buffer.getInt();
			
			buffer = ByteBuffer.allocate(capacity);
			while(capacity > 0)
			{
				read = channel.read(buffer);
				if(read == -1)
					return null;
				
				capacity -= read;
			}
		}
		catch(IOException e)
		{
			
			return null;
		}
		
		return buffer;
	}	
	
	/* (non-Javadoc)
	 * @see idpnado.Transimission#getChunk()
	 */
	@Override
	public byte[] getChunk()
	{
		ByteBuffer buffer = ByteBuffer.allocate(4);
		int read = 0;
		
		int capacity = buffer.capacity();
		try
		{
			while(capacity > 0)
			{
				read = channel.read(buffer);
				if(read == -1)
					return null;
				
				capacity -= read;
			}
			
			buffer.flip();
			capacity = buffer.getInt();
			
			buffer = ByteBuffer.allocate(capacity);
			while(capacity > 0)
			{
				read = channel.read(buffer);
				if(read == -1)
					return null;
				
				capacity -= read;
			}
		}
		catch(IOException e)
		{
		
			return null;
		}
		
		buffer.flip();
		byte[] bytes = new byte[buffer.capacity()];
		buffer.get(bytes);
		
		return bytes;
	}		
	
	/* (non-Javadoc)
	 * @see idpnado.Transimission#close()
	 */
	@Override
	public void close()
	{
		logger.debug("Closing channel");
		try
		{
			channel.close();
		}
		catch(IOException e)
		{
			logger.error("Error on close of channel");
			e.printStackTrace();
		}
	}
}
