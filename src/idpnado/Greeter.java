package idpnado;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import common.Constants;
import mediator.Mediator;

public class Greeter
{
	Mediator mediator;
	SocketChannel channel;
	
	public Greeter(Mediator mediator, SocketChannel channel)
	{
		this.mediator = mediator;
		this.channel = channel;
	}
	
	public void greetClient()
	{
		new Thread(new Runnable()
		{	
			public int getInterest(Transmission transmission) throws IOException
			{
				ByteBuffer buffer = transmission.getMessage(4);
				if(buffer == null)
				{
					channel.close();
					return -1;
				}

				buffer.flip();
					
				return buffer.getInt();
				
			}
			
			public void startUpload(Transmission transmission) throws IOException
			{
				if(!transmission.writeAck())
				{
					channel.close();
					return;
				}
					
				ByteBuffer buffer = transmission.getMessage();
				if(buffer == null)
				{
					channel.close();
					return;
				}
					
				if(!transmission.writeAck())
				{
					channel.close();
					return;
				}						
					
				buffer.flip();
				byte[] bytes = new byte[buffer.capacity()];
				buffer.get(bytes);
										
				String fileName = new String(bytes);
				
				buffer = transmission.getMessage();
				if(buffer == null)
				{
					channel.close();
					return;
				}
					
				if(!transmission.writeAck())
				{
					channel.close();
					return;
				}					
				
				buffer.flip();				
				bytes = new byte[buffer.capacity()];
				buffer.get(bytes);
										
				String destinationName = new String(bytes);					
				
				mediator.startUpload(fileName, destinationName, transmission);				
			}
			
			@Override
			public void run()
			{
				Transmission transmission = new Transmission(channel);
								
				try
				{
					int interest = getInterest(transmission);
					
					switch (interest)
					{
						case Constants.fileTransferInterest:
							startUpload(transmission);						
							break;

						default:
							channel.close();
							return;
					}
				}
			
				catch(IOException e)
				{
					e.printStackTrace(); // TODO : log
					transmission.close();
					return;
				}
			}
				
		}).start();
	}
}
