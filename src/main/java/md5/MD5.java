package md5;

import java.math.BigInteger;

public class MD5 {
	    
	private static Long rest = 0xffffffff00000000L;
	
	Long[] preparedMessage;
	
	private static Long[] initialVectors = {0x67452301L, 0xefcdab89L, 0x98badcfeL, 0x10325476L};
	
	private static Long[] vectors;
	
	private static String hashedMessage;
	
	int roundNumber;
    int kConstantPosition;
    int shiftValuePosition;
    int messageBytePosition;
   	Long functionResult;  
    
    int[] shiftValues = {
    		7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22, 
    		7, 12, 17, 22, 5 ,9 ,14 ,20, 5 ,9 ,14 ,20, 
    		5 ,9 ,14 ,20, 5 ,9 ,14 ,20, 4, 11, 16, 23, 
    		4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, 
    		6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21};
    
   	Long[] kConstants = {
   			0xD76AA478L, 0xE8C7B756L, 0x242070DBL, 0xC1BDCEEEL, 0xF57C0FAFL,
   			0x4787C62AL, 0xA8304613L, 0xFD469501L, 0x698098D8L, 0x8B44F7AFL, 0xFFFF5BB1L, 
   			0x895CD7BEL, 0x6B901122L, 0xFD987193L, 0xA679438EL, 0x49B40821L, 0xF61E2562L, 
   			0xC040B340L, 0x265E5A51L, 0xE9B6C7AAL, 0xD62F105DL, 0x02441453L, 0xD8A1E681L, 
   			0xE7D3FBC8L, 0x21E1CDE6L, 0xC33707D6L, 0xF4D50D87L, 0x455A14EDL, 0xA9E3E905L, 
   			0xFCEFA3F8L, 0x676F02D9L, 0x8D2A4C8AL, 0xFFFA3942L, 0x8771F681L, 0x6D9D6122L, 
   			0xFDE5380CL, 0xA4BEEA44L, 0x4BDECFA9L, 0xF6BB4B60L, 0xBEBFBC70L, 0x289B7EC6L, 
   			0xEAA127FAL, 0xD4EF3085L, 0x04881D05L, 0xD9D4D039L, 0xE6DB99E5L, 0x1FA27CF8L, 
   			0xC4AC5665L, 0xF4292244L, 0x432AFF97L, 0xAB9423A7L, 0xFC93A039L, 0x655B59C3L, 
   			0x8F0CCC92L, 0xFFEFF47DL, 0x85845DD1L, 0x6FA87E4FL, 0xFE2CE6E0L, 0xA3014314L, 
   			0x4E0811A1L, 0xF7537E82L, 0xBD3AF235L, 0x2AD7D2BBL, 0xEB86D391L};
	    
   	
   	
	 public static void main(String[] args) {
	 
	    System.out.println(new MD5().md5Algorithm("someRand0mT3xt"));
	    System.out.println(add0sAheadIfNotMultipleOf8("adding zeros if needed"));
	 }
	 
	 
	 
	 String md5Algorithm(String initialMessage) {
		
		formatMessage(initialMessage);
			  
		execute4RoundsOf16Operations();
		  
	    finalOperationsOnVectors();
	     
	    concatenateVectors(); 
		 
	    return hashedMessage;
	 }
	
	 

	private void formatMessage(String initialMessage) {
		
		byte[] messageAsByteArray = initialMessage.getBytes();
		 
		Pad pad = new Pad(messageAsByteArray);
		
		preparedMessage =  pad.padMessageToGetTo64Bytes();
	}
	
	
	
	private void execute4RoundsOf16Operations() {
		
		vectors = new Long[]{0x67452301L, 0xefcdab89L, 0x98badcfeL, 0x10325476L};
		 
	    kConstantPosition = shiftValuePosition = messageBytePosition = 0;
	   	
	    for(roundNumber = 0; roundNumber < 4; roundNumber++) {
	    	execute16Operations();
	    }
	}
	
	
	
	private void execute16Operations() {
        
		for(int operationNumber = 0; operationNumber < 16 ; operationNumber++){
			  
			  switch(roundNumber){
				  case 0 : 
					  messageBytePosition =  operationNumber == 0 ? 0 : messageBytePosition + 1; 
					  break;
				  case 1 : 
					  messageBytePosition =  operationNumber == 0 ? 1 : (messageBytePosition + 5) % 16 ; 
					  break;
				  case 2 : 
					  messageBytePosition =  operationNumber == 0 ? 5 : (messageBytePosition + 3) % 16 ; 
					  break;
				  case 3 : 
					  messageBytePosition =  operationNumber == 0 ? 0 : (messageBytePosition + 7) % 16 ; 
					  break;
			  }
		  
            functionResult = executeFunctionFGHorI();
            
            operationsOnVector0(functionResult);
            
        }
	}

	
	
    Long executeFunctionFGHorI(){
    	
        Long result = 0L;
        
        switch (roundNumber){
            case 0: result = functionF();
                    break;
            case 1: result = functionG();
                    break;
            case 2: result = functionH();
                    break;
            case 3: result = functionI();
                    break;
            default: System.out.println("Issue in round switch");
                    break;
        }
        return result;
    }
	
	
    
    void operationsOnVector0(Long functionResult){
    	
    	addVector0();
    	
    	addAMessageByte();
    	
    	addKConstant();
    	
    	shiftLeft();
    	
    	addVector1();
		
        shiftVectors();
    	
    }
    
    
	private void addVector0() {
		vectors[0] = modularAddition(vectors[0], functionResult);
	}
	

	private void addAMessageByte() {
		vectors[0] = modularAddition(vectors[0], preparedMessage[messageBytePosition]);
	}
	
	
	private void addKConstant() {
		vectors[0] = modularAddition(vectors[0], kConstants[kConstantPosition++]);
	}
	
	
	private void shiftLeft() {
		vectors[0] = shiftNBitsToTheLeft(vectors[0], shiftValues[shiftValuePosition++]);
	}
	
	
	private void addVector1() {
		vectors[0] = modularAddition(vectors[0], vectors[1]);
	}


	static String toBinaryString(String s) {
		return new BigInteger(s.getBytes()).toString(2);
	}
	
	
	static String lengthInBits(String s) {
		return Integer.toBinaryString(s.length());
	}
	
	
	static Long functionF(){
        return ((vectors[1] & vectors[2])|((~vectors[1])&vectors[3]));
    }

	
    static Long functionG(){
        return (vectors[1]&vectors[3])|(vectors[2]&(~vectors[3]));
    }

    
    static Long functionH(){
        return vectors[1]^vectors[2]^vectors[3];
    }

    
    static Long functionI(){
		  return vectors[2]^(vectors[1]|(~vectors[3] - rest));
    }
    
    
    static Long modularAddition(Long a, Long b){
        return (a + b) % 0x100000000L;
    }
    
    
    static Long shiftNBitsToTheLeft(Long number, int n){
       
    	String numberInBits = Long.toBinaryString(number);
		
    	numberInBits = add0sAheadIfNotMultipleOf8(numberInBits);
        
        for(int i = 0 ; i < n ; i++){
            numberInBits = numberInBits.substring(1, numberInBits.length()) + numberInBits.charAt(0);
        }
        return Long.parseUnsignedLong(numberInBits, 2);
    }


    
    static String add0sAheadIfNotMultipleOf8(String s) {
    	
    	if(s.length() % 8 != 0){
            for(int i = 0 ; i < s.length() % 8; i++ ){
                s = 0 + s;
            }
        }
    	
    	return s;
    }
    
    
    
    static void shiftVectors(){
        Long buff = vectors[0];
        vectors[0] = vectors[3];
        vectors[3] = vectors[2];
        vectors[2] = vectors[1];
        vectors[1] = buff;
    }
    
    
    private void finalOperationsOnVectors() {
    	for(int i = 0; i < 4 ; i++){
			   vectors[i] = modularAddition(vectors[i], initialVectors[i]);
			   vectors[i] = ((vectors[i] & 0xFF) << 24) + ((vectors[i] & 0xFF00) << 8) + ((vectors[i] & 0xFF0000) >> 8) + ((vectors[i] & 0xFF000000) >>24);
	     }
    	
    }
    
    
    private void concatenateVectors() {
    	hashedMessage = "";
    	for(int i = 0; i < 4 ; i++){
			hashedMessage += String.format("%08x" , vectors[i]);
	     }
    }
    
}



class Pad {
	
	private byte[] paddingBytes;
	
	private int numberOfBytesInMessage;
	
	byte[] array;
	
	
	Pad(byte[] array){
		this.array = array;
	}
	
	
	Long[] padMessageToGetTo64Bytes() {
		
		numberOfBytesInMessage = array.length;

		paddingBytes = arrayOf64MinusMessageLength();
		
		setFirstBitTo1();
	    
	    fillLast8BytesWithMessageLength();
	    
	    return appendPaddingBitsToMessage();
	}
	
	
	private byte[] arrayOf64MinusMessageLength(){
		return new byte[64 - numberOfBytesInMessage];
	}
	
	
	private void setFirstBitTo1() {
		paddingBytes[0] = (byte)0x80; // the first padding bit is always "1" (0x80 = 0b10000000)
	}
	
	
	private void fillLast8BytesWithMessageLength(){
		
		int numberOfBitsInMessage = numberOfBytesInMessage << 3; 
		
		for (int i = 0; i < 8; i++){
			paddingBytes[paddingBytes.length - 8 + i] = (byte)numberOfBitsInMessage;
		    numberOfBitsInMessage >>>= 8; 
	    }
	}

	
	private Long[] appendPaddingBitsToMessage() {
		
		Long[] paddedMessage = new Long[16];
		int[] buffer = new int[16];
		
	    for (int j = 0; j < 64; j++) {
				buffer[j >>> 2] = ((int)((j < numberOfBytesInMessage) ? array[j] : paddingBytes[j - numberOfBytesInMessage]) << 24) | (buffer[j >>> 2] >>> 8);
				paddedMessage[j >>> 2] = (long)buffer[j >>> 2];				
	    }
	    return paddedMessage;
	}
}