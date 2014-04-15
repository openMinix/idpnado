package idpnado;

import idpnado.interfaces.Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

import mediator.MediatorImpl;

public class ServerImpl implements Server
{
	private MediatorImpl mediator;
	ServerSocketChannel serverSocketChannel = null;
	
	String IP = null;
	int port = -1;
	
	Logger logger = Logger.getLogger(ServerImpl.class);
	
	boolean mustExit = false;
	
	public ServerImpl(MediatorImpl mediator, String IP, int port)
	{
		this.mediator = mediator;
		this.IP = IP;
		this.port = port;
	}
	
	public boolean create()
	{
		logger.info("Creating server");
		try
		{
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.bind(new InetSocketAddress(IP, port));
			
			new Thread(new Runnable()
			{	
			
				@Override
				public void run()
				{
					while(!mustExit)
					{
						try
						{
							SocketChannel channel = serverSocketChannel.accept();
							
							new GreeterImpl(mediator, channel).greetClient();
						}
						catch(ClosedChannelException e)
						{
							logger.error("Error ClosedChannelException");
							mustExit = true;
						}
						catch(IOException e)
						{
							logger.error("Error IOException on creating server");
						 
							mustExit = true;
						}
					}
				}
			}).start();
			
		}
		catch(IOException e)
		{
			logger.error("Error on IOException");
			return false;
		}
		
		return true;
	}
	
	public void close()
	{
		mustExit = true;
	}
	
}
