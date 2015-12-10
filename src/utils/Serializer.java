package utils;


/**
Serialization is the conversion of an object to a series of bytes, so that
the object can be easily saved to persistent storage or streamed across a communication link. 
The byte stream can then be deserialized - converted into a replica of the original object.
 */
public interface Serializer 
{
	//Push objects onto the stack
	void push(Object o);
	Object pop();

	//All objects pushed are then saved in a single ‘write’ operation
	void write() throws Exception; 

	//If read is called, a persistence state is restored… and recovered by popping the stack
	void read() throws Exception;
}

//push objects to be serialised onto a stack prior to write
//if read has taken place, pop read objects back from stack.
