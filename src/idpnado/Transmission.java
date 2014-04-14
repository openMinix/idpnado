package idpnado;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.log4j.Logger;

import common.Constants;

public class Transmission
{
	public SocketChannel channel;
	
	String IP = null;
	int port = -1;
	String myName = null;
	
	boolean isOpened = false;
	
	static Logger logger = Logger.getLogger(Transmission.class);
	public Transmission(SocketChannel channel)
	{
		this.channel = channel;
		
		isOpened = true;
	}
	
	public Transmission(String IP, int port, String myName)
	{
		this.IP = IP;
		this.port = port;
		this.myName = myName;
	}
	
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
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
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
			e.printStackTrace();
		}
		
		return true;
	}
	
	public boolean writeBytes(byte[] bytes)
	{
		ByteBuffer buffer = ByteBuffer.allocate(bytes.length + 4);
		buffer.clear();
		
		buffer.putInt(bytes.length);
		buffer.put(bytes);
		
		return writeMessage(buffer);
	}
	
	public boolean writeAck()
	{
		logger.debug("Writing ack");
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.putInt(Constants.ack);
		
		return writeMessage(buffer);
	}
	
	public boolean writeInterest(int interest)
	{
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.putInt(interest);
		
		return writeMessage(buffer);		
	}
	
	public boolean writeString(String text)
	{
		byte[] bytes = text.getBytes();
		
		return writeBytes(bytes);
	}
	
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
			e.printStackTrace();
		}
		
		return buffer;
	}
	
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
			e.printStackTrace();
			return null;
		}
		
		return buffer;
	}	
	
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
			e.printStackTrace();
			return null;
		}
		
		buffer.flip();
		byte[] bytes = new byte[buffer.capacity()];
		buffer.get(bytes);
		
		return bytes;
	}		
	
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
