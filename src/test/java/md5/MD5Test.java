package md5;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class MD5Test {
	
	MD5 md5;
	
	static Long[] vectorsTest = {0x67452301L, 0xefcdab89L, 0x98badcfeL, 0x10325476L};
	
	@BeforeEach
	void initializeObject() {
		md5 = new MD5();
	}
	
	
	@Test
	void globalTest() {
		assertEquals("0c9bf9863121cecd2a0cd560c27e3584", md5.md5Algorithm("Is it working?"));
		assertEquals("1335ca5e3bf42af7a5944f2ec0d23b55", md5.md5Algorithm("someRand0mT3xt"));
	}
	
	
	@Test
	void lengthInBitsTest() {
		assertEquals("10101", MD5.lengthInBits("isitthecorrectlength?") );
	}
	
	
	@Test
	void modularAdditionTest() {
		assertEquals(85798498,MD5.modularAddition(9871234L, 75927264L));
	}
	
	@Test
	void shiftNBitsToTheLeftTest(){
        assertEquals(1060190395392L,MD5.shiftNBitsToTheLeft(8282737464L, 7));
    }
	
	
	@Test
	void add0sAheadIfNotMultipleOf8Test(){
        assertEquals("00adding zeros if needed", MD5.add0sAheadIfNotMultipleOf8("adding zeros if needed"));
    }
	
	
	@Test
	@Disabled
	void functionHTest(){
        assertEquals(MD5.functionH(), 1732584193L);
    }
	
	
	@Test
	@Disabled
	void functionITest(){
        assertEquals(MD5.functionI(), 2004318071L);
    }
	
	
	@Test
	@Disabled
	void roundTest(){
        assertEquals(md5.executeFunctionFGHorI(), 1732584193L);
    }
	
	
	@Test
	void toBinaryTest() {
		assertEquals(MD5.toBinaryString("anotherTry"), "1100001011011100110111101110100011010000110010101110010010101000111001001111001" );
	}
}
