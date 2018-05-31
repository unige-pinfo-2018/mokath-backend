package ch.mokath.uniknowledgerestapi.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Base64;

/**
* @author ornela
* @author zue
*/
public class CustomDecoderTest {

	@Test
	public void EncodeDecodeTest(){
        String message = "This is_1-Test@message!";//new String("This is_1-Test@message!", "UTF-8");
//        byte[] byteMessage = CustomDecoder.fromBase64(message.getBytes("UTF_8"));
//        String decodedMessage = CustomDecoder.toBase64(byteMessage);
//        String decodedMessage = CustomDecoder.toBase64(message.getBytes("utf-8"));
//System.out.println("**** [TOTO] : " + message+" / "+decodedMessage);

//        Assert.assertTrue(decodedMessage.equals(message));
	}

}
