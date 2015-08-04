package openthinks.crypto;

import java.security.Key;
import java.util.Arrays;
/**
 * AES encrypt/decrypt 
 * secret key length:	128bit
 * mode: ECB
 * padding:	PKCS5Padding
 * @author minjdai
 * @date 2015/06/16
 *
 */
public class DAESer {
	private Key key;
	public DAESer() {
	}
	public DAESer(Key key) {
		super();
		this.key = key;
	}
	
	public DAESer(String key) {
		super();
		setKey(key);
	}
	
	public void setKey(String key){
		byte[] wrapperKey = KeyWrapper.getInstance().valueOf(key);
		this.key = AESCoder.toKey(wrapperKey);
	}
	public byte[] encrypt(byte[] data) throws Exception{
		return AESCoder.encrypt(data, this.key);
	}
	public byte[] decrypt(byte[] data) throws Exception{
		return AESCoder.decrypt(data, this.key);
	}
	
	
	/**
	 * original string key wrapper to make sure the length of original key is 128bit 
	 * @author minjdai
	 *
	 */
	static class KeyWrapper{
		private static final byte[] KEY_PADDING = "OPENTHINKSDOTXYZ".getBytes();
		private static final KeyWrapper DEFAULT_INSTANCE = new KeyWrapper(KEY_PADDING);
		public static final KeyWrapper getInstance(){
			return DEFAULT_INSTANCE;
		}
		public static final KeyWrapper getInstance(byte[] keyPadding){
			return new KeyWrapper(keyPadding);
		}
		
		private static final int BYTE_ARRAY_LENGTH = 16;
		private byte[] keyPaddings;
		protected KeyWrapper( byte[] keyPaddings) {
			super();
			this.keyPaddings = keyPaddings;
			check();
		}
		
		private void check() {
			if(this.keyPaddings==null || this.keyPaddings.length==0){
				this.keyPaddings = KEY_PADDING;
			}else if(this.keyPaddings.length <= BYTE_ARRAY_LENGTH){//128bit = 16*8 = 16Byte
				byte[] finalKeyPaddings = Arrays.copyOf(this.keyPaddings, BYTE_ARRAY_LENGTH);
				this.keyPaddings=finalKeyPaddings;
			}
		}
		public byte[] valueOf(byte[] originalKey){
			if(originalKey==null || originalKey.length==0 ) throw new IllegalArgumentException();
			final int originalLength = originalKey.length;
			if(originalLength >=BYTE_ARRAY_LENGTH){// ignore greater 16
				return Arrays.copyOf(originalKey, BYTE_ARRAY_LENGTH);
			}else{
				byte[] finalKey = Arrays.copyOf(originalKey, BYTE_ARRAY_LENGTH);
				for(int i= originalLength-1,j=0;i<BYTE_ARRAY_LENGTH ;i++,j++){
					finalKey[i]=this.keyPaddings[j];
				}
				return finalKey;
			}
		}
		public byte[] valueOf(String originalKey){
			return valueOf(originalKey.getBytes());
		}
		
	}
}
