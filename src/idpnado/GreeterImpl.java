package idpnado;

import idpnado.interfaces.Greeter;
import idpnado.interfaces.Transmission;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.log4j.Logger;

import common.Constants;
import mediator.MediatorImpl;

public class GreeterImpl implements Greeter
{
	MediatorImpl mediator;
	SocketChannel channel;
	
	Logger logger = Logger.getLogger(GreeterImpl.class);
	
	public GreeterImpl(MediatorImpl mediator, SocketChannel channel)
	{
		this.mediator = mediator;
		this.channel = channel;
	}
	
	/* (non-Javadoc)
	 * @see idpnado.Greeter#greetClient()
	 */
	@Override
	public void greetClient()
	{
		logger.debug("Greeting client");
		
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
				Transmission transmission = new TransmissionImpl(channel);
								
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
					logger.error("Error on transmission");
					
					transmission.close();
					return;
				}
			}
				
		}).start();
	}
}
