package idpnado;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import mediator.Mediator;

public class Server
{
	private Mediator mediator;
	ServerSocketChannel serverSocketChannel = null;
	
	String IP = null;
	int port = -1;
	
	boolean mustExit = false;
	
	public Server(Mediator mediator, String IP, int port)
	{
		this.mediator = mediator;
		this.IP = IP;
		this.port = port;
	}
	
	public boolean create()
	{
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
							
							new Greeter(mediator, channel).greetClient();
						}
						catch(ClosedChannelException e)
						{
							//TODO log;
							mustExit = true;
						}
						catch(IOException e)
						{
							e.printStackTrace(); //TODO : log
							mustExit = true;
						}
					}
				}
			}).start();
			
		}
		catch(IOException e)
		{
			e.printStackTrace(); // TODO : log
			return false;
		}
		
		return true;
	}
	
	public void close()
	{
		mustExit = true;
	}
	
}
