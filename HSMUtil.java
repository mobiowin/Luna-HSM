import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.Provider;

import java.security.Key;


class HSMUtil 
{
	public static void main(String[] args) 
	{
		System.out.println(""+HSMUtil.getPrivateKey());
	}

	public static Key getPrivateKey()
	{
		KeyStore myStore = null;
		String userpin = "[Your HSM Partition Password]";
		String privateKeyAlias = "[Digital Certificate Private Key alias]";

		Provider provider = new com.safenetinc.luna.provider.LunaProvider();
		System.out.println("Provider is : " + provider);
		try 
		{
			ByteArrayInputStream is1 = new ByteArrayInputStream(("slot:2").getBytes());
			Security.addProvider(provider);
			myStore = KeyStore.getInstance("Luna");

			System.out.println("myStore  is : " + myStore);
			myStore.load(is1, userpin.toCharArray());
			System.out.println("<---------->" + myStore);
			PrivateKey privateKey = (PrivateKey) myStore.getKey(privateKeyAlias, userpin.toCharArray());
			System.out.println("Private key is : " + privateKey);

			return privateKey;
		}
		
		catch (KeyStoreException kse) 
		{
			System.out.println("Unable to create keystore object");
			return null;
			//System.exit(-1);
		}
		catch (NoSuchAlgorithmException nsae) 
		{
			System.out.println("Unexpected NoSuchAlgorithmException while loading keystore");
			return null;
			//System.exit(-1);
		}
		catch (CertificateException e) 
		{
			System.out.println("Unexpected CertificateException while loading keystore");
			return null;
			//System.exit(-1);
		}
		catch (IOException ex1) 
		{
			System.out.println("Unexpected IOException while loading keystore.");
			return null;
			//System.exit(-1);
		}
		catch (Exception ex2) 
		{
			System.out.println("Unexpected IOException while loading keystore.");
			return null;
			//System.exit(-1);
		}
		
		/*
		    To check all the security providers
		*/
		
  		Provider[] providers = Security.getProviders();
  		System.out.println("Provider list");
  		for (int i = 0; i < providers.length; i++)
  		{
  			System.out.println((i + 1) + ":" + providers[i].toString());
  		}
		
	}
}

	
